package com.example.pixelcombat.effects.avatar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.effects.Effect;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.factories.EffectFactory;

import java.util.HashMap;
import java.util.Objects;

public abstract class EffectManager {

    protected GameCharacter character;
    protected Effect attackBgEffect;
    protected HashMap<String, Effect> effects = new HashMap<>();

    public EffectManager(GameCharacter character, Effect attackBgEffect) {
        this.character = character;
        this.attackBgEffect = attackBgEffect;
    }

    public static Bitmap createFlippedBitmap(Bitmap source, boolean xFlip, boolean yFlip) {
        Matrix matrix = new Matrix();
        matrix.postScale(xFlip ? -1 : 1, yFlip ? -1 : 1, source.getWidth() / 2f, source.getHeight() / 2f);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public abstract void init(EffectFactory effectFactory);

    public void reset() {
        for (String effectName : effects.keySet()) {
            Objects.requireNonNull(effects.get(effectName)).reset();
        }
    }

    public void update() {
        attackBgEffect.update();
        Effect profileEffect = checkAttacks();
        profileEffect.update();

    }

    public void draw(Canvas canvas, int screenX, int CX, Rect gameRect) {

        Effect profileEffect = checkAttacks();

        if (profileEffect.isArtWork()) {
            profileEffect.draw(canvas, screenX, 0, gameRect);
            return;
        }

        profileEffect.update();

        Bitmap sourceImage = profileEffect.getCurrentBitmap(gameRect);
        Bitmap result = attackBgEffect.getCurrentBitmap(gameRect);

        float panelWidth = ScreenProperty.SCREEN_WIDTH - 2 * ScreenProperty.OFFSET_X;

        Canvas mcanvas = new Canvas(result);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(0xFFFFFF00);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);


        PorterDuff.Mode mode = PorterDuff.Mode.SRC_ATOP;
        paint.setXfermode(new PorterDuffXfermode(mode));
        mcanvas.drawBitmap(sourceImage, (int) profileEffect.getPos().x, profileEffect.getPos().y, paint);

        if (character.getPos().x - screenX + CX < (ScreenProperty.SCREEN_WIDTH - ScreenProperty.OFFSET_X) / 2f) {
            canvas.drawBitmap(result, ScreenProperty.SCREEN_WIDTH - ScreenProperty.OFFSET_X - ((int) (panelWidth / 10)) - result.getWidth() + 1, 0, null);
        } else {
            result = createFlippedBitmap(result, true, false);
            canvas.drawBitmap(result, ScreenProperty.OFFSET_X + ((int) (panelWidth / 10)), 0, null);
        }
    }

    public abstract Effect checkAttacks();


}
