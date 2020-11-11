package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAirAttack1;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAirAttack2;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAirAttack3;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAirAttack4;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAirAttack5;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAirAttack6;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack1;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack2;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack3;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack4;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack5;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuAttack6;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuBattoJutsuOgi;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuMaidenCall;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuSpecialAttack1;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuSpecialAttack2;
import com.example.pixelcombat.character.chars.kohaku.attacks.KohakuSpecialAttack3;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.manager.actionManager.AttackManager;

import java.util.Objects;

public class KohakuAttackManager extends AttackManager {

    private Kohaku kohaku;

    public KohakuAttackManager(Kohaku character) {
        super(character);
        this.kohaku = character;
    }


    @Override
    public void init() {
        this.getAttacks().put("attack1", new KohakuAttack1(getCharacter(), 0));
        this.getAttacks().put("specialAttack1", new KohakuSpecialAttack1(getCharacter(), 1));
        this.getAttacks().put("specialAttack2", new KohakuSpecialAttack2(kohaku, 2));
        this.getAttacks().put("specialAttack3", new KohakuSpecialAttack3(getCharacter(), 3));
        this.getAttacks().put("attack2", new KohakuAttack2(getCharacter(), 4));
        this.getAttacks().put("attack3", new KohakuAttack3(getCharacter(), 5));
        this.getAttacks().put("attack4", new KohakuAttack4(getCharacter(), 6));
        this.getAttacks().put("attack5", new KohakuAttack5(getCharacter(), 7));
        this.getAttacks().put("attack6", new KohakuAttack6(getCharacter(), 8));
        this.getAttacks().put("airAttack1", new KohakuAirAttack1(getCharacter(), 9));
        this.getAttacks().put("airAttack2", new KohakuAirAttack2(getCharacter(), 9));
        this.getAttacks().put("airAttack3", new KohakuAirAttack3(getCharacter(), 9));
        this.getAttacks().put("airAttack4", new KohakuAirAttack4(getCharacter(), 9));
        this.getAttacks().put("airAttack5", new KohakuAirAttack5(getCharacter(), 9));
        this.getAttacks().put("airAttack6", new KohakuAirAttack6(getCharacter(), 9));
        this.getAttacks().put("battoJutsu", new KohakuBattoJutsuOgi(kohaku, 9));
        this.getAttacks().put("maidenCall", new KohakuMaidenCall(kohaku, 9));
    }

    @Override
    public void updateFurtherAttacks(AttackStatus attackStatus) throws PixelCombatException {
        switch (attackStatus) {
            case BATTO_JUTSU_OGI:
                Objects.requireNonNull(this.getAttacks().get("battoJutsu")).process();
                Objects.requireNonNull(this.getAttacks().get("battoJutsu")).check();
                break;
            case MAIDEN_CALL:
                Objects.requireNonNull(this.getAttacks().get("maidenCall")).process();
                Objects.requireNonNull(this.getAttacks().get("maidenCall")).check();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean cannotUseOnAir(AttackStatus attackStates) {
        return true;
    }


    public boolean isBattoJutsuOgiIng() {
        return getAttackStatus() == AttackStatus.BATTO_JUTSU_OGI;
    }

    public boolean isMaidenCalling() {
        return getAttackStatus() == AttackStatus.MAIDEN_CALL;
    }
}
