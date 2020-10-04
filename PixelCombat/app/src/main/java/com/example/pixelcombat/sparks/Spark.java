package com.example.pixelcombat.sparks;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.animation.Animation;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

import lombok.Getter;

@Getter
public class Spark implements GameObject {

    private Animation animation;
    private Vector2d pos;

    public Spark(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos) {
        this.animation = new Animation(images, times, false, 0);
        this.pos = pos;
        this.animation.play();

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
        return true;
    }

    @Override
    public float getDirection() {
        return 1f;
    }

    @Override
    public int getRank() {
        return 0;
    }


    public boolean isFinished() {
        return !animation.isPlaying();
    }
}
