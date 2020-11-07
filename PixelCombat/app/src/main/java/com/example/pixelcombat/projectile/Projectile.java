package com.example.pixelcombat.projectile;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.observer.Observable;
import com.example.pixelcombat.observer.Observer;
import com.example.pixelcombat.projectile.manager.ProjectileBoxManager;
import com.example.pixelcombat.projectile.manager.ProjectileStatusManager;
import com.example.pixelcombat.projectile.manager.ProjectileViewManager;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;
import java.util.Map;

import lombok.Getter;

@Getter
public class Projectile implements GameObject, Observable {

    private final Map<String, ArrayList<LocatedBitmap>> images;
    private final ArrayList<ArrayList<Float>> times;
    private final ArrayList<Integer> loopIndizes;
    private final ArrayList<Boolean> loopBools;
    private final String owner;
    private int rank;
    private Vector2d pos;
    private ProjectileStatusManager statusManager;
    private ProjectileViewManager viewManager;
    private ProjectileBoxManager boxManager;
    private Map<String, ArrayList<ArrayList<BoundingRectangle>>> boxes;
    private boolean isRight;
    private boolean canHit = true;
    private ArrayList<Observer> observer = new ArrayList<>();


    public Projectile(Vector2d pos, boolean isRight, Map<String, ArrayList<ArrayList<BoundingRectangle>>> boxes, Map<String, ArrayList<LocatedBitmap>> images,
                      ArrayList<ArrayList<Float>> times, ArrayList<Integer> loopIndices, ArrayList<Boolean> loopBools,
                      ProjectileStatusManager statusManager, String owner) {
        this.pos = pos;
        this.boxes = boxes;
        this.images = images;
        this.times = times;
        this.loopIndizes = loopIndices;
        this.loopBools = loopBools;
        this.isRight = isRight;
        this.owner = owner;

        this.statusManager = statusManager;
        this.statusManager.init(this);
        init();
    }


    public void init() {
        this.viewManager = new ProjectileViewManager(this);
        this.boxManager = new ProjectileBoxManager(this, boxes);

    }


    @Override
    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {
        viewManager.draw(canvas, screenX, screenY, gameRect);
    }

    @Override
    public void update() {
        try {
            statusManager.update();
            viewManager.update();
        } catch (Exception e) {
            Log.e("Error", "While Updating the statusmanager and viewmanager of the Projectile, an error ocurred: " + e.getMessage());
        }
    }


    public void checkDefender(GameCharacter defender) {
        if (boxManager.hits(defender) && this.canHit) {
            statusManager.checkDefender(defender);
            this.canHit = false;
        }
    }


    @Override
    public boolean isRight() {
        return isRight;
    }


    public float getDirection() {
        if (isRight())
            return 1.0f;
        else return -1.0f;
    }

    @Override
    public float getScaleFactor() {
        return ScreenProperty.GENERAL_SCALE;
    }

    public Projectile register(Observer observer) {
        addObserver(observer);
        return this;
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
        observer.forEach(x -> {
            try {
                x.processMessage(message);
            } catch (PixelCombatException e) {
                Log.e("Error", "Error while processing Message of Projectile: " + e.getMessage());
            }
        });
    }

}
