package com.example.semesterexam.figure;

import com.example.semesterexam.core.Figure;
import com.example.semesterexam.effect.*;
import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.tool.Player;
import com.example.semesterexam.weapon.*;

import java.io.IOException;

public class SuperHuMan extends Figure implements Archery, RedSlash, BlueSlash {
    public SuperHuMan(GameScreen gameScreen) throws IOException {
        super(gameScreen);

        maxHP.set(500);
        HP.set(500);
        baseDamage.set(120);
        rateSpeed.set(0.8);

    }

    @Override
    public void addSounds() {
        super.addSounds();

        sounds.put("Archery", new Player(gameScreen, "Archery", Player.VOLUME_FIGURE_WEAPON));
        sounds.put("Sword", new Player(gameScreen, "Sword", Player.VOLUME_FIGURE_WEAPON));
        sounds.put("Fire", new Player(gameScreen, "Fire", Player.VOLUME_FIGURE_WEAPON));

    }

    @Override
    public void addIconSkills() {
        try {
            iconSkill.put("Normal", new IconBomber(gameScreen, this, -1));
            iconSkill.put("BlueSlash", new IconBlueSword(gameScreen, this, -1));
            iconSkill.put("RedSlash", new IconRedSlash(gameScreen, this, -1));
            iconSkill.put("Archery", new IconArchery(gameScreen, this, -1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAllWeapon() {
        addBow();
        addBlueSword();
        addRedSword();
    }

    @Override
    public void addActionMoveNormal()  {

        addActions("NormalGoDown", gameScreen.getAction("SuperHuManPack:NormalGoDown"));
        addActions("NormalGoUp", gameScreen.getAction("SuperHuManPack:NormalGoUp"));
        addActions("NormalGoLeft", gameScreen.getAction("SuperHuManPack:NormalGoLeft"));
        addActions("NormalGoRight", gameScreen.getAction("SuperHuManPack:NormalGoRight"));
        addActions("NormalStandUp", gameScreen.getAction("SuperHuManPack:NormalStandUp"));
        addActions("NormalStandDown", gameScreen.getAction("SuperHuManPack:NormalStandDown"));
        addActions("NormalStandLeft", gameScreen.getAction("SuperHuManPack:NormalStandLeft"));
        addActions("NormalStandRight", gameScreen.getAction("SuperHuManPack:NormalStandRight"));

        // Call default Action
        setActions("NormalStandRight");


    }



    @Override
    public void addActionDieNormal() {
        addActions("NormalDieUp", gameScreen.getAction("SuperHuManPack:NormalDieUp"));
        addActions("NormalDieRight", gameScreen.getAction("SuperHuManPack:NormalDieRight"));
        addActions("NormalDieLeft", gameScreen.getAction("SuperHuManPack:NormalDieLeft"));
        addActions("NormalDieDown", gameScreen.getAction("SuperHuManPack:NormalDieDown"));
    }

    @Override
    public void addActionAttackArchery() {
        addActions("ArcheryAttackRight", gameScreen.getAction("SuperHuManPack:ArcheryAttackRight"));
        addActions("ArcheryAttackLeft", gameScreen.getAction("SuperHuManPack:ArcheryAttackLeft"));
        addActions("ArcheryAttackUp", gameScreen.getAction("SuperHuManPack:ArcheryAttackUp"));
        addActions("ArcheryAttackDown", gameScreen.getAction("SuperHuManPack:ArcheryAttackDown"));
    }

    @Override
    public void addBow() {
        super.addWeapon(new Bow(this, gameScreen));
    }

    @Override
    public void addActionAttackRedSlash() {
        addActions("RedSlashAttackRight", gameScreen.getAction("SuperHuManPack:RedSlashAttackRight"));
        addActions("RedSlashAttackLeft", gameScreen.getAction("SuperHuManPack:RedSlashAttackLeft"));
        addActions("RedSlashAttackUp", gameScreen.getAction("SuperHuManPack:RedSlashAttackUp"));
        addActions("RedSlashAttackDown", gameScreen.getAction("SuperHuManPack:RedSlashAttackDown"));
    }

    @Override
    public void addRedSword() {
        super.addWeapon(new RedSword(this, gameScreen));
    }

    @Override
    public void addAttackRedSword() {
        attacks.put("RedSlash", "RedSword");
    }

    @Override
    public void addActionMoveRedSword() {
        addActions("RedSwordGoDown", gameScreen.getAction("SuperHuManPack:RedSlashGoDown"));
        addActions("RedSwordGoUp", gameScreen.getAction("SuperHuManPack:RedSlashGoUp"));
        addActions("RedSwordGoLeft", gameScreen.getAction("SuperHuManPack:RedSlashGoLeft"));
        addActions("RedSwordGoRight", gameScreen.getAction("SuperHuManPack:RedSlashGoRight"));
        addActions("RedSwordStandUp", gameScreen.getAction("SuperHuManPack:RedSlashStandUp"));
        addActions("RedSwordStandDown", gameScreen.getAction("SuperHuManPack:RedSlashStandDown"));
        addActions("RedSwordStandLeft", gameScreen.getAction("SuperHuManPack:RedSlashStandLeft"));
        addActions("RedSwordStandRight", gameScreen.getAction("SuperHuManPack:RedSlashStandRight"));
    }

    @Override
    public void addActionDieRedSword() {
        addActions("RedSwordDieUp", gameScreen.getAction("SuperHuManPack:RedSlashDieUp"));
        addActions("RedSwordDieRight", gameScreen.getAction("SuperHuManPack:RedSlashDieRight"));
        addActions("RedSwordDieLeft", gameScreen.getAction("SuperHuManPack:RedSlashDieLeft"));
        addActions("RedSwordDieDown", gameScreen.getAction("SuperHuManPack:RedSlashDieDown"));
    }

    @Override
    public void addAttackBow() {
        attacks.put("Archery", "Bow");
    }


    @Override
    public void addActionMoveBow() {
        addActions("BowGoDown", gameScreen.getAction("SuperHuManPack:ArcheryGoDown"));
        addActions("BowGoUp", gameScreen.getAction("SuperHuManPack:ArcheryGoUp"));
        addActions("BowGoLeft", gameScreen.getAction("SuperHuManPack:ArcheryGoLeft"));
        addActions("BowGoRight", gameScreen.getAction("SuperHuManPack:ArcheryGoRight"));
        addActions("BowStandUp", gameScreen.getAction("SuperHuManPack:ArcheryStandUp"));
        addActions("BowStandDown", gameScreen.getAction("SuperHuManPack:ArcheryStandDown"));
        addActions("BowStandLeft", gameScreen.getAction("SuperHuManPack:ArcheryStandLeft"));
        addActions("BowStandRight", gameScreen.getAction("SuperHuManPack:ArcheryStandRight"));
    }

    @Override
    public void addActionDieBow() {
        addActions("BowDieUp", gameScreen.getAction("SuperHuManPack:ArcheryDieUp"));
        addActions("BowDieRight", gameScreen.getAction("SuperHuManPack:ArcheryDieRight"));
        addActions("BowDieLeft", gameScreen.getAction("SuperHuManPack:ArcheryDieLeft"));
        addActions("BowDieDown", gameScreen.getAction("SuperHuManPack:ArcheryDieDown"));
    }

    @Override
    public void addActionAttackBlueSlash() {
        addActions("BlueSlashAttackRight", gameScreen.getAction("SuperHuManPack:BlueSlashAttackRight"));
        addActions("BlueSlashAttackLeft", gameScreen.getAction("SuperHuManPack:BlueSlashAttackLeft"));
        addActions("BlueSlashAttackUp", gameScreen.getAction("SuperHuManPack:BlueSlashAttackUp"));
        addActions("BlueSlashAttackDown", gameScreen.getAction("SuperHuManPack:BlueSlashAttackDown"));
    }

    @Override
    public void addBlueSword() {
        super.addWeapon(new BlueSword(this, gameScreen));
    }

    @Override
    public void addAttackBlueSword() {
        attacks.put("BlueSlash", "BlueSword");
    }

    @Override
    public void addActionMoveBlueSword() {
        addActions("BlueSwordGoDown", gameScreen.getAction("SuperHuManPack:BlueSlashGoDown"));
        addActions("BlueSwordGoUp", gameScreen.getAction("SuperHuManPack:BlueSlashGoUp"));
        addActions("BlueSwordGoLeft", gameScreen.getAction("SuperHuManPack:BlueSlashGoLeft"));
        addActions("BlueSwordGoRight", gameScreen.getAction("SuperHuManPack:BlueSlashGoRight"));
        addActions("BlueSwordStandUp", gameScreen.getAction("SuperHuManPack:BlueSlashStandUp"));
        addActions("BlueSwordStandDown", gameScreen.getAction("SuperHuManPack:BlueSlashStandDown"));
        addActions("BlueSwordStandLeft", gameScreen.getAction("SuperHuManPack:BlueSlashStandLeft"));
        addActions("BlueSwordStandRight", gameScreen.getAction("SuperHuManPack:BlueSlashStandRight"));
    }

    @Override
    public void addActionDieBlueSword() {
        addActions("BlueSwordDieUp", gameScreen.getAction("SuperHuManPack:BlueSlashDieUp"));
        addActions("BlueSwordDieRight", gameScreen.getAction("SuperHuManPack:BlueSlashDieRight"));
        addActions("BlueSwordDieLeft", gameScreen.getAction("SuperHuManPack:BlueSlashDieLeft"));
        addActions("BlueSwordDieDown", gameScreen.getAction("SuperHuManPack:BlueSlashDieDown"));
    }

    @Override
    public void addAllActions() {
        addActionMoveNormal();
        addActionDieNormal();
        addActionAttackArchery();
        addActionDieBow();
        addActionMoveBow();

        addActionMoveRedSword();
        addActionAttackRedSlash();
        addActionDieRedSword();

        addActionMoveBlueSword();
        addActionAttackBlueSlash();
        addActionDieBlueSword();

        addIconSkills();


        addAllAttack();
    }

    @Override
    public void addAllAttack() {
        addAttackBow();
        addAttackRedSword();
        addAttackBlueSword();
    }

    @Override
    public void setAttack(int i) {
        switch (i) {
            case 1 -> {
                onAttack.set("Normal");
            }
            case 2 -> {
                onAttack.set("Archery");
            }
            case 3 -> {
                onAttack.set("RedSlash");
            }
            case 4 -> {
                onAttack.set("BlueSlash");
            }
        }
    }

}
