package com.example.pixelcombat.character.chars.kohaku.projectiles;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.GlobalStatus;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.character.status.ProjectileActionStatus;
import com.example.pixelcombat.core.config.SparkConfig;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.projectile.manager.ProjectileStatusManager;

public class HorizontalSlash extends ProjectileStatusManager {
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
            defender.notifyObservers(new GameMessage(MessageType.SPARK_CREATION, SparkConfig.BLOOD_SPLASH_SPARK + ";test;",
                    new Vector2d(box.getPos().x, box.getPos().y), defender.isRight()));

            defender.notifyObservers(new GameMessage(MessageType.SOUND, "kohaku_sword_hit", null, true));

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
    public void move() {

        projectile.getPos().x += projectile.getDirection() * BASE_SPEED_VX;

        if (!projectile.getViewManager().isPlaying()) {
            setActionStatus(ProjectileActionStatus.EXPLOSION);
        }
    }

}
