package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Bullets;
import com.example.semesterexam.core.Monster;
import com.example.semesterexam.core.Weapon;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.figure.Magician;
import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.monster.Winged;

import java.io.IOException;

public class MagicWand extends Weapon {

    public MagicWand(Character character, GameScreen gameScreen) {
        super(character, gameScreen);
    }

    @Override
    public void setNewBullet() {
        if (character.getOnAttack().equals("Normal") && character instanceof Monster) {
            try {
                this.bullet = new SimpleFireGoal(gameScreen, character);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (character.getOnAttack().equals("BuffHP") && character instanceof Magician) {
            try {
                this.bullet = new ReduceHP(gameScreen, character);
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
    public void playOnFlySounds() {
        character.playSound("Fire");
    }


    @Override
    public String getName() {
        return "MagicWand";
    }
}
