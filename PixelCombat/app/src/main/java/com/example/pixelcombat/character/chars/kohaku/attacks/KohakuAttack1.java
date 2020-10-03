package com.example.pixelcombat.character.chars.kohaku.attacks;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.attack.Attack;

public class KohakuAttack1 extends Attack {


    public KohakuAttack1(GameCharacter character, int id) {
        super(character, id);
    }

    /**
     * This method proceeds the events during the attack phase.
     * For each Frame in an Attack there is a different function
     * involved depending on the attacks specialty.
     */
    @Override
    public void process() {

    }

    /**
     * This method is meant for validating the hit on the enemy. If the enemy
     * was able to block this attack, the procedure will not do anything. If the
     * hit was successful, the enemy will be charged with lifepoint reduction, status change
     * and etc.
     */
    @Override
    public void checkContent() {

    }

    /**
     * This method does the final check, whether the attack reached the final
     * frame and the animation time has been lapsed.
     */
    @Override
    public void checkFinished() {

    }

    /**
     * @return the attacking state variable
     */
    @Override
    public boolean isAttacking() {
        return getCharacter().getAttackManager().isAttacking1();
    }

    @Override
    public void resetStats() {

    }
}
