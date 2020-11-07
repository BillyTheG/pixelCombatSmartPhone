package com.example.pixelcombat.sparks.attack;

import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.sparks.Spark;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

public class SlashSpark extends Spark {


    public SlashSpark(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos, boolean isRight) {
        super(images, times, pos, isRight);


    }
}
