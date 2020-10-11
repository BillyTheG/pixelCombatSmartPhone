package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;

public class DefendManager {

    private final int MAX_DEFEND_POINTS = 3;
    private final GameCharacter character;
    private int defendPoints = MAX_DEFEND_POINTS;

    public DefendManager(GameCharacter character) {
        this.character = character;
    }

    public void defend() {

    }

    public void stopDefend() {
        if (!character.getViewManager().getAnimManager().isPlaying()) {
            character.getStatusManager().setActionStatus(ActionStatus.STAND);
        }
    }

    public void damageDefendPoints(int damage) {
        this.defendPoints -= damage;
        if (defendPoints <= 0) {
            defendPoints = MAX_DEFEND_POINTS;
            character.getStatusManager().setActionStatus(ActionStatus.DEFENDSTOP);
        }
    }

    public void resetStats() {
        defendPoints = MAX_DEFEND_POINTS;
    }

}
