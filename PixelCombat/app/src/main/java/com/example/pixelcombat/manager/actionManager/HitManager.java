package com.example.pixelcombat.manager.actionManager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.attack.Attack;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.character.status.GlobalStatus;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.core.config.SparkConfig;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;
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
    private final float knockBackRange_df = 1.0F;
    private final float knockBackHeight_df = -15.0F;

    public HitManager(GameCharacter character) {
        this.character = character;
        this.attacks = character.getAttackManager().getAttacks();
    }

    public void resetCharStats() {

        this.hitDelay = false;
        this.freezeLoop = false;
        this.knockBackHeight = knockBackHeight_df;
        this.knockBackRange = knockBackRange_df;
        character.getMoveManager().resetStats();

        for (String attack : attacks.keySet()) {
            if (this.attacks.get(attack) == null)
                continue;
            (Objects.requireNonNull(this.attacks.get(attack))).resetStats();
        }
    }

    public boolean checkDefender(GameCharacter defender, Attack attack) throws PixelCombatException {
        return (canTouch(defender)) && (!defender.getHitManager().canDefend(character, attack)) && (!hitDelay);
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
        character.getAttackManager().setAttackStatus(AttackStatus.NOT_ATTACKING);
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


    public boolean canDefend(GameCharacter attacker, Attack attack) throws PixelCombatException {
        if ((!isNotHittable()) && (attacker.isRight())) {
            if ((!character.isRight()) && (character.getStatusManager().isDefending() || character.getStatusManager().isAirDefending())) {
                return defend(attacker, attack);
            }
        }
        if ((!isNotHittable()) && (!attacker.isRight())) {
            if ((character.isRight()) && (character.getStatusManager().isDefending() || character.getStatusManager().isAirDefending())) {
                return defend(attacker, attack);
            }
        }
        return isNotHittable();
    }

    private boolean defend(GameCharacter attacker, Attack attack) throws PixelCombatException {
        if (!attacker.getHitManager().isHitDelay()) {

            BoundingRectangle box = character.getBoxManager().getIntersectionBox();
            character.notifyObservers(new GameMessage(MessageType.SPARK_CREATION, SparkConfig.DEFEND_SPARK + ";test;",
                    new Vector2d(box.getPos().x, box.getPos().y), true));
            character.notifyObservers(new GameMessage(MessageType.SOUND, "defend", null, true));

            // sound(this.defendSound);

            character.getPhysics().VX += attacker.getDirection() * 25F;
            character.getDefendManager().damageDefendPoints(attack.getDefendDamage());
            attacker.getHitManager().setHitDelay(true);
        }
        return true;
    }

    public void checkOnAir() {

        character.getPhysics().VY = this.knockBackHeight;
        character.getPhysics().VX = (-character.getDirection() * this.knockBackRange);

        if ((character.getStatusManager().isOnAir())) {
            // sound(cry());
            character.getPhysics().update();
            character.getAttackManager().setAttackStatus(AttackStatus.NOT_ATTACKING);
            character.getStatusManager().setActionStatus(ActionStatus.STAND);
            character.getStatusManager().setGlobalStatus(GlobalStatus.KNOCKBACK);
            return;
        }
        character.getAttackManager().setAttackStatus(AttackStatus.NOT_ATTACKING);
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
