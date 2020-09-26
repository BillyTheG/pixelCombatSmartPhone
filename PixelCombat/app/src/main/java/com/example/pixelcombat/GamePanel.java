package com.example.pixelcombat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pixelcombat.character.chars.ruffy.Ruffy;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.environment.interactor.CollisionDetection;
import com.example.pixelcombat.math.Vector2d;

import lombok.Getter;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private Context context;
    private MainThread thread;
    private Bitmap bg;
    private Rect sourceRect;
    private Rect gameRect;
    private Paint paint;
    @Getter
    private Ruffy ruffy;
    private Ruffy ruffy2;
    private CollisionDetection collisionDetection;

    public GamePanel(Context context) throws Exception {
        super(context);
        this.context = context;
        getHolder().addCallback(this);

        ScreenProperty.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(),this);

        setFocusable(true);

        sourceRect = new Rect(0,0,((int)(ScreenProperty.SCREEN_WIDTH)),ScreenProperty.SCREEN_HEIGHT);

        gameRect = new Rect(ScreenProperty.OFFSET_X,0,ScreenProperty.SCREEN_WIDTH-ScreenProperty.OFFSET_X,ScreenProperty.SCREEN_HEIGHT-ScreenProperty.OFFSET_y);

        init();
    }

    private void init() throws Exception {

        BitmapFactory bf = new BitmapFactory();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        bg = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.cold_winter_1, options);
        bg = Bitmap.createScaledBitmap(bg, ((int) (ScreenProperty.SCREEN_WIDTH * 1.5f)), ((int) (ScreenProperty.SCREEN_HEIGHT)), false);

        ruffy = new Ruffy(new Vector2d(500, ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE), context);
        ruffy2 = new Ruffy(new Vector2d(700, ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE), context);
        ruffy.getBoxManager().loadParsedBoxes();
        ruffy2.getBoxManager().loadParsedBoxes();

        collisionDetection = new CollisionDetection(ruffy, ruffy2);
    }

    @Override
    public void surfaceChanged (SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated (SurfaceHolder holder){
    thread = new MainThread(getHolder(),this);
    thread.setRunning(true);
    thread.start();
    }

    @Override
    public void surfaceDestroyed (SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void update(){
        ruffy.update();
        ruffy2.update();
        collisionDetection.interact();
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(bg,sourceRect,gameRect,null);
        ruffy.draw(canvas);
        ruffy2.draw(canvas);

    }

}
