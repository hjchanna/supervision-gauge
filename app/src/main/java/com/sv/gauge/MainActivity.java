package com.sv.gauge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private Button btnSync;
    private Button btnReports;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //components
        this.btnStart = (Button) findViewById(R.id.btn_start);
        this.btnSync = (Button) findViewById(R.id.btn_sync);
        this.btnReports = (Button) findViewById(R.id.btn_reports);
        this.btnLogout = (Button) findViewById(R.id.btn_logout);

        //actions
        View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.equals(btnStart)) {
                    //start
                    attemptStart();
                } else if (v.equals(btnSync)) {
                    //sync
                    attemptSync();
                } else if (v.equals(btnReports)) {
                    //reports
                    attemptReports();
                } else if (v.equals(btnLogout)) {
                    //logout
                    attemptLogout();
                }
            }
        };

        btnStart.setOnClickListener(buttonOnClickListener);
        btnSync.setOnClickListener(buttonOnClickListener);
        btnReports.setOnClickListener(buttonOnClickListener);
        btnLogout.setOnClickListener(buttonOnClickListener);
    }

    /**
     * start
     */
    private void attemptStart() {
        coinFlip(btnStart);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, DomainSelectActivity.class);
                intent.putExtra(DomainSelectActivity.SELECT_TYPE_KEY, DomainSelectActivity.SELECT_TYPE_MEASURE);
                startActivity(intent);
            }
        };
        btnStart.postDelayed(runnable, 400);
    }

    /**
     * sync
     */
    private void attemptSync() {
        coinFlip(btnSync);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, SyncActivity.class);
                startActivity(intent);
            }
        };
        btnReports.postDelayed(runnable, 400);
    }

    /**
     * reports
     */
    private void attemptReports() {
        coinFlip(btnReports);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, DomainSelectActivity.class);
                intent.putExtra(DomainSelectActivity.SELECT_TYPE_KEY, DomainSelectActivity.SELECT_TYPE_REPORTS);
                startActivity(intent);
            }
        };
        btnReports.postDelayed(runnable,400);
    }

    /**
     * logout
     */
    private void attemptLogout() {
        coinFlip(btnLogout);

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.logout_confirmation)
                .setPositiveButton(R.string.yes, onClickListener)
                .setNegativeButton(R.string.no, null)
                .create();
        alertDialog.show();
    }

    private void coinFlip(View v) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.coin_flip);
        v.startAnimation(animation);
    }
}
