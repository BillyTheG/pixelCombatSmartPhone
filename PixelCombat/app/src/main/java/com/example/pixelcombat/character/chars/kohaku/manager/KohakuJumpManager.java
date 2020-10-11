package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.manager.actionManager.JumpManager;

public class KohakuJumpManager extends JumpManager {

    public boolean moveSound = true;

    public KohakuJumpManager(Kohaku character) throws Exception {
        super(character);
    }


    @Override
    protected void leapSound() throws PixelCombatException {
        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_jump", null, true));
        character.notifyObservers(new GameMessage(MessageType.SOUND, "ruffy_jump", null, true));
    }
}
