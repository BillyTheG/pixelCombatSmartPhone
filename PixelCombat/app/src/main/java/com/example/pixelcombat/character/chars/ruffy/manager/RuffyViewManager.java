package com.example.pixelcombat.character.chars.ruffy.manager;

import com.example.pixelcombat.animation.Animation;
import com.example.pixelcombat.character.chars.ruffy.Ruffy;
import com.example.pixelcombat.manager.GameCharacterViewManager;
import com.example.pixelcombat.xml.CharacterParser;

import java.util.ArrayList;

public class RuffyViewManager extends GameCharacterViewManager {

    public RuffyViewManager(Ruffy character) throws Exception {
        super(character);
    }

    @Override
    protected void loadMoreImages(ArrayList<Animation> animations) {

    }

    @Override
    public void init() throws Exception {
        this.setCharacterParser(new CharacterParser(character.getContext(), "Ruffy_Images.xml"));
        loadParsedImages();
        animManager.playAnim();
        Thread thread = new Thread(animManager);
        thread.start();
    }



}
