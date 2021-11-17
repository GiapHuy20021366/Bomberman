package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Subject;
import com.example.semesterexam.lanscape.ConcreteWall;
import com.example.semesterexam.manage.GameScreen;
import javafx.animation.AnimationTimer;

import java.io.IOException;

public class Arrow extends Subject {
    protected double dx = 0d;
    protected double dy = 0d;
    protected double defaultSpeed = 3d;
    protected double nowRange = 0d;
    protected double maxRange = 50d;
    protected AnimationTimer fly;
    public double widthReality;
    public double heightReality;
    protected Subject subject = null;

    public Arrow(GameScreen gameScreen) throws IOException {
        super(gameScreen);
        setFitWidth(gameScreen.getComponentSize() / 2d);
        setFitHeight(gameScreen.getComponentSize() / 2d);
        widthReality = gameScreen.getComponentSize() / 2d;
        heightReality = gameScreen.getComponentSize() / 2d;
    }

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
        Boom boom = null;
        try {
            boom = new Boom(getX(), getY(), gameScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert boom != null;

        if (subject != null) {
            if (subject instanceof ConcreteWall) {
                if (dx > 0) {
                    boom.setX(getX() - getFitWidth());
                    boom.setY(getY() - getFitHeight() / 2d);
                } else if (dy > 0) {
                    boom.setX(getX() - getFitWidth() / 2d);
//                    boom.setY(getY() - getFitHeight() * 1.5d);
                    boom.setY(getY() - getFitHeight() * 1.5d);
                } else if (dy < 0) {
                    boom.setX(getX() - getFitWidth() / 2d);
                    boom.setY(getY() + getFitHeight() / 2d);
                } else if (dx < 0) {
                    boom.setX(getX() + getFitWidth() / 2d);
                    boom.setY(getY() - getFitHeight() / 2d);
                }
            } else {
                boom.setX(subject.getX());
                boom.setY(subject.getY());
            }
        } else {
            if (dx != 0) {
                boom.setY(getY() - getFitHeight() / 2d);
            } else {
                boom.setX(getX() - getFitWidth() / 2d);
            }
        }
        boom.setDamage(999);
//        boom.setRange(20d);
        boom.setAllRange(20d);
        adjustRange(boom);
//        boom.setActions("QuaBoom");
        gameScreen.getMap().getChildren().add(boom);
        boom.countdown(1);
    }


    public void adjustRange(Boom boom) {
        if (dx > 0) {
            boom.rangeLeft = 1d;
        } else if (dx < 0) {
            boom.rangeRight = 1d;
        } else if (dy > 0) {
            boom.rangeTop = 1d;
        } else {
            boom.rangeDown = 1d;
        }
    }

    public void move() {
        double dx_ = dx * defaultSpeed;
        double dy_ = dy * defaultSpeed;
        subject = gameScreen.getManagement().arrowIntersect(this, getX() + dx_, getY() + dy_);
        if (subject == null) {
            nowRange += Math.abs(dx_) + Math.abs(dy_);
            setX(getX() + dx_);
            setY(getY() + dy_);
        } else {
            HP.set(-1);
        }
    }

    public void setDefaultActions() {
        addActions("ArrowLeft", gameScreen.getAction("ArrowPack:ArrowLeft"));
        addActions("ArrowRight", gameScreen.getAction("ArrowPack:ArrowRight"));
        addActions("ArrowUp", gameScreen.getAction("ArrowPack:ArrowUp"));
        addActions("ArrowDown", gameScreen.getAction("ArrowPack:ArrowDown"));
    }

    public void setDefault(String name, String pack) {
        addActions(name + "Left", gameScreen.getAction(pack + name + "Left"));
        addActions(name + "Right", gameScreen.getAction(pack + name + "Right"));
        addActions(name + "Up", gameScreen.getAction(pack + name + "Up"));
        addActions(name + "Down", gameScreen.getAction(pack + name + "Down"));
    }

    @Override
    public void draw() {
        super.draw();

    }
}
