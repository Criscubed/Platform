package com.whatever.cris.platform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Game mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView view = new GameView(this);
        setContentView(view);
        mGame = new Game(this, view);

    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        mGame.onResume();

    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        mGame.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        mGame.onDestroy();
        super.onDestroy();
    }

}
