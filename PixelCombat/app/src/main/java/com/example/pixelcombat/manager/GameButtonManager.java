package com.example.pixelcombat.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.pixelcombat.GamePanel;
import com.example.pixelcombat.R;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.utils.DoubleClickListener;

import javax.inject.Inject;

import static android.view.MotionEvent.ACTION_UP;


public class GameButtonManager implements View.OnClickListener, View.OnTouchListener {

    private GamePanel gamePanel;
    private ImageButton up;

    private ImageButton down;

    private ImageButton left;

    private ImageButton right;

    private ImageButton attack1;

    private ImageButton attack2;

    private ImageButton dash;

    private ImageButton defend;

    private ImageButton start;

    private ImageButton select;

    private DoubleClickListener doubleClickListener;
    private Context context;

    @Inject
    public GameButtonManager(Context context, GamePanel gamePanel){
        this.context = context;
        this.gamePanel = gamePanel;
        init();
    }

    private void init() {


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap up_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_up, options);
        Bitmap down_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_down, options);
        Bitmap left_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_left, options);
        Bitmap right_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_right, options);

        Bitmap attack1_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_attack1, options);
        Bitmap attack2_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_attack2, options);
        Bitmap defend_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_defend, options);
        Bitmap dash_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_dash, options);

        Bitmap start_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_start, options);
        Bitmap select_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_select, options);


        up = setUpButton(0, 150, 1.15f, 150, 650, up_bitmap);
        down = setUpButton(1, 150, 1.15f, 150, 800, down_bitmap);
        left = setUpButton(2, 150, 1.15f, 0, 725, left_bitmap);
        right = setUpButton(3, 150, 1.15f, 300, 725, right_bitmap);

        attack1 = setUpButton(4, 250, 0.6f, 1400, 675, attack1_bitmap);
        attack2 = setUpButton(5, 250, 0.6f, 1575, 675, attack2_bitmap);
        dash = setUpButton(6, 250, 0.6f, 1750, 675, defend_bitmap);
        defend = setUpButton(7, 250, 0.6f, 1925, 675, dash_bitmap);

        select = setUpButton(8, 150, 0.6f, 800, 700, select_bitmap);
        start = setUpButton(9, 150, 0.6f, 1000, 700, start_bitmap);

        doubleClickListener = new DoubleClickListener(this, 300) {
            @Override
            public void onSingleClick(View v) {
                buttonManager.onClick(v);
            }

            @Override
            public void onDoubleClick(View v) {
                buttonManager.onDoubleClick(v);
            }
        };

    }

    public ImageButton setUpButton(int id,int size, float scale, int x, int y, Bitmap bitmap){
        ImageButton button = new ImageButton(context);
        button.setId(id);
        button.setBackgroundColor(0);
        button.setMinimumHeight(size);
        button.setMinimumWidth(size);
        button.setMaxWidth(size);
        button.setMaxHeight(size);



        button.setScaleX(scale);
        button.setScaleY(scale);
        button.setX(x);
        button.setY(y);
        button.setImageBitmap(bitmap);
        return button;
    }


    @SuppressLint("ClickableViewAccessibility")
    public void addButtonsToView(RelativeLayout GameButtons){

        left.setOnTouchListener(this);
        down.setOnTouchListener(this);
        right.setOnTouchListener(this);
        down.setOnClickListener(this);
        up.setOnClickListener(this);

        right.setOnClickListener(doubleClickListener);
        left.setOnClickListener(doubleClickListener);


        GameButtons.addView(up);
        GameButtons.addView(down);
        GameButtons.addView(left);
        GameButtons.addView(right);

        attack1.setOnClickListener(this);
        attack2.setOnClickListener(this);
        dash.setOnClickListener(this);
        defend.setOnClickListener(this);
        GameButtons.addView(attack1);
        GameButtons.addView(attack2);
        GameButtons.addView(dash);
        GameButtons.addView(defend);

        start.setOnTouchListener(this);
        select.setOnTouchListener(this);
        defend.setOnTouchListener(this);
        start.setOnClickListener(this);
        select.setOnClickListener(this);
        GameButtons.addView(start);
        GameButtons.addView(select);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case 0:
                gamePanel.getComboActionManager().pressKey("player1", "" + view.getId());
                if (!gamePanel.getComboActionManager().checkComboActivation())
                    gamePanel.getPlayer1().getController().jump(false, false);
                break;
            case 1:
                gamePanel.getComboActionManager().pressKey("player1", "" + view.getId());
                if (!gamePanel.getComboActionManager().checkComboActivation())
                    gamePanel.getPlayer1().getController().crouch(false, false);
                break;
            case 2:
                gamePanel.getComboActionManager().pressKey("player1", "" + view.getId());
                if (!gamePanel.getComboActionManager().checkComboActivation())
                    gamePanel.getPlayer1().getController().move(false, false);
                break;
            case 3:
                gamePanel.getComboActionManager().pressKey("player1", "" + view.getId());
                if (!gamePanel.getComboActionManager().checkComboActivation())
                    gamePanel.getPlayer1().getController().move(false, true);
                break;
            case 4:
                gamePanel.getComboActionManager().pressKey("player1", "" + view.getId());
                if (!gamePanel.getComboActionManager().checkComboActivation())
                    gamePanel.getPlayer1().getController().attack(AttackStatus.ATTACK1);
                break;
            case 5:
                gamePanel.getComboActionManager().pressKey("player1", "" + view.getId());
                if (!gamePanel.getComboActionManager().checkComboActivation())
                    gamePanel.getPlayer1().getController().attack(AttackStatus.ATTACK4);
                break;
            case 6:
                gamePanel.getPlayer1().getController().dash();
                break;
            case 7:
                gamePanel.getComboActionManager().pressKey("player1", "" + view.getId());
                if (!gamePanel.getComboActionManager().checkComboActivation())
                    gamePanel.getPlayer1().getController().defend(false, true);
                break;
            case 8:
                gamePanel.getPlayer2().getController().defend(false, false);
                break;
            case 9:
                gamePanel.getPlayer2().getController().move(false, true);
                break;

            default:
                break;
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if((motionEvent.getActionMasked() == ACTION_UP)) {
            onClick(view);
            return false;
        }

        switch(view.getId()) {
            case 1:
                gamePanel.getPlayer1().getController().crouch(true, false);
                break;
            case 2:
                gamePanel.getPlayer1().getController().move(true, false);
                break;
            case 3:
                gamePanel.getPlayer1().getController().move(true, true);
                break;
            case 7:
                gamePanel.getPlayer1().getController().defend(true, true);
                break;
            case 8:
                gamePanel.getPlayer2().getController().defend(true, false);
                break;
            case 9:
                gamePanel.getPlayer2().getController().move(true, true);
                break;


            default:
                break;
        }
        return false;
    }


    public void onDoubleClick(View v) {
        switch (v.getId()) {
            case 2:
                gamePanel.getPlayer1().getController().checkDashOrRetreat(gamePanel.getPlayer2(), false);
                break;
            case 3:
                gamePanel.getPlayer1().getController().checkDashOrRetreat(gamePanel.getPlayer2(), true);
                break;
            default:
                break;
        }
    }

}
