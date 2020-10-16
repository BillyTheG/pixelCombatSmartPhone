package com.example.pixelcombat.character.chars.kohaku.attacks;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.attack.Attack;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.math.Vector2d;

import static com.example.pixelcombat.core.config.ProjectileConfig.KOHAKU_SPECIAL_ATTACK_PROJECTILE_BOTTLE;

public class KohakuSpecialAttack3 extends Attack {

    private boolean firstJumpDone = false;
    private boolean flytowardsDone = false;

    private float startLevel = 0f;
    private float buffer = 0f;

    public KohakuSpecialAttack3(GameCharacter character, int id) {
        super(character, id);
    }

    /**
     * This method proceeds the events during the attack phase.
     * For each Frame in an Attack there is a different function
     * involved depending on the attacks specialty.
     */
    @Override
    public void process() {
        try {

            if (!firstJumpDone) {
                startLevel = character.getPos().y;
                buffer = startLevel;
                firstJumpDone = true;
            }

            float FLY_SPEED_Y = 3;
            float FLY_SPEED_X = 10f;

            float MIN_DISTANCE_Y = 200f;
            float MIN_DISTANCE_X = 800f;

            switch (character.getViewManager().getFrameIndex()) {
                case 0:
                    if (isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_special_attack3",
                                null, character.isRight()));
                        setSwitcher(false);
                    }
                    break;
                case 1:
                    if (!isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, "Kohaku_Special2_Attack_Dust;" + character.getPlayer() + ";",
                                character.getPos(), character.isRight()));
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_special_attack3_rise",
                                null, character.isRight()));
                        setSwitcher(true);
                    }
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    character.getPhysics().VY -= FLY_SPEED_Y;
                    break;
                case 8:
                    character.getPhysics().VY -= FLY_SPEED_Y;
                    if (Math.abs(character.getPos().y - startLevel) < MIN_DISTANCE_Y && character.getViewManager().getAnimManager().animationAlmostFinished(30)) {
                        character.getViewManager().resetFrameIndexTo(3);
                    }
                    break;
                case 10:
                    if (!flytowardsDone) {
                        startLevel = character.getPos().x;
                        buffer = startLevel;
                        flytowardsDone = true;
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_special_attack3_laughter", null, character.isRight()));
                    }

                    character.getPhysics().VX = character.getDirection() * FLY_SPEED_X;
                    buffer += Math.abs(character.getPhysics().VX);
                    break;
                case 11:
                    if (isSwitcher()) {
                        Vector2d pos = new Vector2d(getCharacter().getPos().x + getCharacter().getDirection() * 50, character.getPos().y);
                        character.notifyObservers(new GameMessage(MessageType.PROJECTILE_CREATION, KOHAKU_SPECIAL_ATTACK_PROJECTILE_BOTTLE + ";" + character.getPlayer() + ";",
                                pos, character.isRight()));

                        setSwitcher(false);
                    }

                    character.getPhysics().VX = character.getDirection() * 10f;
                    buffer += Math.abs(character.getPhysics().VX);
                    break;
                case 12:
                case 13:
                    character.getPhysics().VX = character.getDirection() * 10f;
                    buffer += Math.abs(character.getPhysics().VX);
                    break;
                case 14:
                    if ((buffer - startLevel) < MIN_DISTANCE_X && character.getViewManager().getAnimManager().animationAlmostFinished(35)) {
                        character.getViewManager().resetFrameIndexTo(10);
                        character.getPhysics().VX = character.getDirection() * 10f;
                        buffer += Math.abs(character.getPhysics().VX);
                        setSwitcher(true);
                    }
                    break;
                case 15:
                    character.getPhysics().VX = 0f;
                    break;
                default:
                    break;
            }


            if (character.getViewManager().getFrameIndex() > 9) {
                character.getPhysics().VY = 0f;
            }

        } catch (Exception e) {
            Log.e("Error", "In processing the attack, following excpetion was thrown: " + e.getMessage());
        }

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
        setSwitcher(true);
        flytowardsDone = false;
        firstJumpDone = false;
        buffer = 0f;

        if (character.getStatusManager().isOnAir()) {
            character.getStatusManager().setActionStatus(ActionStatus.JUMPFALL);
        }

    }

    /**
     * @return the attacking state variable
     */
    @Override
    public boolean isAttacking() {
        return character.getAttackManager().isSpecialAttacking3();
    }

    @Override
    public void resetStats() {
        setSwitcher(true);
        flytowardsDone = false;
        firstJumpDone = false;
    }
}
