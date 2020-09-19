package com.example.pixelcombat;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.pixelcombat.enums.ScreenProperty;

public class MainActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);


             DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
           ScreenProperty.SCREEN_HEIGHT = dm.heightPixels;
          ScreenProperty.SCREEN_WIDTH = dm.widthPixels;


            setContentView(new GamePanel(this));
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
}