package com.example.pixelcombat.factories;

import android.content.Context;
import android.util.Log;

import com.example.pixelcombat.character.chars.kohaku.projectiles.HorizontalSlash;
import com.example.pixelcombat.core.config.ProjectileConfig;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.projectile.Projectile;
import com.example.pixelcombat.utils.LocatedBitmap;
import com.example.pixelcombat.xml.BoxParser;
import com.example.pixelcombat.xml.CharacterParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProjectileFactory {

    private final Context context;
    //all pictures hold in map
    public HashMap<String, Map<String, ArrayList<LocatedBitmap>>> pictures;

    //all boxes hold in map
    public HashMap<String, Map<String, ArrayList<ArrayList<BoundingRectangle>>>> boxes;

    //all additional variabels
    public HashMap<String, ArrayList<ArrayList<Float>>> times;
    public HashMap<String, ArrayList<Integer>> loopVariabels;
    public HashMap<String, ArrayList<Boolean>> loopBools;
    private ArrayList<String> projectileNames;
    private CharacterParser projectileParser;
    private BoxParser boxParser;

    public Projectile createProjectile(String type, Vector2d pos, boolean right, String owner) {

        Map<String, ArrayList<LocatedBitmap>> imagesP = pictures.get(type);
        Map<String, ArrayList<ArrayList<BoundingRectangle>>> boxesP = boxes.get(type);
        ArrayList<ArrayList<Float>> timesP = times.get(type);
        ArrayList<Integer> loopVariabelsP = loopVariabels.get(type);
        ArrayList<Boolean> loopBoolsP = loopBools.get(type);

        switch (type) {

            case ProjectileConfig.KOHAKU_SPECIAL_ATTACK_PROJECTILE_HORIZONTAL:
                return new Projectile(pos, right, boxesP, imagesP, timesP, loopVariabelsP, loopBoolsP, new HorizontalSlash(), owner);
            default:
                return null;
        }

    }

    public ProjectileFactory(Context context) {
        this.context = context;
    }

    public void init() {
        //create dummy lists for pics/boxes
        this.pictures = new HashMap<>();
        this.loopVariabels = new HashMap<>();
        this.loopBools = new HashMap<>();
        this.times = new HashMap<>();
        this.boxes = new HashMap<>();
        this.projectileNames = new ArrayList<>();

        //load Pics and Boxes
        loadAll();
    }

    private void loadAll() {
        try {
            projectileParser = new CharacterParser(context);
            boxParser = new BoxParser(context);
            projectileNames.add("Kohaku_Projectile_Horizontal");

            for (String projectile : projectileNames) {
                projectileParser.parse(projectile + "_Images.xml");
                boxParser.parse(projectile + "_Boxes.xml");


                Map<String, ArrayList<LocatedBitmap>> picturest = projectileParser.getCharacter();
                ArrayList<Boolean> projectile_loopBools = projectileParser.getLoop();
                ArrayList<Integer> projectile_loopIndices = projectileParser.getLoopIndizes();
                ArrayList<ArrayList<Float>> projectile_times = projectileParser.getTimes();


                pictures.put(projectile, picturest);
                loopBools.put(projectile, projectile_loopBools);
                loopVariabels.put(projectile, projectile_loopIndices);
                times.put(projectile, projectile_times);

                Log.i("Info", "The projectile's images " + projectile + " could be created");

                Map<String, ArrayList<ArrayList<BoundingRectangle>>> boxes = boxParser.getBoxes();
                this.boxes.put(projectile, boxes);

                Log.i("Info", "The projectile's boxes " + projectile + " could be created");

            }

        } catch (Exception e) {
            Log.e("Error", "Beim Erstellen der ProjectileFactory gab es einen Fehler. " + e.getMessage());
        }
    }

}
