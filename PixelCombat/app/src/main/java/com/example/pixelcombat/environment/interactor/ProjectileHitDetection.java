package com.example.pixelcombat.environment.interactor;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.environment.ProjectileInteract;
import com.example.pixelcombat.projectile.Projectile;

import java.util.ArrayList;

public class ProjectileHitDetection implements ProjectileInteract {

    private final GameCharacter player1;
    private final GameCharacter player2;


    public ProjectileHitDetection(GameCharacter player1, GameCharacter player2) {
        this.player1 = player1;
        this.player2 = player2;
    }


    @Override
    public void interact(ArrayList<Projectile> projectiles) {

        try {
            ArrayList<Thread> threads = new ArrayList<>();

            for (Projectile projectile : projectiles) {
                Thread thread = new Thread(() -> {
                    if (projectile.getOwner().equals(player1.getPlayer())) {
                        projectile.checkDefender(player2);
                    } else {
                        projectile.checkDefender(player1);
                    }
                });
                thread.start();
                threads.add(thread);
            }

            for (Thread thread : threads) {
                thread.join();
            }

        } catch (Exception e) {
            Log.e("Error", "During Projectile Interaction an error was thrown: " + e.getMessage());
        }


    }
}
