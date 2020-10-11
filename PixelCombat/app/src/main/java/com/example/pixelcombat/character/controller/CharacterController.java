package com.example.pixelcombat.character.controller;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.enums.KeyCommand;

public class CharacterController {

    public static final float SPEED_BONUS = 15f;
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
            case P1DOWN:
                return crouch(hold, false);
            default:
                return true;
        }

    }

    public boolean jump(boolean hold, boolean b) {
        if (!character.getStatusManager().canNotJump()) {
            character.getStatusManager().setActionStatus(ActionStatus.JUMPSTART);
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
        boolean changeDir = false;
        if (!right) {
            changeDir = character.getStatusManager().setMovementStatus(MovementStatus.LEFT);
        } else {
            changeDir = character.getStatusManager().setMovementStatus(MovementStatus.RIGHT);
        }
        float airFactor = 0.9f;
        if (hold) {
            if (!character.getStatusManager().isOnAir()) {
                if (changeDir) {
                    character.getStatusManager().setActionStatus(ActionStatus.MOVESWITCH);
                    return true;
                } else if (!character.getStatusManager().isMoving() &&
                        !character.getStatusManager().isMoveStarting() &&
                        !character.getStatusManager().isMoveSwitching())
                    character.getStatusManager().setActionStatus(ActionStatus.MOVESTART);
                return true;
            }
            this.character.getPhysics().VX = character.getDirection() * SPEED_BONUS * airFactor;
        } else {
            if (character.getStatusManager().isOnAir()) {
                this.character.getStatusManager().setActionStatus(ActionStatus.JUMPFALL);
            } else {
                character.getStatusManager().setActionStatus(ActionStatus.MOVEEND);
            }

        }
        return true;
    }

    public boolean defend(boolean hold, boolean right) {
        if (character.getStatusManager().canNotDefend())
            return false;


        if (!hold) {
            character.getStatusManager().setActionStatus(ActionStatus.DEFENDSTOP);
        } else {
            character.getStatusManager().setActionStatus(ActionStatus.DEFENDING);
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

    public void checkDashOrRetreat(GameCharacter enemy, boolean right) {

        if (character.getStatusManager().canNotDash())
            return;

        try {
            if (character.getPos().x < enemy.getPos().x && right) {
                character.getStatusManager().setActionStatus(ActionStatus.DASHING);
                return;
            }
            if (character.getPos().x < enemy.getPos().x && !right) {
                character.getStatusManager().setActionStatus(ActionStatus.RETREATING);
                return;
            }
            if (character.getPos().x >= enemy.getPos().x && right) {
                character.getStatusManager().setActionStatus(ActionStatus.RETREATING);
                return;
            }
            if (character.getPos().x >= enemy.getPos().x && !right) {
                character.getStatusManager().setActionStatus(ActionStatus.DASHING);
                return;
            }
        } catch (Exception e) {
            Log.e("Error", "the Dash or Retreat Button has been interrupted: " + e.getMessage());
        }
    }

}
