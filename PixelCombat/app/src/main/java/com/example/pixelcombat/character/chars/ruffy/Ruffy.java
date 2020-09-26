package com.example.pixelcombat.character.chars.ruffy;


import android.content.Context;
import android.graphics.Canvas;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.controller.CharacterController;
import com.example.pixelcombat.character.physics.PlayerPhysics;
import com.example.pixelcombat.manager.BoxManager;
import com.example.pixelcombat.manager.StatusManager;
import com.example.pixelcombat.manager.actionManager.JumpManager;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;

import lombok.Getter;

@Getter
public class Ruffy implements GameCharacter {

    private Vector2d pos;
    private boolean faceRight = true;
    private StatusManager statusManager;
    private RuffyViewManager viewManager;
    private RuffyBoxManager boxManager;
    private PlayerPhysics physics;
    private CharacterController controller;
    private Context context;
    private JumpManager jumpManager;

    public Ruffy(Vector2d pos, Context context) throws Exception {
        this.context = context;
        this.pos = pos;
        init();
    }

    private void init() throws Exception {
        physics = new PlayerPhysics(this);
        statusManager = new StatusManager(this);
        viewManager = new RuffyViewManager(this);
        controller = new CharacterController(this);
        boxManager = new RuffyBoxManager(this);
        jumpManager = new JumpManager(this);
    }

    @Override
    public void draw(Canvas canvas) {
        viewManager.draw(canvas);
        for (BoundingRectangle box : getBoxManager().getCurrentBox().get(viewManager.getFrameIndex())) {
            box.draw(this, canvas, 0, 0);
        }
    }

    @Override
    public void update() {
        viewManager.update();
        physics.update();
        statusManager.update();
    }

    public float getDirection() {
        if (getStatusManager().isMovingRight())
            return 1.0f;
        else return -1.0f;
    }

    @Override
    public BoxManager getBoxManager() {
        return boxManager;
    }

    @Override
    public boolean isRight() {
        return getStatusManager().isMovingRight();
    }

}
