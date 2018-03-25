package com.whatever.cris.platform.Entities;

/**
 * Created by Cris on 3/25/2018.
 */

public class EntityFactory {

    public static Entity makeEntity(final String sprite,final float x, final float y){
        //TODO custom child class
        Entity e = new Entity(sprite);
        e.setPosition(x, y);
        return e;
    }
}
