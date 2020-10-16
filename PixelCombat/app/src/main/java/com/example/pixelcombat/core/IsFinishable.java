package com.example.pixelcombat.core;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface IsFinishable {

    boolean isFinished();

    void update();

    void draw(Canvas canvas, int screenX, int screenY, Rect gameRect);
}
