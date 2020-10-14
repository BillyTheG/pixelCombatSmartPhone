package com.example.pixelcombat.ai;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.character.controller.CharacterController;

public class KohakuAI extends AIManager {


    public KohakuAI(Kohaku character, GameCharacter enemy, CharacterController controller) {
        super(character, enemy, controller);
    }

    @Override
    public void checkMoreCases() {
        switch (actionId) {

            default:
                break;
        }

    }


    @Override
    public void updateFurtherPriorities() {
        //priorities.set(BASICATTACK23, 1);

    }

    @Override
    public void updatePatternsForSpecialAttackPriorities() {


        if (character.getPos().distance(enemy.getPos()) > RECOMMENDMAXDISTANCE) {
            resetSpecs();
	/*		priorities.set(SPECIALATTACK2, priorities.get(SPECIALATTACK2)+20);
			priorities.set(SPECIALATTACK5, priorities.get(SPECIALATTACK5)+10);
			priorities.set(SPECIALATTACK7, priorities.get(SPECIALATTACK7)+5);*/
        } else {
            resetSpecs();
		/*	priorities.set(SPECIALATTACK4, priorities.get(SPECIALATTACK4)+10);
			priorities.set(SPECIALATTACK6, priorities.get(SPECIALATTACK6)+5);*/
        }

        if (enemy.getStatusManager().isJumping() || enemy.getStatusManager().isOnAir()) {
            resetSpecs();
	/*		priorities.set(SPECIALATTACK4, priorities.get(SPECIALATTACK4)+5);
			priorities.set(SPECIALATTACK3, priorities.get(SPECIALATTACK3)+10);*/
        }

        if (character.getStatusManager().isJumping() && enemyIsNear()) {
            resetSpecs();
		/*	priorities.set(SPECIALATTACK4, priorities.get(SPECIALATTACK4)+25);
			priorities.set(SPECIALATTACK6, priorities.get(SPECIALATTACK6)+25);*/


        }

    }


    private boolean enemyIsNear() {
        return (character.getPos().distance(enemy.getPos()) <= 3f);
    }

}
