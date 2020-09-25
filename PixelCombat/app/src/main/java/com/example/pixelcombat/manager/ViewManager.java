package com.example.pixelcombat.manager;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.animation.Animation;
import com.example.pixelcombat.animation.AnimationManager;
import com.example.pixelcombat.exception.parser.XmlParseErrorException;
import com.example.pixelcombat.xml.CharacterParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public abstract class ViewManager {

    // Bildsequenzen als Stütze
    public final int STAND = 0;
    public final int MOVE = 1;
    public final int JUMPING = 2;
    public final int BASICATTACK1 = 3;
    public final int SPECIALATTACK1 = 4;
    public final int SPECIALATTACK2 = 5;
    public final int SPECIALATTACK3 = 6;
    public final int ISHIT = 7;
    public final int KNOCKBACK = 8;
    public final int KNOCKEDOUT = 9;
    public final int AVATAR = 10;
    public final int BASICATTACK2 = 11;


    protected final GameCharacter character;
    protected AnimationManager animManager;
    private CharacterParser characterParser;

    public ViewManager(GameCharacter character) throws Exception {

        this.character = character;
        init();
    }

    public void loadParsedImages() throws XmlParseErrorException, XmlPullParserException, IOException {
        characterParser.parseXMLData();
        ArrayList<Animation> animations = new ArrayList<>();

        Map<String, ArrayList<Bitmap>> images = characterParser.getCharacter();
        ArrayList<ArrayList<Float>> times = characterParser.getTimes();
        ArrayList<Boolean> loop = characterParser.getLoop();
        ArrayList<Integer> loopIndices = characterParser.getLoopIndizes();

        animations.add(new Animation(images.get("stand"),times.get(STAND), loop.get(STAND),loopIndices.get(STAND)));
        animations.add(new Animation(images.get("move"),times.get(MOVE), loop.get(MOVE),loopIndices.get(MOVE)));

        Animation[] array = new Animation[animations.size()];
        animations.toArray(array); // fill the array

        animManager = new AnimationManager(this, array,character);


    }

    protected abstract void init() throws Exception;

    public void update(){
        animManager.update();
    }

    public void draw(Canvas canvas) {
        animManager.draw(canvas);
    }


    public int getAnimation() {
        if (character.getStatus().isActive() || character.getStatus().isBlinking()) {


            if (character.getStatus().isAttacking()) {

                switch (character.getStatus().getAttackStatus()) {
                    case BASIC_ATTACK1:
                        //  character.picManager.change(BASICATTACK);
                        break;
                    case BASIC_ATTACK2:
                        // character.picManager.change(BASICATTACK1);
                        break;
                    default:
                        // changeFurtherImages(character.attackLogic.getAttackStatus());
                        break;
                }
                // Bildreihe gewechselt
                return 0;
            } else {

                switch (character.getStatus().getActionStatus()) {
                    case MOVE:
                        return 1;
                    case STAND:
                        return 0;
                }

            }

        }
        return 0;
    }
    public synchronized void updateAnimation(){
        this.animManager.playAnim();
    }

    public void setCharacterParser(CharacterParser characterParser) {
        this.characterParser = characterParser;
    }

    public CharacterParser getCharacterParser() {
        return characterParser;
    }
}
