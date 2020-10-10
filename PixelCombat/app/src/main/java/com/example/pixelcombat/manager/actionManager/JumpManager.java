package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.exception.PixelCombatException;

public class JumpManager {

    private final GameCharacter character;

    public JumpManager(GameCharacter character) {
        this.character = character;
    }


    public void updateJump() {
        if (character.getPhysics().VY > 0) {
            character.getStatusManager().setActionStatus(ActionStatus.JUMPFALL);
        }
    }

    public void updateJumpFall() throws PixelCombatException {
        if (character.getPos().y == (ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE)) {
            character.notifyObservers(new GameMessage(MessageType.SOUND, "groundrecover", null, true));

            character.getStatusManager().setActionStatus(ActionStatus.JUMP_RECOVER);
        }
    }

    public void updateJumpRecover() {
        character.getPhysics().VX = 0;

        if (!character.getViewManager().getAnimManager().isPlaying()) {
            character.getStatusManager().setActionStatus(ActionStatus.STAND);
        }

    }


}
