package com.example.pixelcombat.character.physics;


import com.example.pixelcombat.GameCharacter;

public class PlayerPhysics {

    private GameCharacter character;
    public static final int VERTICAL_LEAP_SPEED = 0;
    public static final int HORIZONTAL_SPEED = 10;
    public PlayerPhysics(GameCharacter character){
        this.character = character;
    }


    public void update(){
        if(character.getStatus().isMoving()) {
            this.character.getPos().x += character.getDirection() * 7.5f;
        }
    }





}
