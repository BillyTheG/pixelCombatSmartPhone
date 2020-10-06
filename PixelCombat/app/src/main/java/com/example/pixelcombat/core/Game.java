package com.example.pixelcombat.core;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.pixelcombat.core.message.GameMessage;
import com.example.pixelcombat.dusts.Dust;
import com.example.pixelcombat.enums.MessageType;
import com.example.pixelcombat.exception.PixelCombatException;
import com.example.pixelcombat.exception.general.PxNullPointerException;
import com.example.pixelcombat.exception.parser.GameMessageParseException;
import com.example.pixelcombat.factories.DustFactory;
import com.example.pixelcombat.factories.ProjectileFactory;
import com.example.pixelcombat.factories.SparkFactory;
import com.example.pixelcombat.manager.ScreenScrollerManager;
import com.example.pixelcombat.map.PXMap;
import com.example.pixelcombat.map.weather.Weather;
import com.example.pixelcombat.observer.Observer;
import com.example.pixelcombat.projectile.Projectile;
import com.example.pixelcombat.sparks.Spark;

import java.util.ArrayList;
import java.util.List;

public class Game implements Observer {

    private final ScreenScrollerManager screenScrollManager;
    private PXMap map;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Spark> sparks;
    private ArrayList<Dust> dusts;
    private DustFactory dustFactory;
    private ProjectileFactory projectileFactory;
    private SparkFactory sparkFactory;
    private Weather weather;
    private Context context;

    public Game(Context context, PXMap map, Weather weather) {
        this.context = context;
        this.map = map;
        this.screenScrollManager = map.getScreenScrollManager();
        this.projectiles = new ArrayList<>();
        this.sparks = new ArrayList<>();
        this.dusts = new ArrayList<>();
        this.weather = weather;
        this.dustFactory = new DustFactory(context);
        this.projectileFactory = new ProjectileFactory();
        this.sparkFactory = new SparkFactory(context);

        this.map.registerGame(this);
        this.sparkFactory.init();
        this.dustFactory.init();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {
        map.update();
        projectiles.forEach(Projectile::update);
        sparks.forEach(Spark::update);
        dusts.forEach(Dust::update);
        weather.update();


        List<Spark> foundSparks = new ArrayList<>();
        for (Spark spark : sparks) {
            if (spark.isFinished()) {
                foundSparks.add(spark);
            }
        }
        sparks.removeAll(foundSparks);

        List<Dust> foundDusts = new ArrayList<>();
        for (Dust dust : dusts) {
            if (dust.isFinished()) {
                foundDusts.add(dust);
            }
        }
        sparks.removeAll(foundDusts);

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

        map.draw(canvas, 0, 0, null);
        projectiles.forEach(x -> x.draw(canvas, screenX, screenY, map.getGameRect()));
        sparks.forEach(x -> x.draw(canvas, screenX, screenY, map.getGameRect()));
        dusts.forEach(x -> x.draw(canvas, screenX, screenY, map.getGameRect()));
        weather.draw(canvas, screenX, screenY, map.getGameRect());

    }

    @Override
    public void processMessage(GameMessage gameMessage) throws PixelCombatException {

        try {
            MessageType type = gameMessage.getMessageType();
            if (type == MessageType.SOUND)
                return;

            String[] inputs = gameMessage.getGameObject().split(";");
            String gameObject = inputs[0];
            boolean right = gameMessage.isRight();
            String owner = inputs[1];


            switch (type) {
                case PROJECTILE_CREATION:
                    projectiles.add(projectileFactory.createProjectile(gameObject, gameMessage.getPos(), right, owner));
                    break;
                case DUST_CREATION:
                    dusts.add(dustFactory.createDust(gameObject, gameMessage.getPos(), right));
                    break;
                case SPARK_CREATION:
                    sparks.add(sparkFactory.createSpark(gameObject, gameMessage.getPos(), right));
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