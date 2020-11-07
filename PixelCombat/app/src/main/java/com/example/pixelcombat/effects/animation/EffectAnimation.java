package com.example.pixelcombat.effects.animation;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

import lombok.Getter;

public class EffectAnimation {
    private final int loopPoint;
    private final boolean scale;
    protected ArrayList<AnimFrame> frames;
    protected ArrayList<LocatedBitmap> images;
    @Getter
    private float scaleFactor = ScreenProperty.GENERAL_SCALE;

    @Getter
    private int frameIndex;
    private boolean isPlaying;
    private long lastFrame;

    public EffectAnimation(ArrayList<LocatedBitmap> images, ArrayList<Float> times, int loopPoint, boolean scale) {
        this.images = images;
        this.loopPoint = loopPoint;
        frameIndex = 0;
        this.scale = scale;
        if (scale)
            scaleUntilScreenHeight();
        frames = new ArrayList<>();
        lastFrame = System.currentTimeMillis();
        loadFrames(images, times);

    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void play() {
        isPlaying = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }

    protected void loadFrames(ArrayList<LocatedBitmap> images, ArrayList<Float> times) {

        for (int i = 0; i < images.size(); i++) {
            addFrame(images.get(i).image, times.get(i));
        }
    }

    public void addFrame(Bitmap image, float duration) {
        frames.add(new AnimFrame(image, duration));
    }

    public Bitmap getCurrentBitmap(Rect gameRect) {
        if (images.get(frameIndex) == null) {
            Log.e("Error", "the image for the animation in " + frameIndex + " cannot be drawn, It is a null.");
            return null;
        }
        if (scale) {
            Bitmap scaledBitmap = getScaledBitmap(images.get(frameIndex).image, scaleFactor);

            float centerX = (float) ScreenProperty.SCREEN_WIDTH / 2f;
            float centerY = (float) (ScreenProperty.SCREEN_HEIGHT - ScreenProperty.OFFSET_Y) / 2f;

            int left = (int) (centerX - scaledBitmap.getWidth() / 2);
            int top = (int) (centerY - scaledBitmap.getHeight() / 2);
            int right = (int) (centerX + scaledBitmap.getWidth() / 2);
            int bottom = (int) (centerY + scaledBitmap.getHeight() / 2);


            Rect rect = new Rect(left, top, right, bottom);
            return cropBitmap(rect, gameRect, scaledBitmap);
        }

        return images.get(frameIndex).image;
    }

    public void update() {

        if (System.currentTimeMillis() - lastFrame > frames.get(frameIndex).getEndTime()) {
            frameIndex++;
            frameIndex = frameIndex >= frames.size() ? loopPoint : frameIndex;
            lastFrame = System.currentTimeMillis();
        }


    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public void scaleUntilScreenHeight() {
        int heightPic = images.get(frameIndex).image.getHeight();
        float ratio = (ScreenProperty.SCREEN_HEIGHT - (float) ScreenProperty.OFFSET_Y) / (float) heightPic;
        setScaleFactor(ratio);
    }

    private Bitmap getScaledBitmap(Bitmap bitmap, float scaleFactor) {
        if ((int) (bitmap.getHeight() * scaleFactor) > ScreenProperty.SCREEN_HEIGHT - ScreenProperty.OFFSET_Y) {
            scaleUntilScreenHeight();
            scaleFactor = this.scaleFactor;
        }
        return Bitmap.createScaledBitmap(bitmap, ((int) (bitmap.getWidth() * scaleFactor)), ((int) (bitmap.getHeight() * scaleFactor)), false);
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

    private Bitmap cropBitmap(Rect desRect, Rect gameRect, Bitmap bitmap) {

        int top_old = desRect.top;
        int bottom_old = desRect.bottom;

        int left_old = desRect.left;
        int right_old = desRect.right;

        if (!desRect.intersect(gameRect))
            return null;
        else {

            int new_right = (right_old - (ScreenProperty.SCREEN_WIDTH - ScreenProperty.OFFSET_X));
            int new_left = ScreenProperty.OFFSET_X - left_old;

            if (desRect.bottom < bottom_old) {
                if (new_left > 0 && new_right > 0)
                    return Bitmap.createBitmap(bitmap, new_left, 0, bitmap.getWidth() - new_left - new_right, Math.abs(desRect.top - desRect.bottom));
                if (new_left > 0)
                    return Bitmap.createBitmap(bitmap, new_left, 0, bitmap.getWidth() - new_left, Math.abs(desRect.top - desRect.bottom));
                else if (new_right > 0)
                    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() - new_right, Math.abs(desRect.top - desRect.bottom));
                else
                    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), Math.abs(desRect.top - desRect.bottom));

            } else {
                int new_height = desRect.top - desRect.bottom;
                if (new_left > 0 && new_right > 0)
                    return Bitmap.createBitmap(bitmap, new_left, 0, bitmap.getWidth() - new_left - new_right, Math.abs(desRect.top - desRect.bottom));
                if (new_left > 0)
                    return Bitmap.createBitmap(bitmap, new_left, desRect.top - top_old, bitmap.getWidth() - new_left, Math.abs(new_height));
                else if (new_right > 0)
                    return Bitmap.createBitmap(bitmap, 0, desRect.top - top_old, bitmap.getWidth() - new_right, Math.abs(new_height));
                else
                    return Bitmap.createBitmap(bitmap, 0, desRect.top - top_old, bitmap.getWidth(), Math.abs(new_height));
            }
        }

    }


}