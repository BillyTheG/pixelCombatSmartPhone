package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.manager.actionManager.MoveManager;

public class KohakuMoveManager extends MoveManager {

    public boolean moveSound = true;

    public KohakuMoveManager(Kohaku character) throws Exception {
        super(character);
    }


    @Override
    public void move() throws PixelCombatException {
        switch (character.getViewManager().getFrameIndex()) {
            case 1:
            case 5:
            case 9:
                if (moveSound) {
                    character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_step1", null, true));
                    moveSound = false;
                }
                break;

            case 3:
            case 7:
                if (!moveSound) {
                    character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_step2", null, true));
                    moveSound = true;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void resetStats() {
        moveSound = true;
    }
}
