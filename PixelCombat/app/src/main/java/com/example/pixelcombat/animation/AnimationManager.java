package com.example.pixelcombat.animation;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.manager.ObjectViewManager;

public class AnimationManager<T extends GameObject> implements Runnable {
    private final GameObject gameObject;
    private final ObjectViewManager<T> viewManager;
    private Animation[] animations;
    private int animationIndex = 0;


    public AnimationManager(ObjectViewManager<T> viewManager, Animation[] animations, GameObject gameObject) {
        this.viewManager = viewManager;
        this.animations = animations;
        this.gameObject = gameObject;
    }

    public synchronized void playAnim() {

        int index = viewManager.getAnimation();

        if (index == animationIndex && animations[index].isPlaying()) return;

        for (int i = 0; i < animations.length; i++) {
            if (i == index) {
                if (!animations[index].isPlaying())
                    animations[i].play();
            } else
                animations[i].stop();
        }
        animationIndex = index;
    }

    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {
        if (animations[animationIndex].isPlaying())
            animations[animationIndex].draw(canvas, gameObject, screenX, screenY, gameRect);
    }

    public void update() {
        if (animations[animationIndex].isPlaying())
            animations[animationIndex].update();
    }

    @Override
    public void run() {
        update();
    }

    public synchronized int getFrameIndex() {
        return animations[animationIndex].getFrameIndex();
    }

    public synchronized boolean isPlaying() {
        return animations[animationIndex].isPlaying();
    }

    public synchronized boolean animationAlmostFinished(long restTime) {
        return animations[animationIndex].animationSequenceAlmostFinished(restTime);
    }

    public synchronized void resetFrameIndexTo(int newFrameIndex) {
        animations[animationIndex].resetFrameIndexTo(newFrameIndex);
    }

}