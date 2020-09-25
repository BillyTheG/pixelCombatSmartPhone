package com.example.pixelcombat.exception.parser;

import com.example.pixelcombat.enums.ExceptionGroup;
import com.example.pixelcombat.exception.PixelCombatException;

public class XmlParseErrorException extends PixelCombatException
{
    public XmlParseErrorException(String message) {
        super(message, ExceptionGroup.PARSER);
    }

}
