package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.exception.PixelCombatException;

public abstract class DashManager {

    protected final GameCharacter character;
    protected final float DASH_SPEED = 30f;
    protected float MAX_DISTANCE = 500f;
    protected float distance = 0f;
    protected boolean switcher = true;

    public DashManager(GameCharacter character) {
        this.character = character;
    }


    public abstract void dash() throws PixelCombatException;

    public void reset() {
        switcher = true;
        distance = 0f;
    }

    public abstract void retreat() throws PixelCombatException;

    public abstract void retreatStop();
}
