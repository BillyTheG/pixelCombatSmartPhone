package com.example.pixelcombat.environment.interactor;

import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.ActionStatus;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.environment.EnvironmentInteract;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.GeometryUtils;
import com.example.pixelcombat.math.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetection implements EnvironmentInteract {

    private final GameCharacter player1;
    private final GameCharacter player2;
    private final float HORIZONTAL_SHIFT = 15F;

    private BoundingRectangle currentColBox;

    public CollisionDetection(GameCharacter player1, GameCharacter player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void interact() {
        checkCollision(player1, player2);
        checkCollision(player2, player1);
    }

    private void checkCollision(GameCharacter player1, GameCharacter player2) {
        if (playersCanNotCollide(player1) || playersCanNotCollide(player2)) {
            return;
        }
        if (checkFurtherCollisions(player1, player2) || checkFurtherCollisions(player2, player1)) {
            return;
        }

        BoundingRectangle iRect = intersection(player1, player2);
        if (iRect == null) {
            resetCollideVars(player1);
            resetCollideVars(player2);
            return;
        }

        Log.i("Info", "Both Players Collide");
        // the intersection box
        float x1 = iRect.getUpperLeft().x;
        float y1 = iRect.getUpperLeft().y;
        float w1 = iRect.getWidth();
        float h1 = iRect.getHeight();

        Log.i("Info", "IRect: " + iRect);

        checkCollisionFromLeftOrRight(player1, x1, w1);
        checkCollisionFromLeftOrRight(player2, x1, w1);
    }

    private void checkCollisionFromBotton(GameCharacter player1, GameCharacter player2, float y1, float h1) {

        float y2 = player1.getBoxManager().currentColBox.getUpperLeft().y;
        float h2 = player1.getBoxManager().currentColBox.getHeight();

        // we hit from botton
        if ((y1 + h1 / 2.0F < y2 + h2 / 2.0F) && (!player1.getBoxManager().isCollidingY())) {
            player1.getPhysics().VY = Math.abs(player1.getPhysics().VY);
            player1.getBoxManager().setCollidingY(true);
            //both players have to repel each other
            if (player1.getPos().x < player2.getPos().x) {
                player1.getPhysics().VX -= HORIZONTAL_SHIFT;
                player2.getPhysics().VX += HORIZONTAL_SHIFT;
            } else {
                player1.getPhysics().VX += HORIZONTAL_SHIFT;
                player2.getPhysics().VX -= HORIZONTAL_SHIFT;
            }
        }
    }

    private void checkCollisionFromAbove(GameCharacter player1, GameCharacter player2, float y1, float h1) {

        float y2 = player1.getBoxManager().currentColBox.getUpperLeft().y;
        float h2 = player1.getBoxManager().currentColBox.getHeight();

        if ((y1 + h1 / 2.0F > y2 + h2 / 2.0F) && (!player1.getBoxManager().isCollidingY())) {
            player1.getPhysics().VY = -Math.abs(player1.getPhysics().VY);
            player1.getBoxManager().setCollidingY(true);

            //both players have to repel each other
            if (player1.getPos().x < player2.getPos().x) {
                player1.getPhysics().VX -= HORIZONTAL_SHIFT;
                player2.getPhysics().VX += HORIZONTAL_SHIFT;
            } else {
                player1.getPhysics().VX += HORIZONTAL_SHIFT;
                player2.getPhysics().VX -= HORIZONTAL_SHIFT;
            }
        }
    }

    private void checkCollisionFromLeftOrRight(GameCharacter player1, float x1, float w1) {

        // rectangle box of one arbitrary player
        float x2 = player1.getBoxManager().currentColBox.getUpperLeft().x;
        float w2 = player1.getBoxManager().currentColBox.getWidth();

        // we hit from left
        if ((x1 + w1 == x2 + w2)) {
            player1.getPhysics().VX = -HORIZONTAL_SHIFT;
            player1.getBoxManager().setCollidingX(true);
        }
        if ((x1 + w1 < x2 + w2 && x1 == x2)) {
            player1.getPhysics().VX = HORIZONTAL_SHIFT;
            player1.getBoxManager().setCollidingX(true);
        }
    }

    private void resetCollideVars(GameCharacter player1) {
        if ((player1.getBoxManager().isCollidingX()) && (!player1.getBoxManager().isCollidingY())) {
            player1.getStatusManager().setActionStatus(ActionStatus.STAND);
            player1.getBoxManager().setCollidingX(false);
        }
        if (player1.getBoxManager().isCollidingY()) {
            player1.getStatusManager().setActionStatus(ActionStatus.STAND);
            player1.getBoxManager().setCollidingY(false);
        }
    }

    private boolean playersCanNotCollide(GameCharacter player1) {
        return player1.getStatusManager().isOnAir() ||
                !player1.getStatusManager().isActive() ||
                player1.getStatusManager().isDashing();
    }

    private boolean checkFurtherCollisions(GameCharacter player1, GameCharacter player2) {
        return (player1.getStatusManager().isActive() &&
                (player2.getStatusManager().isKnockbacked()) ||
                player2.getStatusManager().isKnockBackRecovering() ||
                player2.getStatusManager().isDisabled() ||
                player2.getStatusManager().isInvincible());
    }




    public BoundingRectangle intersection(GameCharacter player1, GameCharacter player2) {

        List<ArrayList<BoundingRectangle>> enemyBoxes = player2.getBoxManager().currentBox;
        List<ArrayList<BoundingRectangle>> ownBoxes = player1.getBoxManager().currentBox;
        int currentOwnAnimation = player1.getViewManager().getFrameIndex();
        int currentDefenderAnimation = player2.getViewManager().getFrameIndex();

        for (int i = 0; i < ownBoxes.get(currentOwnAnimation).size(); i++) {
            float x1 = player1.getPos().x + player1.getDirection() * ownBoxes.get(currentOwnAnimation).get(i).getPos().x;
            float y1 = player1.getPos().y + ownBoxes.get(currentOwnAnimation).get(i).getPos().y;
            float width1 = ownBoxes.get(currentOwnAnimation).get(i).getWidth();
            float height1 = ownBoxes.get(currentOwnAnimation).get(i).getHeight();
            boolean hurts1 = ownBoxes.get(currentOwnAnimation).get(i).isHurts();

            if (!hurts1) {
                BoundingRectangle ownBox = new BoundingRectangle(height1, new Vector2d(x1, y1), width1);
                ownBox.setHurts(false);

                for (int j = 0; j < enemyBoxes.get(currentDefenderAnimation).size(); j++) {
                    float x2 = player2.getPos().x + player2.getDirection() * enemyBoxes.get(currentDefenderAnimation).get(j).getPos().x;
                    float y2 = player2.getPos().y + enemyBoxes.get(currentDefenderAnimation).get(j).getPos().y;
                    float width2 = enemyBoxes.get(currentDefenderAnimation).get(j).getWidth();
                    float height2 = enemyBoxes.get(currentDefenderAnimation).get(j).getHeight();
                    boolean hurts2 = enemyBoxes.get(currentDefenderAnimation).get(j).isHurts();

                    if (!hurts2) {
                        BoundingRectangle enemyBox = new BoundingRectangle(height2, new Vector2d(x2, y2), width2);
                        enemyBox.setHurts(false);
                        if (GeometryUtils.isCollision(ownBox, enemyBox)) {

                            x1 = ownBox.getUpperLeft().x;
                            y1 = ownBox.getUpperLeft().y;
                            x2 = enemyBox.getUpperLeft().x;
                            y2 = enemyBox.getUpperLeft().y;

                            float x3 = (x1 + ownBox.getWidth());
                            float x4 = (x2 + enemyBox.getWidth());
                            float y3 = (y1 + ownBox.getHeight());
                            float y4 = (y2 + enemyBox.getHeight());

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
                                    player1.getBoxManager().currentColBox = ownBox;
                                    player2.getBoxManager().currentColBox = enemyBox;
                                    return new BoundingRectangle(out_height / ScreenProperty.SCALE, new Vector2d(out_x, out_y), out_width / ScreenProperty.SCALE);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public BoundingRectangle intersection(GameCharacter player1, BoundingRectangle box1) {

        List<ArrayList<BoundingRectangle>> ownBoxes = player1.getBoxManager().currentBox;
        int currentOwnAnimation = player1.getViewManager().getFrameIndex();

        for (int i = 0; i < ownBoxes.get(currentOwnAnimation).size(); i++) {
            float x1 = player1.getPos().x + player1.getDirection() * ownBoxes.get(currentOwnAnimation).get(i).getPos().x;
            float y1 = player1.getPos().y + ownBoxes.get(currentOwnAnimation).get(i).getPos().y;
            float width1 = ownBoxes.get(currentOwnAnimation).get(i).getWidth();
            float height1 = ownBoxes.get(currentOwnAnimation).get(i).getHeight();
            BoundingRectangle ownBox = new BoundingRectangle(height1, new Vector2d(x1, y1), width1);
            if (GeometryUtils.isCollision(ownBox, box1)) {

                x1 = ownBox.getUpperLeft().x;
                y1 = ownBox.getUpperLeft().y;
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
                        this.currentColBox = ownBox;
                        return new BoundingRectangle(out_height, new Vector2d(out_x, out_y), out_width);

                    }
                }
            }
        }
        return null;
    }


}
