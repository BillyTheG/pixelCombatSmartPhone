package com.example.pixelcombat.character.chars.kohaku.attacks;

import android.util.Log;

import com.example.pixelcombat.character.attack.Attack;
import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.GlobalStatus;
import com.example.pixelcombat.core.config.DustConfig;
import com.example.pixelcombat.core.config.SparkConfig;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;

public class KohakuSpecialAttack2 extends Attack {


    private final Kohaku kohaku;

    public KohakuSpecialAttack2(Kohaku character, int id) {
        super(character, id);
        this.kohaku = character;
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
                        character.getStatusManager().setFocused(true);
                        character.notifyObservers(new GameMessage(MessageType.FREEZE, " ;" + character.getPlayer(), null, true));
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_special_attack_signal", null, true));
                        character.notifyObservers(new GameMessage(MessageType.DARKENING, "test;1", null, true));
                        setSwitcher(false);
                    }
                    break;
                case 2:
                    if (!isSwitcher()) {
                        character.getStatusManager().startEffect();
                        kohaku.getSakuraFactory().generate();
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_sword_out", null, true));
                        setSwitcher(true);
                    }
                    break;
                case 3:
                    if (isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_demon_aura", null, true));
                        character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.KOHAKU_SPECIAL_ATTACK2_AURA + ";test;",
                                new Vector2d(character.getPos().x, character.getPos().y), character.isRight()));
                        setSwitcher(false);
                    }
                    break;
                case 4:
                    if (!isSwitcher()) {
                        character.getStatusManager().setEffect(false);
                        character.getStatusManager().setFocused(false);
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_special_attack2", null, true));
                        setSwitcher(true);
                    }
                    break;
                case 6:
                    if (isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.DARKENING, "test;1", null, false));
                        character.notifyObservers(new GameMessage(MessageType.FREEZE, " ;" + character.getPlayer(), null, false));
                        character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.KOHAKU_SPECIAL_ATTACK_SPARK + ";test;",
                                new Vector2d(character.getPos().x - getCharacter().getDirection() * 100, character.getPos().y), character.isRight()));

                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_sword_slash", null, true));

                        Vector2d pos = new Vector2d(getCharacter().getPos().x + getCharacter().getDirection() * 200, character.getPos().y + 25);

                        character.notifyObservers(new GameMessage(MessageType.PROJECTILE_CREATION, "Kohaku_Projectile_Horizontal;" + character.getPlayer() + ";",
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
            enemy.cry();
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
            enemy.getHitManager().setKnockBackRange(30.0F);
            enemy.getHitManager().checkOnAir();
            enemy.getStatusManager().setActionStatus(ActionStatus.STAND);
            enemy.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACK);
        } else {
            enemy.getHitManager().comboTouch(-20.0F, 30.0F);
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
        return character.getAttackManager().isSpecialAttacking2();
    }

    @Override
    public void resetStats() {
        try {
            setSwitcher(true);
            character.getStatusManager().setEffect(false);
            character.getStatusManager().setFocused(false);
            character.notifyObservers(new GameMessage(MessageType.DARKENING, "test;1", null, false));
        } catch (Exception e) {
            Log.e("Error", "SpecialAttack2 of Kohaku could not be resetted: " + e.getMessage());
        }
    }
}
