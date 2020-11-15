package com.example.pixelcombat.sparks.subType;

import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.sparks.Spark;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

public class PreScaledSpark extends Spark {

    private float scaleFactor;

    public PreScaledSpark(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos, boolean isRight, float scaleFactor) {
        super(images, times, pos, isRight);
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
