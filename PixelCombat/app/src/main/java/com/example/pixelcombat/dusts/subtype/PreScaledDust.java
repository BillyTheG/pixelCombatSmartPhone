package com.example.pixelcombat.dusts.subtype;

import com.example.pixelcombat.dusts.Dust;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

public class PreScaledDust extends Dust {

    private float scaleFactor;

    public PreScaledDust(ArrayList<LocatedBitmap> images, ArrayList<Float> times, boolean loops, Vector2d pos, boolean isRight, float scaleFactor, int alpha) {
        super(images, times, false, pos, isRight, scaleFactor, alpha);
        this.scaleFactor = scaleFactor;
        this.animation.setPreScaled(true);
    }

    @Override
    public void update() {
        super.update();
    }


    @Override
    public boolean isFinished() {
        return !this.animation.isPlaying();
    }

    @Override
    public float getScaleFactor() {
        return scaleFactor;
    }
}
