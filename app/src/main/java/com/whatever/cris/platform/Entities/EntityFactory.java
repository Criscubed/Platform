package com.whatever.cris.platform.Entities;

import com.whatever.cris.platform.levels.LevelData;

/**
 * Created by Cris on 3/25/2018.
 */

public class EntityFactory {

    public static Entity makeEntity(final String sprite,final float x, final float y){
        //TODO custom child class
        Entity e = null;
        if(sprite.equalsIgnoreCase(LevelData.PLAYER)){
            e = new Player(sprite);
        } else if(sprite.equalsIgnoreCase(LevelData.ENEMY1)
                || sprite.equalsIgnoreCase(LevelData.ENEMY2)) {
            e = new Enemy(sprite);
        } else if(sprite.equalsIgnoreCase(LevelData.COIN)){
            e = new Coin(sprite);
        } else {
            e = new Entity(sprite);
        }
        e.setPosition(x, y);
        return e;
    }
}
