package com.example.semesterexam.weapon;

import com.example.semesterexam.core.Weapon;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.manage.GameScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class MagicWand extends Weapon {

    public MagicWand(Character character, GameScreen gameScreen) {
        super(character, gameScreen);
        if (!(character instanceof Lighting)) {
            throw new UnsupportedOperationException("MagicWand is not supported for this kind of character, name character: " + character.getName());
        }
    }

    @Override
    public void conduct() {
        Arrow arrow = null;
        try {
            arrow = new Arrow(gameScreen);
            arrow.setDefaultActions();
            arrow.setDefaultActions();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (direction) {
            case "UP" -> {
                assert arrow != null;
                arrow.dy = -2d;
                arrow.widthReality = arrow.widthReality / 3d;
                arrow.setActions("ArrowUp");
                arrow.setX(character.getX() + character.getFitWidth() / 4d);
                arrow.setY(character.getY() - character.getFitHeight() / 2d);
            }
            case "DOWN" -> {
                assert arrow != null;
                arrow.dy = 2d;
                arrow.widthReality = arrow.widthReality / 3d;
                arrow.setActions("ArrowDown");
                arrow.setX(character.getX() + character.getFitWidth() / 4d);
                arrow.setY(character.getY() + character.getFitHeight() / 2d);
            }
            case "LEFT" -> {
                assert arrow != null;
                arrow.dx = -2d;
                arrow.heightReality = arrow.heightReality / 3d;
                arrow.setActions("ArrowLeft");
                arrow.setX(character.getX() - character.getFitWidth() / 2d);
                arrow.setY(character.getY() + character.getFitHeight() / 4d);
            }
            case "RIGHT" -> {
                assert arrow != null;
                arrow.dx = 2d;
                arrow.heightReality = arrow.heightReality / 3d;
                arrow.setActions("ArrowRight");
                arrow.setX(character.getX() + character.getFitWidth() / 2d);
                arrow.setY(character.getY() + character.getFitHeight() / 4d);
            }
        }
        Arrow finalArrow = arrow;
        Timeline appear = new Timeline(new KeyFrame(Duration.millis(cycle / 2), ev -> {
            gameScreen.getMap().getChildren().add(finalArrow);
            finalArrow.fly();
        }));

        appear.play();

    }


    @Override
    public String getAttackName() {
        return "Lighting";
    }

    @Override
    public String getName() {
        return "MagicWand";
    }
}
