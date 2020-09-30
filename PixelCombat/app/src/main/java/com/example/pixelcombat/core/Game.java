package com.example.pixelcombat.core;

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

    public Game(PXMap map, Weather weather) {
        this.map = map;
        this.screenScrollManager = map.getScreenScrollManager();
        this.projectiles = new ArrayList<>();
        this.sparks = new ArrayList<>();
        this.dusts = new ArrayList<>();
        this.weather = weather;
        this.dustFactory = new DustFactory();
        this.projectileFactory = new ProjectileFactory();
        this.sparkFactory = new SparkFactory();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {
        map.update();
        projectiles.forEach(Projectile::update);
        sparks.forEach(Spark::update);
        dusts.forEach(Dust::update);
        weather.update();
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
            String[] inputs = gameMessage.getGameObject().split(";");
            String gameObject = inputs[0];
            boolean right = Boolean.parseBoolean(inputs[1]);
            MessageType type = gameMessage.getMessageType();

            switch (type) {
                case PROJECTILE_CREATION:
                    projectiles.add(projectileFactory.createProjectile(gameObject, gameMessage.getPos(), right, inputs[2]));
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
