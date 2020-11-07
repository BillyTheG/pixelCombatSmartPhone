package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.animation.Animation;
import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.manager.GameCharacterViewManager;
import com.example.pixelcombat.utils.LocatedBitmap;
import com.example.pixelcombat.xml.CharacterParser;

import java.util.ArrayList;
import java.util.Map;

public class KohakuViewManager extends GameCharacterViewManager {

    public final int BATTOJUTSUOGI = 38;


    public KohakuViewManager(Kohaku character) throws Exception {
        super(character);
    }

    @Override
    protected ArrayList<Animation> loadMoreImages(ArrayList<Animation> animations, Map<String, ArrayList<LocatedBitmap>> images, ArrayList<ArrayList<Float>> times, ArrayList<Boolean> loop, ArrayList<Integer> loopIndices) {
        //specialAttack1 and specialAttack2 have same animations
        animations.add(new Animation(images.get("battoJutsu"), times.get(BATTOJUTSUOGI), loop.get(BATTOJUTSUOGI), loopIndices.get(BATTOJUTSUOGI)));


        return animations;
    }

    @Override
    public void init() throws Exception {
        this.setCharacterParser(new CharacterParser(character.getContext(), "Kohaku_Images.xml"));
        loadParsedImages();
        animManager.playAnim();
        Thread thread = new Thread(animManager);
        thread.start();
    }

    @Override
    protected int changeFurtherImages(AttackStatus attackStatus) {
        switch (attackStatus) {
            case BATTO_JUTSU_OGI:
                return BATTOJUTSUOGI;
            default:
                break;
        }
        return 0;
    }


}
