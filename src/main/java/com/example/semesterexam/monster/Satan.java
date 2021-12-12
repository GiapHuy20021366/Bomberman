package com.example.semesterexam.monster;

import com.example.semesterexam.core.Bullets;
import com.example.semesterexam.core.Monster;
import com.example.semesterexam.effect.IconOrc;
import com.example.semesterexam.effect.IconSatan;
import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.tool.Player;
import com.example.semesterexam.weapon.Bow;

import java.io.IOException;

public class Satan extends Monster {
    public Satan(GameScreen gameScreen) throws IOException {
        super(gameScreen);
        onAttack.set("Archery");
        setRangeFar(2.5d);

        maxHP.set(200);
        HP.set(200);
        baseDamage.set(100);
        rateSpeed.set(1d);
    }

    @Override
    public void addIconSkills() {
        try {
            iconSkill.put("Archery", new IconSatan(gameScreen, this, -1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSounds() {
        super.addSounds();
        sounds.put("Fire", new Player(gameScreen, "Fire", Player.VOLUME_MONSTER_WEAPON));
    }

    @Override
    public void addActionMoveNormal() {
        addActions("BowGoDown", gameScreen.getAction("SatanPack:BowGoDown"));
        addActions("BowGoUp", gameScreen.getAction("SatanPack:BowGoUp"));
        addActions("BowGoLeft", gameScreen.getAction("SatanPack:BowGoLeft"));
        addActions("BowGoRight", gameScreen.getAction("SatanPack:BowGoRight"));
        addActions("BowStandUp", gameScreen.getAction("SatanPack:BowStandUp"));
        addActions("BowStandDown", gameScreen.getAction("SatanPack:BowStandDown"));
        addActions("BowStandLeft", gameScreen.getAction("SatanPack:BowStandLeft"));
        addActions("BowStandRight", gameScreen.getAction("SatanPack:BowStandRight"));

        setActions("BowStandRight");
    }

    @Override
    public void addActionDieNormal() {
        addActions("BowDieUp", gameScreen.getAction("SatanPack:BowDieUp"));
        addActions("BowDieRight", gameScreen.getAction("SatanPack:BowDieRight"));
        addActions("BowDieLeft", gameScreen.getAction("SatanPack:BowDieLeft"));
        addActions("BowDieDown", gameScreen.getAction("SatanPack:BowDieDown"));
    }

    @Override
    public void addActionAttack() {
        addActions("ArcheryAttackUp", gameScreen.getAction("SatanPack:ArcheryAttackUp"));
        addActions("ArcheryAttackRight", gameScreen.getAction("SatanPack:ArcheryAttackRight"));
        addActions("ArcheryAttackLeft", gameScreen.getAction("SatanPack:ArcheryAttackLeft"));
        addActions("ArcheryAttackDown", gameScreen.getAction("SatanPack:ArcheryAttackDown"));
    }

    @Override
    public void addAllActions() {
        addActionMoveNormal();
        addActionDieNormal();
        addActionAttack();
    }

    @Override
    public void addAllWeapon() {
        super.addWeapon(new Bow(this, gameScreen));
        addAllAttack();

        setAttacks("Archery");

        addIconSkills();

        onWeapon.plusBullet(Bullets.Arrow, Integer.MAX_VALUE - 1000);
    }

    @Override
    public void addAllAttack() {
        attacks.put("Archery", "Bow");
    }


}
