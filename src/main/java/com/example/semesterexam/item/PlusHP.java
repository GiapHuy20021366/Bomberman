package com.example.semesterexam.item;

import com.example.semesterexam.core.Effect;
import com.example.semesterexam.core.Item;
import com.example.semesterexam.core.ItemsName;
import com.example.semesterexam.effect.EffPlusHP;
import com.example.semesterexam.manage.GameScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class PlusHP extends Item {
    public PlusHP(double x, double y, GameScreen gameScreen) throws IOException {
        super(x, y, gameScreen);
    }

    @Override
    public void setDefault() {
        addActions("Default", gameScreen.getAction("ItemsPack:PlusHP"));
        setActions("Default");
    }

    @Override
    public void effect() {
        Timeline plusHP = new Timeline(new KeyFrame(Duration.millis(300L), ev -> {
            figure.setHP(figure.getHP() + figure.getMaxHP() * 0.03d);
        }));
        plusHP.setCycleCount(10);
        plusHP.play();

        try {
            Effect plus = new EffPlusHP(gameScreen, figure, 3000);
            plus.setAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void disableEffect() {

    }

    @Override
    public ItemsName getItemsName() {
        return ItemsName.PlusHP;
    }
}
