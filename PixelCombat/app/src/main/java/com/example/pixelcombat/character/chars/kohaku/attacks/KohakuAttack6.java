package com.example.pixelcombat.character.chars.kohaku.attacks;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.attack.Attack;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.GlobalStatus;
import com.example.pixelcombat.core.config.SparkConfig;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;

public class KohakuAttack6 extends Attack {

    public KohakuAttack6(GameCharacter character, int id) {
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
            switch (character.getViewManager().getFrameIndex()) {
                case 0:
                    if (isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_attack6", null, true));
                        setSwitcher(false);
                    }
                    break;
                case 2:
                    character.getPhysics().VX = character.getDirection() * 80f;
                    if (!isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_attack1_sonic", null, true));
                        setSwitcher(true);
                    }
                    break;
                case 3:
                    character.getPhysics().VX = character.getDirection() * 80f;
                    if (isSwitcher()) {

                        setSwitcher(false);
                    }
                    break;
                case 7:
                case 12:
                    character.getPhysics().VX = 0;
                    if (isSwitcher()) {
                        character.getHitManager().setHitDelay(false);
                        setSwitcher(false);
                    }
                    break;
                case 6:
                case 11:
                    if (!isSwitcher()) {
                        character.getHitManager().setHitDelay(false);
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_attack6_sonic", null, true));
                        setSwitcher(true);
                    }
                    break;
                default:
                    break;
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
        character.getHitManager().setHitDelay(true);

        float yPush = 0;
        float xPush = 0;

        try {

            BoundingRectangle box = character.getBoxManager().getIntersectionBox();
            character.notifyObservers(new GameMessage(MessageType.SPARK_CREATION, SparkConfig.ATTACK_SPARK + ";test;",
                    new Vector2d(box.getPos().x, box.getPos().y), true));
            character.notifyObservers(new GameMessage(MessageType.SHAKE, "", null, false));

            switch (character.getViewManager().getFrameIndex()) {
                case 2:
                case 3:
                    character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_attack6_hit", null, true));
                    yPush = -20f;
                    xPush = 15f;
                    break;
                case 6:
                case 7:
                    character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_attack5_hit", null, true));
                    yPush = -14f;
                    xPush = -10f;
                    break;
                case 11:
                case 12:
                    character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_attack6_hit", null, true));
                    yPush = -10f;
                    xPush = -5f;
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            Log.e("Error", "The spark could not be created: " + e.getMessage());
        }


        if (!enemy.getStatusManager().isKnockbacked()) {
            // getUser().enemy.timeManager.getDisableTime().setY(Float.valueOf(0.0F));
            enemy.getHitManager().setKnockBackHeight(yPush);
            enemy.getHitManager().setKnockBackRange(xPush);
            enemy.getHitManager().checkOnAir();
            enemy.getStatusManager().setActionStatus(ActionStatus.STAND);
            enemy.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACK);
        } else {
            enemy.getHitManager().comboTouch(yPush, xPush);
        }

    }

    /**
     * This method does the final check, whether the attack reached the final
     * frame and the animation time has been lapsed.
     */
    @Override
    public void checkFinished() {
        resetStats();
    }

    /**
     * @return the attacking state variable
     */
    @Override
    public boolean isAttacking() {
        return character.getAttackManager().isAttacking6();
    }

    @Override
    public void resetStats() {
        setSwitcher(true);
    }

    @Override
    public int getDefendDamage() {
        return 3;
    }
}
