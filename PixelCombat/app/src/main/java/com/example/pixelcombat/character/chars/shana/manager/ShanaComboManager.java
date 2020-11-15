package com.example.pixelcombat.character.chars.shana.manager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.manager.ComboActionManager;

public class ShanaComboManager extends ComboActionManager {


    public ShanaComboManager(GameCharacter player) {
        super(player);
    }

    @Override
    protected void loadMoreCombos() {
        this.combos.clear();
    }

    @Override
    protected boolean doAnotherSpecialAttack(String combo) {
        boolean activated = false;


        return false;
    }
}
