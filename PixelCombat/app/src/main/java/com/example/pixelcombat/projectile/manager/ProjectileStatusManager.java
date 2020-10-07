package com.example.pixelcombat.projectile.manager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.character.status.ProjectileActionStatus;
import com.example.pixelcombat.projectile.Projectile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ProjectileStatusManager {

    protected Projectile projectile;
    private MovementStatus movementStatus = MovementStatus.RIGHT;
    private ProjectileActionStatus actionStatus = ProjectileActionStatus.CREATION;
    private boolean dead = false;
    private float BASE_SPEED_VX = 50f;

    public ProjectileStatusManager(Projectile projectile) {
        this.projectile = projectile;
        if (!projectile.isRight()) movementStatus = MovementStatus.LEFT;
    }

    public ProjectileStatusManager() {
    }


    public void init(Projectile projectile) {
        this.projectile = projectile;
        if (!projectile.isRight()) movementStatus = MovementStatus.LEFT;
    }

    public void update() {

        switch (getActionStatus()) {
            case CREATION:
                create();
                break;
            case MOVE:
                move();
                break;
            case EXPLOSION:
                explosion();
                break;
            default:
                break;
        }
    }

    private void explosion() {
        if (!projectile.getViewManager().isPlaying()) {
            dead = true;
        }
    }

    private void move() {

        projectile.getPos().x += projectile.getDirection() * BASE_SPEED_VX;

        if (!projectile.getViewManager().isPlaying()) {
            setActionStatus(ProjectileActionStatus.EXPLOSION);
        }
    }

    private void create() {

        if (!projectile.getViewManager().isPlaying()) {
            setActionStatus(ProjectileActionStatus.MOVE);
        }

    }


    public void setActionStatus(ProjectileActionStatus actionStatus) {
        this.actionStatus = actionStatus;
        projectile.getViewManager().updateAnimation();
    }


    public void setMovementStatus(MovementStatus movementStatus) {
        this.movementStatus = movementStatus;
        projectile.getViewManager().updateAnimation();
    }

    public void swapDirections() {
        if (movementStatus == MovementStatus.RIGHT)
            setMovementStatus(MovementStatus.LEFT);
        else
            setMovementStatus(MovementStatus.RIGHT);
    }

    public boolean isRight() {
        return movementStatus == MovementStatus.RIGHT;
    }


    public abstract void checkDefender(GameCharacter defender);
}
