package com.example.pixelcombat.factories;

import android.content.Context;
import android.util.Log;

import com.example.pixelcombat.core.config.EffectConfig;
import com.example.pixelcombat.effects.Effect;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;
import com.example.pixelcombat.xml.CharacterParser;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import lombok.Getter;

public class EffectFactory {

    /* all additional variables */
    public HashMap<String, ArrayList<Float>> times;
    //all pictures hold in map
    public HashMap<String, ArrayList<LocatedBitmap>> pictures;
    @Getter
    public HashMap<String, Effect> effects;
    private Context context;
    private ArrayList<String> sparkNames;

    @Inject
    public EffectFactory(Context context) {
        this.context = context;
        this.sparkNames = new ArrayList<>();
        this.pictures = new HashMap<>();
        this.times = new HashMap<>();
    }


    public void init() {
        try {

            sparkNames.add("AttackCover_Avatar_Bg.xml");
            sparkNames.add("AttackCover_Avatar_Delta.xml");
            sparkNames.add("AttackCover_Avatar_Profile1_Kohaku.xml");


            for (String spark : sparkNames) {
                CharacterParser dustParser = new CharacterParser(context);
                dustParser.parse(spark);
                String sparkC = spark.replace(".xml", "");

                pictures.put(sparkC, dustParser.getCharacter().get("sequence"));
                times.put(sparkC, dustParser.getTimes().get(0));
                Log.i("Info", "The spark " + sparkC + " could be created");
            }


            effects.put(EffectConfig.AVATAR_COVER_PROFILE1_KOHAKU, createProfileEffect(EffectConfig.AVATAR_COVER_PROFILE1_KOHAKU));

        } catch (Exception e) {
            Log.e("Error", "Beim Erstellen der SparkFactory gab es einen Fehler. " + e.getMessage());
        }
    }

    public Effect createEffect(String type, Vector2d pos, boolean scale) {
        switch (type) {
            case EffectConfig.AVATAR_COVER:
                return new Effect(pictures.get("AttackCover_Avatar_Bg"), times.get("AttackCover_Avatar_Bg"), pos, scale);
            case EffectConfig.AVATAR_COVER_DELTA:
                return new Effect(pictures.get("AttackCover_Avatar_Delta"), times.get("AttackCover_Avatar_Delta"), pos, scale);
            default:
                break;
        }
        return null;
    }


    public Effect createProfileEffect(String profileName) {
        switch (profileName) {
            case EffectConfig.AVATAR_COVER_PROFILE1_KOHAKU:
                return new Effect(pictures.get(EffectConfig.AVATAR_COVER_PROFILE1_KOHAKU), times.get(EffectConfig.AVATAR_COVER_PROFILE1_KOHAKU), new Vector2d(-330, 0), true);
            default:
                break;
        }
        return null;
    }

}
