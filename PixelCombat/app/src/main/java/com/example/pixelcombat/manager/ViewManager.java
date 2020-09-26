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

import lombok.Getter;
import lombok.SneakyThrows;

public abstract class ViewManager {

    // Bildsequenzen als Stütze
    public final int STAND = 0;
    public final int MOVE = 1;
    public final int JUMP = 2;
    public final int JUMPFALL = 3;
    public final int JUMPRECOVER = 4;

    @Getter
    protected AnimationManager animManager;
    protected final GameCharacter character;
    private CharacterParser characterParser;

    private Runnable imageLoaderRunnable;
    private Thread imageLoaderThread;

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

        animations.add(new Animation(images.get("stand"), times.get(STAND), loop.get(STAND), loopIndices.get(STAND)));
        animations.add(new Animation(images.get("move"), times.get(MOVE), loop.get(MOVE), loopIndices.get(MOVE)));
        animations.add(new Animation(images.get("jump"), times.get(JUMP), loop.get(JUMP), loopIndices.get(JUMP)));
        animations.add(new Animation(images.get("jumpFall"), times.get(JUMPFALL), loop.get(JUMPFALL), loopIndices.get(JUMPFALL)));
        animations.add(new Animation(images.get("jumpRecover"), times.get(JUMPRECOVER), loop.get(JUMPRECOVER), loopIndices.get(JUMPRECOVER)));

        Animation[] array = new Animation[animations.size()];
        animations.toArray(array); // fill the array

        animManager = new AnimationManager(this, array, character);
    }

    public void setUpLoaderThread() {

        imageLoaderRunnable = new Runnable() {

            @SneakyThrows
            @Override
            public void run() {
                loadParsedImages();
            }
        };
        imageLoaderThread = new Thread(imageLoaderRunnable);
        imageLoaderThread.start();
    }


    protected abstract void init() throws Exception;

    public void update(){
        animManager.update();
    }

    public void draw(Canvas canvas) {
        animManager.draw(canvas);
    }


    public int getAnimation() {
        if (character.getStatusManager().isActive() || character.getStatusManager().isBlinking()) {


            if (character.getStatusManager().isAttacking()) {

                switch (character.getStatusManager().getAttackStatus()) {
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

                switch (character.getStatusManager().getActionStatus()) {
                    case MOVE:
                        return MOVE;
                    case STAND:
                        return STAND;
                    case JUMP:
                        return JUMP;
                    case JUMPFALL:
                        return JUMPFALL;
                    case JUMP_RECOVER:
                        return JUMPRECOVER;
                }

            }

        }
        return STAND;
    }
    public synchronized void updateAnimation() {
        animManager.playAnim();
        character.getBoxManager().update();
    }

    public void setCharacterParser(CharacterParser characterParser) {
        this.characterParser = characterParser;
    }

    public CharacterParser getCharacterParser() {
        return characterParser;
    }

    public int getFrameIndex() {
        return getAnimManager().getFrameIndex();
    }
}
