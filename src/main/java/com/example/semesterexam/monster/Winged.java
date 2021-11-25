package com.example.semesterexam.monster;

import com.example.semesterexam.core.Monster;
import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.weapon.Fire;
import com.example.semesterexam.weapon.MagicWand;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class Winged extends Monster {
    Timeline timelineAttack = null;
    public Winged(GameScreen gameScreen) throws IOException {
        super(gameScreen);
//        timelineAttack = new Timeline(new KeyFrame(Duration.millis(2000), ev -> {
//            attack();
//        }));
//        timelineAttack.setCycleCount(-1);
//        timelineAttack.play();
    }



    @Override
    public void addActionMoveNormal() {
        addActions("MagicWandGoDown", gameScreen.getAction("WingedPack:NormalGoDown"));
        addActions("MagicWandGoUp", gameScreen.getAction("WingedPack:NormalGoUp"));
        addActions("MagicWandGoLeft", gameScreen.getAction("WingedPack:NormalGoLeft"));
        addActions("MagicWandGoRight", gameScreen.getAction("WingedPack:NormalGoRight"));
        addActions("MagicWandStandUp", gameScreen.getAction("WingedPack:NormalStandUp"));
        addActions("MagicWandStandDown", gameScreen.getAction("WingedPack:NormalStandDown"));
        addActions("MagicWandStandLeft", gameScreen.getAction("WingedPack:NormalStandLeft"));
        addActions("MagicWandStandRight", gameScreen.getAction("WingedPack:NormalStandRight"));

        setActions("MagicWandStandRight");
    }

    @Override
    public void addActionDieNormal() {
        addActions("MagicWandDieUp", gameScreen.getAction("WingedPack:NormalDieUp"));
        addActions("MagicWandDieRight", gameScreen.getAction("WingedPack:NormalDieRight"));
        addActions("MagicWandDieLeft", gameScreen.getAction("WingedPack:NormalDieLeft"));
        addActions("MagicWandDieDown", gameScreen.getAction("WingedPack:NormalDieDown"));
    }

    @Override
    public void addActionAttack() {
        addActions("NormalAttackUp", gameScreen.getAction("WingedPack:NormalAttackUp"));
        addActions("NormalAttackRight", gameScreen.getAction("WingedPack:NormalAttackRight"));
        addActions("NormalAttackLeft", gameScreen.getAction("WingedPack:NormalAttackLeft"));
        addActions("NormalAttackDown", gameScreen.getAction("WingedPack:NormalAttackDown"));
    }

    @Override
    public void addAllActions() {
        addActionMoveNormal();
        addActionAttack();
        addActionDieNormal();
    }

    @Override
    public void addAllWeapon() {
        super.addWeapon(new MagicWand(this, gameScreen));

        addAllAttack();

        setAttacks("Normal");

    }

    @Override
    public void addAllAttack() {
        attacks.put("Normal", "MagicWand");
    }

}
