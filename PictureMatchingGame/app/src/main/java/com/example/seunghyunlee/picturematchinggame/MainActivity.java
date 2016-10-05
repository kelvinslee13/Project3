package com.example.seunghyunlee.picturematchinggame;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    MediaPlayer mplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mplayer = mplayer.create(MainActivity.this, R.raw.main);
        mplayer.setVolume(0.6f, 0.6f);
        mplayer.setLooping(true);
        mplayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mplayer.stop();
        mplayer.reset();
        mplayer.release();
    }

}
