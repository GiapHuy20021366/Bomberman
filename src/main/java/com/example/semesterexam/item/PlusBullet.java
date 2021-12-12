package com.example.semesterexam.item;

import com.example.semesterexam.core.Bullets;
import com.example.semesterexam.core.Item;
import com.example.semesterexam.core.ItemsName;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class PlusBullet extends Item {
    public PlusBullet(double x, double y, GameScreen gameScreen) throws IOException {
        super(x, y, gameScreen);
    }

    @Override
    public void setDefault() {
        addActions("Default", gameScreen.getAction("ItemsPack:PlusBullet"));
        setActions("Default");
    }

    @Override
    public void effect() {
        figure.plusBullet("Bow", Bullets.Arrow, 5);
        figure.plusBullet("RedSword", Bullets.FireBullet, 5);
        figure.plusBullet("BlueSword", Bullets.IceBullet, 5);
    }

    @Override
    public void disableEffect() {

    }

    @Override
    public ItemsName getItemsName() {
        return ItemsName.PlusBullet;
    }
}
