package com.example.pixelcombat.character.chars.shana.manager;

import android.content.Context;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.effects.Effect;
import com.example.pixelcombat.effects.avatar.EffectManager;
import com.example.pixelcombat.factories.EffectFactory;

import static com.example.pixelcombat.core.config.EffectConfig.AVATAR_COVER_PROFILE1_KOHAKU;

public class ShanaEffectManager extends EffectManager {


    public ShanaEffectManager(Context context, GameCharacter character, Effect attackBgEffect) {
        super(context, character, attackBgEffect);
    }

    @Override
    public void init(EffectFactory effectFactory) {
        setWillNotDraw(false);
    }


    @Override
    public Effect checkAttacks() {
        switch (character.getAttackManager().getAttackStatus()) {
            default:
                return effects.get(AVATAR_COVER_PROFILE1_KOHAKU);
        }
    }
}
