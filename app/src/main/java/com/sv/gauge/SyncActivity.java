package com.sv.gauge;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.gson.Gson;
import com.sv.gauge.controller.AppEnvironmentValues;
import com.sv.gauge.controller.GaugeDatabaseHelper;
import com.sv.gauge.model.TransactionObject;
import com.sv.gauge.server_model.Transaction;
import com.sv.gauge.server_model.TransactionContainer;
import com.sv.gauge.server_model.TransactionDetail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SyncActivity extends AppCompatActivity {
    private GaugeDatabaseHelper databaseHelper;
    private Button btnSync;
    private Button btnBack;
    private boolean processing;
    private boolean error;
    private String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);


        this.databaseHelper = new GaugeDatabaseHelper(this);
        this.btnSync = (Button) findViewById(R.id.btn_sync);
        this.btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSync();
            }
        });

        this.btnBack = (Button) findViewById(R.id.btn_general_back);
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showProgress(boolean progress) {
        processing = progress;
        if (progress) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.coin_flip_infinete);
            btnSync.startAnimation(animation);
        } else {
            btnSync.clearAnimation();
        }
    }

    private void attemptSync() {
        if (processing) {
            return;
        }

        SyncTask syncTask = new SyncTask();
        syncTask.execute((Void) null);
    }

    private void sync() throws IOException {
        List<Transaction> transactions = databaseHelper.getTransactionsSync();

        Log.d("SYNC", transactions.size() +" of transactions");
        for (Transaction transaction : transactions) {
            List<TransactionDetail> transactionDetails = databaseHelper.getTransactionDetailsSync(transaction.getId());

            TransactionContainer transactionContainer = new TransactionContainer();
            transactionContainer.setTransactionObject(transaction);
            transactionContainer.setTransactionDetailObjects(transactionDetails);

            //convert to json
            Gson gson = new Gson();
            String str = gson.toJson(transactionContainer);
            Log.d("GSON", str);

            //send request
            String urlString = AppEnvironmentValues.SERVER_ADDRESS + "/rest/transaction-upload";

            try {
                //url
                URL url = new URL(urlString);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                //content
                byte[] outputInBytes = str.getBytes("UTF-8");
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(outputInBytes);
                os.close();

                //respond
                InputStream response = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response));

                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                Integer serverId = Integer.parseInt(stringBuilder.toString());

                Log.d("SYNC ID", serverId.toString());
                if (serverId > 0) {//server update success
                    //update transaction as synced
                    databaseHelper.updateTransactionStatus(transaction.getId(), transaction.getEndTime(), TransactionObject.TRANSACTION_STATUS_SYNC);
                }
            } catch (NumberFormatException e) {
                Log.e("ERROR", e.toString());
            }
        }
    }

    public class SyncTask extends AsyncTask<Void, Void, Void> {

        public SyncTask() {
        }

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                sync();
                error = false;
                message = "Database synchronized successfully.";

                Log.d("SYNC", "DONE");

            } catch (IOException e) {
                e.printStackTrace();
                error = true;
                message = e.getMessage();

                Log.e("ERROR", message);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            showProgress(false);

            if (error) {
                //error message
                AlertDialog alertDialog = new AlertDialog.Builder(SyncActivity.this)
                        .setTitle(R.string.error)
                        .setMessage(message)
                        .setPositiveButton(R.string.ok, null)
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_warning)
                        .create();
                alertDialog.show();
            } else {
                //success message
                AlertDialog alertDialog = new AlertDialog.Builder(SyncActivity.this)
                        .setTitle(R.string.success)
                        .setMessage(message)
                        .setPositiveButton(R.string.ok, null)
                        .setCancelable(false)
                        .create();
                alertDialog.show();
            }
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }
    }
}