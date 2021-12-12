package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Bullet;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.core.Figure;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class SimpleFireGoal extends Bullet {
    private int damage = 1002;
    public SimpleFireGoal(GameScreen gameScreen, Character owner) throws IOException {
        super(gameScreen, owner);
    }

    @Override
    public void setRealitySize() {

    }


    @Override
    public void effect() {
        if (subject instanceof Figure) {
            subject.getDamage(owner.getBaseDamage() * owner.getIncreaseDamage());
        }
    }

    @Override
    public void setDefaultActions() {
        addActions("SimpleFireGoalLeft", gameScreen.getAction("SimpleFireGoalPack:Left"));
        addActions("SimpleFireGoalRight", gameScreen.getAction("SimpleFireGoalPack:Right"));
        addActions("SimpleFireGoalUp", gameScreen.getAction("SimpleFireGoalPack:Up"));
        addActions("SimpleFireGoalDown", gameScreen.getAction("SimpleFireGoalPack:Down"));
    }

    @Override
    public String getName() {
        return "SimpleFireGoal";
    }
}
