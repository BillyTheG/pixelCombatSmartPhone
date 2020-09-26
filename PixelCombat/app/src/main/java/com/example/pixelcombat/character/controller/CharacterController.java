package com.example.pixelcombat.character.controller;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.enums.KeyCommand;

public class CharacterController {

    private final GameCharacter character;

    public CharacterController(GameCharacter character){
        this.character = character;
    }

    public boolean onKey(KeyCommand key, boolean hold) {

        switch (key) {
            case P1RIGHT:
                return move(hold, true);
            case P1LEFT:
                return move(hold, false);
            case P1JUMP:
                return jump(hold, false);
            default:
                return true;
        }

    }

    public boolean jump(boolean hold, boolean b) {
        if (!character.getStatusManager().canNotJump()) {
            character.getPhysics().VY = (-20f);
            character.getStatusManager().setActionStatus(ActionStatus.JUMP);
            // sound(player.getJumpSound());
        }
        return true;

    }


    public boolean move(boolean hold, boolean right) {
        if (character.getStatusManager().canNotMove())
            return false;

        if (!right) {
            character.getStatusManager().setMovementStatus(MovementStatus.LEFT);
        } else {
            character.getStatusManager().setMovementStatus(MovementStatus.RIGHT);
        }
        float airFactor = 0.9f;
        if (hold) {
            if (!character.getStatusManager().isOnAir()) {
                character.getStatusManager().setActionStatus(ActionStatus.MOVE);
                airFactor = 1f;
            }
            this.character.getPhysics().VX = character.getDirection() * 10f * airFactor;
        } else {
            if (character.getStatusManager().isOnAir()) {
                this.character.getStatusManager().setActionStatus(ActionStatus.JUMPFALL);
            } else {
                character.getStatusManager().setActionStatus(ActionStatus.STAND);
            }

        }
        return true;
    }

}
