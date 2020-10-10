package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack1;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuSpecialAttack2;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuSpecialAttack3;
import com.example.pixelcombat.manager.actionManager.AttackManager;

public class KohakuAttackManager extends AttackManager {

    public KohakuAttackManager(Kohaku character) throws Exception {
        super(character);
    }


    @Override
    public void init() {
        this.getAttacks().put("attack1", new KohakuAttack1(getCharacter(), 0));
        this.getAttacks().put("specialAttack1", new KohakuSpecialAttack2(getCharacter(), 1));
        this.getAttacks().put("specialAttack3", new KohakuSpecialAttack3(getCharacter(), 2));
    }

    @Override
    public void updateFurtherAttacks() {

    }
}
