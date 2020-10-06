package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.attack.Attack;
import com.example.pixelcombat.character.status.AttackStatus;

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

    public void updateAttacks() {

        if (!isAttacking())
            return;

        switch (attackStatus) {
            case ATTACK1:
                attacks.get("attack1").process();
                attacks.get("attack1").check();
                break;
            case ATTACK2:
                attacks.get("attack2").process();
                attacks.get("attack2").check();
                break;
            case SPECIALATTACK1:
                attacks.get("specialAttack1").process();
                attacks.get("specialAttack1").check();
                break;
            default:
                updateFurtherAttacks();
                return;
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

    public boolean isSpecialAttacking1() {
        return attackStatus == AttackStatus.SPECIALATTACK1;
    }

}