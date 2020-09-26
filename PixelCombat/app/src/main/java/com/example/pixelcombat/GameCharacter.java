package com.example.pixelcombat;

import android.content.Context;

import com.example.pixelcombat.character.physics.PlayerPhysics;
import com.example.pixelcombat.manager.BoxManager;
import com.example.pixelcombat.manager.StatusManager;
import com.example.pixelcombat.manager.ViewManager;
import com.example.pixelcombat.manager.actionManager.JumpManager;

public interface GameCharacter extends GameObject {

    float getDirection();

    StatusManager getStatusManager();

    Context getContext();

    ViewManager getViewManager();

    BoxManager getBoxManager();

    JumpManager getJumpManager();

    PlayerPhysics getPhysics();

    boolean isRight();
}
