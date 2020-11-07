package com.example.pixelcombat.effects.impl;


import com.example.pixelcombat.animation.util.impl.ImageMover;
import com.example.pixelcombat.effects.Effect;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

public class ProfileEffect extends Effect {

    private ImageMover imageMover;

    public ProfileEffect(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos, boolean scale) {
        super(images, times, pos, scale);
        this.imageMover = new ImageMover(5, 0);
    }

    @Override
    protected void updateChanger() {
        imageMover.move(this);
    }

    @Override
    protected void resetChanger() {

    }
}
