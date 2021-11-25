package com.example.semesterexam.figure;

import com.example.semesterexam.core.Figure;
import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.weapon.Lighting;
import com.example.semesterexam.weapon.MagicWand;

import java.io.IOException;

public class Magician extends Figure implements Lighting {
    public Magician(GameScreen gameScreen) throws IOException {
        super(gameScreen);
    }

    @Override
    public void addAllWeapon() {
        addMagicWand();
    }

    @Override
    public void addActionMoveNormal() {
        addActions("NormalGoDown", gameScreen.getAction("MagicianPack:NormalGoDown"));
        addActions("NormalGoUp", gameScreen.getAction("MagicianPack:NormalGoUp"));
        addActions("NormalGoLeft", gameScreen.getAction("MagicianPack:NormalGoLeft"));
        addActions("NormalGoRight", gameScreen.getAction("MagicianPack:NormalGoRight"));
        addActions("NormalStandUp", gameScreen.getAction("MagicianPack:NormalStandUp"));
        addActions("NormalStandDown", gameScreen.getAction("MagicianPack:NormalStandDown"));
        addActions("NormalStandLeft", gameScreen.getAction("MagicianPack:NormalStandLeft"));
        addActions("NormalStandRight", gameScreen.getAction("MagicianPack:NormalStandRight"));

        setActions("NormalStandRight");
        HP.set(9999999);
    }

    @Override
    public void addActionDieNormal() {
        addActions("NormalDieUp", gameScreen.getAction("MagicianPack:NormalDieUp"));
        addActions("NormalDieRight", gameScreen.getAction("MagicianPack:NormalDieRight"));
        addActions("NormalDieLeft", gameScreen.getAction("MagicianPack:NormalDieLeft"));
        addActions("NormalDieDown", gameScreen.getAction("MagicianPack:NormalDieDown"));
    }


    @Override
    public void addActionAttackLighting() {
        addActions("LightingAttackRight", gameScreen.getAction("MagicianPack:LightingAttackRight"));
        addActions("LightingAttackLeft", gameScreen.getAction("MagicianPack:LightingAttackLeft"));
        addActions("LightingAttackUp", gameScreen.getAction("MagicianPack:LightingAttackUp"));
        addActions("LightingAttackDown", gameScreen.getAction("MagicianPack:LightingAttackDown"));
    }

    @Override
    public void addMagicWand() {
        super.addWeapon(new MagicWand(this, gameScreen));
    }

    @Override
    public void addAttackMagicWand() {
        attacks.put("Lighting", "MagicWand");
    }

    @Override
    public void addActionMoveMagicWand() {
        addActions("MagicWandGoDown", gameScreen.getAction("MagicianPack:NormalGoDown"));
        addActions("MagicWandGoUp", gameScreen.getAction("MagicianPack:NormalGoUp"));
        addActions("MagicWandGoLeft", gameScreen.getAction("MagicianPack:NormalGoLeft"));
        addActions("MagicWandGoRight", gameScreen.getAction("MagicianPack:NormalGoRight"));
        addActions("MagicWandStandUp", gameScreen.getAction("MagicianPack:NormalStandUp"));
        addActions("MagicWandStandDown", gameScreen.getAction("MagicianPack:NormalStandDown"));
        addActions("MagicWandStandLeft", gameScreen.getAction("MagicianPack:NormalStandLeft"));
        addActions("MagicWandStandRight", gameScreen.getAction("MagicianPack:NormalStandRight"));
    }

    @Override
    public void addActionDieMagicWand() {
        addActions("MagicWandDieUp", gameScreen.getAction("MagicianPack:NormalDieUp"));
        addActions("MagicWandDieRight", gameScreen.getAction("MagicianPack:NormalDieRight"));
        addActions("MagicWandDieLeft", gameScreen.getAction("MagicianPack:NormalDieLeft"));
        addActions("MagicWandDieDown", gameScreen.getAction("MagicianPack:NormalDieDown"));
    }

    @Override
    public void addAllActions() {
        addActionMoveNormal();
        addActionDieNormal();

        addActionAttackLighting();
        addActionMoveMagicWand();
        addActionDieMagicWand();


        addAllAttack();
    }

    @Override
    public void addAllAttack() {
        addAttackMagicWand();
    }
}
