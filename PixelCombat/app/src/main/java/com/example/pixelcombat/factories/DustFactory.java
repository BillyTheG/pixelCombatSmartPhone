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

import javax.inject.Inject;

public class DustFactory {

    /* all additional variables */
    public HashMap<String, ArrayList<Float>> times;
    private Context context;
    //all pictures hold in map
    public HashMap<String, ArrayList<LocatedBitmap>> pictures;
    private SoundManager soundManager;

    private ArrayList<String> sparkNames;

    @Inject
    public DustFactory(Context context, SoundManager soundManager) {
        this.soundManager = soundManager;
        this.context = context;
        this.sparkNames = new ArrayList<>();
        this.pictures = new HashMap<>();
        this.times = new HashMap<>();
    }


    public void init() {
        try {

            sparkNames.add("Kohaku_Special_Attack_Dust.xml");
            sparkNames.add("Kohaku_Special2_Attack_Dust.xml");
            sparkNames.add("Kohaku_Dash_Dust.xml");
            sparkNames.add("Kohaku_Attack6_Dust.xml");
            sparkNames.add("Dust_Hard_Land.xml");
            sparkNames.add("Dust_Hard_Land_Side.xml");

            for (String spark : sparkNames) {
                CharacterParser dustParser = new CharacterParser(context);
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
            case DustConfig.KOHAKU_ATTACK6_DUST:
                return new Dust(pictures.get("Kohaku_Attack6_Dust"), times.get("Kohaku_Attack6_Dust"), pos, right).register(soundManager);
            case DustConfig.DUST_HARD_LAND:
                return new Dust(pictures.get("Dust_Hard_Land"), times.get("Dust_Hard_Land"), pos, right).register(soundManager);
            case DustConfig.DUST_HARD_LAND_SIDE:
                return new Dust(pictures.get("Dust_Hard_Land_Side"), times.get("Dust_Hard_Land_Side"), pos, right).register(soundManager);
            default:
                break;
        }
        return null;
    }

}
