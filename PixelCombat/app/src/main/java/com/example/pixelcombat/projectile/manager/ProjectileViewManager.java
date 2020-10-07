package com.example.pixelcombat.projectile.manager;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.animation.Animation;
import com.example.pixelcombat.animation.AnimationManager;
import com.example.pixelcombat.manager.ObjectViewManager;
import com.example.pixelcombat.projectile.Projectile;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;
import java.util.Map;

import lombok.Getter;

public class ProjectileViewManager extends ObjectViewManager<Projectile> {

    // Bildsequenzen als Stütze
    public final int CREATION = 0;
    public final int MOVE = 1;
    public final int EXPLOSION = 2;


    public final int DEAD = 99;
    protected final Projectile character;
    @Getter
    protected AnimationManager animManager;

    private Runnable imageLoaderRunnable;
    private Thread imageLoaderThread;

    public ProjectileViewManager(Projectile character) {
        super(character);
        this.character = character;
        init();
    }

    public void init() {

        ArrayList<Animation> animations = new ArrayList<>();

        Map<String, ArrayList<LocatedBitmap>> images = character.getImages();
        ArrayList<ArrayList<Float>> times = character.getTimes();
        ArrayList<Boolean> loop = character.getLoopBools();
        ArrayList<Integer> loopIndices = character.getLoopIndizes();

        animations.add(new Animation(images.get("creation"), times.get(CREATION), loop.get(CREATION), loopIndices.get(CREATION)));
        animations.add(new Animation(images.get("move"), times.get(MOVE), loop.get(MOVE), loopIndices.get(MOVE)));
        animations.add(new Animation(images.get("explosion"), times.get(EXPLOSION), loop.get(EXPLOSION), loopIndices.get(EXPLOSION)));

        Animation[] array = new Animation[animations.size()];
        animations.toArray(array);

        this.animManager = new AnimationManager(this, array, character);

        animManager.playAnim();
        animManager.update();

    }


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
