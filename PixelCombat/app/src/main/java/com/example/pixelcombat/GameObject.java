package com.example.pixelcombat;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.math.Vector2d;

public interface GameObject {

    void draw(Canvas canvas, int screenX, int screenY, Rect gameRect);

    void update();

    Vector2d getPos();

    boolean isRight();

}
