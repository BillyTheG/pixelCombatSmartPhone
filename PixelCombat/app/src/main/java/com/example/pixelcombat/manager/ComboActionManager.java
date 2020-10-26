package com.example.pixelcombat.manager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.character.status.AttackStatus;
import com.example.pixelcombat.core.config.ComboAttacksConfig;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.example.pixelcombat.core.config.ComboAttacksConfig.ATTACK1_SECOND;
import static com.example.pixelcombat.core.config.ComboAttacksConfig.ATTACK1_THIRD;
import static com.example.pixelcombat.core.config.ComboAttacksConfig.ATTACK2_SECOND;
import static com.example.pixelcombat.core.config.ComboAttacksConfig.ATTACK2_THIRD;
import static com.example.pixelcombat.core.config.ComboAttacksConfig.FORWARD_LEFT_SPECIAL_ATTACK;
import static com.example.pixelcombat.core.config.ComboAttacksConfig.FORWARD_LEFT_SPECIAL_ATTACK2;
import static com.example.pixelcombat.core.config.ComboAttacksConfig.FORWARD_RIGHT_SPECIAL_ATTACK;
import static com.example.pixelcombat.core.config.ComboAttacksConfig.FORWARD_RIGHT_SPECIAL_ATTACK2;
import static com.example.pixelcombat.core.config.ComboAttacksConfig.UP_SPECIAL_ATTACK3;

public class ComboActionManager {

    private final GameCharacter player1;
    private String pressedKeysP1 = "";
    private int MAXIMUM_KEY_LISTENING_SIZE = 10;
    private long lastFrame = 0;
    private Long[] timesP1 = new Long[MAXIMUM_KEY_LISTENING_SIZE];

    private SortedSet<String> combos = new TreeSet<>();
    private String bonus = "";

    public ComboActionManager(GameCharacter player1) {
        this.player1 = player1;
    }

    public void init() {
        lastFrame = System.currentTimeMillis();
        Arrays.fill(timesP1, 0L);

        //combo1
        combos.add(ATTACK1_SECOND);
        combos.add(ComboAttacksConfig.ATTACK1_THIRD);
        combos.add(ATTACK2_SECOND);
        combos.add(ATTACK2_THIRD);
        combos.add(ComboAttacksConfig.FORWARD_LEFT_SPECIAL_ATTACK);
        combos.add(ComboAttacksConfig.FORWARD_RIGHT_SPECIAL_ATTACK);
        combos.add(ComboAttacksConfig.FORWARD_LEFT_SPECIAL_ATTACK2);
        combos.add(ComboAttacksConfig.FORWARD_RIGHT_SPECIAL_ATTACK2);
        combos.add(ComboAttacksConfig.UP_SPECIAL_ATTACK3);
    }

    public synchronized void pressKey(String player1, String key) {

        if (this.player1.getStatusManager().notCombatReady()) {
            return;
        }
        insertKey(player1, key);
    }

    private void insertKey(String player1, String key) {
        if (player1.equals("player1")) {
            if (pressedKeysP1.length() == MAXIMUM_KEY_LISTENING_SIZE) {
                pressedKeysP1 = pressedKeysP1.substring(1);
            }
            pressedKeysP1 += key;
            timesP1[pressedKeysP1.length() - 1] = 0L;
        }
    }

    public synchronized void update() {
        checkTimes();
    }

    private void checkTimes() {

        StringBuilder futurePressedKeysP1 = new StringBuilder();
        long currentFrame = System.currentTimeMillis();

        for (int i = 0; i < MAXIMUM_KEY_LISTENING_SIZE; i++) {
            timesP1[i] += (currentFrame - lastFrame);


            long MAXIMUM_DELAY_TIME_TIL_CLEAR = 2300L;
            if (timesP1[i] > MAXIMUM_DELAY_TIME_TIL_CLEAR) {
                timesP1[i] = 0L;
            } else {
                if (i < pressedKeysP1.length())
                    futurePressedKeysP1.append("").append(pressedKeysP1.charAt(i));
            }
        }
        lastFrame = currentFrame;
        pressedKeysP1 = futurePressedKeysP1.toString();

    }


    public boolean checkComboActivation() {

        for (String combo : combos) {
            if (pressedKeysP1.contains(combo)) {
                //      Log.d("Debug", "The following combo was activated: "+combo);
                if (!activateCombo(combo)) {
                    continue;
                }
                pressedKeysP1 = "" + bonus;

                Arrays.fill(timesP1, 0L);

                return true;
            }
        }
        return false;
    }

    private boolean activateCombo(String combo) {
        boolean activated;
        switch (combo) {
            case ATTACK1_SECOND:
                //     Log.i("Info", "The following combo was activated: "+combo);
                activated = player1.getController().specialAttack(AttackStatus.ATTACK2);
                bonus = "X";
                return activated;
            case ATTACK1_THIRD:
                //   Log.i("Info", "The following combo was activated: "+combo);
                activated = player1.getController().specialAttack(AttackStatus.ATTACK3);
                bonus = "";
                return activated;
            case ATTACK2_SECOND:
                //     Log.i("Info", "The following combo was activated: "+combo);
                activated = player1.getController().specialAttack(AttackStatus.ATTACK5);
                bonus = "Y";
                return activated;
            case ATTACK2_THIRD:
                //   Log.i("Info", "The following combo was activated: "+combo);
                activated = player1.getController().specialAttack(AttackStatus.ATTACK6);
                bonus = "";
                return activated;
            case FORWARD_RIGHT_SPECIAL_ATTACK:
            case FORWARD_LEFT_SPECIAL_ATTACK:
                activated = player1.getController().specialAttack(AttackStatus.SPECIALATTACK1);
                bonus = "";
                return activated;
            case FORWARD_RIGHT_SPECIAL_ATTACK2:
            case FORWARD_LEFT_SPECIAL_ATTACK2:
                activated = player1.getController().specialAttack(AttackStatus.SPECIALATTACK2);
                bonus = "";
                return activated;
            case UP_SPECIAL_ATTACK3:
                activated = player1.getController().specialAttack(AttackStatus.SPECIALATTACK3);
                bonus = "";
                return activated;
            default:
                bonus = "";
                break;
        }

        return false;
    }


}
