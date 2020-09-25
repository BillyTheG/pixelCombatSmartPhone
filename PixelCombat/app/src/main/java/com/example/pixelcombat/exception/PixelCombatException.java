package com.example.pixelcombat.exception;

import com.example.pixelcombat.enums.ExceptionGroup;

public abstract class PixelCombatException extends Exception {

    public ExceptionGroup exceptionGroup;

    public PixelCombatException(String message, ExceptionGroup exceptionGroup){
        super(message);
        this.exceptionGroup = exceptionGroup;
    }

}
