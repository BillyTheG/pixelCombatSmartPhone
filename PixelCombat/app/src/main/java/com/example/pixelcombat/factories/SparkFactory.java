package com.example.pixelcombat.factories;

import android.content.Context;
import android.util.Log;

import com.example.pixelcombat.core.config.SparkConfig;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.sparks.Spark;
import com.example.pixelcombat.sparks.attack.Attack1Spark;
import com.example.pixelcombat.utils.LocatedBitmap;
import com.example.pixelcombat.xml.CharacterParser;

import java.util.ArrayList;
import java.util.HashMap;

public class SparkFactory {

    private final Context context;
    //all pictures hold in map
    public HashMap<String, ArrayList<LocatedBitmap>> pictures;
    //all additional variabels
    public HashMap<String, ArrayList<Float>> times;

    private CharacterParser dustParser;
    private ArrayList<String> sparkNames;


    public SparkFactory(Context context) {
        this.context = context;
        this.sparkNames = new ArrayList<>();
        this.pictures = new HashMap<>();
        this.times = new HashMap<>();
    }


    public Spark createSpark(String type, Vector2d pos, boolean right) {
        switch (type) {
            case SparkConfig.ATTACK_SPARK:
                return new Attack1Spark(pictures.get("Attack1_Spark"), times.get("Attack1_Spark"), pos);
            case SparkConfig.KOHAKU_SPECIAL_ATTACK_SPARK:
                return new Attack1Spark(pictures.get("Kohaku_Special_Attack1_Spark"), times.get("Kohaku_Special_Attack1_Spark"), pos, right);
            case SparkConfig.BLOOD_SPLASH_SPARK:
                return new Attack1Spark(pictures.get("Blood_Splash_Spark"), times.get("Blood_Splash_Spark"), pos, right);

            default:
                break;
        }
        return null;
    }

    public void init() {
        try {
            dustParser = new CharacterParser(context);
            sparkNames.add("Attack1_Spark.xml");
            sparkNames.add("Kohaku_Special_Attack1_Spark.xml");
            sparkNames.add("Blood_Splash_Spark.xml");

            for (String spark : sparkNames) {
                dustParser.parse(spark);
                String sparkC = spark.replace(".xml", "");

                pictures.put(sparkC, dustParser.getCharacter().get("sequence"));
                times.put(sparkC, dustParser.getTimes().get(0));
                Log.i("Info", "The spark " + sparkC + " could be created");
            }

        } catch (Exception e) {
            Log.e("Error", "Beim Erstellen der SparkFactory gab es einen Fehler. " + e.getMessage());
        }
    }


}
