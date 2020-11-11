package com.example.pixelcombat;

import android.content.Context;

import com.example.pixelcombat.character.controller.CharacterController;
import com.example.pixelcombat.character.physics.PlayerPhysics;
import com.example.pixelcombat.effects.avatar.EffectManager;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.factories.EffectFactory;
import com.example.pixelcombat.manager.BoxManager;
import com.example.pixelcombat.manager.GameCharacterViewManager;
import com.example.pixelcombat.manager.StatusManager;
import com.example.pixelcombat.manager.actionManager.AttackManager;
import com.example.pixelcombat.manager.actionManager.CrouchManager;
import com.example.pixelcombat.manager.actionManager.DashManager;
import com.example.pixelcombat.manager.actionManager.DefendManager;
import com.example.pixelcombat.manager.actionManager.DisabledManager;
import com.example.pixelcombat.manager.actionManager.HitManager;
import com.example.pixelcombat.manager.actionManager.JumpManager;
import com.example.pixelcombat.manager.actionManager.KnockBackManager;
import com.example.pixelcombat.manager.actionManager.MoveManager;
import com.example.pixelcombat.observer.Observable;

public interface GameCharacter extends GameObject, Observable {

    String getPlayer();

    StatusManager getStatusManager();

    Context getContext();

    GameCharacterViewManager getViewManager();

    BoxManager getBoxManager();

    JumpManager getJumpManager();

    CrouchManager getCrouchManager();

    AttackManager getAttackManager();

    EffectManager getEffectManager();

    DashManager getDashManager();

    HitManager getHitManager();

    DisabledManager getDisabledManager();

    KnockBackManager getKnockBackManager();

    DefendManager getDefendManager();

    MoveManager getMoveManager();

    CharacterController getController();

    PlayerPhysics getPhysics();

    GameCharacter getEnemy();

    boolean isRight();

    void setEnemy(GameCharacter enemy);

    void initAttacks();

    void initEffects(Context context, EffectFactory effectFactory);

    void cry() throws PixelCombatException;

}
