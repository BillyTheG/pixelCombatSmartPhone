package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.attack.Attack;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.GlobalStatus;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.projectile.Projectile;

import java.util.Objects;
import java.util.TreeMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HitManager {

    private final GameCharacter character;
    private final TreeMap<String, Attack> attacks;
    private boolean hitDelay;
    private boolean freezeLoop;
    private float knockBackRange = 1.0F;
    private float knockBackHeight = -15.0F;
    private float knockBackRange_df = 1.0F;
    private float knockBackHeight_df = -15.0F;

    public HitManager(GameCharacter character) {
        this.character = character;
        this.attacks = character.getAttackManager().getAttacks();
    }

    public void resetCharStats() {

        this.hitDelay = false;
        this.freezeLoop = false;

        for (String attack : attacks.keySet()) {
            if (this.attacks.get(attack) == null)
                continue;
            (Objects.requireNonNull(this.attacks.get(attack))).resetStats();
        }
    }

    public boolean checkDefender(GameCharacter defender) {
        return (canTouch(defender)) && (!defender.getHitManager().canDefend(character)) && (!hitDelay);
    }

    public void comboTouch(float jumpSpeed, float movementSpeed) {
        if (character.getPhysics().VY >= 0.0F) {
            character.getPhysics().VY = jumpSpeed;
        } else {
            character.getPhysics().VY += jumpSpeed;
        }
        if (Math.signum(character.getPhysics().VX) == Math.signum(-character.getDirection() * movementSpeed)) {
            character.getPhysics().VX += -character.getDirection() * movementSpeed;
        } else {
            character.getPhysics().VX = (-character.getDirection() * movementSpeed);
        }
        character.getStatusManager().setActionStatus(ActionStatus.STAND);
        character.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACK);
        character.getViewManager().resetFrameIndexTo(0);
    }

    public boolean canTouch(GameCharacter defender) {
        return character.getBoxManager().hits(defender);
    }

    public boolean canDefend(Projectile projectile) {
        if ((!isNotHittable()) && (projectile.isRight()) && (!character.isRight())
                && (character.getStatusManager().isDefending()) && (projectileNotMighty(projectile))) {
            return defend(projectile);
        }
        if ((!isNotHittable()) && (!projectile.isRight())) {
            if ((character.isRight()) && (character.getStatusManager().isDefending()) && (projectileNotMighty(projectile))) {

                return defend(projectile);
            }
        }
        if (isNotHittable()) {
            return true;
        }
        if (projectile.isRight()) {
            character.getStatusManager().setMovementStatus(MovementStatus.LEFT);
        } else {
            character.getStatusManager().setMovementStatus(MovementStatus.RIGHT);
        }
        return false;
    }

    private boolean defend(Projectile projectile) {
        //  this.ParticleManager.createDefRings(new Vector2d(getPos().x - 0.5F, getPos().y - 0.75F), this);
        //  sound(this.defendSound, true);
        character.getPhysics().VX += projectile.getDirection() * 0.1F;
        return true;
    }


    public boolean canDefend(GameCharacter attacker) {
        if ((!isNotHittable()) && (attacker.isRight())) {
            if ((!character.isRight()) && (character.getStatusManager().isDefending())) {
                return defend(attacker);
            }
        }
        if ((!isNotHittable()) && (!attacker.isRight())) {
            if ((character.isRight()) && (character.getStatusManager().isDefending())) {
                return defend(attacker);
            }
        }
        return isNotHittable();
    }

    private boolean defend(GameCharacter attacker) {
        if (!attacker.getHitManager().isHitDelay()) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //    makeDefendBubbles();
            // sound(this.defendSound);

            character.getPhysics().VX += attacker.getDirection() * 0.25F;
            attacker.getHitManager().setHitDelay(true);
        }
        return true;
    }

    public void checkOnAir() {
        if ((character.getStatusManager().isOnAir())) {
            // sound(cry());
            character.getPhysics().VY = this.knockBackHeight;
            character.getPhysics().VX = (-character.getDirection() * this.knockBackRange);
            character.getPhysics().update();
            character.getStatusManager().setActionStatus(ActionStatus.STAND);
            character.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACK);
            return;
        }
        character.getStatusManager().setActionStatus(ActionStatus.STAND);
        character.getStatusManager().setGlobalStatus(GlobalStatus.DISABLED);
        character.getViewManager().resetFrameIndexTo(0);
        character.getDisabledManager().damageEnergy();
    }


    public boolean isNotHittable() {
        return (character.getStatusManager().isInvincible()) || (character.getStatusManager().isBlinking());
    }

    public boolean projectileNotMighty(Projectile projectile) {
        return character.getRank() >= projectile.getRank();
    }

}
