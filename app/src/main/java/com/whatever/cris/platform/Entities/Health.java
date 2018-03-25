package com.whatever.cris.platform.Entities;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.whatever.cris.platform.Utils.BitmapPool;



/**
 * Created by Cris on 3/25/2018.
 */

public class Health extends Entity {
    private static final float DEFAULT_DIMENSION = 0.5f;
    public static final String HEART = "life_full";
    public static final String DED = "life_empty";
    private Player mPlayer;
    private int mx;
    private int my;

    public Health(Player mPlayer, int hx, int hy){
        super(HEART, DEFAULT_DIMENSION, DEFAULT_DIMENSION);
        this.mx = hx;
        this.my = hy;
        super.setPosition(mPlayer.x + hx, mPlayer.y + hy);
        this.mPlayer = mPlayer;
    }


    public void update(final float deltaTime){
        x = mPlayer.x + mx;
        y = mPlayer.y + my;
    }
}
