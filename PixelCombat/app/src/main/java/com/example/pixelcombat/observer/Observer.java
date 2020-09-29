package com.example.pixelcombat.observer;

import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.exception.PixelCombatException;

public interface Observer {
    void processMessage(GameMessage gameMessage) throws PixelCombatException;
}

