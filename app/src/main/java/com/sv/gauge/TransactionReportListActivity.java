package com.sv.gauge;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sv.gauge.controller.GaugeDatabaseHelper;
import com.sv.gauge.model.DomainObject;
import com.sv.gauge.model.TransactionObject;

import java.util.List;

public class TransactionReportListActivity extends AppCompatActivity {
    public static final String DOMAIN_ID = "DOMAIN_ID";
    public static final String DOMAIN_NAME = "DOMAIN_NAME";

    //componenets
    private ListView lstDomain;
    private TextView txtHeaderText;
    private Button btnGeneralBack;

    //attributes
    private int domainId;
    private String domainName;

    //database
    private GaugeDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_report_list);

        //db helper
        this.databaseHelper = new GaugeDatabaseHelper(this);

        //
        domainId = getIntent().getIntExtra(DOMAIN_ID, -1);
        domainName = getIntent().getStringExtra(DOMAIN_NAME);

        //components
        this.txtHeaderText = (TextView) findViewById(R.id.txt_header_title);
        this.btnGeneralBack = (Button) findViewById(R.id.btn_general_back);
        this.lstDomain = (ListView) findViewById(R.id.lst_domain);
        this.lstDomain.setAdapter(new ReportListAdapter(this, getListItems(domainId)));

        //actions
        this.btnGeneralBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        this.lstDomain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TransactionObject transactionObject = (TransactionObject) lstDomain.getItemAtPosition(position);
                attemptDomain(transactionObject);
            }
        });

        this.txtHeaderText.setText(domainName);
    }

    /**
     * @return measurement domain list
     */
    private List<TransactionObject> getListItems(int domainId) {
        //return measurement domains here
        return databaseHelper.getTransactionObjects(domainId);
    }

    private void attemptDomain(TransactionObject transactionObject) {
        Intent intent;

        //start view reports
        intent = new Intent(this, TransactionReportActivity.class);
        intent.putExtra(TransactionReportActivity.TRANSACTION_ID,transactionObject.getId());
        startActivity(intent);
    }

    /**
     * ListAdapter for domain list
     */
    public class ReportListAdapter extends ArrayAdapter<TransactionObject> {
        private Context context;
        private List<TransactionObject> transactionObjects;

        public ReportListAdapter(Context context, List<TransactionObject> domainObjects) {
            super(context, R.layout.layout_transaction_report_list_item, domainObjects);
            this.context = context;
            this.transactionObjects = domainObjects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //create view
            View listRowView = inflater.inflate(R.layout.layout_domain_list_item, parent, false);
            TextView lblTitle = (TextView) listRowView.findViewById(R.id.lbl_list_item_title);
            TextView lblDescription = (TextView) listRowView.findViewById(R.id.lbl_list_item_description);

            //set data
            final TransactionObject transactionObject = transactionObjects.get(position);
            lblTitle.setText(transactionObject.getDomainName());
            lblDescription.setText(transactionObject.getStartTime());

            return listRowView;
        }
    }
}
