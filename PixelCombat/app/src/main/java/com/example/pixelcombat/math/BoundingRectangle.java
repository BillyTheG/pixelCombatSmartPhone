package com.example.pixelcombat.math;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.enums.ScreenProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * A simple rectangular bounding box
 *
 * @author BillyG
 * @version 0.1
 */
@Getter
@Setter
public class BoundingRectangle implements BoundingBoxInterface {

    private float delta_pos_y;
    private float delta_pos_x;
    private Vector2d upperLeft;
    private Vector2d lowerRight;

    private float width;
    private float height;
    private Vector2d pos;
    private boolean hurts = false;

    /**
     * Constructor of BoundingRectangle
     *
     * @param upperLeft  position of upper left corner
     * @param lowerRight position of lower right corner
     */
    public BoundingRectangle(Vector2d upperLeft, Vector2d lowerRight) {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
        this.pos = new Vector2d((upperLeft.x - lowerRight.x) / 2f, (upperLeft.y - lowerRight.y) / 2f);

        this.height = height;
        this.width = width;
    }

    /**
     * Constructor of BoundingRectangle
     *
     * @param pos,    the middle point of the bottom line
     * @param height, height of the Rect
     * @param width,  width of the Rect
     */
    public BoundingRectangle(Vector2d pos, float height, float width) {
        this.upperLeft = new Vector2d(pos.x - width / 2f, pos.y - height);
        this.lowerRight = new Vector2d(pos.x + width / 2f, pos.y);
        this.pos = new Vector2d((upperLeft.x - lowerRight.x) / 2f, (upperLeft.y - lowerRight.y) / 2f);

        this.height = height;
        this.width = width;

    }

    /**
     * Constructor of BoundingRectangle
     *
     * @param height, height of the Rect
     * @param pos,    the middle point of the whole Rectangle Volume
     * @param width,  width of the Rect
     */
    public BoundingRectangle(float height, Vector2d pos, float width) {

        this.pos = new Vector2d(pos.x * ScreenProperty.SCALE, pos.y * ScreenProperty.SCALE);
        this.width = ScreenProperty.SCALE * width;
        this.height = ScreenProperty.SCALE * height;

        this.upperLeft = new Vector2d(pos.x - this.width / 2f, pos.y - this.height / 2f);
        this.lowerRight = new Vector2d(pos.x + this.width / 2f, pos.y + this.height / 2f);


    }

    /**
     * Constructor of BoundingRectangle
     *
     * @param height, height of the Rect
     * @param pos,    the middle point of the whole Rectangle Volume
     * @param width,  width of the Rect
     */
    public BoundingRectangle(float height, Vector2d pos, float width, float delta_pos_x, float delta_pos_y) {

        this.width = width;
        this.height = height;

        this.upperLeft = new Vector2d(pos.x - this.width / 2f, pos.y - this.height / 2f);
        this.lowerRight = new Vector2d(pos.x + this.width / 2f, pos.y + this.height / 2f);
        this.pos = pos;

        this.delta_pos_x = delta_pos_x;
        this.delta_pos_y = delta_pos_y;
    }

    /**
     * Returns whether or not the bounding box collides with the vector
     *
     * @param v vector
     * @return does collide
     */

    public boolean isCollision(Vector2d v) {
        if (upperLeft.x <= v.x && upperLeft.y <= v.y && lowerRight.x >= v.x && lowerRight.y >= v.y) {
            return true;
        }
        return false;
    }

    public void draw(GameCharacter character, Canvas canvas, float screenX, float screenY, Rect gameRect) {

        float x1 = character.getDirection() * (pos.x - width / 2f) + character.getPos().x + ScreenProperty.OFFSET_X;
        float y1 = (pos.y - height / 2f) + character.getPos().y - ScreenProperty.OFFSET_Y;
        float x2 = character.getDirection() * (pos.x + width / 2f) + character.getPos().x + ScreenProperty.OFFSET_X;
        float y2 = (pos.y + height / 2f) + character.getPos().y - ScreenProperty.OFFSET_Y;

        Paint paint = new Paint();
        if (hurts)
            paint.setColor(Color.RED);
        else
            paint.setColor(Color.BLUE);

        paint.setStrokeWidth(5);
        //horizontal
        canvas.drawLine(x1 - screenX, y1 - screenY, x2 - screenX, y1 - screenY, paint);
        canvas.drawLine(x1 - screenX, y2 - screenY, x2 - screenX, y2 - screenY, paint);
        //vertical
        canvas.drawLine(x1 - screenX, y1 - screenY, x1 - screenX, y2 - screenY, paint);
        canvas.drawLine(x2 - screenX, y1 - screenY, x2 - screenX, y2 - screenY, paint);
    }


    public void edit(float height, Vector2d point, float width) {
        this.pos.x = point.x;
        this.pos.y = point.y;

        this.upperLeft = new Vector2d(pos.x - width / 2f, pos.y - height / 2f);
        this.lowerRight = new Vector2d(pos.x + width / 2f, pos.y + height / 2f);


        this.height = height;
        this.width = width;

    }


    public float getWidth() {
        return (lowerRight.x - upperLeft.x);
    }

    public float getHeight() {
        return (lowerRight.y - upperLeft.y);

    }

    @Override
    public String toString() {
        return "BoundingRectangle{" +
                "upperLeft=" + upperLeft +
                ", lowerRight=" + lowerRight +
                ", width=" + width +
                ", height=" + height +
                ", pos=" + pos +
                ", hurts=" + hurts +
                '}';
    }
}
