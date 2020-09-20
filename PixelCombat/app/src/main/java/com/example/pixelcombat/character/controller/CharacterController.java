package com.example.pixelcombat.character.controller;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.enums.KeyCommand;

public class CharacterController {

    private final GameCharacter character;

    public CharacterController(GameCharacter character){
        this.character = character;
    }

    public boolean onKey(KeyCommand key, boolean hold) {

        switch (key) {
            case P1RIGHT:
                return move(hold, true);
            case P1LEFT:
                return move(hold, false);


            default:return true;
        }

    }


    public boolean move(boolean hold, boolean right)
    {
        int factor = 1;
        MovementStatus movementState = MovementStatus.RIGHT;
        if(!right)
        {
            factor *=-1;
            movementState =  MovementStatus.LEFT;
            character.getStatus().setMovementStatus(MovementStatus.LEFT);
        }else
        {
            character.getStatus().setMovementStatus(MovementStatus.RIGHT);
        }

        if(hold){
            this.character.getStatus().setActionStatus(ActionStatus.MOVE);
        }else{
            this.character.getStatus().setActionStatus(ActionStatus.STAND);
        }


        return true;
    }

}
