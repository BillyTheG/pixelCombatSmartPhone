package com.example.pixelcombat.effects.attackCover;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pixelcombat.enums.ScreenProperty;

public class Darkening {


    private int alpha = 0;
    private int GAINS = 10;
    private long lastFrame = 0;

    public Darkening() {
        lastFrame = System.currentTimeMillis();
    }

    public void update() {
        alpha += GAINS;
        if (alpha > 255) {
            alpha = 255;
        }
    }

    public void draw(Canvas canvas) {

        Paint paint = new Paint(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(alpha);
        Rect rect = new Rect(ScreenProperty.OFFSET_X, 0, ScreenProperty.SCREEN_WIDTH - ScreenProperty.OFFSET_X, ScreenProperty.SCREEN_HEIGHT - ScreenProperty.OFFSET_Y);

        canvas.drawRect(rect, paint);


    }


    public void reset() {
        alpha = 0;
    }

    public boolean darkened() {
        return alpha == 255;
    }

}
