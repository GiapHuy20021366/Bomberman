package com.example.semesterexam.lanscape;

import com.example.semesterexam.core.Subject;
import com.example.semesterexam.manage.GameScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class Grass extends Subject {
    public Point2D point2D = new Point2D(0, 0);
    private int maxHP = 1000;

    public Grass(double X, double Y, GameScreen gameScreen) throws IOException {

        super(gameScreen);

        setX(X);
        setY(Y);

        addActions("Default", gameScreen.getAction("GrassPack:Default"));
        addActions("Fire", gameScreen.getAction("GrassPack:FireGrass"));


        // Call default
        setActions("Default");

        HP.set(1000);

        setBuffHP();

//        setActions("Multi");
    }

    @Override
    public void getDamage(int damage) {
        HP.set(HP.get() - damage);
    }

    public void setPoint2D(Point2D p) {
        this.point2D = p;
    }

    @Override
    public void draw() {
        setX(point2D.getX() * gameScreen.getComponentSize());
        setY(point2D.getY() * gameScreen.getComponentSize());
        setFitWidth(gameScreen.getComponentSize());
        setFitHeight(gameScreen.getComponentSize());
    }

    Timeline regenerate = null;
    Timeline buffHP = null;

    public void setBuffHP() {
        if (HP.get() < 0) {
            HP.set(1);
        }
        buffHP = new Timeline(new KeyFrame(Duration.millis(1000), ev -> {
            HP.set(Math.min(HP.get() + maxHP / 5, maxHP));
        }));

        buffHP.setCycleCount(Transition.INDEFINITE);
        buffHP.play();
    }

    @Override
    public void die() {
        HP.set(1000);
        setActions("Fire");
        toBack();
        if (regenerate != null) regenerate.stop();
        long timeRegenerate = new Random().nextInt(6000) + 4000;
        regenerate = new Timeline(new KeyFrame(Duration.millis(timeRegenerate), ev -> {
            setActions("Default");
        }));


        regenerate.setCycleCount(1);
        regenerate.play();
    }


}
