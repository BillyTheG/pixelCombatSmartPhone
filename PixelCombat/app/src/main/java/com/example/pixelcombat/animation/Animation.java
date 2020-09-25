package com.example.pixelcombat.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.enums.ScreenProperty;

import java.util.ArrayList;

public class Animation {
    private boolean once;
    private int loopPoint;
    private ArrayList<Bitmap> images;
    protected ArrayList<AnimFrame> frames;
    private ArrayList<Float> times;
    private int frameIndex;
    private Paint paint = new Paint();
    private boolean isPlaying = false;
    private float totalDuration;

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

    private float frameTime;

    private long lastFrame;


    public Animation(ArrayList<Bitmap> images, ArrayList<Float> times, boolean once, int loopPoint) {
        this.images = images;
        this.times = times;
        this.once = once;
        this.loopPoint = loopPoint;
        frameIndex = 0;
        frames = new ArrayList<>();
        lastFrame = System.currentTimeMillis();
        loadFrames(images,times);

    }


    protected void loadFrames(ArrayList<Bitmap> images, ArrayList<Float> times) {

        for(int i = 0; i< images.size();i++){
            addFrame(images.get(i),times.get(i));
        }

    }
    public void addFrame(Bitmap image, float duration)
    {

        totalDuration += duration;
        frames.add(new AnimFrame(image,duration));

    }


    protected class AnimFrame
    {
        Bitmap image;
        float endTime;

        public AnimFrame(Bitmap image, float endTime){
            this.image = image;
            this.endTime = endTime;
        }

        public float getEndTime() {
            return endTime;
        }
    }

    public void draw(Canvas canvas, GameObject object) {
        if(!isPlaying)
            return;

        int width = images.get(frameIndex).getWidth();
        int height = images.get(frameIndex).getHeight();

        Rect sourceRect = new Rect((int)(object.getPos().x-width/2),
                (int)(object.getPos().y-height/2),(int)(object.getPos().x+width/2),
                (int)(object.getPos().y+height/2));

        Rect desRect = new Rect(sourceRect.left+ ScreenProperty.OFFSET_X,sourceRect.top-ScreenProperty.OFFSET_y,(int)(sourceRect.right)+ScreenProperty.OFFSET_X,
                sourceRect.bottom-ScreenProperty.OFFSET_y);
        if(!object.isRight())
            canvas.drawBitmap(createFlippedBitmap(images.get(frameIndex),true,false),null,desRect,null);
        else
            canvas.drawBitmap(images.get(frameIndex),null,desRect,null);
      //  canvas.drawBitmap(frames[frameIndex], ((int) object.getPos().x-width/2), (int)object.getPos().y-height/2, null);
    }

    private void scaleRect(Rect rect) {
        float whRatio = (float)(images.get(frameIndex).getWidth())/images.get(frameIndex).getHeight();
        if(rect.width() > rect.height())
            rect.left = rect.right - (int)(rect.height() * whRatio);
        else
            rect.top = rect.bottom - (int)(rect.width() * (1/whRatio));
    }

    public void update() {
        if(!isPlaying)
            return;

        if(System.currentTimeMillis() - lastFrame > frames.get(frameIndex).getEndTime()) {
            frameIndex++;
            if (once) {
                isPlaying = false;
            } else {
                frameIndex = frameIndex >= frames.size() ? loopPoint : frameIndex;
                lastFrame = System.currentTimeMillis();
            }
        }
    }


    public static Bitmap createFlippedBitmap(Bitmap source, boolean xFlip, boolean yFlip) {
        Matrix matrix = new Matrix();
        matrix.postScale(xFlip ? -1 : 1, yFlip ? -1 : 1, source.getWidth() / 2f, source.getHeight() / 2f);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}