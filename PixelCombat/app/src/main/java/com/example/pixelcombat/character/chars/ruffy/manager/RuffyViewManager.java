package com.example.pixelcombat.character.chars.ruffy.manager;

import com.example.pixelcombat.animation.Animation;
import com.example.pixelcombat.character.chars.ruffy.Ruffy;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.manager.GameCharacterViewManager;
import com.example.pixelcombat.utils.LocatedBitmap;
import com.example.pixelcombat.xml.CharacterParser;

import java.util.ArrayList;
import java.util.Map;

public class RuffyViewManager extends GameCharacterViewManager {

    public RuffyViewManager(Ruffy character) throws Exception {
        super(character);
    }

    @Override
    protected ArrayList<Animation> loadMoreImages(ArrayList<Animation> animations, Map<String, ArrayList<LocatedBitmap>> images, ArrayList<ArrayList<Float>> times, ArrayList<Boolean> loop, ArrayList<Integer> loopIndices) {

        return animations;
    }

    @Override
    public void init() throws Exception {
        this.setCharacterParser(new CharacterParser(character.getContext(), "Ruffy_Images.xml"));
        loadParsedImages();
        animManager.playAnim();
        Thread thread = new Thread(animManager);
        thread.start();
    }

    @Override
    protected int changeFurtherImages(AttackStatus attackStatus) {
        return 0;
    }


}
