package com.example.pixelcombat.utils;

import android.graphics.Bitmap;

import com.example.pixelcombat.math.Vector2d;

public class LocatedBitmap {

    public Bitmap image;
    public Vector2d pos;


    public LocatedBitmap(Bitmap image, Vector2d pos) {
        this.image = image;
        this.pos = pos;
    }
}
