package com.example.pixelcombat.projectile.manager;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.GeometryUtils;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.projectile.Projectile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public abstract class ProjectileBoxManager {

    // Stütze für Bilder
    public final int CREATION = 0;
    public final int MOVE = 1;
    public final int EXPLOSION = 2;

    public int currentAnimation;
    public BoundingRectangle currentColBox;
    @Getter
    public List<ArrayList<BoundingRectangle>> currentBox;
    public Map<String, ArrayList<ArrayList<BoundingRectangle>>> boxes;
    private Projectile character;

    @Getter
    @Setter
    private BoundingRectangle intersectionBox;

    public ProjectileBoxManager(Projectile character) {
        this.character = character;
    }

    public void init() {
        currentAnimation = CREATION;
        currentBox = boxes.get("creation");
    }


    public void update() {

        switch (character.getViewManager().getAnimation()) {
            case CREATION:
                updateBoxSeq(CREATION, "creation");
                break;
            case MOVE:
                updateBoxSeq(MOVE, "move");
                break;
            case EXPLOSION:
                updateBoxSeq(EXPLOSION, "explosion");
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
        List<ArrayList<BoundingRectangle>> ownBoxes = this.currentBox;
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
