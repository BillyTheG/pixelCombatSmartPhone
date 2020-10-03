package com.example.pixelcombat.projectile;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.math.Vector2d;

import lombok.Getter;

@Getter
public class Projectile implements GameObject {

    private int rank;

    @Override
    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {

    }

    @Override
    public void update() {

    }

    @Override
    public Vector2d getPos() {
        return null;
    }

    @Override
    public boolean isRight() {
        return false;
    }

    public float getDirection() {
        if (isRight())
            return 1.0f;
        else return -1.0f;
    }

}
