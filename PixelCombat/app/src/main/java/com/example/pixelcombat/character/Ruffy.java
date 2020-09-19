package com.example.pixelcombat.character;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pixelcombat.GameObject;
import com.example.pixelcombat.R;
import com.example.pixelcombat.animation.Animation;
import com.example.pixelcombat.animation.AnimationManager;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.math.Vector2d;

public class Ruffy implements GameObject {

    private Rect rect;
    private Vector2d pos;
    private AnimationManager animManager;

    public Ruffy(Vector2d pos){
        this.pos = pos;
        init();
    }

    private void init() {

        BitmapFactory bf = new BitmapFactory();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap stand1 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_stand_1,options);
        Bitmap stand2 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_stand_2,options);
        Bitmap stand3 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_stand_3,options);

        stand1 = Bitmap.createScaledBitmap(stand1, ((int)(stand1.getWidth()*ScreenProperty.SCALE)),((int)(stand1.getHeight()*ScreenProperty.SCALE)) , false);
        stand2 = Bitmap.createScaledBitmap(stand2, ((int)(stand2.getWidth()*ScreenProperty.SCALE)),((int)(stand2.getHeight()*ScreenProperty.SCALE)) , false);
        stand3 = Bitmap.createScaledBitmap(stand3, ((int)(stand3.getWidth()*ScreenProperty.SCALE)),((int)(stand3.getHeight()*ScreenProperty.SCALE)) , false);



        Animation stand = new Animation(new Bitmap[]{stand1,stand2,stand3} , 0.5f);

        animManager = new AnimationManager(new Animation[]{stand},this);
        animManager.playAnim(0);
        Thread thread = new Thread(animManager);
        thread.start();



    }

    @Override
    public void draw(Canvas canvas) {
        animManager.draw(canvas,rect);
    }

    @Override
    public void update() {
        animManager.update();
    }

    @Override
    public Vector2d getPos() {
        return pos;
    }


}
