package com.example.pixelcombat.character.chars.shana.manager;

import com.example.pixelcombat.animation.Animation;
import com.example.pixelcombat.character.chars.shana.Shana;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.manager.GameCharacterViewManager;
import com.example.pixelcombat.utils.LocatedBitmap;
import com.example.pixelcombat.xml.CharacterParser;

import java.util.ArrayList;
import java.util.Map;

public class ShanaViewManager extends GameCharacterViewManager {


    public ShanaViewManager(Shana character) throws Exception {
        super(character);
    }

    @Override
    protected ArrayList<Animation> loadMoreImages(ArrayList<Animation> animations, Map<String, ArrayList<LocatedBitmap>> images, ArrayList<ArrayList<Float>> times, ArrayList<Boolean> loop, ArrayList<Integer> loopIndices) {
        //specialAttack1 and specialAttack2 have same animations


        return animations;
    }

    @Override
    public void init() throws Exception {
        this.setCharacterParser(new CharacterParser(character.getContext(), "Shana_Images.xml"));
        loadParsedImages();
        animManager.playAnim();
        Thread thread = new Thread(animManager);
        thread.start();
    }

    @Override
    protected int changeFurtherImages(AttackStatus attackStatus) {
        switch (attackStatus) {
            default:
                break;
        }
        return 0;
    }


}
