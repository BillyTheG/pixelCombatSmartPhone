package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.manager.BoxManager;

public class KohakuBoxManager extends BoxManager {


    public final int BATTOJUTSUOGI = 38;
    public final int MAIDENCALL = 39;

    public KohakuBoxManager(Kohaku character) {
        super(character);
    }


    @Override
    protected String getFileName() {
        return "Kohaku_Boxes.xml";
    }

    @Override
    public void loadFurtherBoxes(int currentAnimation2) {

        switch (character.getViewManager().getAnimation()) {
            case BATTOJUTSUOGI:
                updateBoxSeq(BATTOJUTSUOGI, "battoJutsu");
                break;
            case MAIDENCALL:
                updateBoxSeq(MAIDENCALL, "maidenCall");
                break;
            default:
                updateBoxSeq(STAND, "stand");
                break;
        }
    }


}
