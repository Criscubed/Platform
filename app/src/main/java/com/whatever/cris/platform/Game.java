package com.whatever.cris.platform;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.whatever.cris.platform.Entities.Entity;
import com.whatever.cris.platform.levels.LevelManager;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * Created by Cris on 3/24/2018.
 */

public class Game implements Runnable, SurfaceHolder.Callback {

    private static final String TAG = "Game";
    private static final float METERS_TO_SHOW_X = 16f;
    private static final float METERS_TO_SHOW_y = 0f;
    private static final int FRAMEBUFFER_WIDTH = 1280;
    private static final int FRAMEBUFFER_HEIGHT = 720;

    private GameView mView;
    private MainActivity mainActivity;
    private Thread mGameThread;
    private volatile boolean mIsRunning = false;
    private LevelManager mLevel = null;
    private Viewport mCamera = null;
    private ArrayList<Entity> mVisibleEntities = new ArrayList<>();

    public Game(Context context, final GameView view) {
        mView = view;
        mainActivity = (MainActivity) context;
        Entity.mEngine = this;
        SurfaceHolder holder = mView.getHolder();
        holder.addCallback(this);
        holder.setFixedSize(FRAMEBUFFER_WIDTH, FRAMEBUFFER_HEIGHT);
        mCamera = new Viewport(FRAMEBUFFER_WIDTH, FRAMEBUFFER_HEIGHT,
                METERS_TO_SHOW_X, METERS_TO_SHOW_y);
        mLevel = new LevelManager();
        Log.d(TAG, "Game Created!");
    }

    public Context getAppContext(){
        return mainActivity.getApplicationContext();
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
    @Override
    public void run() {
        long lastFrame = System.nanoTime();
        while (mIsRunning){
            final float deltaTime = TimeUnit.NANOSECONDS.toSeconds(
                    System.nanoTime()-lastFrame);
            input();
            update(deltaTime);
            render(mVisibleEntities, mCamera);
            lastFrame = System.nanoTime();
        }
    }

    private void input(){

    }
    private void update(final float deltaTime){
        mCamera.lookAt(4, 3);
        buildVisibleSet();
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
        mIsRunning = true;
        mGameThread = new Thread(this);

    }
    public void onPause(){
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
        Entity.mEngine = null;
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


}
