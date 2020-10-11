package com.example.pixelcombat.character.chars.ruffy.manager;

import com.example.pixelcombat.character.chars.ruffy.Ruffy;
import com.example.pixelcombat.manager.actionManager.JumpManager;

public class RuffyJumpManager extends JumpManager {

    public boolean moveSound = true;

    public RuffyJumpManager(Ruffy character) throws Exception {
        super(character);
    }


    @Override
    protected void leapSound() {

    }
}
