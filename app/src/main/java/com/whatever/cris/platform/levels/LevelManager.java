package com.whatever.cris.platform.levels;

import com.whatever.cris.platform.Entities.Entity;
import com.whatever.cris.platform.Entities.EntityFactory;

import java.util.ArrayList;

/**
 * Created by Cris on 3/25/2018.
 */

public class LevelManager {

    public final ArrayList<Entity> mEntities = new ArrayList<>();
    public Entity mPlayer = null;
    public int mLevelWidth = 0;
    public int mLevelHeight = 0;

    public  LevelManager(){
        loadMapAssets(new TestLevel());
    }

    private void loadMapAssets(final LevelData data){
        cleanup();
        mLevelHeight = data.mHeight;
        mLevelWidth = data.mWidth;
        for(int y= 0; y < mLevelHeight; y++){
            final int[] row = data.getRow(y);
            for(int x = 0; x < row.length; x++){
                final int tileType = row[x];
                if(tileType == LevelData.NO_TIlE){
                    continue;
                }
                final String spriteName = data.getSpriteName(tileType);
                mEntities.add(EntityFactory.makeEntity(spriteName, x, y));
            }
        }
        //TODO set player
    }
    /* TODO
    private Player findPlayerInstance(){

    }*/

    public void cleanup(){
        for(final Entity e : mEntities){
            e.destroy();
        }
        mEntities.clear();
        mPlayer = null;
    }

    public void destroy(){
        cleanup();
    }
}
