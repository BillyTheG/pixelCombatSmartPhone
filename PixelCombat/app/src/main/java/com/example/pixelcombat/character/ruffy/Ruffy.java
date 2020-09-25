package com.example.pixelcombat.character.ruffy;


import android.content.Context;
import android.graphics.Canvas;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.controller.CharacterController;
import com.example.pixelcombat.character.physics.PlayerPhysics;
import com.example.pixelcombat.manager.StatusManager;
import com.example.pixelcombat.manager.ViewManager;
import com.example.pixelcombat.math.Vector2d;

public class Ruffy implements GameCharacter {

    private Vector2d pos;
    private boolean faceRight = true;
    private StatusManager statusManager;
    private RuffyViewManager viewManager;
    private PlayerPhysics physics;
    private CharacterController controller;
    private Context context;

    public Ruffy(Vector2d pos,Context context) throws Exception {
        this.context = context;
        this.pos = pos;
        init();
    }

    private void init() throws Exception {
        physics = new PlayerPhysics(this);
        statusManager = new StatusManager(this);
        viewManager = new RuffyViewManager(this);
        controller  = new CharacterController(this);

    }

    @Override
    public void draw(Canvas canvas) {
        viewManager.draw(canvas);

    }

    @Override
    public void update() {
        viewManager.update();
        physics.update();
    }

    @Override
    public Vector2d getPos() {
        return pos;
    }

    @Override
    public boolean isRight() {
        return getStatus().isMovingRight();
    }

    @Override
    public StatusManager getStatus() {
        return statusManager;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public ViewManager getViewManager() {
        return viewManager;
    }


    public float getDirection() {
        if (getStatus().isMovingRight())
            return 1.0f;
        else return -1.0f;
    }


    public CharacterController getController() {
        return controller;
    }
}
