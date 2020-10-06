package com.example.pixelcombat.character.chars.kohaku.manager;

import android.graphics.Bitmap;

import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.manager.ViewManager;
import com.example.pixelcombat.xml.CharacterParser;

public class KohakuViewManager extends ViewManager {

    private Thread imageLoaderThread;

    public KohakuViewManager(Kohaku character) throws Exception {
        super(character);
    }


    @Override
    public void init() throws Exception {
        this.setCharacterParser(new CharacterParser(character.getContext(), "Kohaku_Images.xml"));
        loadParsedImages();
        animManager.playAnim();
        Thread thread = new Thread(animManager);
        thread.start();
    }

    private Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, ((int) (bitmap.getWidth() * ScreenProperty.SCALE)), ((int) (bitmap.getHeight() * ScreenProperty.SCALE)), false);
    }


}
