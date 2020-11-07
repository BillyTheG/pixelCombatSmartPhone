package com.example.pixelcombat.animation.util.impl;

import com.example.pixelcombat.animation.util.ICanZoom;
import com.example.pixelcombat.effects.Effect;

public class ImageZoomer implements ICanZoom {

    private float startScale;
    private float currentScale;

    public ImageZoomer(float startScale) {
        this.startScale = startScale;
        this.currentScale = startScale;
    }


    @Override
    public void zoom(Effect effect) {
        float delta = 0.075f;

        effect.setScaleFactor(currentScale += delta);
    }


    public void reset(Effect effect) {
        this.currentScale = startScale;
        effect.setScaleFactor(startScale);
    }
}
