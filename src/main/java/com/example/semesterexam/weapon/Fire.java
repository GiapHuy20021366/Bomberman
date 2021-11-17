package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Subject;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class Fire extends Subject {
    public Fire(double x, double y, GameScreen gameScreen) throws IOException {
        super(gameScreen);
        setX(x);
        setY(y);
    }

    public void setDefaultFire() {
        addActions("RedFire", gameScreen.getAction("FirePack:RedFire"));
        setActions("RedFire");
//        toFront();
    }
}
