package com.example.pixelcombat.manager;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.xml.BoxParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

public abstract class BoxManager {

    // Stütze für Bilder
    public final int STAND = 0;
    public final int MOVE = 1;
    public final int JUMPING = 2;
    public final int JUMPFALL = 3;
    public final int JUMPRECOVER = 4;
    public final int BASICATTACK = 35;
    public final int SPECIALATTACK1 = 45;
    public final int SPECIALATTACK2 = 55;
    public final int SPECIALATTACK3 = 6;
    public final int ISHIT = 7;
    public final int KNOCKBACK = 8;
    public final int KNOCKEDOUT = 9;
    public final int AVATAR = 10;
    public final int BASICATTACK1 = 11;
    public final int JUMPATTACK = 12;
    public final int RETREATING = 13;
    public final int DASHING = 14;
    public final int DEFENDING = 15;
    public final int SPECIALATTACK4 = 16;
    public final int SPECIALATTACK5 = 17;
    public final int BASICATTACK21 = 18;
    public final int BASICATTACK22 = 19;
    public final int BASICATTACK23 = 20;
    public final int SPECIALATTACK6 = 21;
    public final int SPECIALATTACK7 = 22;
    public final int INTRO = 23;
    public final int WIN = 24;
    public final int DEAD = 25;
    public final int RUNATTACK1 = 26;
    public final int RUNATTACK2 = 27;
    public final int AIRDEFENDING = 29;
    public final int AIR_SPECIALATTACK1 = 30;
    public final int KNOCKBACKRECOVER = 31;
    public final int MAX_STANDARD_SPRITES = JUMPFALL;
    public int currentAnimation;
    public BoundingRectangle currentColBox;
    @Getter
    public List<ArrayList<BoundingRectangle>> currentBox;
    public Map<String, ArrayList<ArrayList<BoundingRectangle>>> boxes;
    public BoxParser parser;
    private GameCharacter character;
    @Getter
    @Setter
    private boolean collidingX = false;
    @Getter
    @Setter
    private boolean collidingY = false;
    private boolean collidingBX = false;
    private boolean collidingBY = false;
    private Runnable boxLoaderRunnable;
    private Thread boxLoaderThread;

    public BoxManager(GameCharacter character) {
        this.character = character;
    }

    public void init() {
        currentAnimation = STAND;
        currentBox = boxes.get("stand");
    }

    public void setUpLoaderThread() {

        boxLoaderRunnable = new Runnable() {

            @SneakyThrows
            @Override
            public void run() {
                loadParsedBoxes();
            }
        };
        boxLoaderThread = new Thread(boxLoaderRunnable);
        boxLoaderThread.start();
    }

    public void loadParsedBoxes() throws Exception {
        parser = new BoxParser(character.getContext(), getFileName());
        parser.parseXMLData();
        boxes = parser.getBoxes();
        init();
    }

    protected abstract String getFileName();

    public void update() {
        if (character.getViewManager().getAnimation() == currentAnimation)
            return;

        switch (character.getViewManager().getAnimation()) {
            case STAND:
                updateBoxSeq(STAND, "stand");
                break;
            case MOVE:
                updateBoxSeq(MOVE, "move");
                break;
            case JUMPING:
                updateBoxSeq(JUMPING, "jumping");
                break;
            case JUMPFALL:
                updateBoxSeq(JUMPFALL, "jumpFall");
                break;
            case JUMPRECOVER:
                updateBoxSeq(JUMPRECOVER, "jumpRecover");
                break;
            case BASICATTACK:
                updateBoxSeq(BASICATTACK, "basicAttack");
                break;
            case SPECIALATTACK1:
                updateBoxSeq(SPECIALATTACK1, "specialAttack1");
                break;
            case SPECIALATTACK2:
                updateBoxSeq(SPECIALATTACK2, "specialAttack2");
                break;
            case SPECIALATTACK3:
                updateBoxSeq(SPECIALATTACK3, "specialAttack3");
                break;
            case SPECIALATTACK4:
                updateBoxSeq(SPECIALATTACK4, "specialAttack4");
                break;
            case SPECIALATTACK5:
                updateBoxSeq(SPECIALATTACK5, "specialAttack5");
                break;
            case SPECIALATTACK6:
                updateBoxSeq(SPECIALATTACK6, "specialAttack6");
                break;
            case SPECIALATTACK7:
                updateBoxSeq(SPECIALATTACK7, "specialAttack7");
                break;
            case AIR_SPECIALATTACK1:
                updateBoxSeq(AIR_SPECIALATTACK1, "airSpecialAttack1");
                break;
            case ISHIT:
                updateBoxSeq(ISHIT, "isHit");
                break;
            case KNOCKBACK:
                updateBoxSeq(KNOCKBACK, "knockBack");
                break;
            case KNOCKBACKRECOVER:
                updateBoxSeq(KNOCKBACKRECOVER, "knockBackRecover");
                break;
            case KNOCKEDOUT:
                updateBoxSeq(KNOCKEDOUT, "knockedOut");
                break;
            case AVATAR:
                updateBoxSeq(AVATAR, "avatar");
                break;
            case BASICATTACK1:
                updateBoxSeq(BASICATTACK1, "basicAttack1");
                break;
            case BASICATTACK21:
                updateBoxSeq(BASICATTACK21, "basicAttack21");
                break;
            case BASICATTACK22:
                updateBoxSeq(BASICATTACK22, "basicAttack22");
                break;
            case BASICATTACK23:
                updateBoxSeq(BASICATTACK23, "basicAttack23");
                break;
            case JUMPATTACK:
                updateBoxSeq(JUMPATTACK, "jumpAttack");
                break;
            case DASHING:
                updateBoxSeq(DASHING, "dashing");
                break;
            case DEFENDING:
                updateBoxSeq(DEFENDING, "defend");
                break;
            case AIRDEFENDING:
                updateBoxSeq(AIRDEFENDING, "airDefend");
                break;
            case INTRO:
                updateBoxSeq(INTRO, "intro");
                break;
            case WIN:
                updateBoxSeq(WIN, "win");
                break;
            case DEAD:
                updateBoxSeq(DEAD, "dead");
                break;
            case RUNATTACK1:
                updateBoxSeq(RUNATTACK1, "runAttack1");
                break;
            case RUNATTACK2:
                updateBoxSeq(RUNATTACK2, "runAttack2");
                break;
            default:
                loadFurtherBoxes(character.getViewManager().getFrameIndex());
                break;
        }

    }

    public abstract void loadFurtherBoxes(int currentAnimation2);

    public void updateBoxSeq(int animationIndex, String animation_string) {
        currentAnimation = animationIndex;
        currentBox = boxes.get(animation_string);
        if (currentBox == null) {
            Log.i("info", "Die currentBox fuer: " + currentAnimation + " ist nicht vorhanden.");
        }
    }
}
