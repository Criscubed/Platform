package com.whatever.cris.platform.Input;

/**
 * Created by Cris on 3/25/2018.
 */

public abstract class InputManager {
    public float mVerticalFactor = 0.0f;
    public float mHorizontalFactor = 0.0f;
    public boolean mIsJumping = false;

    public void onStart() {};
    public void onStop() {};
    public void onPause() {};
    public void onResume() {};

    public void update(float deltaTime) {
    }

    public void onDestroy() {
    }
}