package com.whatever.cris.platform.levels;

import android.util.SparseArray;

/**
 * Created by Cris on 3/25/2018.
 */

public class TestLevel extends LevelData {
    private final SparseArray<String> mTileIdtoSpriteName = new SparseArray<>();
    public static final String PLAYER = "left1";
    public TestLevel(){
        mTileIdtoSpriteName.put(0, "background");
        mTileIdtoSpriteName.put(1, PLAYER);
        mTileIdtoSpriteName.put(2, "square_snow");
        mTileIdtoSpriteName.put(3, "upleft");
        mTileIdtoSpriteName.put(4, "upright");

        mTiles = new int[][]{
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,1,0,0,0},
                {0,3,2,2,2,4,0},
                {0,0,0,0,0,0,0}
        };
        updateLevelDimensions();

    }

    @Override
    public String getSpriteName(int tileType) {
        final String fileName = mTileIdtoSpriteName.get(tileType);
        if(fileName != null){
            return fileName;
        }
        return NULL_SPRITE;
    }
}
