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
import com.sv.gauge.model.DomainStepObject;

import java.util.Arrays;
import java.util.List;

public class DomainSelectActivity extends AppCompatActivity {
    //select type measure or reports
    public static final String SELECT_TYPE_KEY = "SELECT_TYPE";
    public static final String SELECT_TYPE_MEASURE = "MEASURE";
    public static final String SELECT_TYPE_REPORTS = "REPORTS";
    private String selectType;

    //componenets
    private ListView lstDomain;
    private TextView txtHeaderText;
    private Button btnGeneralBack;

    //database
    private GaugeDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domain_select);

        //db helper
        this.databaseHelper = new GaugeDatabaseHelper(this);

        //components
        this.txtHeaderText = (TextView) findViewById(R.id.txt_header_title);
        this.btnGeneralBack = (Button) findViewById(R.id.btn_general_back);
        this.lstDomain = (ListView) findViewById(R.id.lst_domain);
        this.lstDomain.setAdapter(new DomainListAdapter(this, getListItems()));

        //select type
        this.selectType = getIntent().getStringExtra(SELECT_TYPE_KEY);
        switch (selectType) {
            case SELECT_TYPE_MEASURE:
                //start measuring
                this.txtHeaderText.setText(R.string.start_measurement);
                break;
            case SELECT_TYPE_REPORTS:
                //start view reports
                this.txtHeaderText.setText(R.string.view_reports);
                break;
        }

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
                DomainObject domainObject = (DomainObject) lstDomain.getItemAtPosition(position);
                attemptDomain(domainObject);
            }
        });
    }

    /**
     * @return measurement domain list
     */
    private List<DomainObject> getListItems() {
        //return measurement domains here
        return databaseHelper.getDomains();
    }

    private void attemptDomain(DomainObject domainObject) {
        if (domainObject.getFirstStep() == -1) {
            return;
        }

        Intent intent;
        switch (selectType) {
            case SELECT_TYPE_MEASURE:
                //start measuring
                intent = new Intent(this, DomainStepActivity.class);
                intent.putExtra(DomainStepActivity.DOMAIN_ID_KEY, domainObject.getId());
                intent.putExtra(DomainStepActivity.DOMAIN_NAME_KEY, domainObject.getTitle());
                intent.putExtra(DomainStepActivity.FIRST_STEP_ID_KEY, domainObject.getFirstStep());
                intent.putExtra(DomainStepActivity.DOMAIN_REPORT_DESCRIPTION_KRY, domainObject.getReportDescription());
                intent.putExtra(DomainStepActivity.IS_RESUME_KEY, false);
                startActivity(intent);
                finish();
                break;
            case SELECT_TYPE_REPORTS:
                //start view reports
                intent = new Intent(this, TransactionReportListActivity.class);
                intent.putExtra(TransactionReportListActivity.DOMAIN_ID, domainObject.getId());
                intent.putExtra(TransactionReportListActivity.DOMAIN_NAME, domainObject.getTitle());
                startActivity(intent);
                break;
        }
    }


    /**
     * ListAdapter for domain list
     */
    public class DomainListAdapter extends ArrayAdapter<DomainObject> {
        private Context context;
        private List<DomainObject> domainObjects;

        public DomainListAdapter(Context context, List<DomainObject> domainObjects) {
            super(context, R.layout.layout_domain_list_item, domainObjects);
            this.context = context;
            this.domainObjects = domainObjects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //create view
            View listRowView = inflater.inflate(R.layout.layout_domain_list_item, parent, false);
            TextView lblTitle = (TextView) listRowView.findViewById(R.id.lbl_list_item_title);
            TextView lblDescription = (TextView) listRowView.findViewById(R.id.lbl_list_item_description);

            //set data
            final DomainObject domainObject = domainObjects.get(position);
            lblTitle.setText(domainObject.getTitle());
            lblDescription.setText(domainObject.getDescription());

            return listRowView;
        }
    }
}
