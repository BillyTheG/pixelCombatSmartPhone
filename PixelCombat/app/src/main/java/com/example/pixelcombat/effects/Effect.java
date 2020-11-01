package com.example.pixelcombat.effects;

import android.graphics.Bitmap;

import com.example.pixelcombat.effects.animation.EffectAnimation;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

public class Effect {
    private final EffectAnimation animation;
    private final Vector2d pos;
    private final boolean scale;
    private float VX = 5;
    private float VY = 0;
    private float xStartPos = 0;
    private float yStartPos = 0;

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

    public void update() {
        animation.update();
        pos.x += VX;
        pos.y += VY;
    }

    public void reset() {
        animation.play();
        pos.x = xStartPos;
        pos.y = yStartPos;
    }


    public Vector2d getPos() {
        return pos;
    }


    public boolean isRight() {
        return scale;
    }


    public Bitmap getCurrentBitmap() {
        return animation.getCurrentBitmap();
    }
}
