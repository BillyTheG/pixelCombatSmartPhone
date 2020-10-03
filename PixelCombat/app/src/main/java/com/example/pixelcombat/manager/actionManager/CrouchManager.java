package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;

public class CrouchManager {

    private final GameCharacter character;

    public CrouchManager(GameCharacter character) {
        this.character = character;
    }

    public void crouch() {

    }

    public void decrouch() {
        if (!character.getViewManager().getAnimManager().isPlaying()) {
            character.getStatusManager().setActionStatus(ActionStatus.STAND);
        }
    }


}
