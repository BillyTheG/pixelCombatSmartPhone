package com.example.pixelcombat;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.manager.GameButtonManager;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout GameButtons;
    private FrameLayout gameFrameLayout;
    private GameButtonManager buttonManager;
    private GamePanel gamePanel;

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
        GameButtons = new RelativeLayout(this);
        gameFrameLayout = new FrameLayout(this);
        try {
            gamePanel = new GamePanel(this);
        } catch (IOException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            }
            buttonManager = new GameButtonManager(this,gamePanel);

            RelativeLayout.LayoutParams b1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams b2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        GameButtons.setLayoutParams(b2);


        b1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        b1.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

        buttonManager.addButtonsToView(GameButtons);

        gameFrameLayout.addView(gamePanel);
        gameFrameLayout.addView(GameButtons);
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

}