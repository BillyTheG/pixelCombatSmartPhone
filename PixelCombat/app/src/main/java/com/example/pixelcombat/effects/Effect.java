package com.example.pixelcombat.effects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.effects.animation.EffectAnimation;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

public abstract class Effect implements GameObject {
    protected final EffectAnimation animation;
    protected final Vector2d pos;
    protected final boolean scale;
    protected float xStartPos = 0;
    protected float yStartPos = 0;

    public Effect(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos, boolean scale) {
        this.animation = new EffectAnimation(images, times, false, 0, scale);
        this.pos = pos;

        if (scale) {
            pos.x *= animation.getScaleFactor();
            pos.y *= animation.getScaleFactor();
            xStartPos = pos.x;
            yStartPos = pos.y;
        }

        this.animation.play();
        this.scale = scale;

    }

    @Override
    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {

    }

    public void update() {
        animation.update();
        updateChanger();
    }

    protected abstract void updateChanger();

    public void reset() {
        animation.play();
        pos.x = xStartPos;
        pos.y = yStartPos;
        resetChanger();
    }

    protected abstract void resetChanger();


    public Vector2d getPos() {
        return pos;
    }


    public boolean isRight() {
        return scale;
    }

    @Override
    public float getDirection() {
        return 1;
    }

    @Override
    public int getRank() {
        return 0;
    }


    public Bitmap getCurrentBitmap() {
        return animation.getCurrentBitmap();
    }
}
