package com.example.pixelcombat.map.weather;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.math.Vector2d;

public class Weather implements GameObject {


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

    @Override
    public float getDirection() {
        return 0;
    }

    @Override
    public int getRank() {
        return 0;
    }
}
