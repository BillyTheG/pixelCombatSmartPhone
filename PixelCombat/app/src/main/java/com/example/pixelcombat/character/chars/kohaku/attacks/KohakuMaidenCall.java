package com.example.pixelcombat.character.chars.kohaku.attacks;

import android.util.Log;

import com.example.pixelcombat.character.attack.Attack;
import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.core.config.DustConfig;
import com.example.pixelcombat.core.config.ProjectileConfig;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.math.Vector2d;

public class KohakuMaidenCall extends Attack {


    private final Kohaku kohaku;

    public KohakuMaidenCall(Kohaku character, int id) {
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
                        character.notifyObservers(new GameMessage(MessageType.DARKENING, "test;1", null, true));
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
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_maiden_call_0", null, true));
                        setSwitcher(true);
                    }
                    break;
                case 3:
                    if (isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.DARKENING, "test;1", null, false));
                        setSwitcher(false);
                    }
                    break;
                case 4:
                    if (!isSwitcher()) {
                        character.getStatusManager().setEffect(false);
                        character.getStatusManager().setFocused(false);
                        character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_maiden_call_1", null, true));
                        setSwitcher(true);
                        Vector2d pos = new Vector2d(getCharacter().getPos().x + -getCharacter().getDirection() * ScreenProperty.SCREEN_WIDTH / 2, character.getPos().y);
                        character.notifyObservers(new GameMessage(MessageType.PROJECTILE_CREATION, ProjectileConfig.KOHAKU_SPECIAL_ATTACK_PROJECTILE_MAIDEN + ";" + character.getPlayer() + ";",
                                pos, character.isRight()));
                    }
                    break;
                case 6:
                    if (isSwitcher()) {
                        character.notifyObservers(new GameMessage(MessageType.DARKENING, "test;1", null, false));
                        character.notifyObservers(new GameMessage(MessageType.FREEZE, " ;" + character.getPlayer(), null, false));
                        character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.KOHAKU_SPECIAL_ATTACK_SPARK + ";test;",
                                new Vector2d(character.getPos().x - getCharacter().getDirection() * 100, character.getPos().y), character.isRight()));

                        Vector2d pos = new Vector2d(getCharacter().getPos().x + -getCharacter().getDirection() * ScreenProperty.SCREEN_WIDTH / 2, character.getPos().y);
                        character.notifyObservers(new GameMessage(MessageType.PROJECTILE_CREATION, ProjectileConfig.KOHAKU_SPECIAL_ATTACK_PROJECTILE_LITTLE_GUY + ";" + character.getPlayer() + ";",
                                pos, character.isRight()));
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
        return kohaku.getAttackManager().isMaidenCalling();
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
