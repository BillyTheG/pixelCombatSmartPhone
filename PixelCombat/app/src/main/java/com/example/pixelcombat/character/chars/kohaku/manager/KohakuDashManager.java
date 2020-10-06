package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.manager.actionManager.DashManager;

public class KohakuDashManager extends DashManager {


    public KohakuDashManager(GameCharacter character) {
        super(character);
    }

    @Override
    public void dash() {

        switch (character.getViewManager().getFrameIndex()) {
            case 0:
            case 1:
            case 2:
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                character.getPhysics().VX = character.getDirection() * DASH_SPEED;
                break;
            case 12:
                if (switcher) {
                    character.getPhysics().VX = 0;
                    switcher = false;
                    break;
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
}
