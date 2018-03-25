package com.whatever.cris.platform.Utils;

/**
 * Created by Cris on 3/25/2018.
 */
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;

public class Utils {

    public static float wrap(float val, final float min, final float max){
        if(val < min){
            val = max;
        } else if(val > max){
            val = min;
        }
        return val;
    }

    public static void clamp(final PointF val, final PointF min, final PointF max){
        if(val.x < min.x){
            val.x = min.x;
        } else if(val.x > max.x){
            val.x = max.x;
        }
        if(val.y < min.y){
            val.y = min.y;
        } else if(val.y > max.y){
            val.y = max.y;
        }
    }


    public static int clamp(int val, final int min, final int max){
        if(val < min){
            val = min;
        } else if(val > max){
            val = max;
        }
        return val;
    }

    public static float clamp(float val, final float min, final float max){
        if(val < min){
            val = min;
        } else if(val > max){
            val = max;
        }
        return val;
    }

    public static Bitmap flipBitmap(Bitmap source, boolean horizontally){
        Matrix m = new Matrix();
        int cx = source.getWidth()/2;
        int cy = source.getWidth()/2;
        if(horizontally){
            m.postScale(1, -1, cx, cy);
        } else {
            m.postScale(-1, 1, cx, cy);
        }
        return Bitmap.createBitmap(source, 0, 0,
                source.getWidth(), source.getHeight(),
                m, true);
    }
    public static Bitmap scaleToTargetHeight(Bitmap source, int height){
        float ratio = height / (float) source.getHeight();
        int newHeight = (int) (source.getHeight() * ratio);
        int newWidth = (int) (source.getWidth() * ratio);
        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }

    public static int pxToDp(final int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(final int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}