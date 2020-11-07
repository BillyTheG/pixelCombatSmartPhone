package com.example.pixelcombat.effects;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.effects.animation.EffectAnimation;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

public abstract class Effect implements GameObject {
    protected final EffectAnimation animation;
    protected final Vector2d pos;
    protected final boolean scale;
    protected float xStartPos = 0;
    protected float yStartPos = 0;
    protected boolean artWork = false;

    public Effect(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos, boolean artWork, boolean scale) {
        this.animation = new EffectAnimation(images, times, 0, scale);
        this.pos = pos;
        this.artWork = artWork;

        if (scale) {
            pos.x *= animation.getScaleFactor();
            pos.y *= animation.getScaleFactor();
            xStartPos = pos.x;
            yStartPos = pos.y;
        }

        this.animation.play();
        this.scale = scale;

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

    @Override
    public float getScaleFactor() {
        return ScreenProperty.GENERAL_SCALE;
    }


    public void setScaleFactor(float scaleFactor) {
        this.animation.setScaleFactor(scaleFactor);
    }

    public Bitmap getCurrentBitmap(Rect gameRect) {
        return animation.getCurrentBitmap(gameRect);
    }

    public boolean isArtWork() {
        return artWork;
    }
}
