package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.manager.BoxManager;

public class KohakuBoxManager extends BoxManager {


    public KohakuBoxManager(Kohaku character) {
        super(character);
    }


    @Override
    protected String getFileName() {
        return "Kohaku_Boxes.xml";
    }

    @Override
    public void loadFurtherBoxes(int currentAnimation2) {

    }


}
