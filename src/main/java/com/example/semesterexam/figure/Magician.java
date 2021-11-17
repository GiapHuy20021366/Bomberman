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
    public void addUniqueWeapon() {
        addMagicWand();
    }

    @Override
    public void setUniqueWeapon() {
        onWeapon = weapons.get("MagicWand");
    }

    @Override
    public void addAllWeapon() {
        addMagicWand();
    }

    @Override
    public void addActionMove() {
        addActions("GoDown", gameScreen.getAction("MagicianPack:GoDown"));
        addActions("GoUp", gameScreen.getAction("MagicianPack:GoUp"));
        addActions("GoLeft", gameScreen.getAction("MagicianPack:GoLeft"));
        addActions("GoRight", gameScreen.getAction("MagicianPack:GoRight"));
        addActions("StandUp", gameScreen.getAction("MagicianPack:StandUp"));
        addActions("StandDown", gameScreen.getAction("MagicianPack:StandDown"));
        addActions("StandLeft", gameScreen.getAction("MagicianPack:StandLeft"));
        addActions("StandRight", gameScreen.getAction("MagicianPack:StandRight"));

        setActions("StandRight");
        HP.set(9999999);
    }

    @Override
    public void addActionDie() {
        addActions("DieUp", gameScreen.getAction("MagicianPack:DieUp"));
        addActions("DieRight", gameScreen.getAction("MagicianPack:DieRight"));
        addActions("DieLeft", gameScreen.getAction("MagicianPack:DieLeft"));
        addActions("DieDown", gameScreen.getAction("MagicianPack:DieDown"));
    }


    @Override
    public void addActionLighting() {
        addActions("LightingRight", gameScreen.getAction("MagicianPack:LightingRight"));
        addActions("LightingLeft", gameScreen.getAction("MagicianPack:LightingLeft"));
        addActions("LightingUp", gameScreen.getAction("MagicianPack:LightingUp"));
        addActions("LightingDown", gameScreen.getAction("MagicianPack:LightingDown"));
    }

    @Override
    public void addMagicWand() {
        super.addWeapon(new MagicWand(this, gameScreen));
    }

    @Override
    public void addAllActions() {
        addActionMove();
        addActionDie();
        addActionLighting();
    }
}
