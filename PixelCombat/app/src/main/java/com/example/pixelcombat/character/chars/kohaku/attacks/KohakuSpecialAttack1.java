package com.example.pixelcombat.character.chars.kohaku.attacks;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.attack.Attack;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.GlobalStatus;
import com.example.pixelcombat.core.config.DustConfig;
import com.example.pixelcombat.core.config.SparkConfig;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;

public class KohakuSpecialAttack1 extends Attack {


    public KohakuSpecialAttack1(GameCharacter character, int id) {
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
                case 2:
                    if (!isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_sword_out", null, true));
                        setSwitcher(true);
                    }
                    break;
                case 0:
                    if (isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_special_attack", null, true));
                        setSwitcher(false);
                    }
                    break;
                case 6:
                    if (isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SPARK_CREATION, SparkConfig.KOHAKU_SPECIAL_ATTACK_SPARK + ";test;",
                                new Vector2d(character.getPos().x, character.getPos().y), character.isRight()));

                        character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.KOHAKU_SPECIAL_ATTACK_SPARK + ";test;",
                                new Vector2d(character.getPos().x, character.getPos().y), character.isRight()));

                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_sword_slash", null, true));

                        Vector2d pos = new Vector2d(getCharacter().getPos().x + getCharacter().getDirection() * 200, character.getPos().y);

                        character.notifyObservers(new GameMessage(MessageType.PROJECTILE_CREATION, "Kohaku_Projectile_Horizontal" + ";test;",
                                pos, character.isRight()));

                        setSwitcher(false);
                    }
                    break;
                case 8:
                    if (!isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_special_attack_end", null, true));
                        setSwitcher(true);
                    }
                    break;
                case 20:
                    if (isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_sword_back", null, true));
                        setSwitcher(false);
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
        try {
            BoundingRectangle box = character.getBoxManager().getIntersectionBox();
            character.notifyObservers(new GameMessage(MessageType.SPARK_CREATION, SparkConfig.BLOOD_SPLASH_SPARK + ";test;",
                    new Vector2d(box.getPos().x, box.getPos().y), character.isRight()));

            character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_sword_hit", null, true));

        } catch (Exception e) {
            Log.e("Error", "The spark could not be created: " + e.getMessage());
        }
        if (!enemy.getStatusManager().isKnockbacked()) {
            // getUser().enemy.timeManager.getDisableTime().setY(Float.valueOf(0.0F));
            enemy.getHitManager().setKnockBackHeight(-27.0F);
            enemy.getHitManager().setKnockBackRange(10.0F);
            enemy.getHitManager().checkOnAir();
            enemy.getStatusManager().setActionStatus(ActionStatus.STAND);
            enemy.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACK);
        } else {
            enemy.getHitManager().comboTouch(-20.0F, 10.0F);
        }

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
        return character.getAttackManager().isSpecialAttacking1();
    }

    @Override
    public void resetStats() {
        setSwitcher(true);
    }
}
