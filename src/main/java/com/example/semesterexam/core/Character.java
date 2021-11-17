package com.example.semesterexam.core;

import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.weapon.Boom;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;

import java.io.IOException;

public abstract  class Character extends Subject {

    protected boolean goUp = false, goDown = false, goRight = false, goLeft = false, stand = false;
    protected BooleanProperty isDisableMoving = new SimpleBooleanProperty(false);
    protected AnimationTimer timer;
    protected double viewValue = 300d;

    public Character(GameScreen gameScreen) throws IOException {
        super(gameScreen);
        HP.set(10000000);
    }


    public void setDefaultLocation(int x, int y) {
        setX(gameScreen.getComponentSize() * x);
        setY(gameScreen.getComponentSize() * y);
    }

    public abstract void addActionMove();

    public abstract void addActionDie();

    public abstract void addAllActions();


    @Override
    public void die(Monster dieBy) {

        if (actions.get("GoRight").equals(getOnAction()) || actions.get("StandRight").equals(getOnAction()))
            setActions("DieRight");
        else if (actions.get("GoLeft").equals(getOnAction()) || actions.get("StandLeft").equals(getOnAction()))
            setActions("DieLeft");
        else if (actions.get("GoUp").equals(getOnAction()) || actions.get("StandUp").equals(getOnAction()))
            setActions("DieUp");
        else if (actions.get("GoDown").equals(getOnAction()) || actions.get("StandDown").equals(getOnAction()))
            setActions("DieDown");

        isDisableMoving.set(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ev -> {
            this.setVisible(false);
//            gameScreen.getViewPlayer().moveViewportBy(dieBy);

        }));
        timeline.play();

    }





    public void startMoving() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isDisableMoving.get()) {
                    move();
                }
            }
        };
        timer.start();
    }

    public abstract void goUp();

    public abstract void goDown();


    public abstract void goRight();

    public abstract void goLeft();

    public void stand() {
        if (!actions.get("StandRight").equals(getOnAction())) {
            if (actions.get("GoRight").equals(getOnAction())) setActions("StandRight");
            else if (actions.get("GoLeft").equals(getOnAction())) setActions("StandLeft");
            else if (actions.get("GoUp").equals(getOnAction())) setActions("StandUp");
            else if (actions.get("GoDown").equals(getOnAction())) setActions("StandDown");
        }
    }



    @Override
    public void getDamage(int damage) {
        super.getDamage(damage);
        System.out.println(HP.get());
    }

    public void move() {

        if (!isMoving()) {
            stand = true;
        } else {
            stand = false;
        }

        if (goUp) {
            goUp();
        } else if (goDown) {
            goDown();
        } else if (goRight) {
            goRight();
        } else if (goLeft) {
            goLeft();
        } else if (stand) {
            stand();
        }
    }


    protected boolean isOverlapping(double newX, double newY, String direction) {
        return gameScreen.getManagement().isOverlapping(this, newX, newY, direction);
    }


    public void die() {
        if (actions.get("GoRight").equals(getOnAction()) || actions.get("StandRight").equals(getOnAction()))
            setActions("DieRight");
        else if (actions.get("GoLeft").equals(getOnAction()) || actions.get("StandLeft").equals(getOnAction()))
            setActions("DieLeft");
        else if (actions.get("GoUp").equals(getOnAction()) || actions.get("StandUp").equals(getOnAction()))
            setActions("DieUp");
        else if (actions.get("GoDown").equals(getOnAction()) || actions.get("StandDown").equals(getOnAction()))
            setActions("DieDown");

        isDisableMoving.set(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ev -> {
            this.setVisible(false);
            gameScreen.getManagement().removeCharacter(this);

        }));
        timeline.play();
    }

    public boolean isMoving() {
        return goDown || goUp || goLeft || goRight;
    }

    public double getViewValue() {
        return viewValue;
    }

    @Override
    public int hashCode() {
        return subjectName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        return subjectName.equals(((Subject) obj).subjectName);
    }



    public abstract void attack();

    public void setViewValue(double viewValue) {
        this.viewValue = viewValue;
    }

    // Using for test
    public void putBoom2() throws IOException {
        Boom boom = new Boom(getX(), getY(), gameScreen);
        boom.setDamage(100000);
        boom.setRange(5d);
        boom.setActions("QuaBoom");
        gameScreen.getMap().getChildren().add(boom);
        gameScreen.getManagement().addBoom(boom);
        toFront();
    }


}
