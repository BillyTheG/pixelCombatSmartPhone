package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.GlobalStatus;

public abstract class DisabledManager {

    protected final GameCharacter character;
    protected final int MaxEnergy = 3;
    private int currentEnergy = 3;
    private boolean switcher = true;

    public DisabledManager(GameCharacter character) {
        this.character = character;
    }

    public void reset() {
        currentEnergy = MaxEnergy;
        switcher = true;
    }

    public void damageEnergy() {
        currentEnergy--;
        if (currentEnergy <= 0) {
            character.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACK);
            character.getPhysics().VY += character.getHitManager().getKnockBackHeight_df() * 1.5f;
            character.getPhysics().VX += (-character.getDirection() * character.getHitManager().getKnockBackRange_df()) * 20f;
            character.getPhysics().update();
            reset();
        }
    }

    public void disabled() {


        if (switcher) {
            cry();
            switcher = false;
        }

        if (!character.getViewManager().getAnimManager().isPlaying()) {
            switcher = true;
            character.getStatusManager().setGlobalStatus(GlobalStatus.DISABLEDRECOVER);
        }
    }

    public abstract void cry();

    public void disabledRecover() {
        if (!character.getViewManager().getAnimManager().isPlaying()) {
            character.getStatusManager().setGlobalStatus(GlobalStatus.ACTIVE);
            character.getHitManager().resetCharStats();
            character.getDefendManager().resetStats();
            character.getDashManager().reset();
            reset();
        }
    }
}
