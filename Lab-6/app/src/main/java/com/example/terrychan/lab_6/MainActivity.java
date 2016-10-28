package com.example.terrychan.lab_6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView status;
    Chronometer currentTime, totalTime;
    SeekBar seekBar;
    Button playBtn, stopBtn, quitBtn;

    protected void getView() {
        imageView = (ImageView) findViewById(R.id.image);
        status = (TextView) findViewById(R.id.musicStatus);
        currentTime = (Chronometer) findViewById(R.id.currentTime);
        totalTime = (Chronometer) findViewById(R.id.totalTime);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        playBtn = (Button) findViewById(R.id.playBtn);
        stopBtn = (Button) findViewById(R.id.stopBtn);
        quitBtn = (Button) findViewById(R.id.quitBtn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();
    }
}
