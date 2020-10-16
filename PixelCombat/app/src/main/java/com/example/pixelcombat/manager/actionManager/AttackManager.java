package com.example.pixelcombat.manager.actionManager;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.attack.Attack;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.exception.PixelCombatException;

import java.util.Objects;
import java.util.TreeMap;

import lombok.Getter;

@Getter
public abstract class AttackManager {
    private final GameCharacter character;
    private TreeMap<String, Attack> attacks;
    private AttackStatus attackStatus = AttackStatus.NOT_ATTACKING;

    public AttackManager(GameCharacter character) {
        this.character = character;
        this.attacks = new TreeMap<>();
    }

    public abstract void init();

    public void updateAttacks() throws PixelCombatException {

        if (!isAttacking())
            return;
        try {
            switch (attackStatus) {
                case ATTACK1:
                    Objects.requireNonNull(attacks.get("attack1")).process();
                    Objects.requireNonNull(attacks.get("attack1")).check();
                    break;
                case ATTACK2:
                    Objects.requireNonNull(attacks.get("attack2")).process();
                    Objects.requireNonNull(attacks.get("attack2")).check();
                    break;
                case ATTACK3:
                    Objects.requireNonNull(attacks.get("attack3")).process();
                    Objects.requireNonNull(attacks.get("attack3")).check();
                    break;
                case ATTACK4:
                    Objects.requireNonNull(attacks.get("attack4")).process();
                    Objects.requireNonNull(attacks.get("attack4")).check();
                    break;
                case ATTACK5:
                    Objects.requireNonNull(attacks.get("attack5")).process();
                    Objects.requireNonNull(attacks.get("attack5")).check();
                    break;
                case ATTACK6:
                    Objects.requireNonNull(attacks.get("attack6")).process();
                    Objects.requireNonNull(attacks.get("attack6")).check();
                    break;
                case SPECIALATTACK1:
                    Objects.requireNonNull(attacks.get("specialAttack1")).process();
                    Objects.requireNonNull(attacks.get("specialAttack1")).check();
                    break;
                case SPECIALATTACK2:
                    Objects.requireNonNull(attacks.get("specialAttack2")).process();
                    Objects.requireNonNull(attacks.get("specialAttack2")).check();
                    break;
                case SPECIALATTACK3:
                    Objects.requireNonNull(attacks.get("specialAttack3")).process();
                    Objects.requireNonNull(attacks.get("specialAttack3")).check();
                    break;
                default:
                    updateFurtherAttacks();
            }
        } catch (NullPointerException e) {
            Log.e("Error", "During Processing and Checking an Attack a NullPointer was thrown: " + e.getMessage());
        }
    }

    public abstract void updateFurtherAttacks();

    public void setAttackStatus(AttackStatus attackStatus) {
        this.attackStatus = attackStatus;
        character.getViewManager().updateAnimation();
    }

    public boolean isAttacking() {
        return attackStatus != AttackStatus.NOT_ATTACKING;
    }

    public boolean isAttacking1() {
        return attackStatus == AttackStatus.ATTACK1;
    }

    public boolean isAttacking2() {
        return attackStatus == AttackStatus.ATTACK2;
    }

    public boolean isAttacking3() {
        return attackStatus == AttackStatus.ATTACK3;
    }

    public boolean isAttacking4() {
        return attackStatus == AttackStatus.ATTACK4;
    }

    public boolean isAttacking5() {
        return attackStatus == AttackStatus.ATTACK5;
    }

    public boolean isAttacking6() {
        return attackStatus == AttackStatus.ATTACK6;
    }

    public boolean isSpecialAttacking1() {
        return attackStatus == AttackStatus.SPECIALATTACK1;
    }

    public boolean isSpecialAttacking2() {
        return attackStatus == AttackStatus.SPECIALATTACK2;
    }

    public boolean isSpecialAttacking3() {
        return attackStatus == AttackStatus.SPECIALATTACK3;
    }

}
