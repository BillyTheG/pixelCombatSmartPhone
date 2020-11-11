package com.example.pixelcombat.character.chars.shana.manager;

import com.example.pixelcombat.character.chars.shana.Shana;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.manager.actionManager.JumpManager;

public class ShanaJumpManager extends JumpManager {

    public ShanaJumpManager(Shana character) throws Exception {
        super(character);
    }


    @Override
    protected void leapSound() throws PixelCombatException {
        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_jump", null, true));
        character.notifyObservers(new GameMessage(MessageType.SOUND, "ruffy_jump", null, true));
    }
}
