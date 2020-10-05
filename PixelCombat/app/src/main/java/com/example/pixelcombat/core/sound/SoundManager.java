package com.example.pixelcombat.core.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.example.pixelcombat.R;
import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.observer.Observer;

import java.util.TreeMap;

import lombok.Getter;

public class SoundManager implements Observer {
    private final Context context;
    @Getter
    private SoundPool soundPool;
    private TreeMap<String, Integer> soundsIds;

    public SoundManager(Context context) {
        this.context = context;
        this.soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        init();
    }

    private void init() {
        this.soundsIds = new TreeMap<>();
        soundsIds.put("kohaku_attack1", soundPool.load(context, R.raw.kohaku_attack1, 1));
        soundsIds.put("kohaku_attack2", soundPool.load(context, R.raw.kohaku_attack2, 1));
        soundsIds.put("kohaku_attack1_hit", soundPool.load(context, R.raw.kohaku_attack1_hit, 1));
        soundsIds.put("kohaku_attack1_sonic", soundPool.load(context, R.raw.kohaku_attack1_sonic, 1));
        soundsIds.put("kohaku_disabled", soundPool.load(context, R.raw.kohaku_disabled, 1));
        soundsIds.put("kohaku_jump", soundPool.load(context, R.raw.kohaku_jump, 1));
        soundsIds.put("kohaku_knockback", soundPool.load(context, R.raw.kohaku_knockback, 1));
        soundsIds.put("ruffy_disabled", soundPool.load(context, R.raw.ruffy_disabled, 1));
        soundsIds.put("ruffy_attack1", soundPool.load(context, R.raw.ruffy_attack1, 1));
        soundsIds.put("ruffy_attack2", soundPool.load(context, R.raw.ruffy_attack2, 1));
        soundsIds.put("kohaku_special_attack", soundPool.load(context, R.raw.kohaku_special_attack, 1));
        soundsIds.put("kohaku_sword_out", soundPool.load(context, R.raw.kohaku_sword_out, 1));
        soundsIds.put("kohaku_sword_hit", soundPool.load(context, R.raw.kohaku_sword_hit, 1));
        soundsIds.put("kohaku_sword_out2", soundPool.load(context, R.raw.kohaku_sword_out2, 1));
        soundsIds.put("kohaku_sword_back", soundPool.load(context, R.raw.kohaku_sword_back, 1));
        soundsIds.put("kohaku_sword_slash", soundPool.load(context, R.raw.kohaku_sword_slash, 1));
        soundsIds.put("kohaku_special_attack_end", soundPool.load(context, R.raw.kohaku_special_attack_end, 1));
    }


    @Override
    public void processMessage(GameMessage gameMessage) throws PixelCombatException {
        MessageType messageType = gameMessage.getMessageType();
        if (!(messageType == MessageType.SOUND))
            return;
        try {
            String soundName = gameMessage.getGameObject();
            int soundId = soundsIds.get(soundName);
            soundPool.play(soundId, 1, 1, 1, 0, 1.0f);
        } catch (Exception e) {
            Log.e("Error", "The attempted sound could not be played");
        }
    }
}
