package com.example.pixelcombat.sparks.attack;

import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.sparks.Spark;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

public class Attack1Spark extends Spark {
    public Attack1Spark(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos) {
        super(images, times, pos);
    }

    public Attack1Spark(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos, boolean isRight) {
        super(images, times, pos, isRight);
    }

    public Attack1Spark(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos, boolean isRight, float scale) {
        super(images, times, pos, isRight);
        this.scaleFactor = scale;
    }
}
