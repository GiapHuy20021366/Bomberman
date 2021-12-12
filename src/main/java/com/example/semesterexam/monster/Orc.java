package com.example.semesterexam.monster;

import com.example.semesterexam.core.Monster;
import com.example.semesterexam.effect.IconOrc;
import com.example.semesterexam.effect.IconWing;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class Orc extends Monster {
    public Orc(GameScreen gameScreen) throws IOException {
        super(gameScreen);

        maxHP.set(100);
        HP.set(100);
        baseDamage.set(50);
        rateSpeed.set(1.2d);
    }


    @Override
    public void addIconSkills() {
        try {
            iconSkill.put("Normal", new IconOrc(gameScreen, this, -1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addActionMoveNormal() {
        addActions("NormalGoDown", gameScreen.getAction("OrcPack:GoDown"));
        addActions("NormalGoUp", gameScreen.getAction("OrcPack:GoUp"));
        addActions("NormalGoLeft", gameScreen.getAction("OrcPack:GoLeft"));
        addActions("NormalGoRight", gameScreen.getAction("OrcPack:GoRight"));
        addActions("NormalStandUp", gameScreen.getAction("OrcPack:StandUp"));
        addActions("NormalStandDown", gameScreen.getAction("OrcPack:StandDown"));
        addActions("NormalStandLeft", gameScreen.getAction("OrcPack:StandLeft"));
        addActions("NormalStandRight", gameScreen.getAction("OrcPack:StandRight"));

        setActions("NormalStandRight");
    }

    @Override
    public void addActionAttack() {
        addActions("NormalAttackUp", gameScreen.getAction("OrcPack:AttackUp"));
        addActions("NormalAttackRight", gameScreen.getAction("OrcPack:AttackRight"));
        addActions("NormalAttackLeft", gameScreen.getAction("OrcPack:AttackLeft"));
        addActions("NormalAttackDown", gameScreen.getAction("OrcPack:AttackDown"));
    }

    @Override
    public void addActionDieNormal() {
        addActions("NormalDieUp", gameScreen.getAction("OrcPack:DieUp"));
        addActions("NormalDieRight", gameScreen.getAction("OrcPack:DieRight"));
        addActions("NormalDieLeft", gameScreen.getAction("OrcPack:DieLeft"));
        addActions("NormalDieDown", gameScreen.getAction("OrcPack:DieDown"));
    }

    @Override
    public void addAllActions() {
        addActionMoveNormal();
        addActionDieNormal();
        addActionAttack();

        addIconSkills();
    }


    @Override
    public void addAllWeapon() {

    }

    @Override
    public void addAllAttack() {

    }

}
