package com.example.pixelcombat.environment.interactor;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.environment.ProjectileInteract;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.projectile.Projectile;

import java.util.ArrayList;

public class ProjectileHitDetecton implements ProjectileInteract {

    private final GameCharacter player1;
    private final GameCharacter player2;
    private final float HORIZONTAL_SHIFT = 15F;

    private BoundingRectangle currentColBox;

    public ProjectileHitDetecton(GameCharacter player1, GameCharacter player2) {
        this.player1 = player1;
        this.player2 = player2;
    }


    @Override
    public void interact(ArrayList<Projectile> projectiles) {
        for (Projectile projectile : projectiles) {

            if (projectile.getOwner().equals(player1.getPlayer())) {
                projectile.checkDefender(player2);
            } else {
                projectile.checkDefender(player1);
            }


        }
    }
}
