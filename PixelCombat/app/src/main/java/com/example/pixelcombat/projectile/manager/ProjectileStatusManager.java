package com.example.pixelcombat.projectile.manager;

import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.character.status.ProjectileActionStatus;
import com.example.pixelcombat.projectile.Projectile;

import lombok.Getter;

@Getter
public class ProjectileStatusManager {

    private final Projectile character;
    private MovementStatus movementStatus = MovementStatus.RIGHT;
    private ProjectileActionStatus actionStatus = ProjectileActionStatus.CREATION;

    public ProjectileStatusManager(Projectile projectile) {
        this.character = projectile;
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
    }

    private void move() {
    }

    private void create() {
    }


    public void setActionStatus(ProjectileActionStatus actionStatus) {
        this.actionStatus = actionStatus;
        character.getViewManager().updateAnimation();
    }


    public void setMovementStatus(MovementStatus movementStatus) {
        this.movementStatus = movementStatus;
        character.getViewManager().updateAnimation();
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


}
