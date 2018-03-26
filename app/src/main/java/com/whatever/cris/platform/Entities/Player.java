package com.whatever.cris.platform.Entities;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;


import com.whatever.cris.platform.Input.InputManager;
import com.whatever.cris.platform.Utils.BitmapPool;

/**
 * Created by Cris on 3/25/2018.
 */

public class Player extends DynamicEntity {
    private static final String TAG = "Player";
    private static final float PLAYER_WIDTH = 0.9f;
    private static final float PLAYER_HEIGHT = 1f;
    private static final float PLAYER_RUN_SPEED = 6f;
    private static final float PLAYER_JUMP_FORCE = -(GRAVITATIONAL_ACCELERATION/2f);
    private static final float MIN_INPUT_TO_TURN = 0.05f;
    private static final int INVINCIBILITY = 20;
    private final int LEFT = 1;
    private final int RIGHT = -1;
    private int mFacing = LEFT;
    private int mInvincibility;




    public Player(String spriteName) {
        super(spriteName, PLAYER_WIDTH, PLAYER_HEIGHT);

    }

    @Override
    public void update(float deltaTime) {
        final InputManager controls = mEngine.getControls();
        final float direction = controls.mHorizontalFactor;
        mTargetspeed.x = direction * PLAYER_RUN_SPEED;
        updateFacingDirection(direction);
        if(controls.mIsJumping && mIsOnGround){
            mVelocity.y = PLAYER_JUMP_FORCE;
            mIsOnGround = false;
            //TODO onGameEvent
        }
        if(mInvincibility > 0){
            mInvincibility--;
        }
        super.update(deltaTime);
    }

    private void updateFacingDirection(final float controlDir){
        if(Math.abs(controlDir) < MIN_INPUT_TO_TURN){ return;}
        if(controlDir < 0){ mFacing = LEFT;}
        else if(controlDir > 0){mFacing = RIGHT;}
    }

    @Override
    public void render(Canvas canvas, Paint paint, Matrix transform) {
        transform.preScale(mFacing, 1.0f);
        if(mFacing == RIGHT){
            final float offset = mEngine.worldToScreenX(width);
            transform.postTranslate(offset, 0f);
        }
        if(mInvincibility == 0 || mInvincibility % 5 != 0) {
            super.render(canvas, paint, transform);
        }
    }

    @Override
    public void onCollision(Entity e) {
        super.onCollision(e);
        if(mInvincibility == 0) {
            if (e instanceof Enemy) {
                mInvincibility = INVINCIBILITY;
                mEngine.dropHealth();
            }
        }
        if(e instanceof Coin){
            mEngine.collectedACoin();
        }
    }
}
