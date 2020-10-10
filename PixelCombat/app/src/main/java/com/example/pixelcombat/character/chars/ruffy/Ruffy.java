package com.example.pixelcombat.character.chars.ruffy;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.chars.ruffy.manager.RuffyAttackManager;
import com.example.pixelcombat.character.chars.ruffy.manager.RuffyBoxManager;
import com.example.pixelcombat.character.chars.ruffy.manager.RuffyDisabledManager;
import com.example.pixelcombat.character.chars.ruffy.manager.RuffyMoveManager;
import com.example.pixelcombat.character.chars.ruffy.manager.RuffyViewManager;
import com.example.pixelcombat.character.controller.CharacterController;
import com.example.pixelcombat.character.physics.PlayerPhysics;
import com.example.pixelcombat.core.config.ViewConfig;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.DrawLevel;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.manager.StatusManager;
import com.example.pixelcombat.manager.actionManager.CrouchManager;
import com.example.pixelcombat.manager.actionManager.DashManager;
import com.example.pixelcombat.manager.actionManager.DisabledManager;
import com.example.pixelcombat.manager.actionManager.HitManager;
import com.example.pixelcombat.manager.actionManager.JumpManager;
import com.example.pixelcombat.manager.actionManager.KnockBackManager;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.observer.Observer;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ruffy implements GameCharacter {

    private Vector2d pos;
    private boolean faceRight = true;
    private int rank;
    private StatusManager statusManager;
    private RuffyViewManager viewManager;
    private RuffyBoxManager boxManager;
    private RuffyMoveManager moveManager;
    private CrouchManager crouchManager;
    private HitManager hitManager;
    private DisabledManager disabledManager;
    private KnockBackManager knockBackManager;
    private RuffyAttackManager attackManager;
    private DashManager dashManager;
    private PlayerPhysics physics;
    private CharacterController controller;
    private Context context;
    private JumpManager jumpManager;
    private ArrayList<Observer> observer;
    private GameCharacter enemy;
    private String player;

    public Ruffy(String player, Vector2d pos, Context context) throws Exception {
        this.player = player;
        this.context = context;
        this.pos = pos;
        init();
        Log.i("Info", "Ruffy was created successfully");
    }

    private void init() throws Exception {
        physics = new PlayerPhysics(this);
        attackManager = new RuffyAttackManager(this);
        moveManager = new RuffyMoveManager(this);
        statusManager = new StatusManager(this);
        viewManager = new RuffyViewManager(this);
        controller = new CharacterController(this);
        boxManager = new RuffyBoxManager(this);
        jumpManager = new JumpManager(this);
        crouchManager = new CrouchManager(this);
        hitManager = new HitManager(this);
        disabledManager = new RuffyDisabledManager(this);
        knockBackManager = new KnockBackManager(this);
        dashManager = new DashManager(this);
        observer = new ArrayList<>();
    }

    public void initAttacks() {
        attackManager.init();
    }

    @Override
    public void cry() {

    }

    @Override
    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {
        viewManager.draw(canvas, screenX, screenY, gameRect);

        if (ViewConfig.DRAW_DEPTH == DrawLevel.DEBUG) {
            for (BoundingRectangle box : getBoxManager().getCurrentBox().get(viewManager.getFrameIndex())) {
                box.draw(this, canvas, screenX, screenY, gameRect);
            }
        }
    }

    @Override
    public void update() throws PixelCombatException {
        viewManager.update();
        physics.update();
        statusManager.update();
        attackManager.updateAttacks();
    }

    public float getDirection() {
        if (getStatusManager().isMovingRight())
            return 1.0f;
        else return -1.0f;
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
    public void notifyObservers(GameMessage message) throws PixelCombatException {
        for (Observer obs : observer) {
            obs.processMessage(message);
        }
    }
}
