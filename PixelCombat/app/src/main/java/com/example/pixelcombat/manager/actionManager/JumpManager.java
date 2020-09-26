package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.enums.ScreenProperty;

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

    public void updateJumpFall() {
        if (character.getPos().y == (ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE)) {
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
