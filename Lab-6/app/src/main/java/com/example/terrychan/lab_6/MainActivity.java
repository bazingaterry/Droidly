package com.example.terrychan.lab_6;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Handler;

import static com.example.terrychan.lab_6.State.idle;
import static com.example.terrychan.lab_6.State.paused;
import static com.example.terrychan.lab_6.State.playing;
import static com.example.terrychan.lab_6.State.prepared;

enum State {idle, prepared, playing, paused}

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView state;
    Chronometer currentTime, totalTime;
    SeekBar seekBar;
    Button playBtn, stopBtn, quitBtn;
    ServiceConnection connection;
    MainService.MusicPlayerBinder musicPlayerBinder;
    MediaPlayer mediaPlayer;
    SimpleDateFormat simpleDateFormat;
    Handler handler;
    ObjectAnimator objectAnimator;
    State playerState;

    protected void getView() {
        imageView = (ImageView) findViewById(R.id.image);
        state = (TextView) findViewById(R.id.musicState);
        currentTime = (Chronometer) findViewById(R.id.currentTime);
        totalTime = (Chronometer) findViewById(R.id.totalTime);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        playBtn = (Button) findViewById(R.id.playBtn);
        stopBtn = (Button) findViewById(R.id.stopBtn);
        quitBtn = (Button) findViewById(R.id.quitBtn);
    }

    protected void init() {
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
                musicPlayerBinder = (MainService.MusicPlayerBinder) iBinder;
                mediaPlayer = musicPlayerBinder.mediaPlayer;
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        state.setVisibility(View.INVISIBLE);
                        playBtn.setText("PLAY");
                        currentTime.setText(simpleDateFormat.format(0));
                        totalTime.setText(simpleDateFormat.format(0));
                        seekBar.setProgress(0);
                        mediaPlayer.reset();
                        playerState = idle;
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        playerState = idle;
        Intent intent = new Intent(this, MainService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0, 360);
        objectAnimator.setDuration(20000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.CHINA);
    }

    protected void setViewsListeners() {
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("onClick", playerState.name());
                if (playerState == idle) {
                    try {
                        mediaPlayer.setDataSource("/storage/emulated/0/Music/my little airport - 土瓜湾情歌.mp3");
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                    playerState = playing;
                    state.setVisibility(View.INVISIBLE);
                    playBtn.setText("PAUSE");
                    totalTime.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
                    seekBar.setMax(mediaPlayer.getDuration());
                    objectAnimator.start();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (playerState == playing) {
                                handler.sendEmptyMessage(mediaPlayer.getCurrentPosition());
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                } else if (playerState == playing) {
                    mediaPlayer.pause();
                    playBtn.setText("PLAY");
                    objectAnimator.pause();
                    playerState = paused;
                } else if (playerState == prepared) {
                    mediaPlayer.start();
                    playerState = playing;
                    state.setVisibility(View.INVISIBLE);
                    playBtn.setText("PAUSE");
                    totalTime.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
                    objectAnimator.start();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (playerState == playing) {
                                handler.sendEmptyMessage(mediaPlayer.getCurrentPosition());
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                } else if (playerState == paused) {
                    mediaPlayer.start();
                    playBtn.setText("PAUSE");
                    objectAnimator.resume();
                    playerState = playing;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (playerState == playing) {
                                handler.sendEmptyMessage(mediaPlayer.getCurrentPosition());
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerState != idle) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
                playerState = idle;
                objectAnimator.end();
                state.setVisibility(View.INVISIBLE);
                playBtn.setText("PLAY");
                currentTime.setText(simpleDateFormat.format(0));
                totalTime.setText(simpleDateFormat.format(0));
                seekBar.setProgress(0);
            }
        });
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                playerState = idle;
                try {
                    MainActivity.this.finish();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        seekBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 29/10/2016
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();
        init();
        setViewsListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
