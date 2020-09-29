package com.example.pixelcombat.character.chars.ruffy;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.controller.CharacterController;
import com.example.pixelcombat.character.physics.PlayerPhysics;
import com.example.pixelcombat.manager.BoxManager;
import com.example.pixelcombat.manager.StatusManager;
import com.example.pixelcombat.manager.actionManager.JumpManager;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.observer.Observer;

import java.util.ArrayList;

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
    private ArrayList<Observer> observer;

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
        observer = new ArrayList<>();
    }

    @Override
    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {
        viewManager.draw(canvas, screenX, screenY, gameRect);
        for (BoundingRectangle box : getBoxManager().getCurrentBox().get(viewManager.getFrameIndex())) {
            box.draw(this, canvas, screenX, screenY, gameRect);
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

    @Override
    public void addObserver(Observer o) {
        observer.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observer.remove(o);
    }

    @Override
    public void notifyObservers() {
        //TODO
    }
}
