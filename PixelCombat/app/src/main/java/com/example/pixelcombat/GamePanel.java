package com.example.pixelcombat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;

import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.core.Game;
import com.example.pixelcombat.core.sound.SoundManager;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.environment.interactor.CollisionDetection;
import com.example.pixelcombat.map.PXMap;
import com.example.pixelcombat.map.weather.Weather;
import com.example.pixelcombat.math.Vector2d;

import lombok.Getter;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private Context context;
    private MainThread thread;
    private Bitmap bg;


    private Kohaku ruffy;

    private Kohaku kohaku;
    private CollisionDetection collisionDetection;
    private PXMap testMap;
    private Game game;
    private SoundManager soundManager;
    @Getter
    private GameCharacter player1;
    @Getter
    private GameCharacter player2;

    public GamePanel(Context context) throws Exception {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        ScreenProperty.CURRENT_CONTEXT = context;
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        init();
    }

    private void init() throws Exception {

        BitmapFactory bf = new BitmapFactory();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        bg = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.cold_winter_1, options);
        bg = Bitmap.createScaledBitmap(bg, ((int) (ScreenProperty.SCREEN_WIDTH * 1.5f)), ((int) (ScreenProperty.SCREEN_HEIGHT * 1.4f)), false);


        ruffy = new Kohaku("player2", new Vector2d(500, ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE), context);
        kohaku = new Kohaku("player1", new Vector2d(1000, ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE), context);
        ruffy.getBoxManager().loadParsedBoxes();
        kohaku.getBoxManager().loadParsedBoxes();
        kohaku.getStatusManager().setMovementStatus(MovementStatus.LEFT);

        player1 = kohaku;
        player2 = ruffy;

        testMap = new PXMap("Blue Winter", bg, context, player1, player2);
        collisionDetection = new CollisionDetection(player1, player2);

        soundManager = new SoundManager(context);
        game = new Game(context, testMap, new Weather(), soundManager);

        testMap.registerSoundManager(soundManager);

        Log.i("Info", "Game was created successfully");
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
                soundManager.getSoundPool().release();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update(){
        game.update();
        collisionDetection.interact();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void draw(Canvas canvas){
        super.draw(canvas);
        game.draw(canvas);
        canvas.restore();
    }

}
