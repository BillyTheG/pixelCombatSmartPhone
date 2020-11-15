package com.example.pixelcombat.dusts;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.enums.ScreenProperty;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BitmapCroppingThread implements Runnable {

    private Bitmap resultBitmap;
    private Bitmap currentBitmap;
    private Rect desRect;
    private Rect gameRect;
    private GameObject gameObject;
    private boolean preScaled;


    public BitmapCroppingThread(Bitmap resultBitmap, Bitmap currentBitmap, Rect desRect, Rect gameRect, GameObject gameObject, boolean preScaled) {
        this.resultBitmap = resultBitmap;
        this.currentBitmap = currentBitmap;
        this.desRect = desRect;
        this.gameRect = gameRect;
        this.gameObject = gameObject;
        this.preScaled = preScaled;
    }

    public static Bitmap createFlippedBitmap(Bitmap source, boolean xFlip, boolean yFlip) {
        Matrix matrix = new Matrix();
        matrix.postScale(xFlip ? -1 : 1, yFlip ? -1 : 1, source.getWidth() / 2f, source.getHeight() / 2f);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        cropBitmap();
    }

    private void cropBitmap() {

        int top_old = desRect.top;
        int bottom_old = desRect.bottom;

        int left_old = desRect.left;
        int right_old = desRect.right;

        if (!desRect.intersect(gameRect))
            resultBitmap = null;
        else {
            Bitmap bitmap = null;
            if (!gameObject.isRight())
                bitmap = createFlippedBitmap(currentBitmap, true, false);
            else
                bitmap = currentBitmap;

            if (!preScaled)
                bitmap = getScaledBitmap(bitmap, gameObject.getScaleFactor());

            int new_right = (right_old - (ScreenProperty.SCREEN_WIDTH - ScreenProperty.OFFSET_X));
            int new_left = ScreenProperty.OFFSET_X - left_old;

            if (desRect.bottom < bottom_old) {
                if (new_left > 0 && new_right > 0)
                    resultBitmap = Bitmap.createBitmap(bitmap, new_left, desRect.top - top_old, bitmap.getWidth() - new_left - new_right, Math.abs(desRect.top - desRect.bottom));
                if (new_left > 0)
                    resultBitmap = Bitmap.createBitmap(bitmap, new_left, desRect.top - top_old, bitmap.getWidth() - new_left, Math.abs(desRect.top - desRect.bottom));
                else if (new_right > 0)
                    resultBitmap = Bitmap.createBitmap(bitmap, 0, desRect.top - top_old, bitmap.getWidth() - new_right, Math.abs(desRect.top - desRect.bottom));
                else
                    resultBitmap = bitmap;

            } else {
                int new_height = desRect.top - desRect.bottom;
                if (new_left > 0 && new_right > 0)
                    resultBitmap = Bitmap.createBitmap(bitmap, new_left, desRect.top - top_old, bitmap.getWidth() - new_left - new_right, Math.abs(desRect.top - desRect.bottom));
                if (new_left > 0)
                    resultBitmap = Bitmap.createBitmap(bitmap, new_left, desRect.top - top_old, bitmap.getWidth() - new_left, Math.abs(new_height));
                else if (new_right > 0)
                    resultBitmap = Bitmap.createBitmap(bitmap, 0, desRect.top - top_old, bitmap.getWidth() - new_right, Math.abs(new_height));
                else
                    resultBitmap = bitmap;
            }
        }

    }

    private Bitmap getScaledBitmap(Bitmap bitmap, float scaleFactor) {

        if (scaleFactor == 1f) {
            return bitmap;
        }

        return Bitmap.createScaledBitmap(bitmap, ((int) (bitmap.getWidth() * scaleFactor)), ((int) (bitmap.getHeight() * scaleFactor)), false);
    }


}
