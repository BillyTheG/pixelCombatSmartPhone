package com.example.pixelcombat.core.message;

import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.math.Vector2d;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameMessage {

    private MessageType messageType;

    private String gameObject;

    private Vector2d pos;


}
