package com.whatever.cris.platform.Entities;

import android.graphics.PointF;

import com.whatever.cris.platform.Utils.Random;
import com.whatever.cris.platform.Utils.Utils;

/**
 * Created by Cris on 3/25/2018.
 */

public class DynamicEntity extends Entity {

    private static final String TAG = "DynamicEntity";
    private static final float MAX_DELTA = 0.48f;
    static final float GRAVITATIONAL_ACCELERATION = 40f;
    public PointF mVelocity = new PointF(0f, 0f);
    PointF mAccel = new PointF(1f, 1f);
    PointF mTargetspeed = new PointF(0,0);
    float mGravity = GRAVITATIONAL_ACCELERATION;
    boolean mIsOnGround = false;



    public DynamicEntity(String spriteName) {
        super(spriteName);
    }
    public DynamicEntity(final String spriteName, float width, float height){
        super(spriteName, width, height);
    }

    @Override
    public void update(float deltaTime) {
        mVelocity.x += (mAccel.x * mTargetspeed.x);
        if(Math.abs(mVelocity.x) > Math.abs(mTargetspeed.x)){
            mVelocity.x = mTargetspeed.x;
        }
        x += Utils.clamp(mVelocity.x * deltaTime, -MAX_DELTA, MAX_DELTA);

        if(!mIsOnGround){
            final float gravityThisTick = mGravity * deltaTime;
            mVelocity.y += gravityThisTick;
            y += Utils.clamp(mVelocity.y * deltaTime, -MAX_DELTA, MAX_DELTA);

        }
        if(y > mEngine.getWorldHeight()){
            y = Random.between(-5f, -2f);
        }
        mIsOnGround = false;
    }

    @Override
    public void onCollision(Entity e) {
        Entity.getOverlap(this, e, Entity.overlap);
        x += Entity.overlap.x;
        y += Entity.overlap.y;
        if(Entity.overlap.y != 0f){
            mVelocity.y = 0.f;
            mTargetspeed.y = 0f;
            if(Entity.overlap.y < 0f){
                mIsOnGround = true;
            }
        }
        super.onCollision(e);
    }
}
