package com.example.pixelcombat.enums;

public enum ExceptionGroup {
    GAMEPLAY(""),
    PARSER("Parsing of the Data {0} resulted in an error: "),
    UNKNOWN("");


    public String description;


    ExceptionGroup(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
