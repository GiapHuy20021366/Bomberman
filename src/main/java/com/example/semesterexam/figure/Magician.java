package com.example.semesterexam.figure;

import com.example.semesterexam.core.Figure;
import com.example.semesterexam.effect.EffPlusHP;
import com.example.semesterexam.effect.IconBomber;
import com.example.semesterexam.effect.IconBuff;
import com.example.semesterexam.item.PlusHP;
import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.weapon.BuffHP;
import com.example.semesterexam.weapon.Lighting;
import com.example.semesterexam.weapon.MagicWand;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.io.IOException;

public class Magician extends Figure implements Lighting, BuffHP {
    private boolean hasBuff = false;
    private long cycleBuff = 6000L;
    private Timeline countdownBuff;
    public Magician(GameScreen gameScreen) throws IOException {
        super(gameScreen);

        maxHP.set(500);
        HP.set(500);
        baseDamage.set(100);
        rateSpeed.set(0.75);
    }

    @Override
    public void addIconSkills() {
        try {
            iconSkill.put("Normal", new IconBomber(gameScreen, this, -1));
            iconSkill.put("BuffHP", new IconBuff(gameScreen, this, -1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attack() {
        if (getOnAttack().equals("BuffHP") && hasBuff) {
            return;
        } else {
            hasBuff = true;
            if (countdownBuff != null) {
                countdownBuff.stop();
            }
            countdownBuff = new Timeline(new KeyFrame(Duration.millis(cycleBuff), ev -> {
                hasBuff = false;
            }));
            countdownBuff.play();
        }
        super.attack();
    }

    @Override
    public EventHandler<KeyEvent> getKeyPressedEvent() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W, UP -> goUp = true;
                    case S, DOWN -> goDown = true;
                    case A, LEFT -> goLeft = true;
                    case D, RIGHT -> goRight = true;
                    case B, SPACE -> attack();
                    case L -> gameScreen.setVision(0.8d);
                    case P -> gameScreen.setVision(1.2d);
                    case O -> gameScreen.sacking();
                    case M -> gameScreen.getViewPlayer().moveViewport();
                    case NUMPAD1 -> onAttack.set("Normal");
                    case NUMPAD2 -> onAttack.set("Lighting");
                    case NUMPAD3 -> onAttack.set("BuffHP");
                }
            }
        };
    }

    @Override
    public void setAttack(int i) {
        switch (i) {
            case 1 -> {
                onAttack.set("Normal");
            }
            case 2 -> {
                onAttack.set("BuffHP");
            }
            case 3 -> {
                onAttack.set("Lighting");
            }
        }
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
    public void addActionAttackBuffHP() {
        addActions("BuffHPAttackRight", gameScreen.getAction("MagicianPack:BuffHPAttackRight"));
        addActions("BuffHPAttackLeft", gameScreen.getAction("MagicianPack:BuffHPAttackLeft"));
        addActions("BuffHPAttackUp", gameScreen.getAction("MagicianPack:BuffHPAttackUp"));
        addActions("BuffHPAttackDown", gameScreen.getAction("MagicianPack:BuffHPAttackDown"));
    }

    @Override
    public void addMagicWand() {
        super.addWeapon(new MagicWand(this, gameScreen));
    }

    @Override
    public void addAttackMagicWand() {
        attacks.put("Lighting", "MagicWand");
        attacks.put("BuffHP", "MagicWand");
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
        addActionAttackBuffHP();
        addActionMoveMagicWand();
        addActionDieMagicWand();

        addIconSkills();

        addAllAttack();
    }

    @Override
    public void addAllAttack() {
        addAttackMagicWand();
    }


}
