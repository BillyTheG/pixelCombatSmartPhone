package com.example.pixelcombat.factories;

import android.content.Context;
import android.util.Log;

import com.example.pixelcombat.core.config.DustConfig;
import com.example.pixelcombat.core.sound.SoundManager;
import com.example.pixelcombat.dusts.Dust;
import com.example.pixelcombat.dusts.subtype.SakuraLeaf;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;
import com.example.pixelcombat.xml.CharacterParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.inject.Inject;

public class DustFactory {

    /* all additional variables */
    public HashMap<String, ArrayList<Float>> times;
    private Context context;
    //all pictures hold in map
    public HashMap<String, ArrayList<LocatedBitmap>> pictures;
    private SoundManager soundManager;

    private ArrayList<String> dustNames;
    private Random random = new Random();


    @Inject
    public DustFactory(Context context, SoundManager soundManager) {
        this.soundManager = soundManager;
        this.context = context;
        this.dustNames = new ArrayList<>();
        this.pictures = new HashMap<>();
        this.times = new HashMap<>();
    }


    public void init() {
        try {

            dustNames.add("Kohaku_Special_Attack_Dust.xml");
            dustNames.add("Kohaku_Special2_Attack_Dust.xml");
            dustNames.add("Kohaku_Dash_Dust.xml");
            dustNames.add("Kohaku_Attack6_Dust.xml");
            dustNames.add("Dust_Hard_Land.xml");
            dustNames.add("Dust_Hard_Land_Side.xml");
            dustNames.add("Kohaku_Special_Attack_Effect.xml");
            dustNames.add("Kohaku_Special_Attack2_Aura.xml");
            dustNames.add("Dust_Fast_Forward.xml");


            dustNames.add("Dust_Sakura1.xml");
            dustNames.add("Dust_Sakura2.xml");
            dustNames.add("Dust_Sakura3.xml");
            dustNames.add("Dust_Sakura4.xml");
            dustNames.add("Dust_Sakura5.xml");
            dustNames.add("Dust_Sakura6.xml");
            dustNames.add("Kohaku_Air_Attack6_Dust.xml");
            dustNames.add("Dust_Run_Walk.xml");


            for (String spark : dustNames) {
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
                return new Dust(pictures.get("Kohaku_Special_Attack_Dust"), times.get("Kohaku_Special_Attack_Dust"), false, pos, right).register(soundManager);
            case DustConfig.KOHAKU_SPECIAL_ATTACK_EFFECT:
                return new Dust(pictures.get("Kohaku_Special_Attack_Effect"), times.get("Kohaku_Special_Attack_Effect"), false, pos, right).register(soundManager);
            case DustConfig.KOHAKU_SPECIAL_ATTACK2_AURA:
                return new Dust(pictures.get("Kohaku_Special_Attack2_Aura"), times.get("Kohaku_Special_Attack2_Aura"), false, pos, right).register(soundManager);
            case DustConfig.KOHAKU_SPECIAL_ATTACK2_SPARK:
                return new Dust(pictures.get("Kohaku_Special2_Attack_Dust"), times.get("Kohaku_Special2_Attack_Dust"), false, pos, right).register(soundManager);
            case DustConfig.KOHAKU_DASH_DUST:
                return new Dust(pictures.get("Kohaku_Dash_Dust"), times.get("Kohaku_Dash_Dust"), false, pos, right).register(soundManager);
            case DustConfig.KOHAKU_ATTACK6_DUST:
                return new Dust(pictures.get("Kohaku_Attack6_Dust"), times.get("Kohaku_Attack6_Dust"), false, pos, right).register(soundManager);
            case DustConfig.KOHAKU_AIR_ATTACK6_DUST:
                return new Dust(pictures.get("Kohaku_Air_Attack6_Dust"), times.get("Kohaku_Air_Attack6_Dust"), false, pos, right, 4).register(soundManager);
            case DustConfig.DUST_HARD_LAND:
                return new Dust(pictures.get("Dust_Hard_Land"), times.get("Dust_Hard_Land"), false, pos, right).register(soundManager);
            case DustConfig.DUST_FAST_FORWARD:
                return new Dust(pictures.get("Dust_Fast_Forward"), times.get("Dust_Fast_Forward"), false, pos, right).register(soundManager);
            case DustConfig.DUST_RUN_WALK:
                return new Dust(pictures.get("Dust_Run_Walk"), times.get("Dust_Run_Walk"), false, pos, right).register(soundManager);
            case DustConfig.DUST_HARD_LAND_SIDE:
                return new Dust(pictures.get("Dust_Hard_Land_Side"), times.get("Dust_Hard_Land_Side"), false, pos, right).register(soundManager);
            case DustConfig.KOHAKU_SAKURA1_DUST:
                return new SakuraLeaf(pictures.get("Dust_Sakura1"), times.get("Dust_Sakura1"), pos, true, random.nextInt(21) + 5, 10, random.nextInt(5) + 1).register(soundManager);
            case DustConfig.KOHAKU_SAKURA2_DUST:
                return new SakuraLeaf(pictures.get("Dust_Sakura2"), times.get("Dust_Sakura2"), pos, true, random.nextInt(21) + 5, 10, random.nextInt(5) + 1).register(soundManager);
            case DustConfig.KOHAKU_SAKURA3_DUST:
                return new SakuraLeaf(pictures.get("Dust_Sakura3"), times.get("Dust_Sakura3"), pos, true, random.nextInt(21) + 5, 10, random.nextInt(5) + 1).register(soundManager);
            case DustConfig.KOHAKU_SAKURA4_DUST:
                return new SakuraLeaf(pictures.get("Dust_Sakura4"), times.get("Dust_Sakura4"), pos, true, random.nextInt(21) + 5, 10, random.nextInt(5) + 1).register(soundManager);
            case DustConfig.KOHAKU_SAKURA5_DUST:
                return new SakuraLeaf(pictures.get("Dust_Sakura5"), times.get("Dust_Sakura5"), pos, true, random.nextInt(21) + 5, 10, random.nextInt(5) + 1).register(soundManager);
            case DustConfig.KOHAKU_SAKURA6_DUST:
                return new SakuraLeaf(pictures.get("Dust_Sakura6"), times.get("Dust_Sakura6"), pos, true, random.nextInt(21) + 5, 10, random.nextInt(5) + 1).register(soundManager);
            default:
                break;
        }
        return null;
    }

}
