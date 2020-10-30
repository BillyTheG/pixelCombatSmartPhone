package com.example.pixelcombat.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.pixelcombat.GamePanel;
import com.example.pixelcombat.R;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.utils.DoubleClickListener;

import javax.inject.Inject;

import static android.view.MotionEvent.ACTION_UP;


public class GameButtonManager implements View.OnClickListener, View.OnTouchListener {

    private GamePanel gamePanel;

    private ImageView up;

    private ImageView down;

    private ImageView left;

    private ImageView right;

    private ImageView attack1;

    private ImageView attack2;

    private ImageView jump;

    private ImageView defend;

    private ImageView start;

    private ImageView select;

    private DoubleClickListener doubleClickListener;
    private Context context;

    @Inject
    public GameButtonManager(Context context, GamePanel gamePanel) {
        this.context = context;
        this.gamePanel = gamePanel;
        init();
    }

    private void init() {


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap up_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ps_button_up, options);
        Bitmap down_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ps_button_down, options);
        Bitmap left_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ps_button_left, options);
        Bitmap right_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ps_button_right, options);

        Bitmap attack1_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_attack1, options);
        Bitmap attack2_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_attack2, options);
        Bitmap defend_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_defend, options);
        Bitmap dash_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_dash, options);

        Bitmap start_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_start, options);
        Bitmap select_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_select, options);

        int size = (int) (ScreenProperty.OFFSET_X / 2.25);
        int yOffSet = ScreenProperty.SCREEN_HEIGHT - ScreenProperty.OFFSET_Y + size / 6;
        int xOffSet = ScreenProperty.SCREEN_WIDTH - ScreenProperty.OFFSET_X;

        left = setUpButton(2, size, 1f, 0, (int) (yOffSet), left_bitmap, false);
        right = setUpButton(3, size, 1f, (int) (2 * size), (int) (yOffSet), right_bitmap, false);
        up = setUpButton(0, size, 1f, (int) (size * 1), yOffSet - size / 2, up_bitmap, false);
        down = setUpButton(1, size, 1f, (int) (size * 1), yOffSet + size / 2, down_bitmap, false);

        size = (int) (ScreenProperty.OFFSET_X / 2);
        yOffSet -= size;
        attack1 = setUpButton(4, size, 1f, xOffSet, yOffSet, attack1_bitmap, false);
        attack2 = setUpButton(5, size, 1f, xOffSet + size, (int) (yOffSet), attack2_bitmap, false);
        jump = setUpButton(6, size, 1f, xOffSet + size, yOffSet + size, defend_bitmap, false);
        defend = setUpButton(7, size, 1f, xOffSet, (int) (yOffSet + size), dash_bitmap, false);

        size = (int) (ScreenProperty.OFFSET_X / 2);
        yOffSet = ScreenProperty.SCREEN_HEIGHT - ScreenProperty.OFFSET_Y + size / 3;
        xOffSet = ScreenProperty.SCREEN_WIDTH / 2 - size;

        select = setUpButton(8, size, 0.6f, xOffSet, yOffSet, select_bitmap, true);
        start = setUpButton(9, size, 0.6f, xOffSet + size + size / 3, yOffSet, start_bitmap, true);

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

    public ImageView setUpButton(int id, int size, float scale, int x, int y, Bitmap bitmap, boolean ratio) {
        ImageView button = new ImageView(context);
        button.setImageBitmap(bitmap);
        button.setId(id);
        button.setBackgroundColor(0);
        button.setMaxWidth(size);
        button.setMaxHeight(size);
        button.setPadding(0, 0, 0, 0);
        button.setAdjustViewBounds(true);
        ViewGroup.LayoutParams params;
        if (ratio)
            params = new ViewGroup.LayoutParams(size, (int) (((float) bitmap.getHeight() / bitmap.getWidth()) * size));
        else
            params = new ViewGroup.LayoutParams(size, size);
        button.setLayoutParams(params);
        button.setScaleType(ImageView.ScaleType.FIT_XY);
        button.setX(x);
        button.setY(y);
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
        jump.setOnClickListener(this);
        defend.setOnClickListener(this);
        GameButtons.addView(attack1);
        GameButtons.addView(attack2);
        GameButtons.addView(jump);
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
                gamePanel.getPlayer1().getController().jump(false, true);
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
            case 6:
                gamePanel.getPlayer1().getController().jump(false, true);
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
