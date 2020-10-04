package com.example.pixelcombat.character.chars.ruffy;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.manager.actionManager.DisabledManager;

public class RuffyDisabledManager extends DisabledManager {

    public RuffyDisabledManager(GameCharacter character) {
        super(character);
    }

    @Override
    public void cry() {
        try {
            character.notifyObservers(new GameMessage(MessageType.SOUND, "ruffy_disabled", null));
        } catch (PixelCombatException e) {
            Log.e("Error", "Der Sound konnte nicht abgespielt werden " + e.getMessage());
        }
    }
}
