package com.whatever.cris.platform.levels;

/**
 * Created by Cris on 3/25/2018.
 */

public abstract class LevelData {
    public static final String NULL_SPRITE = "cratered";
    public static final int NO_TIlE = 0;
    int[][] mTiles;
    int mHeight;
    int mWidth;

    public int getTile(final int x, final int y){
        return mTiles[y][x];
    }

    public int[] getRow(final int y){
        return mTiles[y];
    }

    protected void updateLevelDimensions(){
        mHeight = mTiles.length;
        mWidth = mTiles[0].length;
    }

    abstract public String getSpriteName(final int tileType);
}
