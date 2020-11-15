package com.example.pixelcombat.character.chars.shana.projectiles;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.GlobalStatus;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.character.status.ProjectileActionStatus;
import com.example.pixelcombat.core.config.DustConfig;
import com.example.pixelcombat.core.config.SparkConfig;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.projectile.manager.ProjectileStatusManager;

import java.util.Random;

public class FireBlast extends ProjectileStatusManager {

    private final Vector2d startPos;
    private final float leastDistance = 300 * ScreenProperty.SHANA_BLUE_FIRE_GROUND / 2;
    private float buffer = 0;
    private Random random = new Random();
    private float startScaleFactor = ScreenProperty.SHANA_BLUE_FIRE_BLAST;
    private boolean fireGround = false;

    public FireBlast(Vector2d startPos) {
        this.startPos = new Vector2d(startPos.x, startPos.y);
    }

    @Override
    public void checkDefender(GameCharacter defender) {

        if (projectile.getStatusManager().isRight()) {
            defender.getStatusManager().setMovementStatus(MovementStatus.LEFT);
        } else {
            defender.getStatusManager().setMovementStatus(MovementStatus.RIGHT);
        }

        try {
            defender.cry();
            BoundingRectangle box = defender.getBoxManager().getIntersectionBox();
            defender.notifyObservers(new GameMessage(MessageType.SPARK_CREATION, SparkConfig.SHANA_KICK_HIT + ";test;",
                    new Vector2d(box.getPos().x, box.getPos().y), defender.isRight()));

            defender.notifyObservers(new GameMessage(MessageType.SOUND, "shana_frontal_wave_sound_hit", null, true));

        } catch (Exception e) {
            Log.e("Error", "The spark could not be created: " + e.getMessage());
        }
        if (!defender.getStatusManager().isKnockbacked()) {
            // getUser().enemy.timeManager.getDisableTime().setY(Float.valueOf(0.0F));
            defender.getHitManager().setKnockBackHeight(-27.0F);
            defender.getHitManager().setKnockBackRange(30.0F);
            defender.getHitManager().checkOnAir();
            defender.getStatusManager().setActionStatus(ActionStatus.STAND);
            defender.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACK);
        } else {
            defender.getHitManager().comboTouch(-20.0F, 40.0F);
        }

    }

    @Override
    protected void create() throws PixelCombatException {


        if (!projectile.getViewManager().isPlaying()) {
            setActionStatus(ProjectileActionStatus.MOVE);
        }

    }


    @Override
    protected void move() throws PixelCombatException {

        projectile.getPos().x += projectile.getDirection() * BASE_SPEED_VX * 2.5f;

        buffer += BASE_SPEED_VX * 2.5f;


        if (buffer > leastDistance) {
            startScaleFactor *= 0.65f;
            float offSetX = (216f / 2f) * ScreenProperty.SHANA_BLUE_FIRE_GROUND;

            float offSetY = 10 * projectile.getScaleFactor();

            projectile.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.SHANA_FRONTAL_WAVE + ";test;",
                    new Vector2d(projectile.getPos().x, projectile.getPos().y + offSetY), projectile.isRight(), startScaleFactor));


            projectile.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.SHANA_BLUE_FIRE_GROUND + ";test;",
                    new Vector2d(projectile.getPos().x - projectile.getDirection() * offSetX, projectile.getPos().y + offSetY), true));

            projectile.notifyObservers(new GameMessage(MessageType.DUST_CREATION, DustConfig.SHANA_BLUE_FIRE_SMOKE + ";test;",
                    new Vector2d(projectile.getPos().x, projectile.getPos().y - 6 * offSetY), true));


            buffer = 0;


        }


        if (Math.abs(startPos.x - projectile.getPos().x) > 1 * (ScreenProperty.SCREEN_WIDTH - 2 * ScreenProperty.OFFSET_X)) {
            this.setActionStatus(ProjectileActionStatus.EXPLOSION);

        }

    }

}
