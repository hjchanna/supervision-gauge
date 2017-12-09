package com.sv.gauge;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sv.gauge.controller.AppEnvironmentValues;
import com.sv.gauge.controller.GaugeDatabaseHelper;
import com.sv.gauge.model.TransactionDetailObject;
import com.sv.gauge.model.TransactionObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionReportActivity extends AppCompatActivity {
    public static final String TRANSACTION_ID ="TRANSACTION_ID";
    //
    private TextView txtHeaderTitle;
    private TextView txtStartTime;
    private TextView txtEndTime;
    private TextView txtUser;
    private ListView lstTransactionDetail;
    private Button btnGeneralBack;
    //database helper
    private GaugeDatabaseHelper databaseHelper;
    //attributes
    private int transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_report);

        //database
        this.databaseHelper = new GaugeDatabaseHelper(this);

        //parameters
        this.transactionId = getIntent().getIntExtra(TRANSACTION_ID,-1);

        //components
        this.txtHeaderTitle = (TextView) findViewById(R.id.txt_header_title);
        this.txtStartTime = (TextView) findViewById(R.id.txt_start_time);
        this.txtEndTime = (TextView) findViewById(R.id.txt_end_time);
        this.txtUser = (TextView) findViewById(R.id.txt_user);
        this.lstTransactionDetail = (ListView) findViewById(R.id.lst_transaction_detail);

        //action
        this.btnGeneralBack = (Button) findViewById(R.id.btn_general_back);
        this.btnGeneralBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        attemptReport();
    }

    private void attemptReport() {
        //transaction
        TransactionObject transactionObject = getTransactionObject(transactionId);

        Log.d("DOMAIN NAME", String.valueOf(transactionObject));

        this.txtHeaderTitle.setText(transactionObject.getDomainName().toUpperCase());
        this.txtStartTime.setText(getString(R.string.start_time) + " : " + transactionObject.getStartTime());
        this.txtEndTime.setText(getString(R.string.end_time) + " : " + transactionObject.getEndTime());
        this.txtUser.setText(getString(R.string.user) + " : " + transactionObject.getUserName());

        //transaction detail
        List<TransactionDetailObject> transactionDetailObjects = getTransactionDetails(transactionId);
        Log.d("DETAIL SIZE", String.valueOf(transactionDetailObjects.size()));
        this.lstTransactionDetail.setAdapter(new TransactionListAdapter(this, transactionDetailObjects));
    }

    private TransactionObject getTransactionObject(int transactionId) {
        //get transaction info from database
        TransactionObject transactionObject = databaseHelper.getTransactionObject(transactionId);

        return transactionObject;
    }

    private List<TransactionDetailObject> getTransactionDetails(int transactionId) {
        //get transaction detail info from database
        return databaseHelper.getTransactionDetails(transactionId);
    }


    /**
     * ListAdapter for domain list
     */
    public class TransactionListAdapter extends ArrayAdapter<TransactionDetailObject> {
        private Context context;
        private List<TransactionDetailObject> domainObjects;

        public TransactionListAdapter(Context context, List<TransactionDetailObject> domainObjects) {
            super(context, R.layout.layout_transaction_report_list_item, domainObjects);
            this.context = context;
            this.domainObjects = domainObjects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //create view
            View listRowView = inflater.inflate(R.layout.layout_transaction_report_list_item, parent, false);
            TextView txtDescription = (TextView) listRowView.findViewById(R.id.txt_transaction_description);
            TextView txtValue = (TextView) listRowView.findViewById(R.id.txt_transaction_value);
            TextView txtComment= (TextView) listRowView.findViewById(R.id.txt_transaction_comment);
            ImageView imgWarning = (ImageView) listRowView.findViewById(R.id.img_warning);

            //set data
            TransactionDetailObject domainObject = domainObjects.get(position);
            txtDescription.setText(domainObject.getDescription());
            txtValue.setText(domainObject.getValue());
            txtComment.setText(domainObject.getComment());
            if (domainObject.isWarning()) {
                imgWarning.setVisibility(View.VISIBLE);
            } else {
                imgWarning.setVisibility(View.GONE);
            }

            return listRowView;
        }
    }
}
