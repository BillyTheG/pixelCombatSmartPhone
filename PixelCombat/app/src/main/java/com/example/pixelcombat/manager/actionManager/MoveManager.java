package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.exception.PixelCombatException;

public abstract class MoveManager {

    protected final GameCharacter character;

    public MoveManager(GameCharacter character) {
        this.character = character;
    }

    abstract public void moveStart() throws PixelCombatException;

    abstract public void moveSwitch() throws PixelCombatException;

    abstract public void move() throws PixelCombatException;

    abstract public void moveEnd() throws PixelCombatException;

    protected abstract void resetStats();

}
