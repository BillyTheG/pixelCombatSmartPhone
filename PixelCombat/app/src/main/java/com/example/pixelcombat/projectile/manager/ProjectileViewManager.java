package com.example.pixelcombat.projectile.manager;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.animation.AnimationManager;
import com.example.pixelcombat.projectile.Projectile;
import com.example.pixelcombat.xml.CharacterParser;

import lombok.Getter;

public abstract class ProjectileViewManager {

    // Bildsequenzen als Stütze
    public final int CREATION = 0;
    public final int MOVE = 1;
    public final int EXPLOSION = 2;


    public final int DEAD = 99;
    protected final Projectile character;
    @Getter
    protected AnimationManager animManager;
    private CharacterParser characterParser;

    private Runnable imageLoaderRunnable;
    private Thread imageLoaderThread;

    public ProjectileViewManager(Projectile character) throws Exception {

        this.character = character;
        init();
    }

    protected abstract void init() throws Exception;

    public void update() {
        animManager.update();
    }

    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {
        animManager.draw(canvas, screenX, screenY, gameRect);
    }


    public int getAnimation() {

        switch (character.getStatusManager().getActionStatus()) {
            case MOVE:
                return MOVE;
            case CREATION:
                return CREATION;
            case EXPLOSION:
                return EXPLOSION;
        }
        return CREATION;
    }

    public synchronized void updateAnimation() {
        animManager.playAnim();
        character.getBoxManager().update();
    }

    public int getFrameIndex() {
        return getAnimManager().getFrameIndex();
    }

    public synchronized void resetFrameIndexTo(int newFrameIndex) {
        getAnimManager().resetFrameIndexTo(newFrameIndex);
    }

    public boolean isPlaying() {
        return getAnimManager().isPlaying();
    }
}
