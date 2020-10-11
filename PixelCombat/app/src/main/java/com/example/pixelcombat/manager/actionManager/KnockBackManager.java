package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.GlobalStatus;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.exception.PixelCombatException;

public class KnockBackManager {

    private final GameCharacter character;
    public float RECOVER_JUMP = -25.5f;
    private float requiredMaxVX = 35f;
    private float requiredLeastVY = 10f;
    private float requiredMaxVY = 150f;
    private float leastDistanceToGround = 1.5f;

    public KnockBackManager(GameCharacter character) {
        this.character = character;
    }

    public void knockBack() {

        //Requirement for KnockBack Fall
        if (character.getViewManager().isPlaying()) {
            return;
        }


        //Requirement for KnockBack Recover
        if (Math.abs(character.getPhysics().VX) <= requiredMaxVX && character.getPhysics().VY > requiredLeastVY
                && character.getPhysics().VY <= requiredMaxVY
                && Math.abs(character.getPos().y - (ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE)) > leastDistanceToGround) {

            character.getPhysics().VX = 0;
            character.getPhysics().VY = RECOVER_JUMP;
            character.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACKRECOVER);
            return;
        }

        character.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACKFALL);

    }

    public void knockBackFall() throws PixelCombatException {
        if (character.getPos().y >= (ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE)) {
            character.notifyObservers(new GameMessage(MessageType.SOUND, "groundhit2", null, true));

            character.getStatusManager().setGlobalStatus(GlobalStatus.INVINCIBLE);
            character.getDisabledManager().reset();
            character.getDashManager().reset();
            character.getDefendManager().resetStats();
            character.getHitManager().resetCharStats();
        }
    }


    public void knockBackRecover() {
        if (!character.getViewManager().isPlaying()) {
            character.getStatusManager().setGlobalStatus(GlobalStatus.ACTIVE);
            character.getStatusManager().setActionStatus(ActionStatus.JUMPFALL);
            character.getDisabledManager().reset();
            character.getDashManager().reset();
            character.getDefendManager().resetStats();
            character.getHitManager().resetCharStats();
        }
    }


}
