package com.example.pixelcombat.character.chars.ruffy.manager;

import com.example.pixelcombat.character.chars.ruffy.Ruffy;
import com.example.pixelcombat.manager.BoxManager;

public class RuffyBoxManager extends BoxManager {


    public RuffyBoxManager(Ruffy character) throws Exception {
        super(character);
    }


    @Override
    protected String getFileName() {
        return "Ruffy_Boxes.xml";
    }

    @Override
    public void loadFurtherBoxes(int currentAnimation2) {

    }


}
