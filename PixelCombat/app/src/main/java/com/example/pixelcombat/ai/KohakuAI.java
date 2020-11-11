package com.example.pixelcombat.ai;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.character.controller.CharacterController;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.enums.ScreenProperty;

public class KohakuAI extends AIManager {

    public static final int BATTO_JUTSU_OGI = 14;
    public static final int MAIDEN_CALL = 15;

    private float RECOMMEND_DISTANCE_FOR_DISTANCE_ATTACK = 0;


    public KohakuAI(Kohaku character, GameCharacter enemy, CharacterController controller) {
        super(character, enemy, controller);
        this.MAX_ACTIONS += 2;
        this.RECOMMEND_DISTANCE_FOR_DISTANCE_ATTACK = (ScreenProperty.SCREEN_WIDTH - 2 * ScreenProperty.OFFSET_X) / 2;
        initPrio();
    }

    @Override
    public void checkMoreCases() {
        switch (actionId) {
            case BATTO_JUTSU_OGI:
                specialAttack(AttackStatus.BATTO_JUTSU_OGI);
                break;
            case MAIDEN_CALL:
                specialAttack(AttackStatus.MAIDEN_CALL);
                break;
            default:
                break;
        }

    }


    @Override
    public void updateFurtherPriorities() {

        if (character.getPos().distance(enemy.getPos()) >= RECOMMEND_DISTANCE_FOR_DISTANCE_ATTACK) {
            priorities.set(BATTO_JUTSU_OGI, priorities.get(BATTO_JUTSU_OGI) + (10));
            priorities.set(MAIDEN_CALL, priorities.get(MAIDEN_CALL) + (10));
            priorities.set(SPECIALATTACK2, priorities.get(SPECIALATTACK2) + (15));
        }

        if (character.getPos().distance(enemy.getPos()) < RECOMMENDMAXDISTANCE) {
            priorities.set(SPECIALATTACK1, priorities.get(SPECIALATTACK1) + (10));
        }

        priorities.set(SPECIALATTACK3, priorities.get(SPECIALATTACK3) + (1));


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
