package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Bullets;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.core.Weapon;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class Mace extends Weapon {
    public Mace(Character character, GameScreen gameScreen) {
        super(character, gameScreen);
    }

    @Override
    public void setNewBullet() {
        try {
            this.bullet = new MaceBullet(gameScreen, character);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void playOnFlySounds() {

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
        return "Mace";
    }
}
