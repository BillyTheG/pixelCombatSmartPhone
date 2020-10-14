package com.example.pixelcombat.ai;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.controller.CharacterController;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.character.status.MovementStatus;

import java.util.ArrayList;
import java.util.Random;


public abstract class AIManager {

    public static final float RECOMMENDENERGY = 50;
    public static final float RECOMMENDMAXDISTANCE = 300f;
    public static final int STAND = 0;
    public static final int MOVE = 1;
    public static final int JUMPING = 2;
    public static final int DASHING = 3;
    public static final int DEFENDING = 4;
    public static final int BASICATTACK = 5;
    public static final int BASICATTACK1 = 6;
    public static final int BASICATTACK21 = 7;
    public static final int BASICATTACK22 = 8;
    public static final int BASICATTACK23 = 9;
    public static final int JUMPATTACK = 10;
    public static final int MAXACTIONS = 8;
    private static final long maxPossiibleDelayTime = 200l;
    protected final GameCharacter enemy;
    protected CharacterController controller;
    protected GameCharacter character;
    protected long actionDelay = 50l;
    protected long actionBufferTime = 0l;
    protected int actionId;
    protected ArrayList<Integer> priorities;
    private Random random = new Random();
    private boolean previousDirection = false;
    private long lastFrame = 0;

    public AIManager(GameCharacter character, GameCharacter enemy, CharacterController controller) {
        this.character = character;
        this.enemy = enemy;
        this.controller = controller;
        initPrio();
    }

    private void initPrio() {
        this.priorities = new ArrayList<>();
        for (int i = 0; i < MAXACTIONS; i++)
            priorities.add(1);

        lastFrame = System.currentTimeMillis();

    }

    public void update() {

        if (enemy.getStatusManager().isDead()) //   || controller.uncontrollable())
            return;

        actionBufferTime += (System.currentTimeMillis() - lastFrame);

        if (actionBufferTime >= actionDelay) {

            if (character.getStatusManager().isDefending())
                controller.defend(false, character.isRight());

            //character.calculateNewProbabilities();
            actionBufferTime = 0l;
            updatePriorities();
            actionId = ActionProbabilityHandler.computeActionSquareId(priorities);
            long randomOffset = random.nextLong() * 500l;

            actionDelay = (actionDelay + randomOffset) % maxPossiibleDelayTime;
            doAction();

        }


        lastFrame = System.currentTimeMillis();

    }

    private void doAction() {


        if (!character.getStatusManager().isActive())
            return;


        switch (actionId) {
            case STAND:
                stand();
                break;
            case MOVE:
                moveToEnemy();
                break;
            case JUMPING:
                jump();
                break;
            case DASHING:
                dash();
                break;
            case DEFENDING:
                defend();
                break;
            case BASICATTACK:
                attack(AttackStatus.ATTACK1);
                break;
            case BASICATTACK1:
                attack(AttackStatus.ATTACK2);
                break;
            case BASICATTACK21:
                attack(AttackStatus.ATTACK3);
                break;
            default:
                checkMoreCases();
                break;
        }

    }


    public abstract void checkMoreCases();

    private void defend() {
        if (!character.getAttackManager().isAttacking() && !character.getStatusManager().isDashing()) {
            faceToEnemy();
        }


        controller.defend(true, character.isRight());

    }

    protected void specialAttack(AttackStatus attackState, String attackStateString) {
        if (!character.getAttackManager().isAttacking() && !character.getStatusManager().isDashing()) {
            faceToEnemy();
        }
        controller.specialAttack(attackState);
    }


    private void dash() {
        if (!character.getAttackManager().isAttacking() && !character.getStatusManager().isDashing())
            faceToEnemy();
        else {
            actionBufferTime = actionDelay;
            return;
        }
        controller.dash();


    }


    private void attack(AttackStatus attackStatus) {

        if (!character.getStatusManager().isActive())
            return;

        if (!character.getAttackManager().isAttacking() && !character.getStatusManager().isDashing()) {
            faceToEnemy();
        }


        if (character.getPos().distance(enemy.getPos()) > RECOMMENDMAXDISTANCE) {
            actionBufferTime = actionDelay;
            return;
        } else {
            if (character.getStatusManager().isMoving())
                character.getController().move(false, character.isRight());
        }

        controller.attack(attackStatus);

    }

    protected void faceToEnemy() {
        if (character.getPos().x <= enemy.getPos().x) {
            if (behindEnemy()) {
                character.getStatusManager().setMovementStatus(MovementStatus.RIGHT);
                return;
            }

        } else if (behindEnemy()) {
            character.getStatusManager().setMovementStatus(MovementStatus.LEFT);
        }

    }

    public boolean behindEnemy() {

        if (character.getPos().x <= enemy.getPos().x && character.getDirection() == -1.0f ||
                character.getPos().x >= enemy.getPos().x && character.getDirection() == 1.0f)
            return true;
        return false;
    }


    private void stand() {
        //character.getStatusManager().setActionStates(ActionStates.STAND);
    }

    private void jump() {
        if (character.getAttackManager().isAttacking() || character.getStatusManager().isDashing()) {
            actionBufferTime = actionDelay;
            return;
        }
        controller.jump(false, false);
        actionBufferTime = actionDelay;
    }

    private void moveToEnemy() {

        if (!character.getAttackManager().isAttacking() && !character.getStatusManager().canNotMove()) {
            faceToEnemy();
        } else {
            actionBufferTime = actionDelay;
            return;
        }

        if (character.getAttackManager().isAttacking())
            return;

        boolean hold;
        boolean enemyIsRight = (character.getPos().x <= enemy.getPos().x);

        if (enemyIsRight)
            character.getStatusManager().setMovementStatus(MovementStatus.RIGHT);
        else
            character.getStatusManager().setMovementStatus(MovementStatus.LEFT);

        if (character.getPos().distance(enemy.getPos()) <= RECOMMENDMAXDISTANCE) {
            controller.move(false, character.isRight());
            actionBufferTime = actionDelay;
            return;
        }

        if (previousDirection != enemyIsRight)
            hold = false;
        else
            hold = true;

        controller.move(hold, character.isRight());
        previousDirection = enemyIsRight;

    }

    public void updatePriorities() {

        for (int i = 0; i < priorities.size(); i++)
            priorities.set(i, 1);


        if (character.getPos().distance(enemy.getPos()) > RECOMMENDMAXDISTANCE) {
            priorities.set(MOVE, priorities.get(MOVE) + 10);
            priorities.set(DASHING, priorities.get(DASHING) + 2);
        } else // Character is within range
        {
            int factor = 0;
            priorities.set(MOVE, 1);
            priorities.set(BASICATTACK, priorities.get(BASICATTACK) + (10 + factor));
            priorities.set(BASICATTACK1, priorities.get(BASICATTACK1) + (10 + factor));
            priorities.set(BASICATTACK21, priorities.get(BASICATTACK21) + (10 + factor));
        }


        if (enemy.getStatusManager().isOnAir())
            priorities.set(JUMPING, priorities.get(JUMPING) + 10);

        //  checkEnergy
        // -Use SpecialAttack if sufficient Energy
		
		/*if(10>= RECOMMENDENERGY)
		{
			priorities.set(SPECIALATTACK2, priorities.get(SPECIALATTACK2)+2);
			priorities.set(SPECIALATTACK3, priorities.get(SPECIALATTACK3)+2);
			priorities.set(SPECIALATTACK4, priorities.get(SPECIALATTACK4)+2);
			priorities.set(SPECIALATTACK5, priorities.get(SPECIALATTACK5)+2);
			priorities.set(SPECIALATTACK6, priorities.get(SPECIALATTACK6)+2);
			priorities.set(SPECIALATTACK7, priorities.get(SPECIALATTACK7)+2);
			updatePatternsForSpecialAttackPriorities();
		}
		else
		{
			int factor 	= 0;
			priorities.set(BASICATTACK, priorities.get(BASICATTACK)+(5+factor));
			priorities.set(BASICATTACK1, priorities.get(BASICATTACK1)+(5+factor));
			priorities.set(BASICATTACK21, priorities.get(BASICATTACK21)+(5+factor));
			priorities.set(BASICATTACK22, priorities.get(BASICATTACK22)+(5+factor));
			priorities.set(BASICATTACK23, priorities.get(BASICATTACK23)+(3+factor));
			priorities.set(SPECIALATTACK1, priorities.get(SPECIALATTACK1)+3 + factor);
			
			
		}
		*/

        // check Defend
        if (enemy.getAttackManager().isAttacking()) {
            priorities.set(JUMPING, priorities.get(JUMPING) + 5);
            priorities.set(DASHING, priorities.get(DASHING) + 10);
            priorities.set(DEFENDING, priorities.get(DEFENDING) + 15);
        }
		/*
		if(enemy.getStatusManager().isBlinking() || enemy.getStatusManager().isInvincible())
		{
			priorities.set(STAND, 40);
			priorities.set(BASICATTACK, 1);
			priorities.set(BASICATTACK1,1);
			priorities.set(BASICATTACK21,1);
			priorities.set(BASICATTACK22, 1);
			priorities.set(BASICATTACK23, 1);
			resetSpecs();
		}*/
        updateFurtherPriorities();

    }

    protected void resetSpecs() {

    }

    public abstract void updatePatternsForSpecialAttackPriorities();

    public abstract void updateFurtherPriorities();


}
