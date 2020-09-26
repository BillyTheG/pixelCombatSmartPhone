package com.example.pixelcombat;

import android.graphics.Canvas;

import com.example.pixelcombat.math.Vector2d;

public interface GameObject {

    void draw(Canvas canvas);

    void update();

    Vector2d getPos();

    boolean isRight();

}
