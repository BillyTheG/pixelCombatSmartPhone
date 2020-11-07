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

public class KohakuBattoJutsuOgi extends Attack {


    private static final int LOOPTIMEMAX = 4;
    private final Kohaku kohaku;
    private int loopTime = LOOPTIMEMAX;
    private boolean enemyWasHit = false;

    public KohakuBattoJutsuOgi(Kohaku kohaku, int id) {
        super(kohaku, id);
        this.kohaku = kohaku;
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
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_batto_jutsu_start", null, true));
                        character.notifyObservers(new GameMessage(MessageType.DARKENING, "test;1", null, true));
                        character.notifyObservers(new GameMessage(MessageType.FREEZE, " ;" + character.getPlayer(), null, true));
                        setSwitcher(false);
                    }
                    break;
                case 2:
                    if (!isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_sword_out", null, true));
                        setSwitcher(true);
                    }
                    break;
                case 4:
                    if (isSwitcher()) {

                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_batto_jutsu_laughter", null, true));
                        setSwitcher(false);
                    }
                    break;
                case 5:
                    float SLASHSPEED = 50f;
                    kohaku.getPhysics().VX += kohaku.getDirection() * SLASHSPEED;
                    if (!isSwitcher()) {

                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_batto_jutsu_dash", null, true));
                        setSwitcher(true);
                    }
                    break;
                case 6:
                    if (isSwitcher()) {

                        character.getStatusManager().setFocused(false);
                        character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.DUST_FAST_FORWARD + ";test;",
                                new Vector2d(character.getPos().x, character.getPos().y), character.isRight()));

                        character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.KOHAKU_SPECIAL_ATTACK_SPARK + ";test;",
                                new Vector2d(character.getPos().x, character.getPos().y), character.isRight()));

                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_sword_slash", null, true));

                        setSwitcher(false);
                    }
                    break;
                case 8:
                    if (!isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_batto_jutsu_ogi", null, true));
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_special_attack_end", null, true));
                        setSwitcher(true);
                    }
                    break;
                case 9:
                    if (isSwitcher()) {
                        //character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_special_attack_end", null, true));
                        setSwitcher(false);
                    }
                    break;
                case 10:
                    if (!isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SPARK_CREATION, SparkConfig.KOHAKU_BATTO_JUTSU_SLASH_SPARK + ";test;",
                                new Vector2d((character.getPos().x), character.getPos().y), character.isRight()));
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_special_attack_end", null, true));
                        setSwitcher(true);
                    }
                    break;
                case 11:
                    if (isSwitcher()) {
                        if (enemyWasHit) {
                            kohaku.getSakuraFactory().generate();
                            character.getStatusManager().startEffect();
                        }
                        setSwitcher(false);
                    }
                    break;
                case 15:
                    if (!enemyWasHit) {
                        loopTime = -1;
                    }
                    if (!isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_special_attack_end", null, true));
                        setSwitcher(true);
                    }
                    if (loopTime > 0 && character.getViewManager().getAnimManager().animationAlmostFinished(30)) {
                        character.getViewManager().resetFrameIndexTo(13);
                        loopTime -= 1;
                    }
                    break;
                case 16:
                    if (isSwitcher()) {
                        character.getStatusManager().setEffect(false);
                        character.notifyObservers(new GameMessage(MessageType.DARKENING, "test;1", null, false));
                        character.notifyObservers(new GameMessage(MessageType.FREEZE, " ;" + character.getPlayer(), null, false));
                        setSwitcher(false);
                    }
                    break;
                case 27:
                    if (!isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_sword_back", null, true));
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
        try {
            BoundingRectangle box = character.getBoxManager().getIntersectionBox();
            character.notifyObservers(new GameMessage(MessageType.SPARK_CREATION, SparkConfig.BLOOD_SPLASH_SPARK + ";test;",
                    new Vector2d(box.getPos().x, box.getPos().y), character.isRight()));
            character.notifyObservers(new GameMessage(MessageType.FREEZE, " ;" + character.getPlayer(), null, false));
            character.notifyObservers(new GameMessage(MessageType.SHAKE, "" + "test", null, true));
            character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_sword_hit", null, true));
            enemyWasHit = true;
        } catch (Exception e) {
            Log.e("Error", "The spark could not be created: " + e.getMessage());
        }
        if (!enemy.getStatusManager().isKnockbacked()) {
            // getUser().enemy.timeManager.getDisableTime().setY(Float.valueOf(0.0F));
            enemy.getHitManager().setKnockBackHeight(-47.0F);
            enemy.getHitManager().setKnockBackRange(10.0F);
            enemy.getHitManager().checkOnAir();
            enemy.getStatusManager().setActionStatus(ActionStatus.STAND);
            enemy.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACK);
        } else {
            enemy.getHitManager().comboTouch(-40.0F, 10.0F);
        }

    }

    /**
     * This method does the final check, whether the attack reached the final
     * frame and the animation time has been lapsed.
     */
    @Override
    public void checkFinished() {
        try {
            character.getEffectManager().reset();
            loopTime = LOOPTIMEMAX;
            enemyWasHit = false;
            character.notifyObservers(new GameMessage(MessageType.FREEZE, " ;" + character.getPlayer(), null, false));
        } catch (Exception e) {
            Log.e("Error", "SpecialAttack2 of Kohaku could not be resetted: " + e.getMessage());
        }
    }

    /**
     * @return the attacking state variable
     */
    @Override
    public boolean isAttacking() {
        return kohaku.getAttackManager().isBattoJutsuOgiIng();
    }

    @Override
    public void resetStats() {
        try {
            setSwitcher(true);
            character.getStatusManager().setEffect(false);
            character.getStatusManager().setFocused(false);
            character.getEffectManager().reset();
            enemyWasHit = false;
            character.notifyObservers(new GameMessage(MessageType.FREEZE, " ;" + character.getPlayer(), null, false));
            loopTime = LOOPTIMEMAX;
            character.notifyObservers(new GameMessage(MessageType.DARKENING, "test;1", null, false));
        } catch (Exception e) {
            Log.e("Error", "SpecialAttack2 of Kohaku could not be resetted: " + e.getMessage());
        }
    }
}
