package com.example.pixelcombat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pixelcombat.enums.ScreenProperty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout GameButtons;

    private FrameLayout game;

        @SuppressLint("ResourceType")
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

            game = new FrameLayout(this);

            GameButtons= new RelativeLayout(this);
            RelativeLayout.LayoutParams b1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams b2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

            Button button = new Button(this);
            button.setId(12);

            button.setY(300);
            button.setX(100);  button.setHeight(200);
            button.setWidth(300);
            button.setText("Hello");

            button.setOnClickListener(this);
            GameButtons.setLayoutParams(b2);
            GameButtons.addView(button);

            b1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            b1.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            button.setLayoutParams(b1);


            game.addView(new GamePanel(this));
            game.addView(GameButtons);
            setContentView(game);
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
    public void onClick(View v) {
        System.out.println("Haha");
    }
}