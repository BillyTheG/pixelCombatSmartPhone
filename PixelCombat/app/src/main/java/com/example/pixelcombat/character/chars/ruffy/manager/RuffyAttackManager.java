package com.example.pixelcombat.character.chars.ruffy.manager;

import com.example.pixelcombat.character.chars.ruffy.Ruffy;
import com.example.pixelcombat.character.chars.ruffy.attacks.RuffyAttack1;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.manager.actionManager.AttackManager;

public class RuffyAttackManager extends AttackManager {

    public RuffyAttackManager(Ruffy character) {
        super(character);
    }


    @Override
    public void init() {
        this.getAttacks().put("attack1", new RuffyAttack1(getCharacter(), 0));
    }

    @Override
    public void updateFurtherAttacks(AttackStatus attackStatus) {

    }
}
