package com.example.pixelcombat.manager;

import com.example.pixelcombat.GameCharacter;

public class CharacterManager {


    private final GameCharacter character;
    private BoxManager boxManager;
    private ViewManager vieManager;
    private StatusManager statusManager;



    public CharacterManager(GameCharacter character){
        this.character = character;



    }

}
