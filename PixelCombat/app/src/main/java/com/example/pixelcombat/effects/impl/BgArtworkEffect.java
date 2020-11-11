package com.example.pixelcombat.effects.impl;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pixelcombat.animation.util.impl.ImageZoomer;
import com.example.pixelcombat.effects.Effect;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

public class BgArtworkEffect extends Effect {

    private ImageZoomer imageZoomer;

    public BgArtworkEffect(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos) {
        super(images, times, pos, true, true);
        this.imageZoomer = new ImageZoomer(getScaleFactor());
        animation.setScaleFactor(getScaleFactor());
    }

    @Override
    protected void updateChanger() {
        imageZoomer.zoom(this);
    }

    @Override
    protected void resetChanger() {
        imageZoomer.reset(this);
    }

    @Override
    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {

        Bitmap currentImage = animation.getCurrentBitmap(gameRect);
        float centerX = (float) ScreenProperty.SCREEN_WIDTH / 2f;
        float centerY = (float) (ScreenProperty.SCREEN_HEIGHT - ScreenProperty.OFFSET_Y) / 2f;


        int left = (int) (centerX - currentImage.getWidth() / 2);
        int top = (int) (centerY - currentImage.getHeight() / 2);
        int right = (int) (centerX + currentImage.getWidth() / 2);
        int bottom = (int) (centerY + currentImage.getHeight() / 2);


        Rect rect = new Rect(left, top, right, bottom);

        Paint paint = new Paint();
        paint.setAlpha(150);


        canvas.drawBitmap(currentImage, null, rect, paint);


    }

    @Override
    public boolean isHorizontal() {
        return false;
    }

    @Override
    public float getScaleFactor() {
        return 1f;
    }
}

