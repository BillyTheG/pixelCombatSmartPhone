package com.example.pixelcombat.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.enums.ScreenProperty;

public class Animation {
    private Bitmap[] frames;
    private int frameIndex;
    private Paint paint = new Paint();
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

    private float frameTime;

    private long lastFrame;

    public Animation(Bitmap[] frames, float animTime) {
        this.frames = frames;
        frameIndex = 0;

        frameTime = animTime/frames.length;

        lastFrame = System.currentTimeMillis();
    }

    public void draw(Canvas canvas, GameObject object) {
        if(!isPlaying)
            return;

        int width = frames[frameIndex].getWidth();
        int height = frames[frameIndex].getHeight();

        Rect sourceRect = new Rect((int)(object.getPos().x-width/2),
                (int)(object.getPos().y-height/2),(int)(object.getPos().x+width/2),
                (int)(object.getPos().y+height/2));

        Rect desRect = new Rect(sourceRect.left+ ScreenProperty.OFFSET_X,sourceRect.top-ScreenProperty.OFFSET_y,(int)(sourceRect.right)+ScreenProperty.OFFSET_X,
                sourceRect.bottom-ScreenProperty.OFFSET_y);

        canvas.drawBitmap(frames[frameIndex],null,desRect,null);
      //  canvas.drawBitmap(frames[frameIndex], ((int) object.getPos().x-width/2), (int)object.getPos().y-height/2, null);
    }

    private void scaleRect(Rect rect) {
        float whRatio = (float)(frames[frameIndex].getWidth())/frames[frameIndex].getHeight();
        if(rect.width() > rect.height())
            rect.left = rect.right - (int)(rect.height() * whRatio);
        else
            rect.top = rect.bottom - (int)(rect.width() * (1/whRatio));
    }

    public void update() {
        if(!isPlaying)
            return;

        if(System.currentTimeMillis() - lastFrame > frameTime*1000) {
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }
}