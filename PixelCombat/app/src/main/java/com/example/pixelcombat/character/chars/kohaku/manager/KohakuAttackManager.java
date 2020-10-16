package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack1;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack2;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack3;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack4;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack5;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack6;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuSpecialAttack1;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuSpecialAttack2;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuSpecialAttack3;
import com.example.pixelcombat.manager.actionManager.AttackManager;

public class KohakuAttackManager extends AttackManager {

    public KohakuAttackManager(Kohaku character) {
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
        this.getAttacks().put("attack4", new KohakuAttack4(getCharacter(), 6));
        this.getAttacks().put("attack5", new KohakuAttack5(getCharacter(), 7));
        this.getAttacks().put("attack6", new KohakuAttack6(getCharacter(), 8));
    }

    @Override
    public void updateFurtherAttacks() {

    }
}
