package com.example.pixelcombat.character.chars.kohaku.manager;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.manager.actionManager.DisabledManager;

public class KohakuDisabledManager extends DisabledManager {

    public KohakuDisabledManager(GameCharacter character) {
        super(character);
    }

    @Override
    public void cry() {
        try {
            character.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_cry1", null, true));
        } catch (PixelCombatException e) {
            Log.e("Error", "Der Sound konnte nicht abgespielt werden " + e.getMessage());
        }
    }

}

