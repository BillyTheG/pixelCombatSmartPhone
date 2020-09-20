package com.example.pixelcombat.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.math.Vector2d;

public class PXMap implements GameObject {

    private Bitmap bg;
    private int length;
    private int height;

    public PXMap(Bitmap bg){
        this.bg = bg;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bg, ScreenProperty.OFFSET_X,ScreenProperty.OFFSET_y,null);
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
        return true;
    }
}
