package com.example.pixelcombat.character.physics;


import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.enums.ScreenProperty;

public class PlayerPhysics {

    private GameCharacter character;
    public float VY = 0;
    public float VX = 0;
    public float VX_EPSILON = 0.01f;
    public float GRAVITATION = 1.0F;
    public float ACCELERATION = 1.35F;
    public float FRICTION = 0.65F;

    public PlayerPhysics(GameCharacter character) {
        this.character = character;
    }

    public void update() {

        checkVelocities();

        this.character.getPos().x += VX;
        this.character.getPos().y += VY;

        gravitation();
        checkVirtualBorders();
    }

    private void checkVirtualBorders() {

        float bottom = ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE;

        if (character.getPos().y > bottom) {
            character.getPos().y = bottom;
            VY = 0f;
        }
    }

    private void gravitation() {
        this.VY += GRAVITATION;
    }

    private void checkVelocities() {
        //TODO Character should be active, when it accelerates

        if (character.getStatusManager().isMoving() || character.getStatusManager().isOnAir())
            accelerate();
        else
            friction();
    }

    private void accelerate() {
        //TODO character requires a maximum speed
    }

    public void friction() {
        this.VX *= FRICTION;
        if (Math.abs(VX) <= VX_EPSILON) VX = 0f;
    }
}
