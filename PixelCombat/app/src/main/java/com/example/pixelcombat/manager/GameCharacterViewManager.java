package com.example.pixelcombat.manager;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.animation.Animation;
import com.example.pixelcombat.animation.AnimationManager;
import com.example.pixelcombat.exception.parser.XmlParseErrorException;
import com.example.pixelcombat.utils.LocatedBitmap;
import com.example.pixelcombat.xml.CharacterParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import lombok.Getter;
import lombok.SneakyThrows;

public abstract class GameCharacterViewManager extends ObjectViewManager<GameCharacter> {

    // Bildsequenzen als Stütze
    public final int STAND = 0;
    public final int MOVE = 1;
    public final int JUMP = 2;
    public final int JUMPFALL = 3;
    public final int JUMPRECOVER = 4;
    public final int CROUCH = 5;
    public final int DECROUCH = 6;
    public final int ATTACK1 = 7;
    public final int DISABLED = 8;
    public final int DISABLEDRECOVER = 9;
    public final int KNOCKBACK = 10;
    public final int KNOCKBACKFALL = 11;
    public final int KNOCKBACKRECOVER = 12;
    public final int ONGROUND = 13;
    public final int SPECIALATTACK1 = 14;
    public final int DASH = 15;
    public final int SPECIALATTACK3 = 16;
    public final int ATTACK2 = 17;
    public final int ATTACK3 = 18;
    public final int DEFEND = 19;
    public final int DEFENDSTOP = 20;

    public final int DEAD = 99;
    @Getter
    protected AnimationManager animManager;
    protected final GameCharacter character;
    private CharacterParser characterParser;

    private Runnable imageLoaderRunnable;
    private Thread imageLoaderThread;

    public GameCharacterViewManager(GameCharacter character) throws Exception {
        super(character);
        this.character = character;
        init();
    }

    public void loadParsedImages() throws XmlParseErrorException, XmlPullParserException, IOException {
        characterParser.parseXMLData();
        ArrayList<Animation> animations = new ArrayList<>();

        Map<String, ArrayList<LocatedBitmap>> images = characterParser.getCharacter();
        ArrayList<ArrayList<Float>> times = characterParser.getTimes();
        ArrayList<Boolean> loop = characterParser.getLoop();
        ArrayList<Integer> loopIndices = characterParser.getLoopIndizes();

        animations.add(new Animation(images.get("stand"), times.get(STAND), loop.get(STAND), loopIndices.get(STAND)));
        animations.add(new Animation(images.get("move"), times.get(MOVE), loop.get(MOVE), loopIndices.get(MOVE)));
        animations.add(new Animation(images.get("jump"), times.get(JUMP), loop.get(JUMP), loopIndices.get(JUMP)));
        animations.add(new Animation(images.get("jumpFall"), times.get(JUMPFALL), loop.get(JUMPFALL), loopIndices.get(JUMPFALL)));
        animations.add(new Animation(images.get("jumpRecover"), times.get(JUMPRECOVER), loop.get(JUMPRECOVER), loopIndices.get(JUMPRECOVER)));
        animations.add(new Animation(images.get("crouch"), times.get(CROUCH), loop.get(CROUCH), loopIndices.get(CROUCH)));
        animations.add(new Animation(images.get("decrouch"), times.get(DECROUCH), loop.get(DECROUCH), loopIndices.get(DECROUCH)));
        animations.add(new Animation(images.get("attack1"), times.get(ATTACK1), loop.get(ATTACK1), loopIndices.get(ATTACK1)));
        animations.add(new Animation(images.get("disabled"), times.get(DISABLED), loop.get(DISABLED), loopIndices.get(DISABLED)));
        animations.add(new Animation(images.get("disabledRecover"), times.get(DISABLEDRECOVER), loop.get(DISABLEDRECOVER), loopIndices.get(DISABLEDRECOVER)));
        animations.add(new Animation(images.get("knockBack"), times.get(KNOCKBACK), loop.get(KNOCKBACK), loopIndices.get(KNOCKBACK)));
        animations.add(new Animation(images.get("knockBackFall"), times.get(KNOCKBACKFALL), loop.get(KNOCKBACKFALL), loopIndices.get(KNOCKBACKFALL)));
        animations.add(new Animation(images.get("knockBackRecover"), times.get(KNOCKBACKRECOVER), loop.get(KNOCKBACKRECOVER), loopIndices.get(KNOCKBACKRECOVER)));
        animations.add(new Animation(images.get("onGround"), times.get(ONGROUND), loop.get(ONGROUND), loopIndices.get(ONGROUND)));
        animations.add(new Animation(images.get("specialAttack1"), times.get(SPECIALATTACK1), loop.get(SPECIALATTACK1), loopIndices.get(SPECIALATTACK1)));
        animations.add(new Animation(images.get("dash"), times.get(DASH), loop.get(DASH), loopIndices.get(DASH)));
        animations.add(new Animation(images.get("specialAttack3"), times.get(SPECIALATTACK3), loop.get(SPECIALATTACK3), loopIndices.get(SPECIALATTACK3)));
        animations.add(new Animation(images.get("attack2"), times.get(ATTACK2), loop.get(ATTACK2), loopIndices.get(ATTACK2)));
        animations.add(new Animation(images.get("attack3"), times.get(ATTACK3), loop.get(ATTACK3), loopIndices.get(ATTACK3)));
        animations.add(new Animation(images.get("defend"), times.get(DEFEND), loop.get(DEFEND), loopIndices.get(DEFEND)));
        animations.add(new Animation(images.get("defendStop"), times.get(DEFENDSTOP), loop.get(DEFENDSTOP), loopIndices.get(DEFENDSTOP)));


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

    public void update() {
        animManager.update();
    }

    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {
        animManager.draw(canvas, screenX, screenY, gameRect);
    }


    public int getAnimation() {
        if (character.getStatusManager().isActive() || character.getStatusManager().isBlinking()) {


            if (character.getAttackManager().isAttacking()) {

                switch (character.getAttackManager().getAttackStatus()) {
                    case ATTACK1:
                        return ATTACK1;
                    case SPECIALATTACK1:
                        return SPECIALATTACK1;
                    case SPECIALATTACK3:
                        return SPECIALATTACK3;
                    case ATTACK2:
                        return ATTACK2;
                    case ATTACK3:
                        return ATTACK3;
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
                    case DASHING:
                        return DASH;
                    case STAND:
                        return STAND;
                    case JUMP:
                        return JUMP;
                    case JUMPFALL:
                        return JUMPFALL;
                    case JUMP_RECOVER:
                        return JUMPRECOVER;
                    case CROUCHING:
                        return CROUCH;
                    case DECROUCHING:
                        return DECROUCH;
                    case DEFENDING:
                        return DEFEND;
                    case DEFENDSTOP:
                        return DEFENDSTOP;
                }

            }

        } else {

            switch (character.getStatusManager().getGlobalStatus()) {
                case INVINCIBLE:
                    return ONGROUND;
                case KNOCKBACK:
                    return KNOCKBACK;
                case KNOCKBACKRECOVER:
                    return KNOCKBACKRECOVER;
                case KNOCKBACKFALL:
                    return KNOCKBACKFALL;
                case DISABLED:
                    return DISABLED;
                case DISABLEDRECOVER:
                    return DISABLEDRECOVER;
                case DEAD:
                    return DEAD;
                default:
                    break;

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

    public synchronized void resetFrameIndexTo(int newFrameIndex) {
        getAnimManager().resetFrameIndexTo(newFrameIndex);
    }

    public boolean isPlaying() {
        return getAnimManager().isPlaying();
    }
}
