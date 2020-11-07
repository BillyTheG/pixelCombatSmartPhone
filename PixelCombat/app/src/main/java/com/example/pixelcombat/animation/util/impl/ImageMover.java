package com.example.pixelcombat.animation.util.impl;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.animation.util.ICanMove;

public class ImageMover implements ICanMove {


    private float VX;
    private float VY;

    public ImageMover(float VX, float VY) {
        this.VX = VX;
        this.VY = VY;
    }


    @Override
    public void move(GameObject object) {
        object.getPos().x += object.getDirection() * VX;
        object.getPos().y += VY;
    }
}
