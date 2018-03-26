package com.whatever.cris.platform.Entities;

/**
 * Created by Cris on 3/26/2018.
 */

public class Coin extends Entity {
    public Coin(String sprite){
        super(sprite);
    }

    @Override
    public void onCollision(Entity e) {
        super.onCollision(e);
        if(e instanceof Player){
            mEngine.removeEntity(this);
        }
    }
}

