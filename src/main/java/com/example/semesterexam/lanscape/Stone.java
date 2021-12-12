package com.example.semesterexam.lanscape;

import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class Stone extends Grass {
    public Stone(double X, double Y, GameScreen gameScreen) throws IOException {
        super(X, Y, gameScreen);

        addActions("Stone", gameScreen.getAction("GrassPack:Stone"));
        setActions("Stone");
    }

    @Override
    public void getDamage(double damage) {
    }

    @Override
    public void die() {
    }


}
