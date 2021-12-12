package com.example.semesterexam.weapon;

import com.example.semesterexam.core.*;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.item.PlusHP;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;
import java.util.List;

public class ReduceHP extends Bullet {
    public ReduceHP(GameScreen gameScreen, Character owner) throws IOException {
        super(gameScreen, owner);
    }

    @Override
    public void setRealitySize() {

    }

    @Override
    public void effect() {
        if (subject instanceof Monster) {
            subject.setJustDamage(owner);
            subject.getDamage(subject.getMaxHP() * 0.5d * owner.getIncreaseDamage());
            List<Figure> list = gameScreen.getManagement().getFigureList();
            for (Figure figure : list) {
                try {
                    Item item = new PlusHP(figure.getX(), figure.getY(), gameScreen);
                    gameScreen.getMap().getChildren().add(item);
                    item.setDefault();
                    item.startTimer();
                    item.disappear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 4; i++) {
                for (Figure figure : list) {
                    try {
                        gameScreen.getManagement().randomItem(figure);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void setDefaultActions() {
        addActions("ReduceHPLeft", gameScreen.getAction("SimpleFireGoalPack:Left"));
        addActions("ReduceHPRight", gameScreen.getAction("SimpleFireGoalPack:Right"));
        addActions("ReduceHPUp", gameScreen.getAction("SimpleFireGoalPack:Up"));
        addActions("ReduceHPDown", gameScreen.getAction("SimpleFireGoalPack:Down"));
    }

    @Override
    public String getName() {
        return "ReduceHP";
    }
}
