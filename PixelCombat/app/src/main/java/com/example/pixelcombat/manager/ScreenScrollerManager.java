package com.example.pixelcombat.manager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.map.PXMap;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.observer.Observer;

import lombok.Getter;

import static com.example.pixelcombat.enums.ScreenProperty.GROUND_LINE;
import static com.example.pixelcombat.enums.ScreenProperty.OFFSET_X;
import static com.example.pixelcombat.enums.ScreenProperty.OFFSET_Y;
import static com.example.pixelcombat.enums.ScreenProperty.SCREEN_HEIGHT;
import static com.example.pixelcombat.enums.ScreenProperty.SCREEN_WIDTH;

public class ScreenScrollerManager implements Observer {

    public static float CAMERA_X_SPEED = 25f;
    public static float CAMERA_Y_SPEED = 25f;

    private boolean SHAKE_ME = false;
    private boolean shakeDirection = true;
    private float shake = 0f;
    private long shakeTimer = 0;

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
    private long lastFrame;


    public void init(PXMap map) {
        this.map = map;
        CX = (SCREEN_WIDTH) / 2;
        CY = (SCREEN_HEIGHT) / 2;

        levelMaxX = map.getLength();
        levelMaxY = -map.getDeltaY();
        target = new Vector2d();
        pivot = new Vector2d();
        lastFrame = System.currentTimeMillis();
    }


    public void update(GameCharacter player1, GameCharacter player2) {
        // helper variable for operations working with positions
        Vector2d pos1 = player1.getPos();
        Vector2d pos2 = player2.getPos();

        float x = (pos1.x + pos2.x) / 2f + OFFSET_X;
        float y = (pos1.y + pos2.y) / 2f - OFFSET_Y + GROUND_LINE;

        if (target.x == 0f) {
            target.x = x;
        }

        if (target.y == 0f) {
            target.y = y;
        }

        if (SHAKE_ME) {
            updateShake(player1, player2);
            updateVirtualScrollFactors();
            return;
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

        // Rände dürfen nicht passiert werden, dabei wird Bildschirmhälfte als Maß genommen
        if (target.y < -map.getDeltaY() + CY)
            target.y = -map.getDeltaY() + CY;
        if (target.y > CY) {
            target.y = CY;
        }

        updateVirtualScrollFactors();
        pivot.x = x;
        pivot.y = y;
        lastFrame = System.currentTimeMillis();
    }

    private void updateVirtualScrollFactors() {
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
    }


    // Shaker
    private void updateShake(GameCharacter player1, GameCharacter player2) {

        long currentTime = System.currentTimeMillis();

        if (shakeTimer == 0) {
            // If its the first run from the current shake, initialise x and y
            // offset to 0
            shake = 0;
        }

        // Add passed milliseconds to timer... If timer exceeds configuration,
        // shaking ends
        shakeTimer += (currentTime - lastFrame);
        long SHAKE_TIME_MS = 600;
        if (shakeTimer > SHAKE_TIME_MS) {
            // Shaking ends
            shakeTimer = 0;
            // this.char_player1.shaking = false;
            // this.char_player2.shaking = false;
            SHAKE_ME = false;
            shakeDirection = true;
            shake = 0;
        } else {
            applyScreenShake(player1, player2);
        }
        lastFrame = currentTime;
    }


    private void applyScreenShake(GameCharacter player1, GameCharacter player2) {
        // Depending on shake direction, the screen is moved either to the top
        // left, or the bottom right
        float SHAKE_OFFSET = 25f;
        float shakeSpeed = 12.5f;
        Vector2d new_target = new Vector2d();

        if (shakeDirection) {
            shake -= shakeSpeed;
            new_target.x = getTarget().x - shakeSpeed;
            new_target.y = getTarget().y - shakeSpeed;
            if (shake < -SHAKE_OFFSET) {
                // SWITCH DIRECTION
                shake = 0;
                shakeDirection = false;
            }
        } else {
            shake += shakeSpeed;
            new_target.x = getTarget().x + shakeSpeed;
            new_target.y = getTarget().y + shakeSpeed;
            if (shake > SHAKE_OFFSET) {
                // SWITCH DIRECTION
                shake = 0;
                shakeDirection = true;
            }
        }


        if (getTarget().distance(new_target) < 50) {
            getTarget().x = getTarget().x + shake;
            if (getTarget().x > levelMaxX - CX)
                getTarget().x = levelMaxX - CX;
            if (getTarget().x < CX)
                getTarget().x = CX;
            getTarget().y = getTarget().y + shake;
            if (getTarget().y < levelMaxY + CY)
                getTarget().y = levelMaxY + CY;
            if (getTarget().y > CY)
                getTarget().y = CY;
        } else {
            //char_player1.shaking = false;
            //char_player2.shaking = false;
        }

        Vector2d middle_point = new Vector2d();
        middle_point.x = (player1.getPos().x + player2.getPos().x) / 2f;
        middle_point.y = (player1.getPos().y + player2.getPos().y) / 2f;

        if (getTarget().distance(middle_point) > 10f) {
            //  char_player1.shaking = false;
            //  char_player2.shaking = false;
        }

    }


    @Override
    public void processMessage(GameMessage gameMessage) {
        if (gameMessage.getMessageType() != MessageType.SHAKE) {
            return;
        }
        SHAKE_ME = true;
    }
}
