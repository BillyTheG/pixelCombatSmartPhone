package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;

public class DashManager {

    protected final GameCharacter character;
    protected final float DASH_SPEED = 30f;
    protected boolean switcher = true;

    public DashManager(GameCharacter character) {
        this.character = character;
    }

    public void dash() {

    }

    public void stopDash() {

    }

    public void reset() {
        switcher = true;
    }


}
