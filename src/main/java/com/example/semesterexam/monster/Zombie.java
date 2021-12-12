package com.example.semesterexam.monster;

import com.example.semesterexam.core.Monster;
import com.example.semesterexam.effect.IconOrc;
import com.example.semesterexam.effect.IconZombie;
import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.weapon.Mace;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class Zombie extends Monster {

    public Zombie(GameScreen gameScreen) throws IOException {
        super(gameScreen);
        onAttack.set("Mace");
        setRangeFar(2.5d);

        maxHP.set(400);
        HP.set(400);
        baseDamage.set(80);
        rateSpeed.set(1.4d);
    }

    @Override
    public void addIconSkills() {
        try {
            iconSkill.put("Mace", new IconZombie(gameScreen, this, -1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addActionMoveNormal() {
        addActions("MaceGoDown", gameScreen.getAction("ZombiePack:MaceGoDown"));
        addActions("MaceGoUp", gameScreen.getAction("ZombiePack:MaceGoUp"));
        addActions("MaceGoLeft", gameScreen.getAction("ZombiePack:MaceGoLeft"));
        addActions("MaceGoRight", gameScreen.getAction("ZombiePack:MaceGoRight"));
        addActions("MaceStandUp", gameScreen.getAction("ZombiePack:MaceStandUp"));
        addActions("MaceStandDown", gameScreen.getAction("ZombiePack:MaceStandDown"));
        addActions("MaceStandLeft", gameScreen.getAction("ZombiePack:MaceStandLeft"));
        addActions("MaceStandRight", gameScreen.getAction("ZombiePack:MaceStandRight"));

        setActions("MaceStandRight");
    }

    @Override
    public void addActionDieNormal() {
        addActions("MaceDieUp", gameScreen.getAction("ZombiePack:MaceDieUp"));
        addActions("MaceDieRight", gameScreen.getAction("ZombiePack:MaceDieRight"));
        addActions("MaceDieLeft", gameScreen.getAction("ZombiePack:MaceDieLeft"));
        addActions("MaceDieDown", gameScreen.getAction("ZombiePack:MaceDieDown"));
    }

    @Override
    public void addActionAttack() {
        addActions("MaceAttackUp", gameScreen.getAction("ZombiePack:MaceAttackUp"));
        addActions("MaceAttackRight", gameScreen.getAction("ZombiePack:MaceAttackRight"));
        addActions("MaceAttackLeft", gameScreen.getAction("ZombiePack:MaceAttackLeft"));
        addActions("MaceAttackDown", gameScreen.getAction("ZombiePack:MaceAttackDown"));
    }

    @Override
    public void addAllActions() {
        addActionMoveNormal();
        addActionDieNormal();
        addActionAttack();
    }

    @Override
    public void addAllWeapon() {
        addWeapon(new Mace(this, gameScreen));
        addAllAttack();

        setAttacks("Mace");

        addIconSkills();
    }


    @Override
    public void addAllAttack() {
        attacks.put("Mace", "Mace");
    }


}
