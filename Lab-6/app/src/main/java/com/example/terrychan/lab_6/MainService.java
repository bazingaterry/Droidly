package com.example.terrychan.lab_6;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by terrychan on 28/10/2016.
 */

public class MainService extends Service {

    class MusicPlayerBinder extends Binder {
        public MediaPlayer mediaPlayer;

        public MusicPlayerBinder(MediaPlayer mediaPlayer) {
            this.mediaPlayer = mediaPlayer;
        }
    }

    public MediaPlayer mediaPlayer;

    public MainService() {
        mediaPlayer = new MediaPlayer();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicPlayerBinder(mediaPlayer);
    }
}