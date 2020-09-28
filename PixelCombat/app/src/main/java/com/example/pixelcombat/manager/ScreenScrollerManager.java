package com.example.pixelcombat.manager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.map.PXMap;
import com.example.pixelcombat.math.Vector2d;

import lombok.Getter;

import static com.example.pixelcombat.enums.ScreenProperty.OFFSET_X;
import static com.example.pixelcombat.enums.ScreenProperty.OFFSET_Y;
import static com.example.pixelcombat.enums.ScreenProperty.SCREEN_HEIGHT;
import static com.example.pixelcombat.enums.ScreenProperty.SCREEN_WIDTH;

public class ScreenScrollerManager {

    public static float CAMERA_X_SPEED = 25f;
    public static float CAMERA_Y_SPEED = 25f;
    @Getter
    private int CX;
    @Getter
    private int CY;
    @Getter
    private int screenX;
    @Getter
    private int screenY;
    @Getter
    private Vector2d pivot;
    @Getter
    private Vector2d target;
    private PXMap map;
    private int levelMaxX;
    private int levelMaxY;

    public void init(PXMap map) {
        this.map = map;
        CX = (SCREEN_WIDTH) / 2;
        CY = (SCREEN_HEIGHT) / 2;

        levelMaxX = map.getLength();
        levelMaxY = -map.getDeltaY();
        target = new Vector2d();
        pivot = new Vector2d();
    }


    public void update(GameCharacter player1, GameCharacter player2) {
        // helper variable for operations working with positions
        Vector2d pos1 = player1.getPos();
        Vector2d pos2 = player2.getPos();

        float x = (pos1.x + pos2.x) / 2f + OFFSET_X;
        float y = (pos1.y + pos2.y) / 2f - OFFSET_Y;

        if (target.x == 0f) {
            target.x = x;
        }
        float dir_x = Math.signum(x - target.x);
        // Bringe den Cursor auf die Mitte falls Target schon erreicht
        // (Juristierung)
        if ((target.x <= x && dir_x < 0f) || (target.x >= x && dir_x > 0f)) {
            target.x = x;
        } else { // Bewege den Cursor zur neuen Mitte des Bildschirs
            if ((target.x >= x && dir_x < 0f) || (target.x <= x && dir_x > 0f)) {
                target.x += dir_x * CAMERA_X_SPEED;
            }
            if (dir_x < 0f && target.x <= x)
                target.x = x;
            if (dir_x > 0f && target.x >= x)
                target.x = x;
            // Rände dürfen nicht passiert werden, dabei wird Bildschirmhälfte
            // als Maß genommen
            if (target.x > levelMaxX - CX)
                target.x = levelMaxX - CX;
            if (target.x < CX)
                target.x = CX;
        }

        // GLEICHES für Y
        if (target.y == 0f) {
            target.y = y;
        }
        float dir_y = Math.signum(y - target.y);
        // Bringe den Cursor auf die Mitte falls Target schon erreicht
        // (Juristierung)
        if ((target.y <= y && dir_y < 0f) || (target.y >= y && dir_y > 0f)) {
            target.y = y;
        } else {  // Bewege den Cursor zur neuen Mitte des Bildschirs
            if ((target.y >= y && dir_y < 0f) || (target.y <= y && dir_y > 0f)) {
                target.y += dir_y * CAMERA_Y_SPEED;
                if (dir_y < 0f && target.y <= y)
                    target.y = y;
                if (dir_y > 0f && target.y >= y)
                    target.y = y;
            }
        }

        // Rände dürfen nicht passiert werden, dabei wird Bildschirmhälfte
        // als Maß genommen
        if (target.y < -map.getDeltaY() + CY)
            target.y = -map.getDeltaY() + CY;
        if (target.y > CY) {
            target.y = CY;
        }

        // Virtuellen Bildverschiebung X
        // Cursor befindet sich in der ersten(linken) Bildschirmhaelfte
        if (target.x < CX) {
            screenX = CX;
        }  // Cursor befindet sich an der zweiten (rechten) Bildschirmhaelfte
        else if (target.x > (levelMaxX - CX)) {
            screenX = levelMaxX - CX;
        } // Charakter befindet sich dazwischen
        else {
            screenX = (int) target.x;
        }
        // Virtuellen Bildverschiebung Y
        // Cursor befindet sich in der ersten(obersten) Bildschirmhaelfte
        if (target.y > CY) {
            screenY = CY;
        } // Cursor befindet sich in der zweiten(unteren) Bildschirmhaelfte
        else if (target.y < -map.getDeltaY() + CY) {
            screenY = -levelMaxY + CY;
        } // Charakter befindet sich dazwischen
        else {
            screenY = (int) target.y;
        }

        pivot.x = x;
        pivot.y = y;
    }

}
