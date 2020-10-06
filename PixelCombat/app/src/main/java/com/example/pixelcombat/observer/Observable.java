package com.example.pixelcombat.observer;

import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.exception.PixelCombatException;

public interface Observable {
    void addObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers(GameMessage message) throws PixelCombatException;
}
