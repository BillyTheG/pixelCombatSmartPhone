package com.example.pixelcombat.projectile;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.projectile.manager.ProjectileBoxManager;
import com.example.pixelcombat.projectile.manager.ProjectileStatusManager;
import com.example.pixelcombat.projectile.manager.ProjectileViewManager;

import lombok.Getter;

@Getter
public class Projectile implements GameObject {

    private int rank;
    private Vector2d pos;
    private ProjectileStatusManager statusManager;
    private ProjectileViewManager viewManager;
    private ProjectileBoxManager boxManager;

    @Override
    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {

    }

    @Override
    public void update() {

    }


    @Override
    public boolean isRight() {
        return statusManager.isRight();
    }


    public float getDirection() {
        if (isRight())
            return 1.0f;
        else return -1.0f;
    }

}
