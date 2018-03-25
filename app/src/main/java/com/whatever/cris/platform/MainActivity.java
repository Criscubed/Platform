package com.whatever.cris.platform;


import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.whatever.cris.platform.Input.InputManager;
import com.whatever.cris.platform.Input.TouchController;
import com.whatever.cris.platform.Input.VirtualJoystick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Game mGame;
    public Point size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScreenWidthAndHeight();
        setContentView(R.layout.activity_main);
        GameView view = (GameView) findViewById(R.id.gameView);
        InputManager input = new VirtualJoystick(findViewById(R.id.virtual_joystick));
        mGame = new Game(this, view, input);

    }

    private void getScreenWidthAndHeight() {
        Point size = new Point();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        size.x = displayMetrics.widthPixels;
        size.y = displayMetrics.heightPixels;
        Log.d(TAG, "Screen Width:" + size.x + " Screen Height: " + size.y);
        this.size = size;
    }

    public Point retrieveWidthAndHeight(){
        return size;
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
