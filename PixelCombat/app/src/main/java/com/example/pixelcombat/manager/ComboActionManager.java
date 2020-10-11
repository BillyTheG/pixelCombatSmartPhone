package com.example.pixelcombat.manager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.core.config.ComboAttacksConfig;

import java.util.SortedSet;
import java.util.TreeSet;

public class ComboActionManager {


    private final GameCharacter player1;
    private String pressedKeysP1 = "";
    private int MAXIMUM_KEY_LISTENING_SIZE = 10;
    private long MAXIMUM_DELAY_TIME_TIL_CLEAR = 1000l;
    private long lastFrame = 0;
    private Long[] timesP1 = new Long[MAXIMUM_KEY_LISTENING_SIZE];

    private SortedSet<String> combos = new TreeSet<>();
    private String bonus = "";

    public ComboActionManager(GameCharacter player1) {
        this.player1 = player1;
    }

    public void init() {
        lastFrame = System.currentTimeMillis();
        for (int i = 0; i < timesP1.length; i++) {
            timesP1[i] = 0l;
        }

        //combo1
        combos.add(ComboAttacksConfig.ATTACK1_SECOND);
        combos.add(ComboAttacksConfig.ATTACK1_THIRD);
    }

    public synchronized void pressKey(String player1, String key) {

        if (this.player1.getStatusManager().notCombatReady()) {
            return;
        }
        insertKey(player1, key);
    }

    private void insertKey(String player1, String key) {
        if (player1.equals("player1")) {
            if (pressedKeysP1.length() > MAXIMUM_KEY_LISTENING_SIZE) {
                pressedKeysP1 = pressedKeysP1.substring(1);
            }
            pressedKeysP1 += key;
            timesP1[pressedKeysP1.length() - 1] = 0l;
        }
    }

    public synchronized void update() {
        checkTimes();

        // Log.d("Info", "Pressed Keys so far: "+ pressedKeysP1);
    }

    private void checkTimes() {

        String futurePressedKeysP1 = "";
        long currentFrame = System.currentTimeMillis();

        for (int i = 0; i < MAXIMUM_KEY_LISTENING_SIZE; i++) {
            timesP1[i] += (currentFrame - lastFrame);


            if (timesP1[i] > MAXIMUM_DELAY_TIME_TIL_CLEAR) {
                timesP1[i] = 0l;
            } else {
                if (i < pressedKeysP1.length())
                    futurePressedKeysP1 += "" + pressedKeysP1.charAt(i);
            }
        }
        lastFrame = currentFrame;
        pressedKeysP1 = futurePressedKeysP1;

    }


    public boolean checkComboActivation() {

        for (String combo : combos) {
            if (pressedKeysP1.contains(combo)) {
                //      Log.d("Debug", "The following combo was activated: "+combo);
                if (!activateCombo(combo)) {
                    continue;
                }
                pressedKeysP1 = "" + bonus;

                for (int i = 0; i < timesP1.length; i++) {
                    timesP1[i] = 0l;
                }

                return true;
            }
        }
        return false;
    }

    private boolean activateCombo(String combo) {
        boolean activated = false;
        switch (combo) {
            case "444":
                //     Log.i("Info", "The following combo was activated: "+combo);
                activated = player1.getController().specialAttack(AttackStatus.ATTACK2);
                bonus = "X";
                return activated;
            case "X4":
                //   Log.i("Info", "The following combo was activated: "+combo);
                activated = player1.getController().specialAttack(AttackStatus.ATTACK3);
                bonus = "";
                return activated;
            default:
                bonus = "";
                break;
        }

        return false;
    }


}
