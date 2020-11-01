package com.example.pixelcombat.effects.animation;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

import lombok.Getter;

public class EffectAnimation {
    private final int loopPoint;
    private final boolean scale;
    protected ArrayList<AnimFrame> frames;
    private ArrayList<LocatedBitmap> images;
    @Getter
    private float scaleFactor = ScreenProperty.SCALE;

    @Getter
    private int frameIndex;
    private boolean isPlaying;
    private long lastFrame;

    public EffectAnimation(ArrayList<LocatedBitmap> images, ArrayList<Float> times, boolean loops, int loopPoint, boolean scale) {
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

    public Bitmap getCurrentBitmap() {
        if (images.get(frameIndex) == null) {
            Log.e("Error", "the image for the animation in " + frameIndex + " cannot be drawn, It is a null.");
            return null;
        }
        if (scale)
            return getScaledBitmap(images.get(frameIndex).image, scaleFactor);

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

    private Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, ((int) (bitmap.getWidth() * ScreenProperty.SCALE)), ((int) (bitmap.getHeight() * ScreenProperty.SCALE)), false);
    }

    private Bitmap getScaledBitmap(Bitmap bitmap, float scaleFactor) {
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


}