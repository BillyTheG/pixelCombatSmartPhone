package com.example.pixelcombat.factories;

import android.content.Context;
import android.util.Log;

import com.example.pixelcombat.core.config.DustConfig;
import com.example.pixelcombat.core.sound.SoundManager;
import com.example.pixelcombat.dusts.Dust;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;
import com.example.pixelcombat.xml.CharacterParser;

import java.util.ArrayList;
import java.util.HashMap;

public class DustFactory {

    private final Context context;
    private final SoundManager soundManager;
    //all pictures hold in map
    public HashMap<String, ArrayList<LocatedBitmap>> pictures;
    //all additional variabels
    public HashMap<String, ArrayList<Float>> times;

    private CharacterParser dustParser;
    private ArrayList<String> sparkNames;


    public DustFactory(Context context, SoundManager soundManager) {
        this.soundManager = soundManager;
        this.context = context;
        this.sparkNames = new ArrayList<>();
        this.pictures = new HashMap<>();
        this.times = new HashMap<>();
    }


    public void init() {
        try {
            dustParser = new CharacterParser(context);
            sparkNames.add("Kohaku_Special_Attack_Dust.xml");
            sparkNames.add("Kohaku_Special2_Attack_Dust.xml");
            sparkNames.add("Kohaku_Dash_Dust.xml");

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

    public Dust createDust(String type, Vector2d pos, boolean right) {
        switch (type) {
            case DustConfig.KOHAKU_SPECIAL_ATTACK_SPARK:
                return new Dust(pictures.get("Kohaku_Special_Attack_Dust"), times.get("Kohaku_Special_Attack_Dust"), pos, right).register(soundManager);
            case DustConfig.KOHAKU_SPECIAL_ATTACK2_SPARK:
                return new Dust(pictures.get("Kohaku_Special2_Attack_Dust"), times.get("Kohaku_Special2_Attack_Dust"), pos, right).register(soundManager);
            case DustConfig.KOHAKU_DASH_DUST:
                return new Dust(pictures.get("Kohaku_Dash_Dust"), times.get("Kohaku_Dash_Dust"), pos, right).register(soundManager);
            default:
                break;
        }
        return null;
    }

}
