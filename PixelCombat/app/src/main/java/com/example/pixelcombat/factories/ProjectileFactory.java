package com.example.pixelcombat.factories;

import android.graphics.Bitmap;

import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.projectile.Projectile;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectileFactory {

    //all pictures hold in map
    public HashMap<String, ArrayList<ArrayList<Bitmap>>> pictures;

    //all boxes hold in map
    public HashMap<String, ArrayList<ArrayList<ArrayList<BoundingRectangle>>>> boxes;

    //all additional variabels
    public HashMap<String, ArrayList<ArrayList<Float>>> times;
    public HashMap<String, ArrayList<Integer>> loopVariabels;
    public HashMap<String, ArrayList<Boolean>> loopBools;


    public Projectile createProjectile(String type, Vector2d pos, boolean right, String owner) {

        return null;
    }


}
