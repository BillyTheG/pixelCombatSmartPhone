package com.example.pixelcombat.character.chars.kohaku;

import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack1;
import com.example.pixelcombat.manager.actionManager.AttackManager;

public class KohakuAttackManager extends AttackManager {

    public KohakuAttackManager(Kohaku character) throws Exception {
        super(character);
    }


    @Override
    public void init() {
        this.getAttacks().put("attack1", new KohakuAttack1(getCharacter(), 0));
    }

    @Override
    public void updateFurtherAttacks() {

    }
}
