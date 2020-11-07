package com.example.pixelcombat.character.chars.kohaku.manager;

import com.example.pixelcombat.GameCharacter;
import com.example.pixelcombat.effects.Effect;
import com.example.pixelcombat.effects.avatar.EffectManager;
import com.example.pixelcombat.factories.EffectFactory;

import static com.example.pixelcombat.core.config.EffectConfig.AVATAR_COVER_BATTU_JUTSU_KOHAKU;
import static com.example.pixelcombat.core.config.EffectConfig.AVATAR_COVER_PROFILE1_KOHAKU;

public class KohakuEffectManager extends EffectManager {


    public KohakuEffectManager(GameCharacter character, Effect attackBgEffect) {
        super(character, attackBgEffect);
    }

    @Override
    public void init(EffectFactory effectFactory) {
        effects.put(AVATAR_COVER_PROFILE1_KOHAKU, effectFactory.createProfileEffect(AVATAR_COVER_PROFILE1_KOHAKU));
        effects.put(AVATAR_COVER_BATTU_JUTSU_KOHAKU, effectFactory.createProfileEffect(AVATAR_COVER_BATTU_JUTSU_KOHAKU));
    }


    @Override
    public Effect checkAttacks() {
        switch (character.getAttackManager().getAttackStatus()) {
            case BATTO_JUTSU_OGI:
                return effects.get(AVATAR_COVER_BATTU_JUTSU_KOHAKU);
            default:
                return effects.get(AVATAR_COVER_PROFILE1_KOHAKU);
        }
    }
}
