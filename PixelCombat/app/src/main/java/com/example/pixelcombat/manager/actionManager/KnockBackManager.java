package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.GlobalStatus;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.math.Vector2d;

public class KnockBackManager {

    private final GameCharacter character;
    public float RECOVER_JUMP = -12.5f;
    protected float requiredMaxVX = 25f;
    protected float requiredLeastVY = 0f;
    protected float requiredMaxVY = 10f;
    protected float leastDistanceToGround = 50;

    public KnockBackManager(GameCharacter character) {
        this.character = character;
    }

    public void knockBack() {

        if (character.getPos().y >= (ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE)) {
            character.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACKFALL);
            return;
        }
        //Requirement for KnockBack Fall
        if (character.getViewManager().isPlaying()) {
            return;
        }


        //Requirement for KnockBack Recover
        if (Math.abs(character.getPhysics().VX) <= requiredMaxVX && Math.abs(character.getPhysics().VY) > requiredLeastVY
                && Math.abs(character.getPhysics().VY) <= requiredMaxVY
                && Math.abs(character.getPos().y - (ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE)) > leastDistanceToGround) {

            character.getPhysics().VX = 0;
            character.getPhysics().VY = RECOVER_JUMP;
            character.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACKRECOVER);
            return;
        }

        character.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACKFALL);

    }

    public void knockBackFall() throws PixelCombatException {
        float minimumVY = 35f;
        if (character.getPos().y >= (ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE)) {

            if (Math.abs(character.getPhysics().VY) > minimumVY) {
                character.getPhysics().VY *= -0.5;
                character.notifyObservers(new GameMessage(MessageType.SHAKE, "", null, false));
                character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, "Dust_Hard_Land;" + character.getPlayer(),
                        new Vector2d(character.getPos().x, ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE + 30), false));
                character.notifyObservers(new GameMessage(MessageType.SOUND, "hard_ground_hit", null, true));
                return;
            }


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
