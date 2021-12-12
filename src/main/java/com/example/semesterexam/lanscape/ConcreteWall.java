package com.example.semesterexam.lanscape;

import com.example.semesterexam.core.Wall;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class ConcreteWall extends Wall {
    public ConcreteWall(double X, double Y, GameScreen gameScreen) throws IOException {
        super(X, Y, gameScreen);
        HP.set(50);

        addActions("Default", gameScreen.getAction("WallCungPack:Default"));
        setActions("Default");
    }


    @Override
    public void getDamage(double damage) {
        // Do Nothing
    }
}
