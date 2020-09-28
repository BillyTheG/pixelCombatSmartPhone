package com.example.pixelcombat.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.manager.ScreenScrollerManager;
import com.example.pixelcombat.math.Vector2d;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import static com.example.pixelcombat.enums.GamePlayView.BORDER_HEIGHT;
import static com.example.pixelcombat.enums.GamePlayView.BORDER_WIDTH;
import static com.example.pixelcombat.enums.ScreenProperty.OFFSET_X;
import static com.example.pixelcombat.enums.ScreenProperty.OFFSET_Y;

@Getter
public class PXMap implements GameObject {

    private final Context context;
    private final String name;
    private final ScreenScrollerManager screenScrollManager;
    private Bitmap bg;
    private int length;
    private int height;
    private int deltaY;
    private Rect sourceRect;
    private Rect gameRect;

    private GameCharacter character1;
    private GameCharacter character2;
    private float aFloat;

    public PXMap(String name, Bitmap bg, Context context, GameCharacter character1, GameCharacter character2) {
        this.bg = bg;
        this.name = name;
        this.length = bg.getWidth();
        this.height = bg.getHeight();
        this.context = context;
        this.character1 = character1;
        this.character2 = character2;

        if (height > ScreenProperty.SCREEN_HEIGHT) {
            deltaY = height - ScreenProperty.SCREEN_HEIGHT;
        }
        sourceRect = new Rect(0, 0, ((int) (ScreenProperty.SCREEN_WIDTH)), ScreenProperty.SCREEN_HEIGHT);
        gameRect = new Rect(OFFSET_X, 0, ScreenProperty.SCREEN_WIDTH - OFFSET_X, ScreenProperty.SCREEN_HEIGHT - OFFSET_Y);

        screenScrollManager = new ScreenScrollerManager();
        screenScrollManager.init(this);
        screenScrollManager.update(character1, character2);

        Log.i("Info", "Created the map: " + this);

    }

    @Override
    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {
        sourceRect.offsetTo(0 + screenScrollManager.getScreenX() - screenScrollManager.getCX(),
                deltaY + screenScrollManager.getScreenY() - screenScrollManager.getCY());
        canvas.drawBitmap(bg, sourceRect, this.gameRect, null);
        character1.draw(canvas, screenScrollManager.getScreenX() - screenScrollManager.getCX(), screenScrollManager.getScreenY() - screenScrollManager.getCY(), this.gameRect);
        character2.draw(canvas, screenScrollManager.getScreenX() - screenScrollManager.getCX(), screenScrollManager.getScreenY() - screenScrollManager.getCY(), this.gameRect);

    }

    @Override
    public void update() {

        boolean borders1 = checkHorizontalBorders(character1) || checkVerticalBorders(character1, character2);
        boolean borders2 = checkHorizontalBorders(character2) || checkVerticalBorders(character2, character1);


        if (!borders1 && !borders2)
            screenScrollManager.update(character1, character2);

    }

    private boolean checkHorizontalBorders(GameCharacter character) {
        if (character.getPos().x < BORDER_WIDTH) {
            character.getPos().x = BORDER_WIDTH;
            return true;
        } else {
            float v = BORDER_WIDTH + screenScrollManager.getTarget().x - screenScrollManager.getCX() + OFFSET_X;
            if (character.getPos().x + OFFSET_X < v) {
                character.getPos().x = v - OFFSET_X + 5;
                return true;
            }
        }

        if (character.getPos().x >= (length - 2 * OFFSET_X - BORDER_WIDTH)) {
            character.getPos().x = (length - 2 * OFFSET_X - BORDER_WIDTH) - 5;
            return true;
        } else {
            aFloat = (-BORDER_WIDTH + screenScrollManager.getTarget().x + screenScrollManager.getCX() - OFFSET_X);
            if (character.getPos().x + OFFSET_X >= aFloat) {
                character.getPos().x = aFloat - OFFSET_X - 5;
                return true;
            }
        }

        return false;

    }

    private boolean checkVerticalBorders(GameCharacter character1, GameCharacter character2) {
        //upper Border
        if (character1.getPos().y <= -deltaY + BORDER_HEIGHT) {
            character1.getPos().y = -deltaY + BORDER_HEIGHT;
            return true;
        } else {

            float y_char = character1.getPos().y - screenScrollManager.getScreenY() + screenScrollManager.getCY();
            float v = character2.getPos().y - screenScrollManager.getScreenY() + screenScrollManager.getCY();

            float delta1 = Math.abs(y_char - v);
            float delta2 = Math.abs((float) (screenScrollManager.getCY()));

            if (delta1 >= delta2) {
                return true;
            }
        }

        return false;

    }


    @Override
    public Vector2d getPos() {
        return null;
    }

    @Override
    public boolean isRight() {
        return true;
    }

    @NotNull
    @Override
    public String toString() {
        return "PXMap{" +
                "  name='" + name + '\'' +
                ", length=" + length +
                ", height=" + height +
                '}';
    }
}
