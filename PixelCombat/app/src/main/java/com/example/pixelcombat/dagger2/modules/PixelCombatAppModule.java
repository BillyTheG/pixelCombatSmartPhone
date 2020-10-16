package com.example.pixelcombat.dagger2.modules;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.pixelcombat.GamePanel;
import com.example.pixelcombat.core.Game;
import com.example.pixelcombat.core.sound.SoundManager;
import com.example.pixelcombat.factories.DustFactory;
import com.example.pixelcombat.factories.ProjectileFactory;
import com.example.pixelcombat.factories.SparkFactory;
import com.example.pixelcombat.manager.GameButtonManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class PixelCombatAppModule {

    private final Context context;

    public PixelCombatAppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context context() {
        return context;
    }

    @Singleton
    @Provides
    public GamePanel providesGamePanel(Context context, SoundManager soundManager, Game game) {
        return new GamePanel(context, soundManager, game);
    }

    @Singleton
    @Provides
    public GameButtonManager providesGameButtonManager(Context context, GamePanel gamePanel) {
        return new GameButtonManager(context, gamePanel);
    }

    @Provides
    public RelativeLayout providesRelativeLayout(Context context) {
        return new RelativeLayout(context);
    }

    @Provides
    public FrameLayout providesFrameLayout(Context context) {
        return new FrameLayout(context);
    }

    @Provides
    public SoundManager providesSoundManager(Context context) {
        return new SoundManager(context);
    }

    @Singleton
    @Provides
    public DustFactory providesDustFactory(Context context, SoundManager soundManager) {
        return new DustFactory(context, soundManager);
    }

    @Singleton
    @Provides
    public SparkFactory providesSparkFactory(Context context, SoundManager soundManager) {
        return new SparkFactory(context, soundManager);
    }

    @Singleton
    @Provides
    public ProjectileFactory providesProjectileFactory(Context context, SoundManager soundManager) {
        return new ProjectileFactory(context, soundManager);
    }

    @Singleton
    @Provides
    public Game providesGame(Context context, DustFactory dustFactory, SparkFactory sparkFactory,
                             ProjectileFactory projectileFactory) {
        return new Game(context, dustFactory, sparkFactory, projectileFactory);
    }

}
