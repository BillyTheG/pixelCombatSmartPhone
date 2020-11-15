package com.example.pixelcombat.effects.impl;


import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.animation.util.impl.ImageMover;
import com.example.pixelcombat.effects.Effect;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

public class ProfileHorizontalEffect extends Effect {

    private ImageMover imageMover;

    public ProfileHorizontalEffect(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos, boolean scale) {
        super(images, times, pos, false, scale, images.size() - 1);
        this.imageMover = new ImageMover(0, 0);
    }

    @Override
    protected void updateChanger() {
        imageMover.move(this);
    }

    @Override
    protected void resetChanger() {

    }

    @Override
    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {

    }

    @Override
    public boolean isHorizontal() {
        return true;
    }
}
