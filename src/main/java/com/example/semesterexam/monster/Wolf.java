package com.example.semesterexam.monster;

import com.example.semesterexam.core.Monster;
import com.example.semesterexam.core.Subject;
import com.example.semesterexam.effect.IconOrc;
import com.example.semesterexam.effect.IconWolf;
import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.weapon.Boom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class Wolf extends Monster {
    public Wolf(GameScreen gameScreen) throws IOException {
        super(gameScreen);

        maxHP.set(100);
        HP.set(100);
        baseDamage.set(120);
        rateSpeed.set(1.4d);
    }

    @Override
    public void addIconSkills() {
        try {
            iconSkill.put("Normal", new IconWolf(gameScreen, this, -1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void defaultAttack() {
        try {
            putBoom();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void causeDamage(Subject s, double damage) {
        try {
            putBoom();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void countdownPutBoom() {
        if (countdownPutBoom != null) countdownPutBoom.stop();

        countdownPutBoom =  new Timeline(new KeyFrame(Duration.millis(cyclePutBoom.get()), ev -> {
            hasPutBoom = false;
        }));

        countdownPutBoom.setCycleCount(1);
        countdownPutBoom.play();
    }

    private void putBoom() throws IOException {
        if (hasPutBoom) return;
        hasPutBoom = true;
        Boom boom = new Boom(this, gameScreen);
        boom.setDamage(200);
        boom.setAllRange(2d);
        boom.setActions("QuaBoom");
        gameScreen.getMap().getChildren().add(boom);
        gameScreen.getManagement().addBoom(boom);
        boom.countdown(1300);
        toFront();
        countdownPutBoom();
    }
    @Override
    public void addActionMoveNormal() {
        addActions("NormalGoDown", gameScreen.getAction("WolfPack:NormalGoDown"));
        addActions("NormalGoUp", gameScreen.getAction("WolfPack:NormalGoUp"));
        addActions("NormalGoLeft", gameScreen.getAction("WolfPack:NormalGoLeft"));
        addActions("NormalGoRight", gameScreen.getAction("WolfPack:NormalGoRight"));
        addActions("NormalStandUp", gameScreen.getAction("WolfPack:NormalStandUp"));
        addActions("NormalStandDown", gameScreen.getAction("WolfPack:NormalStandDown"));
        addActions("NormalStandLeft", gameScreen.getAction("WolfPack:NormalStandLeft"));
        addActions("NormalStandRight", gameScreen.getAction("WolfPack:NormalStandRight"));

        setActions("NormalStandRight");
    }

    @Override
    public void addActionDieNormal() {
        addActions("NormalDieUp", gameScreen.getAction("WolfPack:NormalDieUp"));
        addActions("NormalDieRight", gameScreen.getAction("WolfPack:NormalDieRight"));
        addActions("NormalDieLeft", gameScreen.getAction("WolfPack:NormalDieLeft"));
        addActions("NormalDieDown", gameScreen.getAction("WolfPack:NormalDieDown"));
    }

    @Override
    public void addActionAttack() {

    }

    @Override
    public void addAllActions() {
        addActionMoveNormal();
        addActionDieNormal();

        addIconSkills();
    }

    @Override
    public void addAllWeapon() {

    }

    @Override
    public void addAllAttack() {

    }


}
