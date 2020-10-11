package com.example.pixelcombat.manager;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.GeometryUtils;
import com.example.pixelcombat.math.Vector2d;
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
    public final int CROUCH = 5;
    public final int DECROUCH = 6;
    public final int ATTACK1 = 7;
    public final int DISABLE = 8;
    public final int DISABLERECOVER = 9;
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
    @Getter
    @Setter
    private BoundingRectangle intersectionBox;

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
        //   if (character.getViewManager().getAnimation() == currentAnimation)
        //       return;

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
            case CROUCH:
                updateBoxSeq(CROUCH, "crouch");
                break;
            case DECROUCH:
                updateBoxSeq(DECROUCH, "decrouch");
                break;
            case ATTACK1:
                updateBoxSeq(ATTACK1, "attack1");
                break;
            case ATTACK2:
                updateBoxSeq(ATTACK2, "attack2");
                break;
            case ATTACK3:
                updateBoxSeq(ATTACK3, "attack3");
                break;
            case DEFENDSTOP:
                updateBoxSeq(DEFENDSTOP, "defendStop");
                break;
            case DEFEND:
                updateBoxSeq(DEFEND, "defend");
                break;
            case SPECIALATTACK1:
                updateBoxSeq(SPECIALATTACK1, "specialAttack1");
                break;
            case SPECIALATTACK3:
                updateBoxSeq(SPECIALATTACK3, "specialAttack3");
                break;
            case DISABLE:
                updateBoxSeq(KNOCKBACK, "disabled");
                break;
            case DISABLERECOVER:
                updateBoxSeq(KNOCKBACK, "disabledRecover");
                break;
            case KNOCKBACK:
                updateBoxSeq(KNOCKBACK, "knockBack");
                break;
            case KNOCKBACKFALL:
                updateBoxSeq(KNOCKBACK, "knockBackFall");
                break;
            case KNOCKBACKRECOVER:
                updateBoxSeq(KNOCKBACKRECOVER, "knockBackRecover");
                break;
            case ONGROUND:
                updateBoxSeq(ONGROUND, "onGround");
                break;
            case DASH:
                updateBoxSeq(DASH, "dash");
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

    /**
     * Checks if a hurtable rect of this player hits the enemy player
     *
     * @param defender Enemy Character
     * @return do rectangles collide
     */
    public boolean hits(GameCharacter defender) {

        List<ArrayList<BoundingRectangle>> enemyBoxes = defender.getBoxManager().currentBox;
        List<ArrayList<BoundingRectangle>> ownBoxes = character.getBoxManager().currentBox;
        int currentOwnAnimation = character.getViewManager().getFrameIndex();
        int currentDefenderAnimation = defender.getViewManager().getFrameIndex();

        for (int i = 0; i < ownBoxes.get(currentOwnAnimation).size(); i++) {
            float x1 = character.getPos().x + character.getDirection() * (ownBoxes.get(currentOwnAnimation).get(i).getPos().x);
            float y1 = character.getPos().y + ownBoxes.get(currentOwnAnimation).get(i).getPos().y;
            float delta_x1 = ownBoxes.get(currentOwnAnimation).get(i).getDelta_pos_x();
            float delta_y1 = ownBoxes.get(currentOwnAnimation).get(i).getDelta_pos_y();
            float width1 = (delta_x1 * 2 + ownBoxes.get(currentOwnAnimation).get(i).getWidth());
            float height1 = (delta_y1 * 2 + ownBoxes.get(currentOwnAnimation).get(i).getHeight());
            boolean hurts1 = ownBoxes.get(currentOwnAnimation).get(i).isHurts();

            BoundingRectangle ownBox = new BoundingRectangle(height1, new Vector2d(x1, y1), width1, delta_x1, delta_y1);
            ownBox.setHurts(hurts1);

            for (int j = 0; j < enemyBoxes.get(currentDefenderAnimation).size(); j++) {
                float x2 = defender.getPos().x + defender.getDirection() * enemyBoxes.get(currentDefenderAnimation).get(j).getPos().x;
                float y2 = defender.getPos().y + enemyBoxes.get(currentDefenderAnimation).get(j).getPos().y;
                boolean hurts2 = enemyBoxes.get(currentDefenderAnimation).get(j).isHurts();
                float delta_x2 = enemyBoxes.get(currentDefenderAnimation).get(j).getDelta_pos_x();
                float delta_y2 = enemyBoxes.get(currentDefenderAnimation).get(j).getDelta_pos_y();
                float width2 = (delta_x1 * 2 + enemyBoxes.get(currentDefenderAnimation).get(j).getWidth());
                float height2 = (delta_y1 * 2 + enemyBoxes.get(currentDefenderAnimation).get(j).getHeight());

                BoundingRectangle enemyBox = new BoundingRectangle(height2, new Vector2d(x2, y2), width2, delta_x2, delta_y2);
                enemyBox.setHurts(hurts2);

                if (GeometryUtils.isCollision(ownBox, enemyBox) && ownBox.isHurts() && !enemyBox.isHurts()) {
                    intersectionBox = intersection(ownBox, enemyBox);
                    defender.getBoxManager().setIntersectionBox(intersectionBox);
                    return true;
                }
            }

        }

        return false;
    }

    public BoundingRectangle intersection(BoundingRectangle ownBox, BoundingRectangle box1) {
        float x1 = ownBox.getUpperLeft().x;
        float y1 = ownBox.getUpperLeft().y;
        float x2 = box1.getUpperLeft().x;
        float y2 = box1.getUpperLeft().y;

        float x3 = (x1 + ownBox.getWidth());
        float x4 = (x2 + box1.getWidth());
        float y3 = (y1 + ownBox.getHeight());
        float y4 = (y2 + box1.getHeight());

        float xmin = Math.max(x1, x2);
        float xmax1 = x3;
        float xmax2 = x4;
        float xmax = Math.min(xmax1, xmax2);
        if (xmax > xmin) {
            float ymin = Math.max(y1, y2);
            float ymax1 = y3;
            float ymax2 = y4;
            float ymax = Math.min(ymax1, ymax2);
            if (ymax > ymin) {
                float out_x = xmin + (xmax - xmin) / 2f;
                float out_y = ymin + (ymax - ymin) / 2f;
                float out_width = xmax - xmin;
                float out_height = ymax - ymin;
                return new BoundingRectangle(out_height, new Vector2d(out_x, out_y), out_width, 0, 0);
            }
        }
        return null;
    }

}
