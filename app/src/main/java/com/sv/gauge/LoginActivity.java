package com.sv.gauge;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sv.gauge.controller.AppEnvironmentValues;
import com.sv.gauge.controller.GaugeDatabaseHelper;
import com.sv.gauge.model.UserObject;
import com.sv.gauge.server_model.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private boolean processing;
    //
    private GaugeDatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //database
        this.databaseHelper = new GaugeDatabaseHelper(this);

        //components
        this.txtUsername = (EditText) findViewById(R.id.txt_username);
        this.txtPassword = (EditText) findViewById(R.id.txt_password);
        this.btnLogin = (Button) findViewById(R.id.btn_login);

        //actions
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        this.txtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                attemptLogin();
                return true;
            }
        });
    }

    /**
     * Try to login
     */
    private void attemptLogin() {
//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.coin_flip);
//        btnLogin.startAnimation(animation);

        if (processing) {
            return;
        }

        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        //validation
        View focusView = null;
        if (TextUtils.isEmpty(username)) {
            focusView = txtUsername;
            txtUsername.setError(getString(R.string.username_error));
        } else if (TextUtils.isEmpty(password)) {
            focusView = txtPassword;
            txtPassword.setError(getString(R.string.password_error));
        }

        if (focusView != null) {   //validation error has occured
            focusView.requestFocus();
        } else {  //no validation errors
            //execute login task
            LoginTask loginTask = new LoginTask(username, password);
            loginTask.execute((Void) null);
        }
    }

    private UserObject login(String username, String password) {
        //check username and password from database
        return databaseHelper.getUserObjectLogin(username, password);
    }

    /**
     * show login progress bar
     *
     * @param progress progressbar visibility status
     */
    private void showProgress(boolean progress) {
        //hide keyboard before show progress
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);

        processing = progress;


        if (progress) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.coin_flip_infinete);
            btnLogin.startAnimation(animation);
        } else {
            btnLogin.clearAnimation();
        }

        //show progress
//        this.progressBar.setVisibility(progress ? View.VISIBLE : View.GONE);
//        this.loginView.setVisibility(progress ? View.GONE : View.VISIBLE);
//        ObjectAnimator animation = ObjectAnimator.ofFloat(btnLogin,"rotationY",0.0f,360.0f);
//        RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(3600);
//        animation.setRepeatCount(ObjectAnimator.INFINITE);
//        animation.setInterpolator(new AccelerateDecelerateInterpolator());
//        animation.start();
    }

    /**
     * Login AsyncTask
     */
    public class LoginTask extends AsyncTask<Void, Void, Boolean> {
        private String username;
        private String password;

        public LoginTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                return false;
//            }

            //sync with server
            String urlString = AppEnvironmentValues.SERVER_ADDRESS + "/rest/user-list";
            try {
                URL url = new URL(urlString);
//                URLConnection urlConnection = url.openConnection();
//
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//                urlConnection.setUseCaches(false);
//                urlConnection.setDoInput(false);
//                urlConnection.setDoOutput(true);

//                urlConnection.getE

//                System.out.println(urlConnection.getResponseCode());
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String usersJSON = response.toString();
                Log.d("USER GSON", usersJSON);

                //
                Gson gson = new Gson();
                User[] users = gson.fromJson(usersJSON, User[].class);
                for (User user : users) {
                    Log.d("USER", user.toString());
                }

                databaseHelper.syncUsers(Arrays.asList(users));
            } catch (Exception e) {
                Log.e("USERS SYNC", e.getMessage());
                e.printStackTrace();
            }


            UserObject userObject = login(username, password);

            //add userObject environment variable
            if (userObject != null) {
                AppEnvironmentValues.USER_ID = userObject.getId();
                AppEnvironmentValues.USER_NAME = userObject.getName();
            }

            return userObject != null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            showProgress(false);

            if (result) {//login success
                //open main activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {//login failed
                //show alert dialog
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                        .setMessage(getString(R.string.login_failed_message))
                        .setTitle(getString(R.string.login_failed))
                        .setPositiveButton(getString(R.string.ok), null)
                        .setCancelable(false)
                        .create();
                alertDialog.show();

                //reset text fields
                txtUsername.setText(null);
                txtPassword.setText(null);
            }
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }
    }
}
