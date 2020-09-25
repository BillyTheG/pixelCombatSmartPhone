package com.example.pixelcombat;

import android.content.Context;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.character.status.GlobalStatus;
import com.example.pixelcombat.manager.StatusManager;
import com.example.pixelcombat.manager.ViewManager;

public interface GameCharacter extends GameObject {

    public float getDirection();
    public boolean isRight();
    public StatusManager getStatus();
    public Context getContext();
    public ViewManager getViewManager();
}
