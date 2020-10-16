package com.example.pixelcombat.manager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.GlobalStatus;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.exception.PixelCombatException;


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

    public void update() throws PixelCombatException {

        switch (getGlobalStatus()) {
            case INVINCIBLE:
                invincible();
                return;
            case DEAD:
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
            case RETREATING:
                character.getDashManager().retreat();
                break;
            case RETREATING_STOP:
                character.getDashManager().retreatStop();
                break;
            case JUMPSTART:
                character.getJumpManager().updateJumpStart();
                break;
            case JUMP:
                character.getJumpManager().updateJump();
                break;
            case DASHING:
                character.getDashManager().dash();
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
            case DEFENDING:
                character.getDefendManager().defend();
                break;
            case DEFENDSTOP:
                character.getDefendManager().stopDefend();
                break;
            case MOVESWITCH:
                character.getMoveManager().moveSwitch();
                break;
            case MOVE:
                character.getMoveManager().move();
                break;
            case MOVESTART:
                character.getMoveManager().moveStart();
                break;
            case MOVEEND:
                character.getMoveManager().moveEnd();
                break;
            default:
                break;
        }
    }

    private void invincible() {
        if (!character.getViewManager().isPlaying()) {
            setGlobalStatus(GlobalStatus.ACTIVE);
            setActionStatus(ActionStatus.STAND);
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

    public boolean canNotDefend() {
        return isOnAir() ||
                notCombatReady();
    }

    public boolean canNotDash() {
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
                || isJumpStarting()
                || isJumpFalling()
                || isJumpRecovering()
                || isDisabled()
                || isDisabledRecovering()
                || isDashing()
                || isRetreating()
                || isRetreatStopping();

    }

    private boolean isJumpStarting() {
        return this.actionStatus == ActionStatus.JUMPSTART;
    }

    public boolean isMoveEnding() {
        return this.actionStatus == ActionStatus.MOVEEND;
    }

    public boolean isMoveStarting() {
        return this.actionStatus == ActionStatus.MOVESTART;
    }

    public boolean isMoveSwitching() {
        return this.actionStatus == ActionStatus.MOVESWITCH;
    }

    public boolean isDefendStopping() {
        return this.actionStatus == ActionStatus.DEFENDSTOP;
    }

    public boolean landed() {
        return character.getPos().y == (ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE);
    }

    public void setActionStatus(ActionStatus actionStatus) {
        this.actionStatus = actionStatus;
        character.getViewManager().updateAnimation();
    }

    public void setGlobalStatus(GlobalStatus globalStatus) {
        this.globalStatus = globalStatus;
        character.getViewManager().updateAnimation();
    }

    public boolean setMovementStatus(MovementStatus movementStatus) {
        boolean changed = true;
        if (this.movementStatus.equals(movementStatus))
            changed = false;
        this.movementStatus = movementStatus;
        character.getViewManager().updateAnimation();
        return changed;
    }

    public void swapDirections() {
        if (movementStatus == MovementStatus.RIGHT)
            setMovementStatus(MovementStatus.LEFT);
        else
            setMovementStatus(MovementStatus.RIGHT);
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


    public boolean shouldNotBeDecelerated() {
        return isKnockbacked() || isDashing();
    }

    public boolean isRetreatStopping() {
        return actionStatus == ActionStatus.RETREATING_STOP;
    }

}
