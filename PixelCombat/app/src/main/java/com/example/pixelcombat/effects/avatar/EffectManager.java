package com.example.pixelcombat.effects.avatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.effects.Effect;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.factories.EffectFactory;

import java.util.HashMap;
import java.util.Objects;

public abstract class EffectManager extends AppCompatImageView {

    protected GameCharacter character;
    protected Effect attackBgEffect;
    protected HashMap<String, Effect> effects = new HashMap<>();

    public EffectManager(Context context, GameCharacter character, Effect attackBgEffect) {
        super(context);
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

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        draw(canvas, 0, 0, null);
    }

    public abstract Effect checkAttacks();

    public void draw(Canvas canvas, int screenX, int CX, Rect gameRect) {

        Effect profileEffect = checkAttacks();

        if (profileEffect.isArtWork()) {
            profileEffect.draw(canvas, screenX, 0, gameRect);
            return;
        }

        profileEffect.update();

        if (profileEffect.isHorizontal()) {
            attackBgEffect.getAnimation().setHorizontal(true);
        } else {
            attackBgEffect.getAnimation().setHorizontal(false);
        }

        Bitmap sourceImage = profileEffect.getCurrentBitmap(gameRect);
        Bitmap result = attackBgEffect.getCurrentBitmap(gameRect);

        float panelWidth = ScreenProperty.SCREEN_WIDTH - 2 * ScreenProperty.OFFSET_X;

        if (profileEffect.isHorizontal()) {
            result = rotateBitmap(result, 90);
            result = scaleUntilScreenWidth(result);
        }

        Canvas mcanvas = new Canvas(result);


        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(0xFFFFFF00);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        paint.setAlpha(150);

        PorterDuff.Mode mode = PorterDuff.Mode.SRC_ATOP;
        paint.setXfermode(new PorterDuffXfermode(mode));

        mcanvas.drawBitmap(sourceImage, profileEffect.getPos().x, profileEffect.getPos().y, paint);
        int panelHeight = ScreenProperty.SCREEN_HEIGHT - ScreenProperty.OFFSET_Y;

        if (profileEffect.isHorizontal()) {
            canvas.drawBitmap(result, ScreenProperty.OFFSET_X, (float) panelHeight / 8f, null);
            return;
        }

        if (character.getPos().x - screenX + CX < (ScreenProperty.SCREEN_WIDTH - ScreenProperty.OFFSET_X) / 2f) {
            canvas.drawBitmap(result, ScreenProperty.SCREEN_WIDTH - ScreenProperty.OFFSET_X - ((int) (panelWidth / 10)) - result.getWidth() + 1, 0, null);
        } else {
            result = createFlippedBitmap(result, true, false);
            canvas.drawBitmap(result, ScreenProperty.OFFSET_X + ((int) (panelWidth / 10)), 0, null);
        }
    }

    public Bitmap scaleUntilScreenWidth(Bitmap bitmap) {
        int widthPic = bitmap.getWidth();
        float ratio = (ScreenProperty.SCREEN_WIDTH - (float) 2 * ScreenProperty.OFFSET_X) / (float) widthPic;

        int panelHeight = ScreenProperty.SCREEN_HEIGHT - ScreenProperty.OFFSET_Y;
        float scaleY = ratio;
        if (bitmap.getHeight() * ratio > panelHeight / 3f) {
            scaleY = ((float) panelHeight / 3f) / (float) bitmap.getHeight();
        }


        return Bitmap.createScaledBitmap(bitmap, ((int) (bitmap.getWidth() * ratio)), ((int) (bitmap.getHeight() * scaleY)), false);

    }

}
