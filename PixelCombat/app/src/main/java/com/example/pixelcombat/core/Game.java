package com.example.pixelcombat.core;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.environment.interactor.ProjectileHitDetection;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.exception.general.PxNullPointerException;
import com.example.pixelcombat.exception.parser.GameMessageParseException;
import com.example.pixelcombat.factories.DustFactory;
import com.example.pixelcombat.factories.EffectFactory;
import com.example.pixelcombat.factories.ProjectileFactory;
import com.example.pixelcombat.factories.SparkFactory;
import com.example.pixelcombat.manager.ScreenScrollerManager;
import com.example.pixelcombat.map.PXMap;
import com.example.pixelcombat.observer.Observer;
import com.example.pixelcombat.projectile.Projectile;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;

public class Game implements Observer {

    @Getter
    private Context context;
    private ScreenScrollerManager screenScrollManager;
    private PXMap map;
    private ArrayList<Projectile> projectiles;
    private ArrayList<IsFinishable> sparks;
    private ArrayList<IsFinishable> dusts;
    private DustFactory dustFactory;
    private ProjectileFactory projectileFactory;
    private SparkFactory sparkFactory;
    private EffectFactory effectFactory;
    private ProjectileHitDetection projectileDetection;


    @Inject
    public Game(Context context, DustFactory dustFactory, SparkFactory sparkFactory,
                ProjectileFactory projectileFactory, EffectFactory effectFactory) {
        this.context = context;
        this.projectiles = new ArrayList<>();
        this.sparks = new ArrayList<>();
        this.dusts = new ArrayList<>();

        this.dustFactory = dustFactory;
        this.projectileFactory = projectileFactory;
        this.sparkFactory = sparkFactory;
        this.effectFactory = effectFactory;

        this.sparkFactory.init();
        this.dustFactory.init();
        this.projectileFactory.init();
        this.effectFactory.init();
    }

    public void init(PXMap map) {
        this.map = map;
        this.screenScrollManager = map.getScreenScrollManager();
        this.projectileDetection = new ProjectileHitDetection(map.getCharacter1(), map.getCharacter2());
        this.map.registerGame(this);
        this.map.initEffects(effectFactory);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {

        try {

            ArrayList<Thread> threads = new ArrayList<>();

            projectiles.forEach(Projectile::update);
            Thread sparkThread = new Thread(() -> sparks.forEach(IsFinishable::update));
            Thread dustThread = new Thread(() -> dusts.forEach(IsFinishable::update));

            threads.add(sparkThread);
            threads.add(dustThread);

            map.update();
            threads.forEach(Thread::start);
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (Exception e) {
            Log.e("Error", "During Update of Thread-Elements an error was thrown: " + e.getMessage());
        }

        List<Projectile> foundProjectiles = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            if (projectile.getStatusManager().isDead()) {
                foundProjectiles.add(projectile);
            }
        }
        projectiles.removeAll(foundProjectiles);

        removeFinishedObjects(sparks);
        removeFinishedObjects(dusts);

        projectileDetection.interact(projectiles);

    }


    public void removeFinishedObjects(ArrayList<IsFinishable> objects) {
        List<IsFinishable> foundObjects = new ArrayList<>();
        for (IsFinishable gameObject : objects) {
            if (gameObject.isFinished()) {
                foundObjects.add(gameObject);
            }
        }
        objects.removeAll(foundObjects);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void draw(Canvas canvas) {

        int screenX = screenScrollManager.getScreenX() - screenScrollManager.getCX();
        int screenY = screenScrollManager.getScreenY() - screenScrollManager.getCY();


        try {
            map.draw(canvas, 0, 0, null);


            projectiles.forEach(x -> x.draw(canvas, screenX, screenY, map.getGameRect()));
  /*                sparks.forEach(x -> x.draw(canvas, screenX, screenY, map.getGameRect()));
                     dusts.forEach(x -> x.draw(canvas, screenX, screenY, map.getGameRect()));*/
            ArrayList<Thread> threads = new ArrayList<>();
            Thread dustThread = new Thread(() -> dusts.forEach(x -> x.draw(canvas, screenX, screenY, map.getGameRect())));
            Thread sparkThread = new Thread(() -> sparks.forEach(x -> x.draw(canvas, screenX, screenY, map.getGameRect())));

            threads.add(sparkThread);
            threads.add(dustThread);

            threads.forEach(Thread::start);
            for (Thread thread : threads) {
                thread.join();
            }


        } catch (Exception e) {
            Log.e("Error", "Error during drawing objects: " + e.getMessage());
        }


        map.drawEffects(canvas, map.getGameRect(), false);


    }

    @Override
    public void processMessage(GameMessage gameMessage) throws PixelCombatException {

        try {
            MessageType type = gameMessage.getMessageType();
            if (type == MessageType.SOUND || type == MessageType.SHAKE)
                return;

            String[] inputs = gameMessage.getGameObject().split(";");
            String gameObject = inputs[0];
            boolean state = gameMessage.isSwitcher();
            String owner = inputs[1];
            float scaleFactor = gameMessage.getScaleFactor();

            switch (type) {
                case FREEZE:
                    map.putFreezeOn(owner, state);
                    break;
                case PROJECTILE_CREATION:
                    projectiles.add(projectileFactory.createProjectile(gameObject, gameMessage.getPos(), state, owner, screenScrollManager, this, scaleFactor));
                    break;
                case DUST_CREATION:
                    dusts.add(dustFactory.createDust(gameObject, gameMessage.getPos(), state, scaleFactor));
                    break;
                case SPARK_CREATION:
                    sparks.add(sparkFactory.createSpark(gameObject, gameMessage.getPos(), state, scaleFactor));
                    break;
                case DARKENING:
                    map.setDarkeningActivated(gameMessage.isSwitcher());
                    break;
                default:
                    break;
            }

        } catch (NullPointerException e) {
            throw new PxNullPointerException();
        } catch (Exception e) {
            throw new GameMessageParseException(gameMessage.getGameObject());
        }
    }
}
