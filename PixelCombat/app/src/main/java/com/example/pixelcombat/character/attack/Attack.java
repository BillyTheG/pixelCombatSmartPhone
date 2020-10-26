package com.example.pixelcombat.character.attack;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.exception.PixelCombatException;

import lombok.Getter;
import lombok.Setter;

/**
 * The class, that processes the attacks of all Characters
 * Each Character will have a certain number of Attacks.
 *
 * @author BillyG
 */
@Getter
@Setter
public abstract class Attack {

    protected GameCharacter character;
    private int id;
    private float requiredEnergy;
    private int defendDamage = 1;
    protected GameCharacter enemy;
    private boolean attackOnAir = false;
    private boolean switcher = true;

    public Attack(GameCharacter character, int id) {
        this.character = character;
        this.enemy = character.getEnemy();
        this.id = id;
    }

    /**
     * This method proceeds the events during the attack phase.
     * For each Frame in an Attack there is a different function
     * involved depending on the attacks specialty.
     */
    public abstract void process();

    /**
     * This method is meant for validating the hit on the enemy. If the enemy
     * was able to block this attack, the procedure will not do anything. If the
     * hit was successful, the enemy will be charged with lifepoint reduction, status change
     * and etc.
     */
    public abstract void checkContent();

    /**
     * This method does the final check, whether the attack reached the final
     * frame and the animation time has been lapsed.
     */
    public abstract void checkFinished();

    /**
     * @return the attacking state variable
     */
    public abstract boolean isAttacking();


    /**
     * Completes the procedure for checking the result of the attack
     */
    public void check() throws PixelCombatException {
        if (enemy == null) {
            Log.e("Error", "No Enemy was found");
            return;
        }

        if (character.getHitManager().checkDefender(enemy, this) && isAttacking()) {
            if (character.isRight())
                enemy.getStatusManager().setMovementStatus(MovementStatus.LEFT);
            else enemy.getStatusManager().setMovementStatus(MovementStatus.RIGHT);

            checkContent();
        }
        if (!character.getViewManager().isPlaying() && isAttacking()) {
            checkFinished();
            character.getAttackManager().setAttackStatus(AttackStatus.NOT_ATTACKING);
            if (character.getStatusManager().isOnAir()) {
                character.getStatusManager().setActionStatus(ActionStatus.JUMPFALL);
            }
            character.getHitManager().setHitDelay(false);
            switcher = true;
            attackOnAir = false;
        }
    }


    public abstract void resetStats();


}