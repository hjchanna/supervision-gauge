package com.sv.gauge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

public class MapActivity extends AppCompatActivity {
    public static final String HEADER_TITLE_KEY = "HEADER_TITLE";
    public static final String MAP_LINK_KEY = "MAP_LINK";

    //
    private static final String MAP_FOLDER = "/sdcard/gauge/map/";
    private static final String MAP_EXTENSION = ".gif";

    private TextView txtHeaderTitle;
    private ImageView imgMap;
    private Button btnGeneralBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //componenets
        this.txtHeaderTitle = (TextView) findViewById(R.id.txt_header_title);
        this.imgMap = (ImageView) findViewById(R.id.img_map);
        this.btnGeneralBack = (Button) findViewById(R.id.btn_general_back);

        //view modifications
        Intent intent = getIntent();
        String title = intent.getStringExtra(HEADER_TITLE_KEY);
        this.txtHeaderTitle.setText(title);

        String mapLink= intent.getStringExtra(MAP_LINK_KEY);
        mapLink = MAP_FOLDER + mapLink + MAP_EXTENSION;
        Ion.with(imgMap).load(mapLink);

        //actions
        this.btnGeneralBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
                finish();
    }
}
