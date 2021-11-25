package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Subject;
import com.example.semesterexam.manage.GameScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
    protected int damage = 100;
    private boolean hasPow = false;
    protected Rectangle2D rowPow;
    protected Rectangle2D colPow;
    protected List<Fire> fires = new ArrayList<>();
    public double rangeTop = 1.5d;
    public double rangeDown = 1.5d;
    public double rangeLeft = 1.5d;
    public double rangeRight = 1.5d;

    public void setRange(double range) {
        this.range = range;
    }

    public void setAllRange(double range) {
        rangeTop = range;
        rangeDown = range;
        rangeRight = range;
        rangeLeft = range;
    }

    public void setDamage(int damage) {
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

    public Boom(double x, double y, GameScreen gameScreen) throws IOException {
        super(gameScreen);

        addActions("QuaBoom", gameScreen.getAction("BoomPack:QuaBoom"));
        addActions("BoomPow", gameScreen.getAction("BoomPack:BoomPow"));

        HP.set(1);
        setX(x);
        setY(y);
        setDefaultSize(1d);

    }

    public void power() throws IOException {
//        setActions("BoomPow");
        setVisible(false);
        gameScreen.getMap().getChildren().remove(this);
        List<Subject> subjects = gameScreen.getManagement().subjects(this,rangeTop, rangeDown, rangeLeft, rangeRight);
        setFire();
        for (Subject s : subjects) s.getDamage(damage);
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
    public void getDamage(int damage) {
        if (!hasPow) {
            super.getDamage(damage);
        }
    }
}
