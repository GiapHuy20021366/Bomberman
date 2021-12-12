package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Bullet;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class MaceBullet extends Bullet {
    public MaceBullet(GameScreen gameScreen, Character owner) throws IOException {
        super(gameScreen, owner);
    }

    @Override
    public void setRealitySize() {
        heightReality = getFitHeight() * 0.18d;
        widthReality = getFitWidth() * 0.18d;
    }

    @Override
    public void effect() {
        if (subject != null) {
            subject.setJustDamage(owner);
            subject.getDamage(owner.getBaseDamage() * owner.getIncreaseDamage());
        }
    }

    @Override
    public void setDefaultActions() {
        addActions("MaceBulletLeft", gameScreen.getAction("MaceBulletPack:Left"));
        addActions("MaceBulletRight", gameScreen.getAction("MaceBulletPack:Right"));
        addActions("MaceBulletUp", gameScreen.getAction("MaceBulletPack:Up"));
        addActions("MaceBulletDown", gameScreen.getAction("MaceBulletPack:Down"));
    }

    @Override
    public String getName() {
        return "MaceBullet";
    }
}
