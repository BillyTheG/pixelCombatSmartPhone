package com.example.pixelcombat.character.chars.shana.manager;

import com.example.pixelcombat.character.chars.shana.Shana;
import com.example.pixelcombat.manager.BoxManager;

public class ShanaBoxManager extends BoxManager {


    public ShanaBoxManager(Shana character) {
        super(character);
    }


    @Override
    protected String getFileName() {
        return "Shana_Boxes.xml";
    }

    @Override
    public void loadFurtherBoxes(int currentAnimation2) {

        switch (character.getViewManager().getAnimation()) {
            default:
                updateBoxSeq(STAND, "stand");
                break;
        }
    }


}
