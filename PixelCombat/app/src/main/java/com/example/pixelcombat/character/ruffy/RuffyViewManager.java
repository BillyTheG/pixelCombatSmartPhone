package com.example.pixelcombat.character.ruffy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.R;
import com.example.pixelcombat.animation.Animation;
import com.example.pixelcombat.animation.AnimationManager;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.manager.ViewManager;
import com.example.pixelcombat.xml.CharacterParser;

public class RuffyViewManager extends ViewManager {


    public RuffyViewManager(Ruffy character) throws Exception {
        super(character);
    }


    @Override
    public void init() throws Exception {
        this.setCharacterParser(new CharacterParser(character.getContext(),"Ruffy.xml"));
        loadParsedImages();
        animManager.playAnim();
        Thread thread = new Thread(animManager);
        thread.start();
    }

    private Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, ((int) (bitmap.getWidth() * ScreenProperty.SCALE)), ((int) (bitmap.getHeight() * ScreenProperty.SCALE)), false);
    }


}
