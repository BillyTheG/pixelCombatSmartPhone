package com.example.pixelcombat.character.chars.shana.attacks;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.attack.Attack;
import com.example.pixelcombat.core.config.DustConfig;
import com.example.pixelcombat.core.config.ProjectileConfig;
import com.example.pixelcombat.core.config.SparkConfig;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;

public class ShanaAttack4 extends Attack {

    private int PUMP_MAX = 1;
    private int pumps = PUMP_MAX;

    private boolean pumpAuraActivated = false;


    public ShanaAttack4(GameCharacter character, int id) {
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
                    if (!pumpAuraActivated) {
                        character.notifyObservers(new GameMessage(MessageType.SHAKE, "" + "test", null, true));

                        float offSetX = -character.getDirection() * 0 * ScreenProperty.SHANA_SCALE;
                        float offSetY = -200 * ScreenProperty.SHANA_SCALE;
                        character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.SHANA_SWORD_AURA + ";test;",
                                new Vector2d(character.getPos().x, character.getPos().y + offSetY), true));
                        pumpAuraActivated = true;
                    }

                    if (isSwitcher()) {
                        setSwitcher(false);
                    }
                    break;
                case 1:
                    if (pumps > 0 && character.getViewManager().getAnimManager().animationAlmostFinished(40)) {
                        character.getViewManager().resetFrameIndexTo(0);
                        pumps -= 1;
                    }
                    break;
                case 2:
                    if (!isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "shana_frontal_wave_sound_3", null, true));
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "shana_frontal_wave_sound_2", null, true));

                        float offSetX = -character.getDirection() * 10 * ScreenProperty.SHANA_SCALE;
                        float offSetY = 15 * ScreenProperty.SHANA_SCALE;
                        character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.SHANA_FRONTAL_WAVE + ";test;",
                                new Vector2d(character.getPos().x, character.getPos().y + offSetY), character.isRight(), ScreenProperty.SHANA_FRONTAL_WAVE));

                        character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.SHANA_GREAT_WAVE + ";test;",
                                new Vector2d(character.getPos().x + offSetX, character.getPos().y + offSetY), !character.isRight(), ScreenProperty.SHANA_FRONTAL_WAVE));


                        Vector2d pos = new Vector2d(getCharacter().getPos().x + -getCharacter().getDirection() * 50 * ScreenProperty.SHANA_SCALE, character.getPos().y - 20 * ScreenProperty.SHANA_SCALE);

                        character.notifyObservers(new GameMessage(MessageType.PROJECTILE_CREATION, ProjectileConfig.SHANA_BLUE_FIRE_BLAST + ";" + character.getPlayer() + ";",
                                pos, character.isRight()));

                        character.notifyObservers(new GameMessage(MessageType.SHAKE, "" + "test", null, true));

                        setSwitcher(true);
                    }
                    break;
                case 3:
                    if (isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "shana_fire_blast", null, true));

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
            character.notifyObservers(new GameMessage(MessageType.SPARK_CREATION, SparkConfig.ATTACK_SPARK + ";test;",
                    new Vector2d(box.getPos().x, box.getPos().y), true));

            character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_attack4_hit", null, true));

        } catch (Exception e) {
            Log.e("Error", "The spark could not be created: " + e.getMessage());
        }
        if (!enemy.getStatusManager().isKnockbacked()) {
            enemy.getHitManager().setKnockBackHeight(-7.0F);
            enemy.getHitManager().setKnockBackRange(5.0F);
            enemy.getHitManager().checkOnAir();
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
        pumps = PUMP_MAX;
        pumpAuraActivated = false;
    }

    /**
     * @return the attacking state variable
     */
    @Override
    public boolean isAttacking() {
        return character.getAttackManager().isAttacking4();
    }

    @Override
    public void resetStats() {
        setSwitcher(true);
        pumpAuraActivated = false;
        pumps = PUMP_MAX;
    }
}
