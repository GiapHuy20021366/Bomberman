package com.example.semesterexam.weapon;


import com.example.semesterexam.core.Bullets;
import com.example.semesterexam.core.Weapon;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class Bow extends Weapon {

    private int arrowCount = 5;


    public Bow(Character character, GameScreen gameScreen) {
        super(character, gameScreen);
        if (!(character instanceof Archery)) {
            throw new UnsupportedOperationException("Bow is not supported for this kind of character, name character: " + character.getName());
        }
    }

    @Override
    public void setNewBullet() {
        try {
            this.bullet = new Arrow(gameScreen, character);
            arrowCount--;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasBullet() {
        if ("Archery".equals(character.getOnAttack())) {
            return arrowCount != 0;
        }
        return false;
    }

    @Override
    public void plusBullet(Bullets bullet, int count) {
        if (bullet == Bullets.Arrow) {
            arrowCount += count;
        }
    }


    @Override
    public String getName() {
        return "Bow";
    }
}
