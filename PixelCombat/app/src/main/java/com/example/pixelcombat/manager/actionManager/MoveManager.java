package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.exception.PixelCombatException;

public abstract class MoveManager {

    protected final GameCharacter character;

    public MoveManager(GameCharacter character) {
        this.character = character;
    }


    abstract public void move() throws PixelCombatException;

    protected abstract void resetStats();

}
