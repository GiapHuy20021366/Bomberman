package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Bullet;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.core.Figure;
import com.example.semesterexam.core.Subject;
import com.example.semesterexam.lanscape.Gate;
import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.tool.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Boom extends Subject {
    protected Timeline boomPow;
    protected Timeline boomDisable;
    protected double range = 1.5d;
    protected double damage = 100;
    private boolean hasPow = false;
    protected Rectangle2D rowPow;
    protected Rectangle2D colPow;
    protected List<Fire> fires = new ArrayList<>();
    public double rangeTop = 1.5d;
    public double rangeDown = 1.5d;
    public double rangeLeft = 1.5d;
    public double rangeRight = 1.5d;
    protected Subject owner = null;

    public void setRange(double range) {
        this.range = range;
    }

    public void setAllRange(double range) {
        rangeTop = range;
        rangeDown = range;
        rangeRight = range;
        rangeLeft = range;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void countdown(long time) {
        boomPow = new Timeline(new KeyFrame(Duration.millis(time), ev -> {
            hasPow = true;
            gameScreen.getManagement().removeBoom(this);
            HP.set(HP.get() - HP.get() - 100);
        }));
        boomPow.setCycleCount(1);
        boomPow.play();
    }

    public void disappear(long time) {
        boomDisable = new Timeline(new KeyFrame(Duration.millis(time), ev -> {
            for (Fire fire : fires) gameScreen.getMap().getChildren().remove(fire);
        }));

        boomDisable.setCycleCount(1);
        boomDisable.play();
    }

    public Boom(Subject owner, GameScreen gameScreen) throws IOException {
        super(gameScreen);
        this.owner = owner;

        addActions("QuaBoom", gameScreen.getAction("BoomPack:QuaBoom"));
        addActions("BoomPow", gameScreen.getAction("BoomPack:BoomPow"));

        HP.set(1);
        setX(owner.getX());
        setY(owner.getY());
        setFitWidth(owner.getFitWidth());
        setFitHeight(owner.getFitHeight());

    }

    public void power() throws IOException {
        setVisible(false);
        gameScreen.getMap().getChildren().remove(this);
        List<Subject> subjects = gameScreen.getManagement().subjects(this,rangeTop, rangeDown, rangeLeft, rangeRight);
        setFire();
        for (Subject s : subjects) {
            if (s == null) continue;
            if (owner instanceof Arrow) {
                Character x = ((Arrow) owner).getOwner();
                if (x instanceof Figure) {
                    s.setJustDamage(x);
                }
            } else if (owner instanceof Figure){
                s.setJustDamage(owner);
            }
            s.getDamage(damage);
        }
        disappear(500);
    }

    public void setFire() throws IOException {
        // Row
        for (double i = rowPow.getMinX() ; i < rowPow.getMaxX()  ; i += gameScreen.getComponentSize()) {
            Fire fire = new Fire(i, rowPow.getMinY(), gameScreen);
            fire.setDefaultFire();
            fire.setFitHeight(gameScreen.getComponentSize());
            fire.setFitWidth(Math.min(gameScreen.getComponentSize(), rowPow.getMaxX() - i));
            fires.add(fire);
        }

        // Col
        for (double i = colPow.getMinY() ; i < colPow.getMaxY()  ; i += gameScreen.getComponentSize()) {
            Fire fire = new Fire( colPow.getMinX(), i , gameScreen);
            fire.setDefaultFire();
            fire.setFitWidth(gameScreen.getComponentSize());
            fire.setFitHeight(Math.min(gameScreen.getComponentSize(), colPow.getMaxY() - i));
            fires.add(fire);
        }

        for (Fire fire : fires) {
            gameScreen.getMap().getChildren().add(fire);
//            fire.toBack();
            fire.toFront();
        }
    }



    @Override
    public void die() {
        hasPow = true;
        gameScreen.getManagement().removeBoom(this);
        if (boomPow != null) boomPow.stop();
        toBack();

        Timeline t = new Timeline(new KeyFrame(Duration.millis(200), ev -> {
            try {
                if (!(owner instanceof Arrow) && !(owner instanceof FireBullet)) {
                    if (owner instanceof Character) {
                        ((Character) owner).playSound("BoomPow");
                        ((Character) owner).playSound("Fire");
                    }

                } else {
                    ((Bullet) owner).getOwner().playSound("Fire");
                }
                power();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        t.setCycleCount(1);
        t.play();
    }

    public void setRowPow(Rectangle2D rowPow) {
        this.rowPow = rowPow;
    }

    public void setColPow(Rectangle2D colPow) {
        this.colPow = colPow;
    }

    @Override
    public void getDamage(double damage) {
        if (!hasPow) {
            super.getDamage(damage);
        }
    }
}
