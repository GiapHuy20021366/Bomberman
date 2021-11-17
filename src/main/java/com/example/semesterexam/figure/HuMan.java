package com.example.semesterexam.figure;

import com.example.semesterexam.core.Figure;
import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.weapon.Archery;
import com.example.semesterexam.weapon.Bow;

import java.io.IOException;

public class HuMan extends Figure implements Archery {
    public HuMan(GameScreen gameScreen) throws IOException {
        super(gameScreen);
    }

    @Override
    public void addUniqueWeapon() {
        addBow();
    }

    @Override
    public void setUniqueWeapon() {
        super.setWeapon("Bow");
    }

    @Override
    public void addAllWeapon() {
        addBow();
    }

    @Override
    public void addActionMove()  {

        addActions("GoDown", gameScreen.getAction("HuManPack:GoDown"));
        addActions("GoUp", gameScreen.getAction("HuManPack:GoUp"));
        addActions("GoLeft", gameScreen.getAction("HuManPack:GoLeft"));
        addActions("GoRight", gameScreen.getAction("HuManPack:GoRight"));
        addActions("StandUp", gameScreen.getAction("HuManPack:StandUp"));
        addActions("StandDown", gameScreen.getAction("HuManPack:StandDown"));
        addActions("StandLeft", gameScreen.getAction("HuManPack:StandLeft"));
        addActions("StandRight", gameScreen.getAction("HuManPack:StandRight"));

        // Call default Action
        setActions("StandRight");


    }

    @Override
    public void addActionDie() {
        addActions("DieUp", gameScreen.getAction("HuManPack:DieUp"));
        addActions("DieRight", gameScreen.getAction("HuManPack:DieRight"));
        addActions("DieLeft", gameScreen.getAction("HuManPack:DieLeft"));
        addActions("DieDown", gameScreen.getAction("HuManPack:DieDown"));
    }

    @Override
    public void addActionArchery() {
        addActions("ArcheryRight", gameScreen.getAction("HuManPack:ArcheryRight"));
        addActions("ArcheryLeft", gameScreen.getAction("HuManPack:ArcheryLeft"));
        addActions("ArcheryUp", gameScreen.getAction("HuManPack:ArcheryUp"));
        addActions("ArcheryDown", gameScreen.getAction("HuManPack:ArcheryDown"));
    }

    @Override
    public void addBow() {
        super.addWeapon(new Bow(this, gameScreen));
    }

    @Override
    public void addAllActions() {
        addActionMove();
        addActionDie();
        addActionArchery();
    }
}
