package com.example.pixelcombat.character.chars.kohaku.manager;

import android.content.Context;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.effects.Effect;
import com.example.pixelcombat.effects.avatar.EffectManager;
import com.example.pixelcombat.factories.EffectFactory;

import static com.example.pixelcombat.core.config.EffectConfig.AVATAR_COVER_BATTU_JUTSU_KOHAKU;
import static com.example.pixelcombat.core.config.EffectConfig.AVATAR_COVER_MAIDEN_CALL;
import static com.example.pixelcombat.core.config.EffectConfig.AVATAR_COVER_PROFILE1_KOHAKU;
import static com.example.pixelcombat.core.config.EffectConfig.AVATAR_COVER_WITCH_KOHAKU;

public class KohakuEffectManager extends EffectManager {


    public KohakuEffectManager(Context context, GameCharacter character, Effect attackBgEffect) {
        super(context, character, attackBgEffect);
    }

    @Override
    public void init(EffectFactory effectFactory) {
        setWillNotDraw(false);
        effects.put(AVATAR_COVER_PROFILE1_KOHAKU, effectFactory.createProfileEffect(AVATAR_COVER_PROFILE1_KOHAKU));
        effects.put(AVATAR_COVER_BATTU_JUTSU_KOHAKU, effectFactory.createProfileEffect(AVATAR_COVER_BATTU_JUTSU_KOHAKU));
        effects.put(AVATAR_COVER_WITCH_KOHAKU, effectFactory.createProfileEffect(AVATAR_COVER_WITCH_KOHAKU));
        effects.put(AVATAR_COVER_MAIDEN_CALL, effectFactory.createProfileEffect(AVATAR_COVER_MAIDEN_CALL));

    }


    @Override
    public Effect checkAttacks() {
        switch (character.getAttackManager().getAttackStatus()) {
            case BATTO_JUTSU_OGI:
                return effects.get(AVATAR_COVER_BATTU_JUTSU_KOHAKU);
            case SPECIALATTACK3:
                return effects.get(AVATAR_COVER_WITCH_KOHAKU);
            case MAIDEN_CALL:
                return effects.get(AVATAR_COVER_MAIDEN_CALL);
            default:
                return effects.get(AVATAR_COVER_PROFILE1_KOHAKU);
        }
    }
}
