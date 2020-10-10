package com.example.pixelcombat.manager;

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

import static android.view.MotionEvent.ACTION_UP;


public class GameButtonManager implements View.OnClickListener, View.OnTouchListener {

    private GamePanel gamePanel;
    private ImageButton up;
    private Bitmap up_bitmap;

    private ImageButton down;
    private Bitmap down_bitmap;

    private ImageButton left;
    private Bitmap left_bitmap;

    private ImageButton right;
    private Bitmap right_bitmap;

    private ImageButton attack1;
    private Bitmap attack1_bitmap;

    private ImageButton attack2;
    private Bitmap attack2_bitmap;

    private ImageButton dash;
    private Bitmap dash_bitmap;

    private ImageButton defend;
    private Bitmap defend_bitmap;

    private ImageButton start;
    private Bitmap start_bitmap;

    private ImageButton select;
    private Bitmap select_bitmap;


    private Context context;

    public GameButtonManager(Context context, GamePanel gamePanel){
        this.context = context;
        this.gamePanel = gamePanel;
        init();
    }

    private void init() {


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        up_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_up,options);
        down_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_down,options);
        left_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_left,options);
        right_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_right,options);

        attack1_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_attack1,options);
        attack2_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_attack2,options);
        defend_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_defend,options);
        dash_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_dash,options);

        start_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_start,options);
        select_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_select,options);


        up      = setUpButton(0,150,1.15f,150,650,up_bitmap);
        down    = setUpButton(1,150,1.15f,150,800,down_bitmap);
        left    = setUpButton(2,150,1.15f,0,725,left_bitmap);
        right   = setUpButton(3,150,1.15f,300,725,right_bitmap);

        attack1 = setUpButton(4,150,0.6f,1400,675,attack1_bitmap);
        attack2 = setUpButton(5,150,0.6f,1575,675,attack2_bitmap);
        dash    = setUpButton(6,150,0.6f,1750,675,defend_bitmap);
        defend  = setUpButton(7,150,0.6f,1925,675,dash_bitmap);

        select  = setUpButton(8,150,0.6f,800,700,select_bitmap);
        start   = setUpButton(9,150,0.6f,1000,700,start_bitmap);



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



    public void addButtonsToView(RelativeLayout GameButtons){
      //  up.setLayoutParams(b1);
      //  down.setLayoutParams(b1);
      //  left.setLayoutParams(b1);
     //   right.setLayoutParams(b1);

        left.setOnTouchListener(this);
        down.setOnTouchListener(this);
        right.setOnTouchListener(this);

        left.setOnClickListener(this);
        down.setOnClickListener(this);
        right.setOnClickListener(this);
        up.setOnClickListener(this);
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
        start.setOnClickListener(this);
        select.setOnClickListener(this);
        GameButtons.addView(start);
        GameButtons.addView(select);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case 0:
                gamePanel.getPlayer1().getController().jump(false, false);
                break;
            case 1:
                gamePanel.getPlayer1().getController().crouch(false, false);
                break;
            case 2:
                gamePanel.getPlayer1().getController().move(false, false);
                break;
            case 3:
                gamePanel.getPlayer1().getController().move(false, true);
                break;
            case 4:
                gamePanel.getPlayer1().getController().attack(AttackStatus.ATTACK1);
                break;
            case 5:
                gamePanel.getPlayer1().getController().specialAttack(AttackStatus.SPECIALATTACK3);
                break;
            case 6:
                gamePanel.getPlayer1().getController().specialAttack(AttackStatus.SPECIALATTACK1);
                break;
            case 7:
                gamePanel.getPlayer1().getController().dash();
                break;
            case 8:
                gamePanel.getPlayer2().getController().move(false, false);
                break;
            case 9:
                gamePanel.getPlayer2().getController().move(false, true);
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        System.out.println("Touch: "+motionEvent.getActionMasked());

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
            case 8:
                gamePanel.getPlayer2().getController().move(true, false);
                break;
            case 9:
                gamePanel.getPlayer2().getController().move(true, true);
                break;


            default:
                break;
        }
        return false;
    }


}
