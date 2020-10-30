package com.example.pixelcombat;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pixelcombat.core.sound.SoundManager;
import com.example.pixelcombat.dagger2.components.DaggerPixelCombatAppComponent;
import com.example.pixelcombat.dagger2.components.PixelCombatAppComponent;
import com.example.pixelcombat.dagger2.modules.PixelCombatAppModule;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.manager.GameButtonManager;

import javax.inject.Inject;

import lombok.Getter;

public class MainActivity extends AppCompatActivity {

    @Inject
    SoundManager soundManager;

    @Inject
    RelativeLayout GameButtons;

    @Inject
    FrameLayout gameFrameLayout;

    @Inject
    GameButtonManager buttonManager;

    @Inject
    GamePanel gamePanel;

 /*   @Inject
    DustFactory dustFactory;

    @Inject
    ProjectileFactory projectileFactory;

    @Inject
    SparkFactory sparkFactory;*/

    @Getter
    private PixelCombatAppComponent component;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);


            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            ScreenProperty.SCREEN_HEIGHT = dm.heightPixels;
            ScreenProperty.SCREEN_WIDTH = dm.widthPixels;

            ScreenProperty.OFFSET_X = (int) (ScreenProperty.SCREEN_WIDTH * ScreenProperty.X_PORTION);
            ScreenProperty.OFFSET_Y = (int) (ScreenProperty.SCREEN_HEIGHT * ScreenProperty.Y_PORTION);

            // GameButtons = new RelativeLayout(this);
            //  gameFrameLayout = new FrameLayout(this);


            //gamePanel = new GamePanel(this);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        //     buttonManager = new GameButtonManager(this,gamePanel);

        RelativeLayout.LayoutParams b1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams b2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        component = DaggerPixelCombatAppComponent.builder().pixelCombatAppModule(new PixelCombatAppModule(this))
                .build();
        component.inject(this);

        GameButtons.setLayoutParams(b2);

        b1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        b1.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);


        gameFrameLayout.addView(gamePanel);
        gameFrameLayout.addView(GameButtons);

        buttonManager.addButtonsToView(GameButtons);

        setContentView(gameFrameLayout);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundManager.getSoundPool().release();
    }
}