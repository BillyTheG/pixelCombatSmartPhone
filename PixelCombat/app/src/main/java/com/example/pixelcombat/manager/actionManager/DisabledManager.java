package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.GlobalStatus;

public class DisabledManager {

    private final int MaxEnergy = 3;
    private int currentEnergy = 3;
    private final GameCharacter character;

    public DisabledManager(GameCharacter character) {
        this.character = character;
    }

    public void reset() {
        currentEnergy = MaxEnergy;
    }

    public void damageEnergy() {
        currentEnergy--;
        if (currentEnergy <= 0) {
            character.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACK);
            reset();
        }
    }

    public void disabled() {
        if (!character.getViewManager().getAnimManager().isPlaying()) {
            character.getStatusManager().setGlobalStatus(GlobalStatus.DISABLEDRECOVER);
        }
    }

    public void disabledRecover() {
        if (!character.getViewManager().getAnimManager().isPlaying()) {
            character.getStatusManager().setGlobalStatus(GlobalStatus.ACTIVE);
        }
    }
}
