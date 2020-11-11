package com.example.pixelcombat.factories;

import android.content.Context;
import android.util.Log;

import com.example.pixelcombat.core.config.EffectConfig;
import com.example.pixelcombat.effects.Effect;
import com.example.pixelcombat.effects.impl.AvatarBgEffect;
import com.example.pixelcombat.effects.impl.BgArtworkEffect;
import com.example.pixelcombat.effects.impl.ProfileHorizontalEffect;
import com.example.pixelcombat.effects.impl.ProfileVerticalEffect;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;
import com.example.pixelcombat.xml.CharacterParser;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

public class EffectFactory {

    /* all additional variables */
    public HashMap<String, ArrayList<Float>> times;
    //all pictures hold in map
    public HashMap<String, ArrayList<LocatedBitmap>> pictures;
    private Context context;
    private ArrayList<String> effectNames;

    @Inject
    public EffectFactory(Context context) {
        this.context = context;
        this.effectNames = new ArrayList<>();
        this.pictures = new HashMap<>();
        this.times = new HashMap<>();
    }


    public void init() {
        try {

            effectNames.add("AttackCover_Avatar_Bg.xml");
            effectNames.add("AttackCover_Avatar_Delta.xml");
            effectNames.add("AttackCover_Avatar_Profile1_Kohaku.xml");
            effectNames.add("AttackCover_Battu_Jutsu_Kohaku.xml");
            effectNames.add("AttackCover_Witch_Kohaku.xml");
            effectNames.add("AttackCover_Maiden_Call_Kohaku.xml");


            for (String spark : effectNames) {
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

    public Effect createEffect(String type, Vector2d pos, boolean scale) {
        switch (type) {
            case EffectConfig.AVATAR_COVER:
                return new AvatarBgEffect(pictures.get("AttackCover_Avatar_Bg"), times.get("AttackCover_Avatar_Bg"), pos, scale);
            case EffectConfig.AVATAR_COVER_DELTA:
                return new AvatarBgEffect(pictures.get("AttackCover_Avatar_Delta"), times.get("AttackCover_Avatar_Delta"), pos, scale);
            default:
                break;
        }
        return null;
    }


    public Effect createProfileEffect(String profileName) {
        switch (profileName) {
            case EffectConfig.AVATAR_COVER_PROFILE1_KOHAKU:
                return new ProfileVerticalEffect(pictures.get(EffectConfig.AVATAR_COVER_PROFILE1_KOHAKU), times.get(EffectConfig.AVATAR_COVER_PROFILE1_KOHAKU), new Vector2d(-330, 0), true);
            case EffectConfig.AVATAR_COVER_BATTU_JUTSU_KOHAKU:
                return new BgArtworkEffect(pictures.get(EffectConfig.AVATAR_COVER_BATTU_JUTSU_KOHAKU), times.get(EffectConfig.AVATAR_COVER_BATTU_JUTSU_KOHAKU), new Vector2d(0, 0));
            case EffectConfig.AVATAR_COVER_WITCH_KOHAKU:
                return new ProfileHorizontalEffect(pictures.get(EffectConfig.AVATAR_COVER_WITCH_KOHAKU), times.get(EffectConfig.AVATAR_COVER_WITCH_KOHAKU), new Vector2d(0, 0), true);
            case EffectConfig.AVATAR_COVER_MAIDEN_CALL:
                return new BgArtworkEffect(pictures.get(EffectConfig.AVATAR_COVER_MAIDEN_CALL), times.get(EffectConfig.AVATAR_COVER_MAIDEN_CALL), new Vector2d(0, 0));

            default:
                break;
        }
        return null;
    }

}
