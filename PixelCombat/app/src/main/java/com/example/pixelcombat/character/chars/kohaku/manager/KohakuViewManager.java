package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.animation.Animation;
import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.manager.GameCharacterViewManager;
import com.example.pixelcombat.xml.CharacterParser;

import java.util.ArrayList;

public class KohakuViewManager extends GameCharacterViewManager {


    public KohakuViewManager(Kohaku character) throws Exception {
        super(character);
    }

    @Override
    protected void loadMoreImages(ArrayList<Animation> animations) {
        //specialAttack1 and specialAttack2 have same animations

    }

    @Override
    public void init() throws Exception {
        this.setCharacterParser(new CharacterParser(character.getContext(), "Kohaku_Images.xml"));
        loadParsedImages();
        animManager.playAnim();
        Thread thread = new Thread(animManager);
        thread.start();
    }


}
