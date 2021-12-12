package com.example.semesterexam.monster;

import com.example.semesterexam.core.Direction;
import com.example.semesterexam.core.Monster;
import com.example.semesterexam.effect.IconBlueSword;
import com.example.semesterexam.effect.IconBomber;
import com.example.semesterexam.effect.IconSkullcap;
import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.weapon.Fire;
import com.example.semesterexam.weapon.MagicWand;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class BossHuMan extends Monster {

    public BossHuMan(GameScreen gameScreen) throws IOException {
        super(gameScreen);

        setRangeFar(2.5d);


        cycleHideBloodBar = 100000L;

        maxHP.set(1000);
        HP.set(1000);
        baseDamage.set(200);
        rateSpeed.set(1.3d);
    }

    @Override
    public void addIconSkills() {
        try {
            iconSkill.put("Normal", new IconSkullcap(gameScreen, this, -1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addActionMoveNormal() {
        addActions("MagicWandGoDown", gameScreen.getAction("BossHuManPack:NormalGoDown"));
        addActions("MagicWandGoUp", gameScreen.getAction("BossHuManPack:NormalGoUp"));
        addActions("MagicWandGoLeft", gameScreen.getAction("BossHuManPack:NormalGoLeft"));
        addActions("MagicWandGoRight", gameScreen.getAction("BossHuManPack:NormalGoRight"));
        addActions("MagicWandStandUp", gameScreen.getAction("BossHuManPack:NormalStandUp"));
        addActions("MagicWandStandDown", gameScreen.getAction("BossHuManPack:NormalStandDown"));
        addActions("MagicWandStandLeft", gameScreen.getAction("BossHuManPack:NormalStandLeft"));
        addActions("MagicWandStandRight", gameScreen.getAction("BossHuManPack:NormalStandRight"));

        setActions("MagicWandStandRight");
    }

    @Override
    public void addActionDieNormal() {
        addActions("MagicWandDieUp", gameScreen.getAction("BossHuManPack:NormalDieUp"));
        addActions("MagicWandDieRight", gameScreen.getAction("BossHuManPack:NormalDieRight"));
        addActions("MagicWandDieLeft", gameScreen.getAction("BossHuManPack:NormalDieLeft"));
        addActions("MagicWandDieDown", gameScreen.getAction("BossHuManPack:NormalDieDown"));
    }

    @Override
    public void addActionAttack() {
        addActions("NormalAttackUp", gameScreen.getAction("BossHuManPack:NormalAttackUp"));
        addActions("NormalAttackRight", gameScreen.getAction("BossHuManPack:NormalAttackRight"));
        addActions("NormalAttackLeft", gameScreen.getAction("BossHuManPack:NormalAttackLeft"));
        addActions("NormalAttackDown", gameScreen.getAction("BossHuManPack:NormalAttackDown"));
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

        addIconSkills();

        setAttacks("Normal");
    }

    @Override
    public void addAllAttack() {
        attacks.put("Normal", "MagicWand");
    }

    private boolean hasSummon = false;
    private Timeline countdownNextSummon;
    private Timeline summon;

    private void summon() {
        if (hasSummon) {
            return;
        }
        if (countdownNextSummon != null) {
            countdownNextSummon.stop();
        }
        if (countdownNextSummon != null) {
            countdownNextSummon.stop();
        }

        hasSummon = true;
        summon = new Timeline(new KeyFrame(Duration.millis(1000L), ev -> {
            if (!gameScreen.getManagement().isAccess()) {
                return;
            }
            Monster m = null;
            try {
                m = gameScreen.getManagement().randomMonster(gameScreen);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!(m instanceof BossHuMan)) {
                assert m != null;
                m.setDefaultSize(1d);
                m.setX(this.getX());
                m.setY(this.getY());
                m.addAllActions();
                m.addAllWeapon();
                m.setDefaultDirection(this.getOnDirection());
                m.setCanChangeDirection(true);
                m.startMoving();
                m.showBloodBar();
                gameScreen.getManagement().addMonster(m);
                gameScreen.getMap().getChildren().add(m);
            }


        }));
        summon.setCycleCount(5);
        summon.play();

        countdownNextSummon = new Timeline(new KeyFrame(Duration.millis(10000L), ev -> {
            hasSummon = false;
        }));
        countdownNextSummon.play();
    }

    @Override
    public void doAfterCompleteAttack() {
        super.doAfterCompleteAttack();
        summon();
    }



    @Override
    public void die() {
        hasSummon = true;
        if (summon != null) {
            summon.stop();
            countdownNextSummon.stop();
        }
        super.die();
    }

    @Override
    public void freeze() {
        if (summon != null) {
            summon.stop();
        }
        super.freeze();
    }
}
