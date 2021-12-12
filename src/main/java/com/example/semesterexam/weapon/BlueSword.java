package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Bullets;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.core.Weapon;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class BlueSword extends Weapon {
    private int iceBulletCount = 15;

    public BlueSword(Character character, GameScreen gameScreen) {
        super(character, gameScreen);
    }

    @Override
    public void setNewBullet() {
        try {
            this.bullet = new IceBullet(gameScreen, character);
            iceBulletCount--;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasBullet() {
        if ("BlueSlash".equals(character.getOnAttack())) {
            return iceBulletCount != 0;
        }
        return false;
    }

    @Override
    public void plusBullet(Bullets bullet, int count) {
        if (bullet == Bullets.IceBullet) {
            iceBulletCount += count;
        }
    }

    @Override
    public void playOnFlySounds() {
        character.playSound("Sword");
    }

    @Override
    public String getName() {
        return "BlueSword";
    }
}
