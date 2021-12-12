package com.example.semesterexam.item;

import com.example.semesterexam.core.Effect;
import com.example.semesterexam.core.Item;
import com.example.semesterexam.core.ItemsName;
import com.example.semesterexam.effect.EffImmortal;
import com.example.semesterexam.manage.GameScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class Immortal extends Item {
    private long cycle = 10000;
    private Timeline end;
    private final ItemsName name = ItemsName.Immortal;

    public Immortal(double x, double y, GameScreen gameScreen) throws IOException {
        super(x, y, gameScreen);
    }

    @Override
    public void setDefault() {
        addActions("Default", gameScreen.getAction("ItemsPack:Immortal"));
        setActions("Default");
    }

    @Override
    public void effect() {
        figure.removeEffectByItem(name);

        figure.addItems(this);
        try {
            effect = new EffImmortal(gameScreen, figure, cycle);
            effect.setAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        effecting = true;
        figure.setReduceDamage(1d);
        if (end != null) end.stop();
        end = new Timeline(new KeyFrame(Duration.millis(cycle), ev -> {
            disableEffect();
        }));
        end.setCycleCount(1);
        end.play();
    }

    @Override
    public void disableEffect() {
        if (!effecting) return;

        figure.setReduceDamage(0d);
        effecting = false;
    }

    @Override
    public ItemsName getItemsName() {
        return name;
    }
}
