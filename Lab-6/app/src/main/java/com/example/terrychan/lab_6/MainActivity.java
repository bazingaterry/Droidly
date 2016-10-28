package com.example.terrychan.lab_6;


import android.content.ComponentName;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Handler;


public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView status;
    Chronometer currentTime, totalTime;
    SeekBar seekBar;
    Button playBtn, stopBtn, quitBtn;
    ServiceConnection connection;
    MainService.MyBinder myBinder;
    MediaPlayer mediaPlayer;
    SimpleDateFormat simpleDateFormat;
    Handler handler;

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

    protected void init() {
        simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.CHINA);
        currentTime.setText(simpleDateFormat.format(0));
        totalTime.setText(simpleDateFormat.format(0));
        seekBar.setProgress(0);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                seekBar.setProgress(msg.what);
                currentTime.setText(simpleDateFormat.format(msg.what));
            }
        };
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                myBinder = (MainService.MyBinder) iBinder;
                mediaPlayer = myBinder.mediaPlayer;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
    }

    protected void setListeners() {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                status.setVisibility(View.INVISIBLE);
                playBtn.setText("PLAY");
                currentTime.setText(simpleDateFormat.format(0));
                totalTime.setText(simpleDateFormat.format(0));
                seekBar.setProgress(0);
                mediaPlayer.reset();
            }
        });
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        playBtn.setText("PLAY");
                    } else {
                        mediaPlayer.setDataSource("/storage/emulated/0/Music/my little airport - 土瓜湾情歌.mp3");
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        playBtn.setText("PAUSE");
                        seekBar.setMax(mediaPlayer.getDuration());
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (mediaPlayer.isPlaying()) {
                                    handler.sendEmptyMessage(mediaPlayer.getCurrentPosition());
                                }
                            }
                        }).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();
        init();
        setListeners();
    }
}
