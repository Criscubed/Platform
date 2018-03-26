package com.whatever.cris.platform;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.TextView;

import com.whatever.cris.platform.Entities.Coin;
import com.whatever.cris.platform.Entities.Entity;
import com.whatever.cris.platform.Input.InputManager;
import com.whatever.cris.platform.levels.LevelManager;

import java.util.ArrayList;


/**
 * Created by Cris on 3/24/2018.
 */

public class Game implements Runnable, SurfaceHolder.Callback {

    private static final String TAG = "Game";
    private static final float METERS_TO_SHOW_X = 16f;
    private static final float METERS_TO_SHOW_y = 0f;
    private static final float SCALE_FACTOR = 0.5f;
    private final int screenWidth;
    private final int screenHeight;

    private GameView mView;
    private MainActivity mainActivity;
    private Thread mGameThread;
    private volatile boolean mIsRunning = false;
    private LevelManager mLevel = null;
    private Viewport mCamera = null;
    private ArrayList<Entity> mVisibleEntities = new ArrayList<>();
    private InputManager mControls = null;
    PointF mCameraPos = new PointF(0f, 0f);
    private TextView mTextView;

    public Game(Context context, final GameView view, final InputManager inputs) {
        mView = view;
        mainActivity = (MainActivity) context;
        Point size;
        size = mainActivity.retrieveWidthAndHeight();
        screenWidth = (int)(size.x * SCALE_FACTOR);
        screenHeight = (int)(size.y * SCALE_FACTOR);
        //Log.d(TAG, "Screen Width:" + screenWidth + " Screen Height: " + screenHeight);
        mControls = inputs;
        Entity.mEngine = this;
        SurfaceHolder holder = mView.getHolder();
        holder.addCallback(this);
        holder.setFixedSize((int)(screenWidth),(int)(screenHeight));
        mCamera = new Viewport(screenWidth, screenHeight,
                METERS_TO_SHOW_X, METERS_TO_SHOW_y);
        loadLevel();
        Log.d(TAG, "Game Created!");
    }


    public Context getAppContext(){
        return mainActivity.getApplicationContext();
    }
    public InputManager getControls(){return mControls;}
    public float getWorldHeight(){
        return mLevel.mLevelHeight;
    }
    public float getWorldWidth(){
        return mLevel.mLevelWidth;
    }
    public float screenToWorldX(final float pixelDistance){
        return (float) pixelDistance / mCamera.getPixelsPerMeterX();
    }
    public float screenToWorldY(final float pixelDistance){
        return (float) pixelDistance / mCamera.getPixelsPerMeterY();
    }
    public int worldToScreenX(final float worldDistance){
        return (int) (worldDistance * mCamera.getPixelsPerMeterX());
    }
    public int worldToScreenY(final float worldDistance){
        return (int) (worldDistance * mCamera.getPixelsPerMeterY());
    }

    public static final long SECONDS_TO_NANOS = 1000000000;
    public static final float NANOS_TO_SECONDS = 1.0f/SECONDS_TO_NANOS;

    @Override
    public void run() {
        long lastFrame = System.nanoTime();
        while (mIsRunning){
            final float deltaTime = (System.nanoTime()-lastFrame) * NANOS_TO_SECONDS;
            lastFrame = System.nanoTime();
            update(deltaTime);
            render(mVisibleEntities, mCamera);
        }
    }

    private void input(){

    }
    private void update(final float deltaTime){
        mControls.update(deltaTime);
        mCamera.lookAt(mLevel.mPlayer.x(), mLevel.mPlayer.y());
        mLevel.update(deltaTime);
        buildVisibleSet();
        DebugTextRenderer.CAMERA_INFO = mCamera.toString();
        DebugTextRenderer.PLAYER_POSITION.x = mLevel.mPlayer.x;
        DebugTextRenderer.PLAYER_POSITION.y = mLevel.mPlayer.y;
        DebugTextRenderer.PLAYER_VEL.x = mLevel.mPlayer.mVelocity.x;
        DebugTextRenderer.PLAYER_VEL.y = mLevel.mPlayer.mVelocity.y;
        DebugTextRenderer.TOTAL_OBJECT_COUNT = mLevel.mEntities.size();
        DebugTextRenderer.VISIBLE_OBJECTS = mVisibleEntities.size();
        DebugTextRenderer.FRAMERATE = 0;
    }
    private void render(ArrayList<Entity> visibleSet, final Viewport camera){
        mView.render(visibleSet, camera);
    }



    private void buildVisibleSet(){
        mVisibleEntities.clear();
        for(final Entity e : mLevel.mEntities){
            if(mCamera.inView(e)){
                mVisibleEntities.add(e);
            }
        }
    }

    //On the UI Thread
    public void onResume(){
        mControls.onResume();
        mIsRunning = true;
        mGameThread = new Thread(this);

    }
    public void onPause(){
        mControls.onPause();
        mIsRunning = false;
        while(mGameThread.getState() != Thread.State.TERMINATED) {
            try {
                mGameThread.join();
            } catch (InterruptedException e) {

            }
        }
        Log.d(TAG, "Game Thread is terminated");
    }

    public void onDestroy(){
        if(mLevel != null){
            mLevel.destroy();
        }
        if(mView != null){
            mView.getHolder().removeCallback(this);
        }
        mControls.onDestroy();
        Entity.mEngine = null;
    }

    private void loadLevel(){
        mLevel = new LevelManager();
        mCameraPos.x = 0f;
        mCameraPos.y = 0f;
        mCamera.lookAt(mCameraPos);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {
        Log.d(TAG, "surfaceChanged");
        Log.d(TAG, "\t Dimensions: " + width + " : " + height);
        if(mGameThread != null && mIsRunning){
            Log.d(TAG, "Game Thread started");
            mGameThread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
    }

    public void dropHealth(){
        mLevel.dropHealth();
    }


    public void removeEntity(Entity e) {
        mLevel.removeEntity(e);
    }

    public void collectedACoin() {
        mLevel.regenHealth();
        mLevel.collectedACoin();
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(MainActivity.coinString + getCollectedCoins() + "/" + getTotalCoins());
            }
        });

    }

    public int getTotalCoins() {
        return mLevel.numberOfCoins;
    }

    private int getCollectedCoins() {
        return mLevel.mCollectedCoins;
    }

    public void setTextView(TextView t) {
        mTextView = t;
    }
}
