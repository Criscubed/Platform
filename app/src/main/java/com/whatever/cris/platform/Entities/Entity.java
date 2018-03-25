package com.whatever.cris.platform.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.whatever.cris.platform.Game;
import com.whatever.cris.platform.Utils.BitmapUtils;

/**
 * Created by Cris on 3/25/2018.
 */

public class Entity {

    private static final String TAG = "Entity";
    private  static final float DEFAULT_LOCATION = 0f;
    private static final float DEFAULT_DIMENSION = 1f;
    public float x = DEFAULT_LOCATION;
    public float y = DEFAULT_LOCATION;
    public float width = DEFAULT_DIMENSION;
    public float height = DEFAULT_DIMENSION;

    public static Game mEngine;
    Bitmap mBitmap = null; //TODO bitmap pool
    private String mSprite;

    public Entity(final String spriteName){
        init(spriteName, DEFAULT_DIMENSION, DEFAULT_DIMENSION);
    }

    private void init(final String spriteName, float width, float height){
        mSprite = spriteName;
        this.width = width;
        this.height = height;
        final int pixelWidth = mEngine.worldToScreenX(width);
        final int pixelHeight = mEngine.worldToScreenY(height);
        try {
            mBitmap = BitmapUtils.loadScaledBitmap(mEngine.getAppContext(),
                    spriteName, pixelWidth, pixelHeight);
        } catch (Exception e) {
            throw new AssertionError("this bitmap dont work:" + spriteName);
        }
    }

    public void update(final float deltaTime){

    }

    public void render(final Canvas canvas, final Paint paint, final Matrix transform){
        canvas.drawBitmap(mBitmap, transform, paint);
    }

    public boolean isColliding(Entity that){
        return Entity.intersectsAABB(this, that);
    }
    public void onCollision(Entity e){
    }
    public static boolean intersectsAABB(Entity a, Entity b){
        return !(a.right() < b.left()
                || b.right() < a.left()
                || a.bottom() < b.top()
                || b.bottom() < a.top());
    }

    public void destroy(){
        if(mBitmap != null){
            mBitmap.recycle();
        }
    }

    public float x(){return x;}
    public float y(){return y;}
    public float width(){return width;}
    public float height(){return height;}
    public float centerX(){return x+width*0.5f;}
    public float centerY(){return y+height*0.5f;}
    public float left(){return x;}
    public float right(){return x+width;}
    public float top(){return y;}
    public float bottom(){return y+height;}
    public void setPosition(final float x,final float y){
        this.x = x;
        this.y = y;
    }

}
