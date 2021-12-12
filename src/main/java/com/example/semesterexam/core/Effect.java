package com.example.semesterexam.core;

import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.weapon.Fire;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public abstract class Effect extends Subject{
    protected Timeline disappear;
    protected Character character;
    protected long timeExist;

    public Effect(GameScreen gameScreen, Character character, long timeExist) throws IOException {
        super(gameScreen, false);

        this.character = character;
        this.timeExist = timeExist;

        if (timeExist == -1) {
            setAll();
        }
    }

    public void setAll() {
        setDefaultAction();
        bindByCharacter(this.character);
        show();
        makeDisappear(this.timeExist);
    }

    public void makeDisappear(long timeExist) {
        if (disappear != null) disappear.stop();

        if (timeExist < 0) {
            return;
        }

        disappear = new Timeline(new KeyFrame(Duration.millis(timeExist), ev -> {
            HP.set(-1);
        }));

        disappear.setCycleCount(1);
        disappear.play();
    }

    public void bindByCharacter(Character character) {
        this.xProperty().bind(character.xProperty());
        this.yProperty().bind(character.yProperty().subtract(character.fitHeightProperty().divide(4)));
        this.fitWidthProperty().bind(character.fitWidthProperty());
        this.fitHeightProperty().bind(character.fitHeightProperty().multiply(1.25d));
    }

    public void unBind() {
        this.xProperty().unbind();
        this.yProperty().unbind();
        this.fitWidthProperty().unbind();
        this.fitHeightProperty().unbind();
    }

    public void show() {
        gameScreen.getMap().getChildren().add(this);
    }

    public abstract void setDefaultAction();

    @Override
    public void die() {
        if (disappear != null) disappear.stop();

        unBind();
        setVisible(false);
        gameScreen.getMap().getChildren().remove(this);
    }

}
