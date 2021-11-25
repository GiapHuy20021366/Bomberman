package com.example.semesterexam.core;

import com.example.semesterexam.manage.GameScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Objects;

public abstract class Weapon {
    protected String name = "";
    protected Character character;
    protected GameScreen gameScreen;
    public long cycle = 700;
    protected Direction onDirection;
    protected Bullet bullet;

    public Weapon(Character character, GameScreen gameScreen) {
        this.character = character;
        this.gameScreen = gameScreen;
        this.onDirection = character.getOnDirection();
    }

    public abstract void setNewBullet();

    public abstract boolean hasBullet();

    public abstract void plusBullet(Bullets bullet, int count);

    public void conduct() {


        setNewBullet();

        bullet.setDefaultActions();

        switch (character.getOnDirection()) {
            case UP -> {
                assert bullet != null;
                bullet.dy = -2d;
//                bullet.widthReality = bullet.widthReality / 3d;
                bullet.setActions(bullet.getName() + "Up");
                bullet.setX(character.getX());
                bullet.setY(character.getY() - character.getFitHeight() / 2d);
            }
            case DOWN -> {
                assert bullet != null;
                bullet.dy = 2d;
//                bullet.widthReality = bullet.widthReality / 3d;
                bullet.setActions(bullet.getName() + "Down");
                bullet.setX(character.getX());
                bullet.setY(character.getY() + character.getFitHeight() / 2d);
            }
            case LEFT -> {
                assert bullet != null;
                bullet.dx = -2d;
//                bullet.heightReality = bullet.heightReality / 3d;
                bullet.setActions(bullet.getName() + "Left");
                bullet.setX(character.getX() - character.getFitWidth() / 2d);
                bullet.setY(character.getY());
            }
            case RIGHT -> {
                assert bullet != null;
                bullet.dx = 2d;
//                bullet.heightReality = bullet.heightReality / 3d;
                bullet.setActions(bullet.getName() + "Right");
                bullet.setX(character.getX() + character.getFitWidth() / 2d);
                bullet.setY(character.getY());
            }
        }
        bullet.setRealitySize();

        Bullet finalArrow = bullet;
        Timeline appear = new Timeline(new KeyFrame(Duration.millis(cycle / 2), ev -> {
            gameScreen.getMap().getChildren().add(finalArrow);
            finalArrow.fly();
        }));

        appear.play();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weapon weapon = (Weapon) o;
        return Objects.equals(name, weapon.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public abstract String getName();

}
