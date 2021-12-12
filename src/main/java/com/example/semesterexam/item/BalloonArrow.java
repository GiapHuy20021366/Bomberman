package com.example.semesterexam.item;

import com.example.semesterexam.core.Bullets;
import com.example.semesterexam.core.Item;
import com.example.semesterexam.core.ItemsName;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class BalloonArrow extends Item {
    private ItemsName name = ItemsName.BalloonArrow;
    public BalloonArrow(double x, double y, GameScreen gameScreen) throws IOException {
        super(x, y, gameScreen);
    }

    @Override
    public void setDefault() {
        addActions("Default", gameScreen.getAction("ItemsPack:BalloonArrow"));
        setActions("Default", 500, -1);
    }

    @Override
    public void effect() {
        figure.plusBullet("Bow", Bullets.Arrow, 5);
    }

    @Override
    public void disableEffect() {

    }

    @Override
    public ItemsName getItemsName() {
        return name;
    }
}
