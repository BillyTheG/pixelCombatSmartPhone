package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.core.config.DustConfig;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.manager.actionManager.DashManager;
import com.example.pixelcombat.math.Vector2d;

public class KohakuDashManager extends DashManager {


    public KohakuDashManager(GameCharacter character) {
        super(character);
    }

    @Override
    public void dash() throws PixelCombatException {

        switch (character.getViewManager().getFrameIndex()) {
            case 0:
                if (switcher) {
                    character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_dash", null, true));
                    switcher = false;
                }
                break;
            case 3:
                if (!switcher) {
                    character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_dash_sonic", null, true));
                    switcher = true;
                }
                character.getPhysics().VX = character.getDirection() * DASH_SPEED;
                break;
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                character.getPhysics().VX = character.getDirection() * DASH_SPEED;
                break;
            case 5:
                if (switcher) {
                    character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.KOHAKU_DASH_DUST + ";test;",
                            new Vector2d(character.getPos().x, character.getPos().y), true));
                    switcher = false;
                }
                character.getPhysics().VX = character.getDirection() * DASH_SPEED;
                break;
            case 12:
                if (!switcher) {
                    character.getPhysics().VX = 0;
                    switcher = true;
                }
                break;
            case 13:
                if (!switcher) {

                    switcher = true;
                }
            default:
                break;
        }

        if (!character.getViewManager().isPlaying()) {
            switcher = true;
            character.getStatusManager().setActionStatus(ActionStatus.STAND);
        }
    }

    @Override
    public void retreat() throws PixelCombatException {
        switch (character.getViewManager().getFrameIndex()) {
            case 0:
                if (switcher) {
                    character.getStatusManager().swapDirections();
                    character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_jump", null, true));
                    character.notifyObservers(new GameMessage(MessageType.SOUND, "ruffy_jump", null, true));
                    switcher = false;
                }
                break;
            case 3:
                if (!switcher) {
                    this.character.getPhysics().VY = -22f;
                    switcher = true;
                }
                break;
            case 4:
            case 5:
            case 6:
                this.character.getPhysics().VX = -character.getDirection() * 1.25f * DASH_SPEED;
                distance += Math.abs(DASH_SPEED);
                if (distance > 1.5 * MAX_DISTANCE || character.getStatusManager().landed()) {
                    distance = 0f;
                    reset();
                    this.character.getPhysics().VX = 0;
                    character.notifyObservers(new GameMessage(MessageType.SOUND, "groundrecover", null, true));
                    character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.KOHAKU_DASH_DUST + ";test;",
                            new Vector2d(character.getPos().x, character.getPos().y), true));
                    character.getStatusManager().setActionStatus(ActionStatus.RETREATING_STOP);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void retreatStop() {
        if (!character.getViewManager().isPlaying()) {
            reset();
            character.getStatusManager().setActionStatus(ActionStatus.STAND);
        }
    }
}
