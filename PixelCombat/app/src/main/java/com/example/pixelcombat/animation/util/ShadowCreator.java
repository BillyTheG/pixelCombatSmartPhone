package com.example.pixelcombat.animation.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;

public class ShadowCreator {

    private static float skewX = -0.2f;
    private static float skewY = 0.4f;

    public static void drawCharacterShadow(Canvas canvas, Bitmap bitmap, boolean right, Rect destRec, int distanceToGround) {
        int offSet = (int) (skewX * bitmap.getHeight());
        if (!right)
            offSet *= 1;
        offSet = 0;
        Rect rect = new Rect((int) ((destRec.left)) + offSet,
                (int) (destRec.top + 2 * distanceToGround) + bitmap.getHeight(),
                (int) ((destRec.right)) + offSet,
                (int) (destRec.bottom + 2 * distanceToGround) + bitmap.getHeight());


        int pivotX = rect.left + rect.width() / 2;
        int pivotY = rect.top + rect.height() / 2;


        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(createFlippedBitmap(bitmap, rect, false, true), null, rect, paint);

    }

    public static Bitmap createFlippedBitmap(Bitmap source, Rect rect, boolean xFlip, boolean yFlip) {
        Matrix matrix = new Matrix();
        matrix.postScale(xFlip ? -1 : 1, yFlip ? -1 : 1, source.getWidth() / 2f, source.getHeight() / 2f);
        //       matrix.postSkew(skewX,skewY);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


}
