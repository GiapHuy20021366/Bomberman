package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Bullets;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.core.Weapon;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class RedSword extends Weapon {
    private int fireBulletCount = 5;

    public RedSword(Character character, GameScreen gameScreen) {
        super(character, gameScreen);
    }

    @Override
    public void setNewBullet() {
        try {
            this.bullet = new FireBullet(gameScreen, character);
            fireBulletCount--;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasBullet() {
        if ("RedSlash".equals(character.getOnAttack())) {
            return fireBulletCount != 0;
        }
        return false;
    }

    @Override
    public void plusBullet(Bullets bullet, int count) {
        if (bullet == Bullets.FireBullet) {
            fireBulletCount += count;
        }
    }

    @Override
    public String getName() {
        return "RedSword";
    }
}
