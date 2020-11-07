package com.example.pixelcombat.factories;

import android.content.Context;
import android.util.Log;

import com.example.pixelcombat.character.chars.kohaku.projectiles.FireBottle;
import com.example.pixelcombat.character.chars.kohaku.projectiles.HorizontalSlash;
import com.example.pixelcombat.core.config.ProjectileConfig;
import com.example.pixelcombat.core.sound.SoundManager;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.manager.ScreenScrollerManager;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.projectile.Projectile;
import com.example.pixelcombat.utils.LocatedBitmap;
import com.example.pixelcombat.xml.BoxParser;
import com.example.pixelcombat.xml.CharacterParser;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

public class ProjectileFactory {

    //all additional variables
    public ConcurrentHashMap<String, ArrayList<ArrayList<Float>>> times;
    private Context context;
    //all pictures hold in map
    public ConcurrentHashMap<String, ConcurrentHashMap<String, ArrayList<LocatedBitmap>>> pictures;

    //all boxes hold in map
    public ConcurrentHashMap<String, ConcurrentHashMap<String, ArrayList<ArrayList<BoundingRectangle>>>> boxes;
    private SoundManager soundManager;
    public ConcurrentHashMap<String, ArrayList<Integer>> loopVariabels;
    public ConcurrentHashMap<String, ArrayList<Boolean>> loopBools;
    private ArrayList<String> projectileNames;

    @Inject
    public ProjectileFactory(Context context, SoundManager soundManager) {
        this.soundManager = soundManager;
        this.context = context;
    }

    public Projectile createProjectile(String type, Vector2d pos, boolean right, String owner, ScreenScrollerManager screenScrollerManager) {

        ConcurrentHashMap<String, ArrayList<LocatedBitmap>> imagesP = pictures.get(type);
        ConcurrentHashMap<String, ArrayList<ArrayList<BoundingRectangle>>> boxesP = boxes.get(type);
        ArrayList<ArrayList<Float>> timesP = times.get(type);
        ArrayList<Integer> loopVariabelsP = loopVariabels.get(type);
        ArrayList<Boolean> loopBoolsP = loopBools.get(type);

        switch (type) {
            case ProjectileConfig.KOHAKU_SPECIAL_ATTACK_PROJECTILE_HORIZONTAL:
                return new Projectile(pos, right, boxesP, imagesP, timesP, loopVariabelsP, loopBoolsP, new HorizontalSlash(), owner).register(soundManager).register(screenScrollerManager);
            case ProjectileConfig.KOHAKU_SPECIAL_ATTACK_PROJECTILE_BOTTLE:
                return new Projectile(pos, right, boxesP, imagesP, timesP, loopVariabelsP, loopBoolsP, new FireBottle(), owner).register(soundManager).register(screenScrollerManager);

            default:
                return null;
        }

    }

    public void init() {
        //create dummy lists for pics/boxes
        this.pictures = new ConcurrentHashMap<>();
        this.loopVariabels = new ConcurrentHashMap<>();
        this.loopBools = new ConcurrentHashMap<>();
        this.times = new ConcurrentHashMap<>();
        this.boxes = new ConcurrentHashMap<>();
        this.projectileNames = new ArrayList<>();

        //load Pics and Boxes
        loadAll();
    }

    private void loadAll() {
        try {

            projectileNames.add("Kohaku_Projectile_Horizontal");
            projectileNames.add("Kohaku_Projectile_Bottle");

            for (String projectile : projectileNames) {
                CharacterParser projectileParser = new CharacterParser(context);
                BoxParser boxParser = new BoxParser(context, checkScaleFactor(projectile.split("_")[0]));
                //  projectileParser.setTrim(true);
                projectileParser.parse(projectile + "_Images.xml");
                boxParser.parse(projectile + "_Boxes.xml");


                ConcurrentHashMap<String, ArrayList<LocatedBitmap>> picturest = projectileParser.getCharacter();
                ArrayList<Boolean> projectile_loopBools = projectileParser.getLoop();
                ArrayList<Integer> projectile_loopIndices = projectileParser.getLoopIndizes();
                ArrayList<ArrayList<Float>> projectile_times = projectileParser.getTimes();


                pictures.put(projectile, picturest);
                loopBools.put(projectile, projectile_loopBools);
                loopVariabels.put(projectile, projectile_loopIndices);
                times.put(projectile, projectile_times);

                Log.i("Info", "The projectile's images " + projectile + " could be created");

                ConcurrentHashMap<String, ArrayList<ArrayList<BoundingRectangle>>> boxes = boxParser.getBoxes();
                this.boxes.put(projectile, boxes);

                Log.i("Info", "The projectile's boxes " + projectile + " could be created");

            }

        } catch (Exception e) {
            Log.e("Error", "Beim Erstellen der ProjectileFactory gab es einen Fehler. " + e.getMessage());
        }
    }

    private float checkScaleFactor(String s) {
        switch (s) {
            case "Kohaku":
                return ScreenProperty.KOHAKU_SCALE;
            default:
                return ScreenProperty.GENERAL_SCALE;
        }


    }

}
