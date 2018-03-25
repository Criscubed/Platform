package com.whatever.cris.platform;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.whatever.cris.platform.Entities.Entity;

import java.util.ArrayList;

/**
 * Created by Cris on 3/25/2018.
 */

public class GameView extends SurfaceView {

    private static final int BG_COLOR = Color.rgb(135, 206, 235);
    private SurfaceHolder mHolder;
    private Paint mPaint = new Paint();
    private Canvas mCanvas = null;

    public GameView(Context context) {
        super(context);
        init();
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    /*    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    public void init(){
        mHolder = getHolder();
    }

    public void render(final ArrayList<Entity> visibleEntities,
                       final Viewport camera){
        if (!acquireAndLockCanvas()){
            return;
        }
        try {
            mCanvas.drawColor(BG_COLOR);

            for (final Entity e : visibleEntities) {
                //camera.worldToScreen(e, );
                e.render(mCanvas, mPaint);
            }
        } finally {
            if(mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private boolean acquireAndLockCanvas() {
        if(!mHolder.getSurface().isValid()){
            return false;
        }
        mCanvas = mHolder.lockCanvas();
        return mCanvas != null;

    }
}
