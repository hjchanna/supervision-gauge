package com.sv.gauge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Test extends AppCompatActivity {
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        this.videoView = (VideoView) findViewById(R.id.video_view);
        this.videoView.setVideoPath("/sdcard/gauge/media/29_generator_01_battery_voltage.mp4");
        this.videoView.setMediaController(new MediaController(this));
        this.videoView.start();
    }
}
