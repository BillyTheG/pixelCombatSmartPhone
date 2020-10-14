package com.example.pixelcombat.character.chars.kohaku;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.ai.AIManager;
import com.example.pixelcombat.ai.HasAI;
import com.example.pixelcombat.character.chars.kohaku.manager.KohakuAttackManager;
import com.example.pixelcombat.character.chars.kohaku.manager.KohakuBoxManager;
import com.example.pixelcombat.character.chars.kohaku.manager.KohakuDashManager;
import com.example.pixelcombat.character.chars.kohaku.manager.KohakuDisabledManager;
import com.example.pixelcombat.character.chars.kohaku.manager.KohakuJumpManager;
import com.example.pixelcombat.character.chars.kohaku.manager.KohakuMoveManager;
import com.example.pixelcombat.character.chars.kohaku.manager.KohakuViewManager;
import com.example.pixelcombat.character.controller.CharacterController;
import com.example.pixelcombat.character.physics.PlayerPhysics;
import com.example.pixelcombat.core.config.ViewConfig;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.DrawLevel;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.manager.StatusManager;
import com.example.pixelcombat.manager.actionManager.CrouchManager;
import com.example.pixelcombat.manager.actionManager.DefendManager;
import com.example.pixelcombat.manager.actionManager.DisabledManager;
import com.example.pixelcombat.manager.actionManager.HitManager;
import com.example.pixelcombat.manager.actionManager.JumpManager;
import com.example.pixelcombat.manager.actionManager.KnockBackManager;
import com.example.pixelcombat.manager.actionManager.MoveManager;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.observer.Observer;

import java.util.ArrayList;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Kohaku implements GameCharacter, HasAI {

    private String player;
    private Vector2d pos;
    private AIManager kohakuAIManager;
    private boolean faceRight = true;
    private int rank;
    private StatusManager statusManager;
    private KohakuViewManager viewManager;
    private KohakuBoxManager boxManager;
    private CrouchManager crouchManager;
    private HitManager hitManager;
    private DisabledManager disabledManager;
    private KnockBackManager knockBackManager;
    private KohakuAttackManager attackManager;
    private DefendManager defendManager;
    private MoveManager moveManager;
    private KohakuDashManager dashManager;
    private PlayerPhysics physics;
    private CharacterController controller;
    private Context context;
    private JumpManager jumpManager;
    private ArrayList<Observer> observer;
    private GameCharacter enemy;

    public Kohaku(String player, Vector2d pos, Context context) throws Exception {
        this.context = context;
        this.player = player;
        this.pos = pos;
        init();
        Log.i("Info", "Kohaku was created successfully");
    }

    private void init() throws Exception {
        physics = new PlayerPhysics(this);
        attackManager = new KohakuAttackManager(this);
        defendManager = new DefendManager(this);
        statusManager = new StatusManager(this);
        viewManager = new KohakuViewManager(this);
        controller = new CharacterController(this);
        boxManager = new KohakuBoxManager(this);
        moveManager = new KohakuMoveManager(this);
        jumpManager = new KohakuJumpManager(this);
        crouchManager = new CrouchManager(this);
        hitManager = new HitManager(this);
        disabledManager = new KohakuDisabledManager(this);
        knockBackManager = new KnockBackManager(this);
        dashManager = new KohakuDashManager(this);
        observer = new ArrayList<>();
    }

    public void initAttacks() {
        attackManager.init();
    }

    @Override
    public void cry() throws PixelCombatException {
        int rand = new Random().nextInt(5) + 1;
        notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_cry" + rand, null, true));
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
        if (getAIManager() != null)
            kohakuAIManager.update();

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

    @Override
    public AIManager getAIManager() {
        return kohakuAIManager;
    }

    @Override
    public void setAIManager(AIManager aiManager) {
        this.kohakuAIManager = aiManager;

    }
}
