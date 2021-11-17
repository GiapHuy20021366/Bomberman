package com.example.semesterexam.monster;

import com.example.semesterexam.core.Monster;
import com.example.semesterexam.manage.GameScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class Orc extends Monster {
    public Orc(GameScreen gameScreen) throws IOException {
        super(gameScreen);
    }



    @Override
    public void addActionMove() {
        addActions("GoDown", gameScreen.getAction("OrcPack:GoDown"));
        addActions("GoUp", gameScreen.getAction("OrcPack:GoUp"));
        addActions("GoLeft", gameScreen.getAction("OrcPack:GoLeft"));
        addActions("GoRight", gameScreen.getAction("OrcPack:GoRight"));
        addActions("StandUp", gameScreen.getAction("OrcPack:StandUp"));
        addActions("StandDown", gameScreen.getAction("OrcPack:StandDown"));
        addActions("StandLeft", gameScreen.getAction("OrcPack:StandLeft"));
        addActions("StandRight", gameScreen.getAction("OrcPack:StandRight"));

        setActions("StandRight");
    }

    @Override
    public void addActionAttack() {
        addActions("AttackUp", gameScreen.getAction("OrcPack:AttackUp"));
        addActions("AttackRight", gameScreen.getAction("OrcPack:AttackRight"));
        addActions("AttackLeft", gameScreen.getAction("OrcPack:AttackLeft"));
        addActions("AttackDown", gameScreen.getAction("OrcPack:AttackDown"));
    }

    @Override
    public void addActionDie() {
        addActions("DieUp", gameScreen.getAction("OrcPack:DieUp"));
        addActions("DieRight", gameScreen.getAction("OrcPack:DieRight"));
        addActions("DieLeft", gameScreen.getAction("OrcPack:DieLeft"));
        addActions("DieDown", gameScreen.getAction("OrcPack:DieDown"));
    }

    @Override
    public void addAllActions() {
        addActionMove();
        addActionDie();
        addActionAttack();
    }

}
