package com.example.pixelcombat.sparks.attack;

import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.sparks.Spark;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

public class Attack1Spark extends Spark {
    public Attack1Spark(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos) {
        super(images, times, pos);
    }
}
