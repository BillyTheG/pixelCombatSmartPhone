package com.example.pixelcombat.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class DustAnimation {
    private final int loopPoint;
    private final ArrayList<LocatedBitmap> images;
    protected ArrayList<AnimFrame> frames;
    private int alpha = 0;
    private boolean loops;
    @Getter
    private int frameIndex;
    private boolean isPlaying;
    private long lastFrame;
    private long currentTime;
    private Paint paint = new Paint();
    @Getter
    private Bitmap currentBitmap = null;
    @Getter
    private Rect currentDesRect;
    @Getter
    private Paint currentPaint;
    private int currentIndex;
    @Setter
    private boolean preScaled = false;

    public DustAnimation(ArrayList<LocatedBitmap> images, ArrayList<Float> times, boolean loops, int loopPoint, int alpha) {
        this(images, times, loops, loopPoint);
        this.alpha = alpha;
    }


    public DustAnimation(ArrayList<LocatedBitmap> images, ArrayList<Float> times, boolean loops, int loopPoint) {
        this.images = images;
        this.loops = loops;
        this.loopPoint = loopPoint;
        frameIndex = 0;
        frames = new ArrayList<>();
        lastFrame = System.currentTimeMillis();
        loadFrames(images, times);


    }

    public static Bitmap createFlippedBitmap(Bitmap source, boolean xFlip, boolean yFlip) {
        Matrix matrix = new Matrix();
        matrix.postScale(xFlip ? -1 : 1, yFlip ? -1 : 1, source.getWidth() / 2f, source.getHeight() / 2f);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void play() {
        isPlaying = true;
        frameIndex = 0;
        currentIndex = frameIndex;
        lastFrame = System.currentTimeMillis();
    }

    public void stop() {
        isPlaying = false;
    }

    protected void loadFrames(ArrayList<LocatedBitmap> images, ArrayList<Float> times) {

        for (int i = 0; i < images.size(); i++) {
            addFrame(images.get(i).image, times.get(i));
        }
    }

    public void addFrame(Bitmap image, float duration) {
        frames.add(new AnimFrame(image, duration));
    }

    public void draw(Canvas canvas, GameObject object, int screenX, int screenY, Rect gameRect) {
        if (!isPlaying)
            return;

        if (images.get(frameIndex) == null) {
            Log.e("Error", "the image for the animation in " + frameIndex + " cannot be drawn, It is a null.");
            return;
        }

        if (currentBitmap != null && currentIndex == frameIndex) {
            if (alpha != 0) {
                canvas.drawBitmap(currentBitmap, null, currentDesRect, paint);
            } else
                canvas.drawBitmap(currentBitmap, null, currentDesRect, null);

            return;
        }


        int width = (int) (images.get(frameIndex).image.getWidth());
        int height = images.get(frameIndex).image.getHeight();

        if (!preScaled) {
            width = (int) (width * object.getScaleFactor());
            height = (int) (height * object.getScaleFactor());
        }

        int x = (int) (object.getPos().x + object.getDirection() * images.get(frameIndex).pos.x * object.getScaleFactor() - screenX);
        int y = (int) (object.getPos().y + images.get(frameIndex).pos.y * object.getScaleFactor() - screenY);

        Rect sourceRect = new Rect(x - width / 2,
                (int) (y - height / 2), x + width / 2,
                y + height / 2);

        Rect desRect = new Rect(sourceRect.left + ScreenProperty.OFFSET_X, sourceRect.top - ScreenProperty.OFFSET_Y, sourceRect.right + ScreenProperty.OFFSET_X,
                sourceRect.bottom - ScreenProperty.OFFSET_Y);




       /* int distanceToGround = (int) Math.abs(ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE - object.getPos().y);

        if (object instanceof GameCharacter && ViewConfig.SHADOW_LEVEL == ShadowLevel.REAL)
            ShadowCreator.drawCharacterShadow(canvas, bitmap, object.isRight(), desRect, distanceToGround);*/

        Bitmap bitmap = cropBitmap(desRect, gameRect, object);


        paint.setAlpha(alpha);

        this.currentBitmap = bitmap;
        this.currentDesRect = desRect;
        this.currentPaint = paint;
        this.currentIndex = frameIndex;
        if (bitmap == null) return;


        if (alpha != 0) {
            canvas.drawBitmap(bitmap, null, desRect, paint);
        } else
            canvas.drawBitmap(bitmap, null, desRect, null);

    }

    private Bitmap cropBitmap(Rect desRect, Rect gameRect, GameObject gameObject) {

        int top_old = desRect.top;
        int bottom_old = desRect.bottom;

        int left_old = desRect.left;
        int right_old = desRect.right;

        if (!desRect.intersect(gameRect))
            return null;
        else {
            Bitmap bitmap;
            if (!gameObject.isRight())
                bitmap = createFlippedBitmap(images.get(frameIndex).image, true, false);
            else
                bitmap = images.get(frameIndex).image;

            if (!preScaled)
                bitmap = getScaledBitmap(bitmap, gameObject.getScaleFactor());

            int new_right = (right_old - (ScreenProperty.SCREEN_WIDTH - ScreenProperty.OFFSET_X));
            int new_left = ScreenProperty.OFFSET_X - left_old;

            if (desRect.bottom < bottom_old) {
                if (new_left > 0 && new_right > 0)
                    return Bitmap.createBitmap(bitmap, new_left, desRect.top - top_old, bitmap.getWidth() - new_left - new_right, Math.abs(desRect.top - desRect.bottom));
                if (new_left > 0)
                    return Bitmap.createBitmap(bitmap, new_left, desRect.top - top_old, bitmap.getWidth() - new_left, Math.abs(desRect.top - desRect.bottom));
                else if (new_right > 0)
                    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() - new_right, Math.abs(desRect.top - desRect.bottom));
                else
                    return bitmap;

            } else {
                int new_height = desRect.top - desRect.bottom;
                if (new_left > 0 && new_right > 0)
                    return Bitmap.createBitmap(bitmap, new_left, desRect.top - top_old, bitmap.getWidth() - new_left - new_right, Math.abs(desRect.top - desRect.bottom));
                if (new_left > 0)
                    return Bitmap.createBitmap(bitmap, new_left, desRect.top - top_old, bitmap.getWidth() - new_left, Math.abs(new_height));
                else if (new_right > 0)
                    return Bitmap.createBitmap(bitmap, 0, desRect.top - top_old, bitmap.getWidth() - new_right, Math.abs(new_height));
                else
                    return bitmap;
            }
        }

    }

    public void update() {
        if (!isPlaying)
            return;

        if (System.currentTimeMillis() - lastFrame > frames.get(frameIndex).getEndTime()) {
            frameIndex++;

            if (!loops && frameIndex == frames.size()) {
                isPlaying = false;
                frameIndex--;
                return;
            }
            frameIndex = frameIndex >= frames.size() ? loopPoint : frameIndex;

            lastFrame = System.currentTimeMillis();
        }
        long delta = System.currentTimeMillis() - currentTime;
        currentTime = System.currentTimeMillis();

    }

    private Bitmap getScaledBitmap(Bitmap bitmap, float scaleFactor) {

        if (scaleFactor == 1f) {
            return bitmap;
        }

        return Bitmap.createScaledBitmap(bitmap, ((int) (bitmap.getWidth() * scaleFactor)), ((int) (bitmap.getHeight() * scaleFactor)), false);
    }

    public synchronized void resetFrameIndexTo(int newFrameIndex) {
        if (newFrameIndex < 0 || newFrameIndex >= frames.size()) {
            Log.e("Error", "the new Index: " + newFrameIndex + " is out of Range.");
            return;
        }

        frameIndex = newFrameIndex;
        lastFrame = System.currentTimeMillis();

    }

    public synchronized void setLoops(boolean loops) {
        this.loops = loops;
    }

    public synchronized boolean animationSequenceAlmostFinished(long restTime) {
        // return System.currentTimeMillis() - lastFrame > frames.get(frameIndex).getEndTime() - restTime;

        return lastFrame + (long) frames.get(frameIndex).getEndTime() - restTime <= currentTime;
    }

    protected static class AnimFrame {
        Bitmap image;
        float endTime;

        public AnimFrame(Bitmap image, float endTime) {
            this.image = image;
            this.endTime = endTime;
        }

        public float getEndTime() {
            return endTime;
        }
    }

}