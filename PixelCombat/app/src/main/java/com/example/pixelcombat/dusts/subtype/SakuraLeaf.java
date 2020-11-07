package com.example.pixelcombat.dusts.subtype;

import com.example.pixelcombat.dusts.Dust;
import com.example.pixelcombat.dusts.Movable;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

public class SakuraLeaf extends Dust implements Movable {

    private float VX;
    private float VY;


    public SakuraLeaf(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos, boolean isRight, float VX, float VY) {
        super(images, times, true, pos, isRight);
        this.VX = VX;
        this.VY = VY;
    }

    @Override
    public void update() {
        super.update();
        move();
    }

    @Override
    public void move() {
        this.getPos().x += getDirection() * VX;
        this.getPos().y += VY;
    }

    @Override
    public boolean isFinished() {
        return this.getPos().y >= ScreenProperty.SCREEN_HEIGHT;
    }
}
