package com.example.pixelcombat.manager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.GlobalStatus;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.enums.ScreenProperty;


public class StatusManager {

    private GlobalStatus globalStatus = GlobalStatus.ACTIVE;
    private MovementStatus movementStatus = MovementStatus.RIGHT;
    private ActionStatus actionStatus = ActionStatus.STAND;
    private final GameCharacter character;

    public StatusManager(GameCharacter character) {
        this.character = character;

    }


    public boolean isStanding() {
        return actionStatus == ActionStatus.STAND;
    }

    public boolean isJumping() {
        return actionStatus == ActionStatus.JUMP;
    }

    public boolean isJumpFalling() {
        return actionStatus == ActionStatus.JUMPFALL;
    }

    public boolean isJumpRecovering() {
        return actionStatus == ActionStatus.JUMP_RECOVER;
    }

    public boolean isDashing() {
        return actionStatus == ActionStatus.DASHING;
    }

    public boolean isDefending() {
        return actionStatus == ActionStatus.DEFENDING;
    }

    public boolean isCrouching() {
        return actionStatus == ActionStatus.CROUCHING;
    }

    public boolean isDeCrouching() {
        return actionStatus == ActionStatus.DECROUCHING;
    }


    public boolean isAirDefending() {
        return actionStatus == ActionStatus.AIR_DEFENDING;
    }

    public boolean isWinning() {
        return actionStatus == ActionStatus.WIN;
    }

    public boolean isMoving() {
        return actionStatus == ActionStatus.MOVE;
    }

    public boolean isRetreating() {
        return actionStatus == ActionStatus.RETREATING;
    }

    public boolean isIntroing() {
        return actionStatus == ActionStatus.INTRO;
    }

    public boolean isKnockbacked() {
        return globalStatus == GlobalStatus.KNOCKBACK;
    }

    public boolean isDisabled() {
        return globalStatus == GlobalStatus.DISABLED;
    }

    public boolean isKnockBackRecovering() {
        return globalStatus == GlobalStatus.KNOCKBACKRECOVER;
    }

    public boolean isDead() {
        return globalStatus == GlobalStatus.DEAD;
    }

    public boolean isInvincible() {
        return globalStatus == GlobalStatus.INVINCIBLE;
    }

    public boolean isOnAir() {
        return isJumping() || isJumpFalling() || isJumpRecovering() || character.getPos().y < ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE;
    }

    public void update() {

        switch (getGlobalStatus()) {
            case DEAD:
            case INVINCIBLE:
            case ACTIVE:
            case BLINK:
                break;
            case KNOCKBACK:
                character.getKnockBackManager().knockBack();
                return;
            case KNOCKBACKRECOVER:
                character.getKnockBackManager().knockBackRecover();
                return;
            case KNOCKBACKFALL:
                character.getKnockBackManager().knockBackFall();
                return;
            case DISABLED:
                character.getDisabledManager().disabled();
                return;
            case DISABLEDRECOVER:
                character.getDisabledManager().disabledRecover();
                return;

        }


        switch (getActionStatus()) {
            case JUMP:
                character.getJumpManager().updateJump();
                break;
            case JUMPFALL:
                character.getJumpManager().updateJumpFall();
                break;
            case JUMP_RECOVER:
                character.getJumpManager().updateJumpRecover();
                break;
            case CROUCHING:
                character.getCrouchManager().crouch();
                break;
            case DECROUCHING:
                character.getCrouchManager().decrouch();
                break;
            default:
                break;
        }
    }


    public boolean canNotJump() {
        return isOnAir() ||
                notCombatReady();
        //|| character.timeManager.getJumpDelay().getY() < character.timeManager.getDelayHolder().get(TimeState.JUMPDELAY).floatValue();

    }

    public boolean canNotCrouch() {
        return isOnAir() ||
                notCombatReady();
    }

    public boolean canNotMove() {
        return notCombatReady();
        //   || isJumping()
        //   || isOnAir()
        //   || isJumpFalling()
        //   || character.getPos().y < (ScreenProperty.SCREEN_HEIGHT-ScreenProperty.GROUND_LINE-ScreenProperty.OFFSET_y);

    }

    public boolean notCombatReady() {
        return character.getAttackManager().isAttacking()
                || isKnockbacked()
                || isKnockBackFalling()
                || isKnockBackRecovering()
                || isInvincible()
                || isDead()
                || isJumpRecovering()
                || isDisabled()
                || isDisabledRecovering()
                || isDashing()
                || isDefending()
                || isDeCrouching();

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

    public boolean isActive() {
        return globalStatus == GlobalStatus.ACTIVE;
    }

    public boolean isBlinking() {
        return globalStatus == GlobalStatus.BLINK;
    }

    public boolean isDisabledRecovering() {
        return this.globalStatus == GlobalStatus.DISABLEDRECOVER;
    }

    public boolean isKnockBackFalling() {
        return this.globalStatus == GlobalStatus.KNOCKBACKFALL;
    }


    public boolean isMovingRight() {
        return getMovementStatus() == MovementStatus.RIGHT;
    }
}
