package com.example.pixelcombat.character.chars.kohaku.special;

import android.util.Log;

import com.example.pixelcombat.character.chars.kohaku.Kohaku;
import com.example.pixelcombat.core.config.DustConfig;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.math.Vector2d;

import java.util.Random;

import static com.example.pixelcombat.enums.ScreenProperty.OFFSET_X;

public class SakuraFactory {

    private final Kohaku kohaku;
    private final int NUMBER_LEAFS = 50;
    private Random random = new Random();

    public SakuraFactory(Kohaku kohaku) {
        this.kohaku = kohaku;
    }


    public void generate() {

        float gameScreenHalf = ScreenProperty.SCREEN_WIDTH - 2 * OFFSET_X;
        gameScreenHalf /= 2;

        Vector2d startPos = new Vector2d(kohaku.getPos().x - gameScreenHalf - random.nextInt((int) gameScreenHalf), -100);
        try {
            for (int i = 0; i < NUMBER_LEAFS; i++) {

                int yOffset = random.nextInt((int) ((float) (ScreenProperty.SCREEN_HEIGHT - ScreenProperty.OFFSET_Y)));


                kohaku.notifyObservers(new GameMessage(MessageType.DUST_CREATION, pickRandomSakuraLeaf() + ";test;",
                        new Vector2d(startPos.x, startPos.y + yOffset), true));


            }
        } catch (Exception e) {
            Log.e("Error", "The sakuras could not be created: " + e.getMessage());
        }

    }


    private String pickRandomSakuraLeaf() {

        int leafNumber = random.nextInt(6) + 1;

        switch (leafNumber) {
            case 2:
                return DustConfig.KOHAKU_SAKURA2_DUST;
            case 3:
                return DustConfig.KOHAKU_SAKURA3_DUST;
            case 4:
                return DustConfig.KOHAKU_SAKURA4_DUST;
            case 5:
                return DustConfig.KOHAKU_SAKURA5_DUST;
            case 6:
                return DustConfig.KOHAKU_SAKURA6_DUST;
            default:
                return DustConfig.KOHAKU_SAKURA1_DUST;
        }

    }


}
