package com.example.semesterexam.lanscape;

import com.example.semesterexam.manage.GameScreen;
import javafx.geometry.Rectangle2D;

import java.io.IOException;
import java.util.Random;

public class Lava extends Grass{
    public Lava(double X, double Y, GameScreen gameScreen) throws IOException {
        super(X, Y, gameScreen);

        addActions("Default", gameScreen.getAction("GrassPack:LavaLive"));
        addActions("Fire", gameScreen.getAction("GrassPack:Lava"));

        setActions("Default");

        maxHP.set(200);
        HP.set(200);

    }

}
