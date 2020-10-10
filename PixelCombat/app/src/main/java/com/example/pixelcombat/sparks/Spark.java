package com.example.pixelcombat.sparks;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.animation.Animation;
import com.example.pixelcombat.core.IsFinishable;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.core.sound.SoundManager;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.observer.Observable;
import com.example.pixelcombat.observer.Observer;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

import lombok.Getter;

@Getter
public class Spark implements GameObject, IsFinishable, Observable {

    private Animation animation;
    private Vector2d pos;
    private boolean isRight = true;
    private Observer observer;

    public Spark(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos) {
        this.animation = new Animation(images, times, false, 0);
        this.pos = pos;
        this.animation.play();

    }

    public Spark(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos, boolean isRight) {
        this.animation = new Animation(images, times, false, 0);
        this.pos = pos;
        this.animation.play();
        this.isRight = isRight;

    }

    @Override
    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {
        animation.draw(canvas, this, screenX, screenY, gameRect);
    }

    @Override
    public void update() {
        animation.update();
    }

    @Override
    public Vector2d getPos() {
        return pos;
    }

    @Override
    public boolean isRight() {
        return isRight;
    }

    @Override
    public float getDirection() {
        if (isRight())
            return 1f;
        else
            return -1f;
    }

    @Override
    public int getRank() {
        return 0;
    }


    public boolean isFinished() {
        return !animation.isPlaying();
    }

    public Spark register(SoundManager soundManager) {
        addObserver(soundManager);
        return this;
    }

    @Override
    public void addObserver(Observer o) {
        this.observer = o;
    }

    @Override
    public void removeObserver(Observer o) {
        this.observer = null;
    }

    @Override
    public void notifyObservers(GameMessage message) throws PixelCombatException {
        observer.processMessage(message);
    }
}
