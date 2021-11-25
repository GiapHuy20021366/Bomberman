package com.example.semesterexam.item;

import com.example.semesterexam.core.Item;
import com.example.semesterexam.core.ItemsName;
import com.example.semesterexam.manage.GameScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class SpeedUp extends Item {
    private long cycle = 5000;
    private Timeline end;
    private ItemsName name = ItemsName.SpeedUp;
    public SpeedUp(double x, double y, GameScreen gameScreen) throws IOException {
        super(x, y, gameScreen);
    }

    @Override
    public void setDefault() {
        addActions("Default", gameScreen.getAction("ItemsPack:SpeedUp"));
        setActions("Default");
    }

    @Override
    public void effect() {
        figure.removeEffectByItem(name);

        figure.addItems(this);

        effecting = true;
        figure.setSpeedUp(figure.getSpeedUp()*1.5d);
        end = new Timeline(new KeyFrame(Duration.millis(timeOut), ev -> {
           disableEffect();
        }));
        end.setCycleCount(1);
        end.play();
    }

    @Override
    public void disableEffect() {
        if (!effecting) return;

        figure.setSpeedUp(figure.getSpeedUp() / 1.5d);
        effecting = false;
    }

    @Override
    public ItemsName getItemsName() {
        return name;
    }
}
