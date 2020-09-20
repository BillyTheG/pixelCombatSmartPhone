package com.example.pixelcombat.manager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.character.status.GlobalStatus;
import com.example.pixelcombat.character.status.MovementStatus;


public class StatusManager {

    private GlobalStatus globalStatus = GlobalStatus.ACTIVE;
    private MovementStatus movementStatus = MovementStatus.RIGHT;
    private ActionStatus actionStatus = ActionStatus.STAND;
    private AttackStatus attackStatus = AttackStatus.NOT_ATTACKING;
    private final GameCharacter character;

    public StatusManager(GameCharacter character){
        this.character = character;

    }


    public boolean isStanding(){
        return actionStatus == ActionStatus.STAND;
    }

    public boolean isMoving(){
        return actionStatus == ActionStatus.MOVE;
    }




    public void setActionStatus(ActionStatus actionStatus) {
        this.actionStatus = actionStatus;
        character.getViewManager().updateAnimation();
    }

    public void setGlobalStatus(GlobalStatus globalStatus) {
        this.globalStatus = globalStatus;
        character.getViewManager().updateAnimation();
    }

    public void setMovementStatus(MovementStatus movementStatus) {
        this.movementStatus = movementStatus;
        character.getViewManager().updateAnimation();
    }

    public ActionStatus getActionStatus() {
        return actionStatus;
    }

    public GlobalStatus getGlobalStatus() {
        return globalStatus;
    }

    public MovementStatus getMovementStatus() {
        return movementStatus;
    }

    public AttackStatus getAttackStatus() {
        return attackStatus;
    }

    public boolean isActive() {
        return globalStatus == GlobalStatus.ACTIVE;
    }

    public boolean isBlinking() {
        return globalStatus == GlobalStatus.BLINK;
    }

    public boolean isAttacking() {
        return attackStatus != AttackStatus.NOT_ATTACKING;
    }

    public boolean isMovingRight() {
        return getMovementStatus() == MovementStatus.RIGHT;
    }
}
