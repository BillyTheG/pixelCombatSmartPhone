package com.example.pixelcombat.core;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.animation.DustAnimation;

public interface IsFinishable {

    boolean isFinished();

    void update();

    DustAnimation getAnimation();

    void draw(Canvas canvas, int screenX, int screenY, Rect gameRect);

    void draw(int screenX, int screenY, Rect gameRect);
}
