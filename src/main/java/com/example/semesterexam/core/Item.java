package com.example.semesterexam.core;

import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.tool.Player;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public abstract class Item extends Subject {
    protected double timeOut = 10000;
    protected Timeline disappear;
    protected AnimationTimer timer;
    protected Figure figure = null;
    protected boolean effecting = false;
    protected Effect effect;

    public Item(double x, double y, GameScreen gameScreen) throws IOException {
        super(gameScreen);
        setX(x);
        setY(y);
        setFitWidth(gameScreen.getComponentSize());
        setFitHeight(gameScreen.getComponentSize());
    }

    public void removeEffect() {
        disableEffect();
        if (effect != null) {
            effect.setHP(-1); // Die -> Disappear
        }
    }

    private Item getThis() {
        return this;
    }

    public void setTimeOut(double timeOut) {
        this.timeOut = timeOut;
    }

    public void disappear() {
        disappear = new Timeline(new KeyFrame(Duration.millis(timeOut), ev -> {
            timer.stop();
            gameScreen.getMap().getChildren().remove(getThis());
        }));
        disappear.play();
    }

    public abstract void setDefault();
    public void startTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                figure = gameScreen.getManagement().getFigureEarnItem(getThis());
                if (figure != null) {
                    HP.set(-1);
                }
            }
        };
        timer.start();
    }

    public abstract void effect();

    public abstract void disableEffect();

    public abstract ItemsName getItemsName();

    @Override
    public void die() {
        if (timer != null) timer.stop();
        if (disappear != null) disappear.stop();
//        new Player(gameScreen, gameScreen.getMediaManagement().getSound("EatItem"), Player.VOLUME_PLAYER).play();
        if (figure != null) {
            figure.playSound("EatItem");
        }

        gameScreen.getMap().getChildren().remove(getThis());

        effect();
    }
}
