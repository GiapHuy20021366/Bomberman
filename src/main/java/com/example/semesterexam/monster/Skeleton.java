package com.example.semesterexam.monster;

import com.example.semesterexam.core.Monster;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class Skeleton extends Monster {
    public Skeleton(GameScreen gameScreen) throws IOException {
        super(gameScreen);
    }


    @Override
    public void addActionMove() {
        addActions("GoDown", gameScreen.getAction("SkeletonPack:GoDown"));
        addActions("GoUp", gameScreen.getAction("SkeletonPack:GoUp"));
        addActions("GoLeft", gameScreen.getAction("SkeletonPack:GoLeft"));
        addActions("GoRight", gameScreen.getAction("SkeletonPack:GoRight"));
        addActions("StandUp", gameScreen.getAction("SkeletonPack:StandUp"));
        addActions("StandDown", gameScreen.getAction("SkeletonPack:StandDown"));
        addActions("StandLeft", gameScreen.getAction("SkeletonPack:StandLeft"));
        addActions("StandRight", gameScreen.getAction("SkeletonPack:StandRight"));

        setActions("StandRight");
    }

    @Override
    public void addActionDie() {
        addActions("DieUp", gameScreen.getAction("SkeletonPack:DieUp"));
        addActions("DieRight", gameScreen.getAction("SkeletonPack:DieRight"));
        addActions("DieLeft", gameScreen.getAction("SkeletonPack:DieLeft"));
        addActions("DieDown", gameScreen.getAction("SkeletonPack:DieDown"));
    }

    @Override
    public void addActionAttack() {
        addActions("AttackUp", gameScreen.getAction("SkeletonPack:AttackUp"));
        addActions("AttackRight", gameScreen.getAction("SkeletonPack:AttackRight"));
        addActions("AttackLeft", gameScreen.getAction("SkeletonPack:AttackLeft"));
        addActions("AttackDown", gameScreen.getAction("SkeletonPack:AttackDown"));
    }

    @Override
    public void addAllActions() {
        addActionMove();
        addActionDie();
        addActionAttack();
    }
}
