package com.example.pixelcombat.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.enums.ScreenProperty;

import java.util.ArrayList;

import lombok.Getter;

public class Animation {
    private boolean loops;
    private int loopPoint;
    private ArrayList<Bitmap> images;
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


    public Animation(ArrayList<Bitmap> images, ArrayList<Float> times, boolean loops, int loopPoint) {
        this.images = images;
        this.loops = loops;
        this.loopPoint = loopPoint;
        frameIndex = 0;
        frames = new ArrayList<>();
        lastFrame = System.currentTimeMillis();
        loadFrames(images, times);

    }


    protected void loadFrames(ArrayList<Bitmap> images, ArrayList<Float> times) {

        for (int i = 0; i < images.size(); i++) {
            addFrame(images.get(i), times.get(i));
        }

    }

    public void addFrame(Bitmap image, float duration) {
        frames.add(new AnimFrame(image, duration));
    }

    public void draw(Canvas canvas, GameObject object, int screenX, int screenY, Rect gameRect) {
        if (!isPlaying)
            return;

        int width = images.get(frameIndex).getWidth();
        int height = images.get(frameIndex).getHeight();

        int x = (int) (object.getPos().x - screenX);
        int y = (int) (object.getPos().y - screenY);

        Rect sourceRect = new Rect((int) (x - width / 2),
                (int) (y - height / 2), (int) (x + width / 2),
                (int) (y + height / 2));

        Rect desRect = new Rect(sourceRect.left + ScreenProperty.OFFSET_X, sourceRect.top - ScreenProperty.OFFSET_Y, (int) (sourceRect.right) + ScreenProperty.OFFSET_X,
                sourceRect.bottom - ScreenProperty.OFFSET_Y);

        Bitmap bitmap = cropBitmap(desRect, gameRect);
        if (bitmap == null) return;

        if (!object.isRight())
            canvas.drawBitmap(createFlippedBitmap(bitmap, true, false), null, desRect, null);
        else
            canvas.drawBitmap(bitmap, null, desRect, null);
    }

    private Bitmap cropBitmap(Rect desRect, Rect gameRect) {

        int top_old = desRect.top;
        int bottom_old = desRect.bottom;

        if (!desRect.intersect(gameRect))
            return null;
        else {
            if (desRect.bottom < bottom_old)
                return Bitmap.createBitmap(images.get(frameIndex), 0, 0, images.get(frameIndex).getWidth(), Math.abs(desRect.top - desRect.bottom));
            else {
                int new_height = desRect.top - desRect.bottom;
                return Bitmap.createBitmap(images.get(frameIndex), 0, desRect.top - top_old, images.get(frameIndex).getWidth(), Math.abs(new_height));
            }
        }

    }

    protected class AnimFrame {
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

    public void scaleRect(Rect rect) {
        float whRatio = (float) (images.get(frameIndex).getWidth()) / images.get(frameIndex).getHeight();
        if (rect.width() > rect.height())
            rect.left = rect.right - (int) (rect.height() * whRatio);
        else
            rect.top = rect.bottom - (int) (rect.width() * (1 / whRatio));
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
}