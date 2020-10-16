package com.example.pixelcombat.dagger2.components;

import android.content.Context;

import com.example.pixelcombat.MainActivity;
import com.example.pixelcombat.dagger2.modules.PixelCombatAppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {PixelCombatAppModule.class})
public interface PixelCombatAppComponent {

    void inject(Context context);

    void inject(MainActivity mainActivity);
}

