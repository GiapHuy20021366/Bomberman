package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Bullet;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.core.Monster;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class IceBullet extends Bullet {
    public IceBullet(GameScreen gameScreen, Character owner) throws IOException {
        super(gameScreen, owner);
    }

    @Override
    public void setRealitySize() {

    }

    @Override
    public void effect() {
        for (Monster m : gameScreen.getManagement().getMonsterEffectByIce(subject.getX(), subject.getY(), 5 * gameScreen.getComponentSize())) {
            freeze(m);
        }
    }

    private void freeze(Monster monster) {
        monster.setIsDisableMoving(true);
        monster.setDisableCauseDamage(true);
        monster.outOfFreeze(3000);
    }

    @Override
    public void setDefaultActions() {
        addActions("IceBulletLeft", gameScreen.getAction("IceBulletPack:Left"));
        addActions("IceBulletRight", gameScreen.getAction("IceBulletPack:Right"));
        addActions("IceBulletUp", gameScreen.getAction("IceBulletPack:Up"));
        addActions("IceBulletDown", gameScreen.getAction("IceBulletPack:Down"));
    }

    @Override
    public String getName() {
        return "IceBullet";
    }
}
