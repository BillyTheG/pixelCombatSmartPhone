package com.example.pixelcombat.manager;

import android.graphics.Canvas;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.animation.AnimationManager;

public abstract class ViewManager {

    // Bildsequenzen als Stütze
    public final int STAND = 0;
    public final int MOVE = 1;
    public final int JUMPING = 2;
    public final int BASICATTACK = 3;
    public final int SPECIALATTACK1 = 4;
    public final int SPECIALATTACK2 = 5;
    public final int SPECIALATTACK3 = 6;
    public final int ISHIT = 7;
    public final int KNOCKBACK = 8;
    public final int KNOCKEDOUT = 9;
    public final int AVATAR = 10;
    public final int BASICATTACK1 = 11;


    protected final GameCharacter character;
    protected AnimationManager animManager;

    public ViewManager(GameCharacter character){

        this.character = character;
        init();
    }

    protected abstract void init();

    public void update(){
        animManager.update();
    }

    public void draw(Canvas canvas) {
        animManager.draw(canvas);
    }


    public int getAnimation() {
        if (character.getStatus().isActive() || character.getStatus().isBlinking()) {


            if (character.getStatus().isAttacking()) {

                switch (character.getStatus().getAttackStatus()) {
                    case BASIC_ATTACK1:
                        //  character.picManager.change(BASICATTACK);
                        break;
                    case BASIC_ATTACK2:
                        // character.picManager.change(BASICATTACK1);
                        break;
                    default:
                        // changeFurtherImages(character.attackLogic.getAttackStatus());
                        break;
                }
                // Bildreihe gewechselt
                return 0;
            } else {

                switch (character.getStatus().getActionStatus()) {
                    case MOVE:
                        return 1;
                    case STAND:
                        return 0;
                }

            }

        }
        return 0;
    }
    public synchronized void updateAnimation(){
        this.animManager.playAnim();
    }

}
