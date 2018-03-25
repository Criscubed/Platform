package com.whatever.cris.platform.Entities;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.whatever.cris.platform.Input.InputManager;

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
    private final int LEFT = 1;
    private final int RIGHT = -1;
    private int mFacing = LEFT;

    public Player(String spriteName) {
        super(spriteName, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    @Override
    public void update(float deltaTime) {
        final InputManager controls = mEngine.getControls();
        final float direction = controls.mHorizontalFactor;
        mVelocity.x = direction * PLAYER_RUN_SPEED;
        updateFacingDirection(direction);
        if(controls.mIsJumping && mIsOnGround){
            mVelocity.y = PLAYER_JUMP_FORCE;
            mIsOnGround = false;
            //TODO onGameEvent
        }
        super.update(deltaTime);
    }

    private void updateFacingDirection(final float controlDir){
        if(Math.abs(controlDir) < MIN_INPUT_TO_TURN){ return};
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
        super.render(canvas, paint, transform);
    }
}
