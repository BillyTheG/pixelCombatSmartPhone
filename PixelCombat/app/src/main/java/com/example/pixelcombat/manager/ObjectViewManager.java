package com.example.pixelcombat.manager;

import com.example.pixelcombat.GameObject;

public abstract class ObjectViewManager<T extends GameObject> {

    public T gameObject;


    public ObjectViewManager(T gameObject) {
        this.gameObject = gameObject;
    }


    abstract public int getAnimation();


}
