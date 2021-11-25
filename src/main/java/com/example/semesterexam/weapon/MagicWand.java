package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Bullets;
import com.example.semesterexam.core.Weapon;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class MagicWand extends Weapon {

    public MagicWand(Character character, GameScreen gameScreen) {
        super(character, gameScreen);
//        if (!(character instanceof Lighting)) {
//            throw new UnsupportedOperationException("MagicWand is not supported for this kind of character, name character: " + character.getName());
//        }
    }

    @Override
    public void setNewBullet() {
        if (character.getOnAttack().equals("Normal")) {
            try {
                this.bullet = new SimpleFireGoal(gameScreen, character);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean hasBullet() {
        return true;
    }

    @Override
    public void plusBullet(Bullets bullet, int count) {

    }


    @Override
    public String getName() {
        return "MagicWand";
    }
}
