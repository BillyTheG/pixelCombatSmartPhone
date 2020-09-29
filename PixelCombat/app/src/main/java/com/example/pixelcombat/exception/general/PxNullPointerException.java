package com.example.pixelcombat.exception.general;

import com.example.pixelcombat.exception.PixelCombatException;

import static com.example.pixelcombat.enums.ExceptionGroup.NULLPOINTER;

public class PxNullPointerException extends PixelCombatException {
    public PxNullPointerException() {
        super("", NULLPOINTER);
    }
}
