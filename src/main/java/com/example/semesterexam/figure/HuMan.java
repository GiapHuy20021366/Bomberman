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
    public void addAllWeapon() {
        addBow();
    }

    @Override
    public void addActionMoveNormal()  {

        addActions("NormalGoDown", gameScreen.getAction("HuManPack:NormalGoDown"));
        addActions("NormalGoUp", gameScreen.getAction("HuManPack:NormalGoUp"));
        addActions("NormalGoLeft", gameScreen.getAction("HuManPack:NormalGoLeft"));
        addActions("NormalGoRight", gameScreen.getAction("HuManPack:NormalGoRight"));
        addActions("NormalStandUp", gameScreen.getAction("HuManPack:NormalStandUp"));
        addActions("NormalStandDown", gameScreen.getAction("HuManPack:NormalStandDown"));
        addActions("NormalStandLeft", gameScreen.getAction("HuManPack:NormalStandLeft"));
        addActions("NormalStandRight", gameScreen.getAction("HuManPack:NormalStandRight"));

        // Call default Action
        setActions("NormalStandRight");


    }



    @Override
    public void addActionDieNormal() {
        addActions("NormalDieUp", gameScreen.getAction("HuManPack:NormalDieUp"));
        addActions("NormalDieRight", gameScreen.getAction("HuManPack:NormalDieRight"));
        addActions("NormalDieLeft", gameScreen.getAction("HuManPack:NormalDieLeft"));
        addActions("NormalDieDown", gameScreen.getAction("HuManPack:NormalDieDown"));
    }

    @Override
    public void addActionAttackArchery() {
        addActions("ArcheryAttackRight", gameScreen.getAction("HuManPack:ArcheryAttackRight"));
        addActions("ArcheryAttackLeft", gameScreen.getAction("HuManPack:ArcheryAttackLeft"));
        addActions("ArcheryAttackUp", gameScreen.getAction("HuManPack:ArcheryAttackUp"));
        addActions("ArcheryAttackDown", gameScreen.getAction("HuManPack:ArcheryAttackDown"));
    }

    @Override
    public void addBow() {
        super.addWeapon(new Bow(this, gameScreen));
    }

    @Override
    public void addAttackBow() {
        attacks.put("Archery", "Bow");
    }

    @Override
    public void addActionMoveBow() {
        addActions("BowGoDown", gameScreen.getAction("HuManPack:NormalGoDown"));
        addActions("BowGoUp", gameScreen.getAction("HuManPack:NormalGoUp"));
        addActions("BowGoLeft", gameScreen.getAction("HuManPack:NormalGoLeft"));
        addActions("BowGoRight", gameScreen.getAction("HuManPack:NormalGoRight"));
        addActions("BowStandUp", gameScreen.getAction("HuManPack:NormalStandUp"));
        addActions("BowStandDown", gameScreen.getAction("HuManPack:NormalStandDown"));
        addActions("BowStandLeft", gameScreen.getAction("HuManPack:NormalStandLeft"));
        addActions("BowStandRight", gameScreen.getAction("HuManPack:NormalStandRight"));
    }

    @Override
    public void addActionDieBow() {
        addActions("BowDieUp", gameScreen.getAction("HuManPack:NormalDieUp"));
        addActions("BowDieRight", gameScreen.getAction("HuManPack:NormalDieRight"));
        addActions("BowDieLeft", gameScreen.getAction("HuManPack:NormalDieLeft"));
        addActions("BowDieDown", gameScreen.getAction("HuManPack:NormalDieDown"));
    }


    @Override
    public void addAllActions() {
        addActionMoveNormal();
        addActionDieNormal();

        addActionAttackArchery();
        addActionMoveBow();
        addActionDieBow();


        addAllAttack();
    }

    @Override
    public void addAllAttack() {
        addAttackBow();
    }
}
