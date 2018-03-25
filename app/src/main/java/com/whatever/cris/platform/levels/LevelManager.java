package com.whatever.cris.platform.levels;

import com.whatever.cris.platform.Entities.Entity;
import com.whatever.cris.platform.Entities.EntityFactory;
import com.whatever.cris.platform.Entities.Health;
import com.whatever.cris.platform.Entities.Player;
import com.whatever.cris.platform.Utils.BitmapPool;

import java.util.ArrayList;

/**
 * Created by Cris on 3/25/2018.
 */

public class LevelManager {

    public final ArrayList<Entity> mEntities = new ArrayList<>();
    public final ArrayList<Entity> mEntitiesToAdd = new ArrayList<>();
    public final ArrayList<Entity> mEntitiesToRemove = new ArrayList<>();

    public Player mPlayer = null;
    public Health h1 = null;
    public Health h2 = null;
    public Health h3 = null;
    public int totalHealth = 0;
    public int mLevelWidth = 0;
    public int mLevelHeight = 0;

    public  LevelManager(){
        loadMapAssets(new TestLevel());
        totalHealth = 3;
    }

    public void dropHealth(){
        totalHealth--;
        switch (totalHealth){
            case 2:
                h3.changeSprite(Health.DED);
                break;
            case 1:
                h2.changeSprite(Health.DED);
                break;
            case 0:
                h1.changeSprite(Health.DED);
                break;
        }
    }

    public void update(final float deltaTime){
        final int ic = mEntities.size();
        for(int i = 0; i < ic; i++){
            mEntities.get(i).update(deltaTime);
        }
        checkCollisions();
        addAndRemoveEntities();
    }

    private void checkCollisions(){
        final int count =  mEntities.size();
        Entity a, b;
        for(int i = 0; i < count-1; i++){
            a = mEntities.get(i);
            for(int j = i + 1; j < count; j++){
                b = mEntities.get(j);
                if(a.isColliding(b)){
                    a.onCollision(b);
                    b.onCollision(a);
                }
            }
        }
    }

    private void addAndRemoveEntities() {
        Entity temp;
        for(int i = mEntitiesToRemove.size() - 1;  i >= 0; i--){
            temp = mEntitiesToRemove.remove(i);
            mEntities.remove(temp);
        }
        for(int i = mEntitiesToAdd.size() - 1;  i >= 0; i--){
            temp = mEntitiesToAdd.remove(i);
            mEntities.add(temp);
        }

    }

    public void addEntity(final Entity e){
        if(e != null){
            mEntitiesToAdd.add(e);
        }
    }
    public void removeEntity(final Entity e){
        if(e != null){
            mEntitiesToRemove.add(e);
        }
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
        mPlayer = findPlayerInstance();
        h1 = new Health(mPlayer, 1, -1);
        h2 = new Health(mPlayer, 0, -1);
        h3 = new Health(mPlayer, -1, -1);
        mEntities.add(h1);
        mEntities.add(h2);
        mEntities.add(h3);
    }
    private Player findPlayerInstance(){
        for(Entity e : mEntities){
            if(Player.class.isInstance(e)){
                return (Player) e;
            }
        }
        throw new AssertionError("Player not found in level!");
    }

    public void cleanup(){
        for(final Entity e : mEntities){
            e.destroy();
        }
        mEntitiesToAdd.clear();
        mEntitiesToRemove.clear();
        mEntities.clear();
        mPlayer = null;
    }

    public void destroy(){
        cleanup();
        BitmapPool.empty();
    }
}
