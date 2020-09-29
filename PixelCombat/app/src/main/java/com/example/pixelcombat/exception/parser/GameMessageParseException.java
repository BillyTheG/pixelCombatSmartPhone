package com.example.pixelcombat.exception.parser;

import com.example.pixelcombat.enums.ExceptionGroup;
import com.example.pixelcombat.exception.PixelCombatException;

public class GameMessageParseException extends PixelCombatException {

    public GameMessageParseException(String message) {
        super(message, ExceptionGroup.GAMEMESSAGE);
    }
}
