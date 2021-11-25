package com.example.semesterexam.core;

import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.tool.Action;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

public abstract class Character extends Subject {

    protected boolean goUp = false, goDown = false, goRight = false, goLeft = false, stand = false;
    protected BooleanProperty isDisableMoving = new SimpleBooleanProperty(false);
    protected AnimationTimer timer;
    protected double viewValue = 300d;
    protected boolean onAttacking = false;

    protected HashMap<String, Weapon> weapons = new HashMap<>();
    protected HashMap<String, String> attacks = new HashMap<>();
    protected Weapon onWeapon;
    protected StringProperty onAttack = new SimpleStringProperty("Normal");


    public Character(GameScreen gameScreen) throws IOException {
        super(gameScreen);
        HP.set(10000000);
    }

    public void setDefaultLocation(int x, int y) {
        setX(gameScreen.getComponentSize() * x);
        setY(gameScreen.getComponentSize() * y);
    }

    public abstract void addActionMoveNormal();

    public abstract void addActionDieNormal();

    public abstract void addAllActions();

    public abstract void defaultAttack();

    public void startMoving() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isDisableMoving.get()) {
                    move();
                }
            }
        };
        timer.start();
    }

    public abstract void goUp();

    public abstract void goDown();


    public abstract void goRight();

    public abstract void goLeft();

    @Deprecated
    public void setWeapon(String name) {
        onWeapon = weapons.get(name);
    }

    public void addWeapon(Weapon weapon) {
        weapons.put(weapon.getName(), weapon);
    }

    public void setAttacks(String attackName) {
        String weaponName = attacks.get(attackName);

        if (weaponName == null && !attackName.equals("Normal")) {
            onAttack.set("Normal");
            System.out.println("Warning!!! attack Name: " + attackName + " is not be supported for this character, character name: " + this.getName());
            return;
        }

        onWeapon = weapons.get(weaponName);

    }

    public abstract void addAllWeapon();

    public abstract void addAllAttack();

    public String getOnWeapon() {
        String weapon = attacks.get(onAttack.get());
        if (weapon == null) {
            weapon = "Normal";
        }
        return weapon;
    }

    public void setActionGoDirection(Direction direction) {
        String onWeapon = getOnWeapon();
        switch (direction) {
            case UP -> {
                setActions(onWeapon + "GoUp");
            }
            case DOWN -> {
                setActions(onWeapon + "GoDown");
            }
            case LEFT -> {
                setActions(onWeapon + "GoLeft");
            }
            case RIGHT -> {
                setActions(onWeapon + "GoRight");
            }
        }
    }

    public void stand() {
        String onWeapon = getOnWeapon();
        switch (onDirection) {
            case UP -> {
                setActions(onWeapon + "StandUp");
            }
            case DOWN -> {
                setActions(onWeapon + "StandDown");
            }
            case LEFT -> {
                setActions(onWeapon + "StandLeft");
            }
            case RIGHT -> {
                setActions(onWeapon + "StandRight");
            }
        }
    }

    public void attack() {

        if (onAttacking) {
            return;
        }

        if (onWeapon == null) {

            defaultAttack();

            onAttacking = false;
            return;
        }

        if (!onWeapon.hasBullet()) {
//            System.out.println("No bullet!");
            return;
        }

        onAttacking = true;
        stand();
        isDisableMoving.set(true);

        Timeline attack = new Timeline(new KeyFrame(Duration.millis(10), ev -> {

            Action action = getOnAction();

            switch (onDirection) {
                case UP -> {
                    setActions(onAttack.get() + "AttackUp");
                }
                case DOWN -> {
                    setActions(onAttack.get() + "AttackDown");
                }
                case LEFT -> {
                    setActions(onAttack.get() + "AttackLeft");
                }
                case RIGHT -> {
                    setActions(onAttack.get() + "AttackRight");
                }
            }

            Timeline performComplete = new Timeline(new KeyFrame(Duration.millis(onWeapon.cycle * 0.95d), av -> {
                isDisableMoving.set(false);
                setActions(action);
                onAttacking = false;
            }));
            performComplete.play();

            onWeapon.conduct();
        }));
        attack.setCycleCount(1);
        attack.play();


    }


    @Override
    public void getDamage(int damage) {
        super.getDamage(damage);
        System.out.println(HP.get());
    }

    public void move() {

        stand = !isMoving();

        if (goUp) {
            goUp();
        } else if (goDown) {
            goDown();
        } else if (goRight) {
            goRight();
        } else if (goLeft) {
            goLeft();
        } else if (stand) {
            stand();
        }
    }


    protected boolean isOverlapping(double newX, double newY, Direction direction) {
        return gameScreen.getManagement().isOverlapping(this, newX, newY, direction);
    }


    @Override
    public void die() {
        String onWeapon = getOnWeapon();
        switch (onDirection) {
            case UP -> {
                setActions(onWeapon + "DieUp");
            }
            case DOWN -> {
                setActions(onWeapon + "DieDown");
            }
            case LEFT -> {
                setActions(onWeapon + "DieLeft");
            }
            case RIGHT -> {
                setActions(onWeapon + "DieRight");
            }
        }

        isDisableMoving.set(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ev -> {
            this.setVisible(false);
            gameScreen.getManagement().removeCharacter(this);
        }));
        timeline.play();
    }


    public void setIsDisableMoving(boolean isDisableMoving) {
        this.isDisableMoving.set(isDisableMoving);
    }

    public boolean isMoving() {
        return goDown || goUp || goLeft || goRight;
    }

    public double getViewValue() {
        return viewValue;
    }

    @Override
    public int hashCode() {
        return subjectName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        return subjectName.equals(((Subject) obj).subjectName);
    }


    public void setViewValue(double viewValue) {
        this.viewValue = viewValue;
    }

    public String getOnAttack() {
        return onAttack.get();
    }


}
