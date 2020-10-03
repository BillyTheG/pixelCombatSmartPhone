package com.example.pixelcombat.character.chars.kohaku;

import com.example.pixelcombat.manager.BoxManager;

public class KohakuBoxManager extends BoxManager {


    public KohakuBoxManager(Kohaku character) throws Exception {
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
