package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Bullet;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.lanscape.ConcreteWall;
import com.example.semesterexam.lanscape.Grass;
import com.example.semesterexam.manage.GameScreen;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class FireBullet extends Bullet {

    protected double maxRange = 100d;
    public FireBullet(GameScreen gameScreen, Character owner) throws IOException {
        super(gameScreen, owner);
//        setFitWidth(gameScreen.getComponentSize());
//        setFitHeight(gameScreen.getComponentSize());

        Timeline startFire = new Timeline(new KeyFrame(Duration.millis(this.cycle / 2), ev -> {
            firing();
        }));

        startFire.play();
//        firing();

    }

    @Override
    public void setRealitySize() {

    }

    private AnimationTimer fire;

    private void firing() {
        fire = new AnimationTimer() {
            @Override
            public void handle(long l) {
                Grass[] grasses = gameScreen.getManagement().getGrassAt(getThis());
                for (Grass grass : grasses) {
                    if (grass != null) {
                        grass.getDamage(owner.getBaseDamage());
                    }
                }
            }
        };
        fire.start();
    }

    private Bullet getThis() {
        return this;
    }

    @Override
    public void effect() {
        if (fire != null) {
            fire.stop();
        }

        Boom boom = null;
        try {
            boom = new Boom(this, gameScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert boom != null;

        if (subject != null) {
            if (!(subject instanceof ConcreteWall)) {
                boom.setX(subject.getX());
                boom.setY(subject.getY());
            }
        } else {
            boom.setX(this.getX());
            boom.setY(this.getY());
        }
        boom.setDamage(owner.getBaseDamage() * owner.getIncreaseDamage());
        boom.setAllRange(5d);
        adjustRange(boom);
        gameScreen.getMap().getChildren().add(boom);
        boom.countdown(1);
    }

    public void adjustRange(Boom boom) {
        boom.setAllRange(1d);
        if (dx > 0) {
            boom.rangeRight = maxRange;
        } else if (dx < 0) {
            boom.rangeLeft = maxRange;
        } else if (dy > 0) {
            boom.rangeDown = maxRange;
        } else {
            boom.rangeTop = maxRange;
        }
    }

    @Override
    public void setDefaultActions() {
        addActions("FireBulletLeft", gameScreen.getAction("FireBulletPack:Left"));
        addActions("FireBulletRight", gameScreen.getAction("FireBulletPack:Right"));
        addActions("FireBulletUp", gameScreen.getAction("FireBulletPack:Up"));
        addActions("FireBulletDown", gameScreen.getAction("FireBulletPack:Down"));
    }

    @Override
    public String getName() {
        return "FireBullet";
    }
}
