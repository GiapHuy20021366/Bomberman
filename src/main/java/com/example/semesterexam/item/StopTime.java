package com.example.semesterexam.item;

import com.example.semesterexam.core.Item;
import com.example.semesterexam.core.ItemsName;
import com.example.semesterexam.manage.GameScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class StopTime extends Item {
    private long cycle = 5000;
    private Timeline end;
    private final ItemsName name = ItemsName.StopTime;

    public StopTime(double x, double y, GameScreen gameScreen) throws IOException {
        super(x, y, gameScreen);
    }

    @Override
    public void setDefault() {
        addActions("Default", gameScreen.getAction("ItemsPack:StopTime"));
        setActions("Default");
    }

    @Override
    public void effect() {
        if (effecting) return;

//        figure.removeEffectByItem(name);

        figure.addItems(this);

        effecting = true;
        gameScreen.getManagement().setDisableAllMonster(true, cycle);
        end = new Timeline(new KeyFrame(Duration.millis(cycle), ev -> {
            disableEffect();
        }));
        end.setCycleCount(1);
        end.play();
    }

    @Override
    public void disableEffect() {
        if (!effecting) return;

        if (end != null) end.stop();
        gameScreen.getManagement().setDisableAllMonster(false, cycle);
        effecting = false;
    }

    @Override
    public ItemsName getItemsName() {
        return name;
    }
}
