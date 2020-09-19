package com.example.pixelcombat.animation;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;

public class AnimationManager implements  Runnable{
    private final GameObject gameObject;
    private Animation[] animations;
    private int animationIndex = 0;

    public AnimationManager(Animation[] animations, GameObject gameObject) {
        this.animations = animations;
        this.gameObject = gameObject;
    }

    public void playAnim(int index) {
        for(int i = 0; i < animations.length; i++) {
            if(i == index) {
                if(!animations[index].isPlaying())
                    animations[i].play();
            } else
                animations[i].stop();
        }
        animationIndex = index;
    }

    public synchronized void draw(Canvas canvas, Rect rect) {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].draw(canvas, gameObject);
    }

    public void update() {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].update();
    }

    @Override
    public void run() {
        update();
    }
}