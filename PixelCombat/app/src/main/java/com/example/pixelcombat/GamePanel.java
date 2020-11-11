package com.example.pixelcombat;

import android.annotation.SuppressLint;
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

import com.example.pixelcombat.ai.KohakuAI;
import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.character.chars.kohaku.manager.KohakuComboManager;
import com.example.pixelcombat.character.chars.shana.Shana;
import com.example.pixelcombat.character.status.MovementStatus;
import com.example.pixelcombat.core.Game;
import com.example.pixelcombat.core.sound.SoundManager;
import com.example.pixelcombat.enums.EnemyConfig;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.environment.interactor.CollisionDetection;
import com.example.pixelcombat.manager.ComboActionManager;
import com.example.pixelcombat.map.PXMap;
import com.example.pixelcombat.math.Vector2d;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import lombok.Getter;

import static com.example.pixelcombat.core.config.AIConfig.ENEMY_CONFIG;


@SuppressLint("ViewConstructor")
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private Context context;
    private MainThread thread;


    private CollisionDetection collisionDetection;
    private Game game;
    private SoundManager soundManager;

    @Getter
    private GameCharacter player1;
    @Getter
    private GameCharacter player2;
    @Getter
    private ComboActionManager comboActionManager;

    @Inject
    public GamePanel(Context context, SoundManager soundManager, Game game) {
        super(context);
        this.context = context;
        this.game = game;
        this.soundManager = soundManager;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        init();
    }

    private void init() {

        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            Bitmap bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.cold_winter_1, options);
            bg = Bitmap.createScaledBitmap(bg, ((int) (ScreenProperty.SCREEN_WIDTH * 1.5f)), ((int) (ScreenProperty.SCREEN_HEIGHT * 1.4f)), false);


            Kohaku ruffy = new Kohaku("player2", new Vector2d(1000, ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE), context);
            Shana kohaku = new Shana("player1", new Vector2d(500, ScreenProperty.SCREEN_HEIGHT - ScreenProperty.GROUND_LINE), context);

            if (ENEMY_CONFIG == EnemyConfig.VERSUS_AI)
                ruffy.setAIManager(new KohakuAI(ruffy, kohaku, ruffy.getController()));


            ruffy.getBoxManager().loadParsedBoxes();
            kohaku.getBoxManager().loadParsedBoxes();
            ruffy.getStatusManager().setMovementStatus(MovementStatus.LEFT);

            player1 = kohaku;
            player2 = ruffy;


            PXMap testMap = new PXMap("Blue Winter", bg, context, player1, player2);
            collisionDetection = new CollisionDetection(player1, player2);

            testMap.registerSoundManager(soundManager);
            comboActionManager = new KohakuComboManager(player1);
            comboActionManager.init();
            game.init(testMap);
            Log.i("Info", "Game was created successfully");
        } catch (Exception e) {
            Log.e("Error", "THe GamePanel could not initiated, Check the Exception: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(@NotNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(@NotNull SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(@NotNull SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update(){
        game.update();
        collisionDetection.interact();
        comboActionManager.update();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void draw(Canvas canvas){
        super.draw(canvas);
        game.draw(canvas);
        canvas.restore();

    }

}
