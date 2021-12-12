package com.example.semesterexam.core;

import com.example.semesterexam.manage.GameScreen;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.IOException;

public abstract class Bullet extends Subject {
    public double dx = 0d;
    public double dy = 0d;
    protected DoubleProperty defaultSpeed = new SimpleDoubleProperty(gameScreen.getComponentSize() / 20d);
    protected double nowRange = 0d;
    protected double maxRange = 50d;
    protected AnimationTimer fly;
    public double widthReality;
    public double heightReality;
    protected Subject subject = null;
    protected Character owner;

    public Bullet(GameScreen gameScreen, Character owner) throws IOException {
        super(gameScreen);
        this.owner = owner;

        defaultSpeed.bind(gameScreen.getSizeProperties().divide(13d));

        setFitWidth(gameScreen.getComponentSize());
        setFitHeight(gameScreen.getComponentSize());
        widthReality = gameScreen.getComponentSize();
        heightReality = gameScreen.getComponentSize();

    }

    public abstract void setRealitySize();

    public void fly() {
        fly = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (nowRange < maxRange * gameScreen.getComponentSize()) {
                    move();
                } else {
                    HP.set(-1);
                }
            }
        };
        fly.start();
    }

    public void die() {
        fly.stop();
        setVisible(false);
        gameScreen.getMap().getChildren().remove(this);
        effect();
    }

    public abstract void effect();

    public Character getOwner() {
        return owner;
    }

//    public abstract Subject getIntersects(double newX, double newY);


    public void move() {
        double dx_ = dx * defaultSpeed.get();
        double dy_ = dy * defaultSpeed.get();
        subject = gameScreen.getManagement().arrowIntersect(this, getX() + dx_, getY() + dy_);
        if (subject == null) {
            nowRange += Math.abs(dx_) + Math.abs(dy_);
            setX(getX() + dx_);
            setY(getY() + dy_);
        } else {
            HP.set(-1);
        }
    }

    public abstract void setDefaultActions();


    @Override
    public void draw() {
        super.draw();
    }

    public abstract String getName();

}
