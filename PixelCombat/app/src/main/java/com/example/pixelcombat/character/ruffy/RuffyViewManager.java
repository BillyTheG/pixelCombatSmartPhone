package com.example.pixelcombat.character.ruffy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.R;
import com.example.pixelcombat.animation.Animation;
import com.example.pixelcombat.animation.AnimationManager;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.manager.ViewManager;

public class RuffyViewManager extends ViewManager {


    public RuffyViewManager(Ruffy character) {
        super(character);
    }

    @Override
    public void init() {
        BitmapFactory bf = new BitmapFactory();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap stand1 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_stand_1,options);
        Bitmap stand2 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_stand_2,options);
        Bitmap stand3 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_stand_3,options);

        stand1 = getScaledBitmap(stand1);
        stand2 = getScaledBitmap(stand2);
        stand3 = getScaledBitmap(stand3);

        Bitmap move1 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_move_1,options);
        Bitmap move2 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_move_2,options);
        Bitmap move3 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_move_3,options);
        Bitmap move4 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_move_4,options);
        Bitmap move5 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_move_5,options);
        Bitmap move6 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_move_6,options);
        Bitmap move7 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_move_8,options);
        Bitmap move8 = bf.decodeResource(ScreenProperty.CURRENT_CONTEXT.getResources(), R.drawable.ruffy_move_9,options);


        move1 = getScaledBitmap(move1);
        move2 = getScaledBitmap(move2);
        move3 = getScaledBitmap(move3);
        move4 = getScaledBitmap(move4);
        move5 = getScaledBitmap(move5);
        move6 = getScaledBitmap(move6);
        move7 = getScaledBitmap(move7);
        move8 = getScaledBitmap(move8);



        Animation stand = new Animation(new Bitmap[]{stand1,stand2,stand3} , 0.5f);
        Animation move = new Animation(new Bitmap[]{move1,move2,move3,move4,move5,move6,move7,move8} , 0.5f);

        animManager = new AnimationManager(this,new Animation[]{stand,move},character);
        animManager.playAnim();
        Thread thread = new Thread(animManager);
        thread.start();
    }

    private Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, ((int) (bitmap.getWidth() * ScreenProperty.SCALE)), ((int) (bitmap.getHeight() * ScreenProperty.SCALE)), false);
    }
}
