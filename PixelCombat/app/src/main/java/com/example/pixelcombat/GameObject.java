package com.example.pixelcombat;

import android.graphics.Canvas;

import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.math.Vector2d;

public interface GameObject {

    public void draw(Canvas canvas);
    public void update();

    public Vector2d getPos();
    public boolean isRight();
}
