package com.whatever.cris.platform.levels;

import android.util.SparseArray;

/**
 * Created by Cris on 3/25/2018.
 */

public class TestLevel extends LevelData {
    private final SparseArray<String> mTileIdToSpriteName = new SparseArray<>();
    public TestLevel(){
        mTileIdToSpriteName.put(0, "background");
        mTileIdToSpriteName.put(1, PLAYER);
        mTileIdToSpriteName.put(2, "square_snow");
        mTileIdToSpriteName.put(3, "upleft");
        mTileIdToSpriteName.put(4, "upright");
        mTileIdToSpriteName.put(5, ENEMY1);
        mTileIdToSpriteName.put(6, ENEMY2);
        mTileIdToSpriteName.put(7, COIN);

        mTiles = new int[][]{
                {0,0,0,0,0,0,0},
                {0,0,0,1,0,0,0},
                {0,0,0,0,0,0,7},
                {5,3,2,2,2,4,6},
                {0,0,0,0,0,0,0}
        };
        updateLevelDimensions();

    }

    @Override
    public String getSpriteName(int tileType) {
        final String fileName = mTileIdToSpriteName.get(tileType);
        if(fileName != null){
            return fileName;
        }
        return NULL_SPRITE;
    }
}
