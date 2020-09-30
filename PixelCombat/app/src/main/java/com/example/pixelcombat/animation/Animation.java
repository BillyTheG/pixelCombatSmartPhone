package com.example.pixelcombat.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

import lombok.Getter;

public class Animation {
    private boolean loops;
    private int loopPoint;
    private ArrayList<LocatedBitmap> images;
    protected ArrayList<AnimFrame> frames;

    @Getter
    private int frameIndex;
    private boolean isPlaying = false;

    public boolean isPlaying() {
        return isPlaying;
    }

    public void play() {
        isPlaying = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }
    public void stop() {
        isPlaying = false;
    }


    private long lastFrame;


    public Animation(ArrayList<LocatedBitmap> images, ArrayList<Float> times, boolean loops, int loopPoint) {
        this.images = images;
        this.loops = loops;
        this.loopPoint = loopPoint;
        frameIndex = 0;
        frames = new ArrayList<>();
        lastFrame = System.currentTimeMillis();
        loadFrames(images, times);

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

        int width = (int) (images.get(frameIndex).image.getWidth() * ScreenProperty.SCALE);
        int height = (int) (images.get(frameIndex).image.getHeight() * ScreenProperty.SCALE);

        int x = (int) (object.getPos().x + images.get(frameIndex).pos.x - screenX);
        int y = (int) (object.getPos().y + images.get(frameIndex).pos.y - screenY);

        Rect sourceRect = new Rect((int) (x - width / 2),
                (int) (y - height / 2), (int) (x + width / 2),
                (int) (y + height / 2));

        Rect desRect = new Rect(sourceRect.left + ScreenProperty.OFFSET_X, sourceRect.top - ScreenProperty.OFFSET_Y, (int) (sourceRect.right) + ScreenProperty.OFFSET_X,
                sourceRect.bottom - ScreenProperty.OFFSET_Y);

        Bitmap bitmap = cropBitmap(desRect, gameRect, object.isRight());
        if (bitmap == null) return;

        canvas.drawBitmap(bitmap, null, desRect, null);
    }

    private Bitmap cropBitmap(Rect desRect, Rect gameRect, boolean objectIsRight) {

        int top_old = desRect.top;
        int bottom_old = desRect.bottom;

        int left_old = desRect.left;
        int right_old = desRect.right;

        if (!desRect.intersect(gameRect))
            return null;
        else {
            Bitmap bitmap = getScaledBitmap(images.get(frameIndex).image);
            if (!objectIsRight)
                bitmap = createFlippedBitmap(bitmap, true, false);

            int new_right = (right_old - (ScreenProperty.SCREEN_WIDTH - ScreenProperty.OFFSET_X));
            int new_left = ScreenProperty.OFFSET_X - left_old;

            if (desRect.bottom < bottom_old) {
                if (new_left > 0)
                    return Bitmap.createBitmap(bitmap, new_left, 0, bitmap.getWidth() - new_left, Math.abs(desRect.top - desRect.bottom));
                else if (new_right > 0)
                    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() - new_right, Math.abs(desRect.top - desRect.bottom));
                else
                    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), Math.abs(desRect.top - desRect.bottom));

            } else {
                int new_height = desRect.top - desRect.bottom;
                if (new_left > 0)
                    return Bitmap.createBitmap(bitmap, new_left, desRect.top - top_old, bitmap.getWidth() - new_left, Math.abs(new_height));
                else if (new_right > 0)
                    return Bitmap.createBitmap(bitmap, 0, desRect.top - top_old, bitmap.getWidth() - new_right, Math.abs(new_height));
                else
                    return Bitmap.createBitmap(bitmap, 0, desRect.top - top_old, bitmap.getWidth(), Math.abs(new_height));
            }
        }

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


    public void update() {
        if (!isPlaying)
            return;

        if (System.currentTimeMillis() - lastFrame > frames.get(frameIndex).getEndTime()) {
            frameIndex++;
            if (!loops && frameIndex == frames.size()) {
                isPlaying = false;
                return;
            }
            frameIndex = frameIndex >= frames.size() ? loopPoint : frameIndex;
            lastFrame = System.currentTimeMillis();
        }


    }


    public static Bitmap createFlippedBitmap(Bitmap source, boolean xFlip, boolean yFlip) {
        Matrix matrix = new Matrix();
        matrix.postScale(xFlip ? -1 : 1, yFlip ? -1 : 1, source.getWidth() / 2f, source.getHeight() / 2f);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, ((int) (bitmap.getWidth() * ScreenProperty.SCALE)), ((int) (bitmap.getHeight() * ScreenProperty.SCALE)), false);
    }

}