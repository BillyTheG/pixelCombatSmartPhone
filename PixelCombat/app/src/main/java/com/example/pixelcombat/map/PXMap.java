package com.example.pixelcombat.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.core.Game;
import com.example.pixelcombat.core.config.ViewConfig;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.core.sound.SoundManager;
import com.example.pixelcombat.effects.attackCover.Darkening;
import com.example.pixelcombat.enums.DrawLevel;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.factories.EffectFactory;
import com.example.pixelcombat.manager.ScreenScrollerManager;
import com.example.pixelcombat.math.Vector2d;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;

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
    private Darkening darkening = new Darkening();
    @Setter
    private boolean darkeningActivated = false;

    public PXMap(String name, Bitmap bg, Context context, GameCharacter character1, GameCharacter character2) {
        this.bg = bg;
        this.name = name;
        this.length = bg.getWidth();
        this.height = bg.getHeight();
        this.context = context;
        this.character1 = character1;
        this.character2 = character2;

        character1.setEnemy(character2);
        character2.setEnemy(character1);

        character1.initAttacks();
        character2.initAttacks();


        if (height > ScreenProperty.SCREEN_HEIGHT) {
            deltaY = height - ScreenProperty.SCREEN_HEIGHT;
        }
        sourceRect = new Rect(0, 0, ScreenProperty.SCREEN_WIDTH, ScreenProperty.SCREEN_HEIGHT);
        gameRect = new Rect(OFFSET_X, 0, ScreenProperty.SCREEN_WIDTH - OFFSET_X, ScreenProperty.SCREEN_HEIGHT - OFFSET_Y);

        screenScrollManager = new ScreenScrollerManager();
        screenScrollManager.init(this);
        screenScrollManager.update(character1, character2);

        Log.i("Info", "Created the map: " + this);

    }

    public void initEffects(EffectFactory effectFactory) {
        character1.initEffects(effectFactory);
        character2.initEffects(effectFactory);
    }

    @Override
    public void draw(Canvas canvas, int screenX, int screenY, Rect gameRect) {
        sourceRect.offsetTo(screenScrollManager.getScreenX() - screenScrollManager.getCX(),
                deltaY + screenScrollManager.getScreenY() - screenScrollManager.getCY());

        canvas.drawBitmap(bg, sourceRect, this.gameRect, null);

        darkening.draw(canvas);

        drawEffects(canvas, this.gameRect, true);

        character1.draw(canvas, screenScrollManager.getScreenX() - screenScrollManager.getCX(), screenScrollManager.getScreenY() - screenScrollManager.getCY(), this.gameRect);
        character2.draw(canvas, screenScrollManager.getScreenX() - screenScrollManager.getCX(), screenScrollManager.getScreenY() - screenScrollManager.getCY(), this.gameRect);

        Paint paint = new Paint();
        paint.setColor(Color.RED);

        if (ViewConfig.DRAW_DEPTH == DrawLevel.DEBUG) {
            canvas.drawRect(character1.getPos().x - 5 - screenScrollManager.getScreenX() + screenScrollManager.getCX() + OFFSET_X,
                    character1.getPos().y - 5 - screenScrollManager.getScreenY() + screenScrollManager.getCY() - OFFSET_Y,
                    character1.getPos().x + 5 - screenScrollManager.getScreenX() + screenScrollManager.getCX() + OFFSET_X,
                    character1.getPos().y + 5 - screenScrollManager.getScreenY() + screenScrollManager.getCY() - OFFSET_Y,
                    paint);
        }
    }

    @Override
    public void update() throws PixelCombatException {

        int factor = (darkeningActivated) ? 1 : -1;

        darkening.update(factor);

        if (!character1.getStatusManager().isFreezed())
            character1.update();
        if (!character2.getStatusManager().isFreezed())
            character2.update();


        boolean borders1 = checkHorizontalBorders(character1);
        boolean borders2 = checkHorizontalBorders(character2);

        if (!borders1 && !borders2)
            screenScrollManager.update(character1, character2);

    }

    private boolean checkHorizontalBorders(GameCharacter character) {

        if (character.getStatusManager().isFocused() || character.getEnemy().getStatusManager().isFocused())
            return false;

        if (screenScrollManager.isOneCharacterWasFocused())
            return false;

        if (character.getPos().x < BORDER_WIDTH) {
            character.getPos().x = BORDER_WIDTH;
            checkReflect(character);
            return true;
        } else {
            float v = BORDER_WIDTH + screenScrollManager.getTarget().x - screenScrollManager.getCX() + OFFSET_X;
            if (character.getPos().x + OFFSET_X < v) {
                character.getPos().x = v - OFFSET_X + 5;
                checkReflect(character);
                return true;
            }
        }

        if (character.getPos().x >= (length - 2 * OFFSET_X - BORDER_WIDTH)) {
            character.getPos().x = (length - 2 * OFFSET_X - BORDER_WIDTH) - 5;
            checkReflect(character);
            return true;
        } else {
            aFloat = (-BORDER_WIDTH + screenScrollManager.getTarget().x + screenScrollManager.getCX() - OFFSET_X);
            if (character.getPos().x + OFFSET_X >= aFloat) {
                character.getPos().x = aFloat - OFFSET_X - 5;
                checkReflect(character);
                return true;
            }
        }
        return false;
    }

    private void checkReflect(GameCharacter character) {
        try {
            if (character.getStatusManager().isKnockbacked() || character.getStatusManager().isKnockBackFalling()) {

                character.notifyObservers(new GameMessage(MessageType.SHAKE, "", null, true));
                character.notifyObservers(new GameMessage(MessageType.DUST_CREATION, "Dust_Hard_Land_Side;" + character.getPlayer(),
                        new Vector2d(character.getPos().x - character.getDirection() * BORDER_WIDTH, character.getPos().y - BORDER_WIDTH), character.getPhysics().VX > 0));
                character.notifyObservers(new GameMessage(MessageType.SOUND, "hard_ground_hit", null, true));
                character.getStatusManager().swapDirections();
                character.getPhysics().VX = -character.getPhysics().VX;

            }
        } catch (Exception e) {
            Log.e("Error", "The Reflect could not be handled: " + e.getMessage());
        }
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

            return delta1 >= delta2;
        }
    }


    @Override
    public Vector2d getPos() {
        return null;
    }

    @Override
    public boolean isRight() {
        return true;
    }

    @Override
    public float getDirection() {
        return 0;
    }

    @Override
    public int getRank() {
        return 0;
    }

    @Override
    public float getScaleFactor() {
        return ScreenProperty.GENERAL_SCALE;
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

    public void registerGame(Game game) {
        character1.addObserver(game);
        character2.addObserver(game);
        character1.addObserver(screenScrollManager);
        character2.addObserver(screenScrollManager);
    }

    public void registerSoundManager(SoundManager soundManager) {
        character1.addObserver(soundManager);
        character2.addObserver(soundManager);
    }


    public void putFreezeOn(String owner, boolean state) {

        if (owner.equals(character1.getPlayer())) {
            character2.getStatusManager().setFreeze(state);
        } else {
            character1.getStatusManager().setFreeze(state);
        }

    }

    public void drawEffects(Canvas canvas, Rect gameRect, boolean back) {
        if (character1.getStatusManager().makesEffect() && character1.getEffectManager().checkAttacks().isArtWork() == back)
            character1.getEffectManager().draw(canvas, screenScrollManager.getScreenX(), screenScrollManager.getCX(), gameRect);
        if (character2.getStatusManager().makesEffect() && character1.getEffectManager().checkAttacks().isArtWork() == back)
            character2.getEffectManager().draw(canvas, screenScrollManager.getScreenX(), screenScrollManager.getCX(), gameRect);
    }
}
