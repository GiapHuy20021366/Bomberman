package com.example.semesterexam.monster;

import com.example.semesterexam.core.Monster;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class Skeleton extends Monster {
    public Skeleton(GameScreen gameScreen) throws IOException {
        super(gameScreen);
    }


    @Override
    public void addActionMoveNormal() {
        addActions("NormalGoDown", gameScreen.getAction("SkeletonPack:GoDown"));
        addActions("NormalGoUp", gameScreen.getAction("SkeletonPack:GoUp"));
        addActions("NormalGoLeft", gameScreen.getAction("SkeletonPack:GoLeft"));
        addActions("NormalGoRight", gameScreen.getAction("SkeletonPack:GoRight"));
        addActions("NormalStandUp", gameScreen.getAction("SkeletonPack:StandUp"));
        addActions("NormalStandDown", gameScreen.getAction("SkeletonPack:StandDown"));
        addActions("NormalStandLeft", gameScreen.getAction("SkeletonPack:StandLeft"));
        addActions("NormalStandRight", gameScreen.getAction("SkeletonPack:StandRight"));

        setActions("NormalStandRight");
    }

    @Override
    public void addActionDieNormal() {
        addActions("NormalDieUp", gameScreen.getAction("SkeletonPack:DieUp"));
        addActions("NormalDieRight", gameScreen.getAction("SkeletonPack:DieRight"));
        addActions("NormalDieLeft", gameScreen.getAction("SkeletonPack:DieLeft"));
        addActions("NormalDieDown", gameScreen.getAction("SkeletonPack:DieDown"));
    }

    @Override
    public void addActionAttack() {
        addActions("NormalAttackUp", gameScreen.getAction("SkeletonPack:AttackUp"));
        addActions("NormalAttackRight", gameScreen.getAction("SkeletonPack:AttackRight"));
        addActions("NormalAttackLeft", gameScreen.getAction("SkeletonPack:AttackLeft"));
        addActions("NormalAttackDown", gameScreen.getAction("SkeletonPack:AttackDown"));
    }

    @Override
    public void addAllActions() {
        addActionMoveNormal();
        addActionDieNormal();
        addActionAttack();
    }


    @Override
    public void addAllWeapon() {

    }

    @Override
    public void addAllAttack() {

    }
}
