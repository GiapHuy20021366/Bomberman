package com.example.semesterexam.core;

import com.example.semesterexam.manage.GameScreen;
import javafx.geometry.Point2D;

import java.io.IOException;

public abstract class Wall extends Subject {
    public Point2D point2D = new Point2D(0, 0);
    public Wall(double X, double Y, GameScreen gameScreen) throws IOException {

        super(gameScreen);

        setX(X);
        setY(Y);

        addActions("Default", gameScreen.getAction("WallPack:Default"));


        // Call default
        setActions("Default");

        setFitWidth(gameScreen.getComponentSize());
        setFitHeight(gameScreen.getComponentSize());


//        setActions("Multi");
    }

    public void setPoint2D(Point2D p) {
        this.point2D = p;
    }

    @Override
    public void draw() {
        setX(point2D.getX()*gameScreen.getComponentSize());
        setY(point2D.getY()*gameScreen.getComponentSize());
        setFitWidth(gameScreen.getComponentSize());
        setFitHeight(gameScreen.getComponentSize());
    }

    @Override
    public void die() {
        setVisible(false);
        gameScreen.getManagement().removeWall(this);
    }


}
