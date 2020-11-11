package com.example.pixelcombat.character.chars.shana.manager;

import com.example.pixelcombat.character.chars.shana.Shana;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.manager.actionManager.MoveManager;

import static com.example.pixelcombat.character.controller.CharacterController.SPEED_BONUS;

public class ShanaMoveManager extends MoveManager {

    public boolean moveSound = true;

    public ShanaMoveManager(Shana character) {
        super(character);
    }


    @Override
    public void moveStart() {
        character.getStatusManager().setActionStatus(ActionStatus.MOVE);

    }

    @Override
    public void moveSwitch() {
        if (!character.getViewManager().getAnimManager().isPlaying()) {
            character.getStatusManager().setActionStatus(ActionStatus.MOVESTART);
        }
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
        this.character.getPhysics().VX = character.getDirection() * SPEED_BONUS;
    }

    @Override
    public void moveEnd() {
        if (!character.getViewManager().getAnimManager().isPlaying()) {
            character.getStatusManager().setActionStatus(ActionStatus.STAND);
        }
    }

    @Override
    protected void resetStats() {
        moveSound = true;
    }
}
