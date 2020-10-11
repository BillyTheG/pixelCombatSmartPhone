package com.example.pixelcombat.character.controller;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.enums.KeyCommand;

public class CharacterController {

    public static final float SPEED_BONUS = 15f;
    private final GameCharacter character;
    private float VERTICAL_LEAP = -45f;

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
            case P1DOWN:
                return crouch(hold, false);
            default:
                return true;
        }

    }

    public boolean jump(boolean hold, boolean b) {
        if (!character.getStatusManager().canNotJump()) {
            character.getPhysics().VY = VERTICAL_LEAP;
            character.getStatusManager().setActionStatus(ActionStatus.JUMP);
            // sound(player.getJumpSound());
        }
        return true;

    }

    public boolean crouch(boolean hold, boolean b) {

        if (character.getStatusManager().canNotCrouch())
            return false;

        if (!hold) {
            character.getStatusManager().setActionStatus(ActionStatus.DECROUCHING);
        } else {
            character.getStatusManager().setActionStatus(ActionStatus.CROUCHING);
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
            this.character.getPhysics().VX = character.getDirection() * SPEED_BONUS * airFactor;
        } else {
            if (character.getStatusManager().isOnAir()) {
                this.character.getStatusManager().setActionStatus(ActionStatus.JUMPFALL);
            } else {
                character.getStatusManager().setActionStatus(ActionStatus.STAND);
            }

        }
        return true;
    }


    public boolean attack(AttackStatus attackStates) {
        if (character.getStatusManager().isOnAir()) {
            //check jumpAttacks
            return true;
        }
        if (character.getStatusManager().notCombatReady()) {
            return true;
        }

        if (character.getStatusManager().isMoving()) {   //&& Math.abs(player.physics.VX) == player.physics.maximumSpeed)
            return true;
        }

        character.getStatusManager().setActionStatus(ActionStatus.STAND);
        character.getAttackManager().setAttackStatus(attackStates);
        return true;
    }

    public boolean specialAttack(AttackStatus attackStates) {
        if (character.getStatusManager().isOnAir()) {
            //check jumpAttacks
            return false;
        }
        if (character.getStatusManager().notCombatReady()) {
            return false;
        }

        if (character.getStatusManager().isMoving()) {   //&& Math.abs(player.physics.VX) == player.physics.maximumSpeed)
            return false;
        }

        character.getStatusManager().setActionStatus(ActionStatus.STAND);
        character.getAttackManager().setAttackStatus(attackStates);
        return true;
    }

    public boolean dash() {
        if (character.getStatusManager().canNotDash())
            return true;
        character.getStatusManager().setActionStatus(ActionStatus.DASHING);
        return true;
    }


}
