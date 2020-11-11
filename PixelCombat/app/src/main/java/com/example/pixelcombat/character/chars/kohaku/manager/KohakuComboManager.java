package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.core.config.ComboAttacksConfig;
import com.example.pixelcombat.manager.ComboActionManager;

import static com.example.pixelcombat.core.config.ComboAttacksConfig.LEFT_BATTO_JUTSU;
import static com.example.pixelcombat.core.config.ComboAttacksConfig.MAIDEN_CALL;
import static com.example.pixelcombat.core.config.ComboAttacksConfig.RIGHT_BATTO_JUTSU;

public class KohakuComboManager extends ComboActionManager {


    public KohakuComboManager(GameCharacter player) {
        super(player);
    }

    @Override
    protected void loadMoreCombos() {
        combos.add(ComboAttacksConfig.LEFT_BATTO_JUTSU);
        combos.add(ComboAttacksConfig.RIGHT_BATTO_JUTSU);
        combos.add(MAIDEN_CALL);
    }

    @Override
    protected boolean doAnotherSpecialAttack(String combo) {
        boolean activated = false;
        switch (combo) {
            case RIGHT_BATTO_JUTSU:
            case LEFT_BATTO_JUTSU:
                activated = player1.getController().specialAttack(AttackStatus.BATTO_JUTSU_OGI);
                bonus = "";
                return activated;
            case MAIDEN_CALL:
                activated = player1.getController().specialAttack(AttackStatus.MAIDEN_CALL);
                bonus = "";
                return activated;
        }

        return false;
    }
}
