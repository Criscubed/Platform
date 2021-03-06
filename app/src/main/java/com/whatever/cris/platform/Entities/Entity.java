package com.whatever.cris.platform.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PointF;
import android.util.Log;

import com.whatever.cris.platform.Game;
import com.whatever.cris.platform.Utils.BitmapPool;
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

    public Entity(final String spriteName, float width, float height){
        init(spriteName, width, height);
    }

    private void init(final String spriteName,final float width,final float height) {
        mSprite = spriteName;
        this.width = width;
        this.height = height;
        if (mSprite.isEmpty()) {
            return;
        }
        loadSprite();
    }

    public void changeSprite(String sprite){
        mSprite = sprite;
        if (mSprite.isEmpty()) {
            return;
        }
        loadSprite();
    }
    private void loadSprite(){
        /*
        if(mBitmap != null){
            BitmapPool.remove(mBitmap);
        }
        */
        mBitmap = BitmapPool.createBitmap(mEngine, mSprite, width, height);
        if(mBitmap == null){
            Log.e(TAG, "Failed to create bitmap: " + mSprite);
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

    //SAT intersection test. http://www.metanetsoftware.com/technique/tutorialA.html
    //returns true on intersection, and sets the least intersecting axis in overlap
    static final PointF overlap = new PointF(0,0); //Q&D PointF pool for collision detection. Assumes single threading.
    @SuppressWarnings("UnusedReturnValue")
    static boolean getOverlap(final Entity a, final Entity b, final PointF overlap) {
        overlap.x = 0.0f;
        overlap.y = 0.0f;
        final float centerDeltaX = a.centerX() - b.centerX();
        final float halfWidths = (a.width() + b.width()) * 0.5f;
        float dx = Math.abs(centerDeltaX);//cache the abs, we need it twice

        if (dx > halfWidths) return false; //no overlap on x == no collision

        final float centerDeltaY = a.centerY() - b.centerY();
        final float halfHeights = (a.height() + b.height()) * 0.5f;
        float dy = Math.abs(centerDeltaY);

        if (dy > halfHeights) return false; //no overlap on y == no collision

        dx = halfWidths - dx; //overlap on x
        dy = halfHeights - dy; //overlap on y
        if (dy < dx) {
            overlap.y = (centerDeltaY < 0) ? -dy : dy;
        } else if (dy > dx) {
            overlap.x = (centerDeltaX < 0) ? -dx : dx;
        } else {
            overlap.x = (centerDeltaX < 0) ? -dx : dx;
            overlap.y = (centerDeltaY < 0) ? -dy : dy;
        }
        return true;
    }

    public void destroy(){
        mBitmap = null;
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

    public PointF getPosition(){
        PointF p = new PointF(x, y);
        return p;
    }

}
