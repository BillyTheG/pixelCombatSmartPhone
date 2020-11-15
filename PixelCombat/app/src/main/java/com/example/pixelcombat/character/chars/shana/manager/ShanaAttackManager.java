package com.example.pixelcombat.character.chars.shana.manager;

import com.example.pixelcombat.character.chars.shana.Shana;
import com.example.pixelcombat.character.chars.shana.attacks.ShanaAttack1;
import com.example.pixelcombat.character.chars.shana.attacks.ShanaAttack4;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.manager.actionManager.AttackManager;

public class ShanaAttackManager extends AttackManager {

    private Shana kohaku;

    public ShanaAttackManager(Shana character) {
        super(character);
        this.kohaku = character;
    }


    @Override
    public void init() {
        this.getAttacks().put("attack1", new ShanaAttack1(getCharacter(), 0));
        this.getAttacks().put("attack4", new ShanaAttack4(getCharacter(), 0));
    }

    @Override
    public void updateFurtherAttacks(AttackStatus attackStatus) {
        switch (attackStatus) {
            default:
                break;
        }
    }

    @Override
    public boolean cannotUseOnAir(AttackStatus attackStates) {
        return true;
    }


}
