package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack1;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack2;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack3;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuSpecialAttack1;
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
        this.getAttacks().put("specialAttack1", new KohakuSpecialAttack1(getCharacter(), 1));
        this.getAttacks().put("specialAttack2", new KohakuSpecialAttack2(getCharacter(), 2));
        this.getAttacks().put("specialAttack3", new KohakuSpecialAttack3(getCharacter(), 3));
        this.getAttacks().put("attack2", new KohakuAttack2(getCharacter(), 4));
        this.getAttacks().put("attack3", new KohakuAttack3(getCharacter(), 5));
    }

    @Override
    public void updateFurtherAttacks() {

    }
}
