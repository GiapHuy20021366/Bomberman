package com.example.semesterexam.core;

import com.example.semesterexam.effect.IconBeSeen;
import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.monster.Orc;
import com.example.semesterexam.tool.Player;
import com.example.semesterexam.weapon.Boom;
import com.example.semesterexam.weapon.Fire;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

public abstract class Figure extends Character {

    protected HashMap<ItemsName, Item> items = new HashMap<>();
    private final BooleanProperty beSeen = new SimpleBooleanProperty(false);

    private Timeline outSaw;

    public void countdownForNotBeSeen(long cycle) {
        if (outSaw != null) outSaw.stop();
        outSaw = new Timeline(new KeyFrame(Duration.millis(cycle), ev -> {
            beSeen.set(false);
        }));
        outSaw.play();
    }

    @Override
    public void doAfterCompleteAttack() {
        // DO nothing
    }

    @Override
    public void addIconSkills() {

    }

    @Override
    public void countdownPutBoom() {
        if (countdownPutBoom != null) countdownPutBoom.stop();

        countdownPutBoom = new Timeline(new KeyFrame(Duration.millis(cyclePutBoom.get()), ev -> {
            hasPutBoom = false;
        }));

        countdownPutBoom.setCycleCount(1);
        countdownPutBoom.play();

    }

    public void removeEffectByItem(ItemsName name) {
        Item item = items.get(name);
        if (item != null) {
            item.removeEffect();
        }
    }

    public void addItems(Item item) {
        items.put(item.getItemsName(), item);
    }

    public void plusBullet(String weapon, Bullets bullet, int count) {
        Weapon weaponPlus = weapons.get(weapon);
        if (weaponPlus == null) return;
        weaponPlus.plusBullet(bullet, count);
    }


    public Figure(GameScreen gameScreen) throws IOException {
        super(gameScreen);

        Effect eff = new IconBeSeen(gameScreen, this, -1);
        eff.setVisible(false);
        iconSkill.put("BeSeen", eff);

        onAttack.addListener((observableValue, oldValue, newValue) -> setAttacks(newValue));

        cyclePutBoom.set(1200);

        beSeen.addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                countdownForNotBeSeen(3000);
                Effect e = iconSkill.get("BeSeen");
                if (e != null) {
                    e.setVisible(true);
                    e.toFront();
                }
            } else {
                Effect e = iconSkill.get("BeSeen");
                if (e != null) {
                    e.setVisible(false);
                }
            }
        });


    }

    public boolean isSeen() {
        return this.beSeen.get();
    }

    public void setBeSeen() {
        beSeen.set(true);
    }

    @Override
    public void defaultAttack() {
        try {
            putBoom();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goUp() {
        double d = defaultSpeed.get() * speedUp.get();

        Subject s = gameScreen.getManagement().getOverlapping(this, getX(), getY() - d, Direction.UP);

        if (s == null) {
            setActionGoDirection(Direction.UP);
            setY(getY() - d);
            return;
        }

        if (!(s instanceof Wall wall)) {
            setActionGoDirection(Direction.UP);
            return;
        }

        // s is Wall
        Point2D r = gameScreen.getManagement().getNearestWall(wall, Direction.RIGHT, Direction.UP);
        Point2D l = gameScreen.getManagement().getNearestWall(wall, Direction.LEFT, Direction.UP);

        if (r != null && l == null) goRight();
        else if (r == null && l != null) goLeft();
        else if (r != null) {
            if (Math.abs(r.getX() * gameScreen.getComponentSize() - this.getX())
                    < Math.abs(this.getX() - l.getX() * gameScreen.getComponentSize())) goRight();
            else goLeft();
        } else if (!isOverlapping(getX() - d, getY(), Direction.LEFT)) goLeft();


    }

    @Override
    public void goDown() {
        double d = defaultSpeed.get() * speedUp.get();

        Subject s = gameScreen.getManagement().getOverlapping(this, getX(), getY() + d, Direction.DOWN);

        if (s == null) {
            setActionGoDirection(Direction.DOWN);
            setY(getY() + d);
            return;
        }

        if (!(s instanceof Wall wall)) {
            setActionGoDirection(Direction.DOWN);
            return;
        }

        // s is Wall
        Point2D r = gameScreen.getManagement().getNearestWall(wall, Direction.RIGHT, Direction.DOWN);
        Point2D l = gameScreen.getManagement().getNearestWall(wall, Direction.LEFT, Direction.DOWN);

        if (r != null && l == null) goRight();
        else if (r == null && l != null) goLeft();
        else if (r != null) {
            if (Math.abs(r.getX() * gameScreen.getComponentSize() - this.getX())
                    < Math.abs(this.getX() - l.getX() * gameScreen.getComponentSize())) goRight();
            else goLeft();
        } else if (!isOverlapping(getX() + d, getY(), Direction.RIGHT)) goRight();


    }


    @Override
    public void goRight() {
        double d = defaultSpeed.get() * speedUp.get();

        Subject s = gameScreen.getManagement().getOverlapping(this, getX() + d, getY(), Direction.RIGHT);

        if (s == null) {
            setActionGoDirection(Direction.RIGHT);
            setX(getX() + d);
            return;
        }

        if (!(s instanceof Wall wall)) {
            setActionGoDirection(Direction.RIGHT);
            return;
        }

        // s is Wall
        Point2D down = gameScreen.getManagement().getNearestWall(wall, Direction.DOWN, Direction.RIGHT);
        Point2D up = gameScreen.getManagement().getNearestWall(wall, Direction.UP, Direction.RIGHT);

        if (down != null && up == null) goDown();
        else if (down == null && up != null) goUp();
        else if (down != null) {
            if (Math.abs(down.getY() * gameScreen.getComponentSize() - this.getY())
                    < Math.abs(this.getY() - up.getY() * gameScreen.getComponentSize())) goDown();
            else goUp();
        } else if (!isOverlapping(getX(), getY() - d, Direction.UP)) goUp();


    }

    @Override
    public void goLeft() {
        double d = defaultSpeed.get() * speedUp.get();

        Subject s = gameScreen.getManagement().getOverlapping(this, getX() - d, getY(), Direction.LEFT);

        if (s == null) {
            setActionGoDirection(Direction.LEFT);
            setX(getX() - d);
            return;
        }

        if (!(s instanceof Wall wall)) {
            setActionGoDirection(Direction.LEFT);
            return;
        }

        // s is Wall
        Point2D down = gameScreen.getManagement().getNearestWall(wall, Direction.DOWN, Direction.LEFT);
        Point2D up = gameScreen.getManagement().getNearestWall(wall, Direction.UP, Direction.LEFT);

        if (down != null && up == null) goDown();
        else if (down == null && up != null) goUp();
        else if (down != null) {
            if (Math.abs(down.getY() * gameScreen.getComponentSize() - this.getY())
                    < Math.abs(this.getY() - up.getY() * gameScreen.getComponentSize())) goDown();
            else goUp();
        } else if (!isOverlapping(getX(), getY() + d, Direction.DOWN)) goDown();

    }


    public EventHandler<KeyEvent> getKeyPressedEvent() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W, UP -> goUp = true;
                    case S, DOWN -> goDown = true;
                    case A, LEFT -> goLeft = true;
                    case D, RIGHT -> goRight = true;
                    case B, SPACE -> attack();
                    case L -> gameScreen.setVision(0.95d);
                    case P -> gameScreen.setVision(1.05d);
                    case O -> gameScreen.sacking();
                    case M -> gameScreen.getViewPlayer().moveViewport();
                    case NUMPAD1 -> onAttack.set("Normal");
                    case NUMPAD2 -> onAttack.set("Archery");
                    case NUMPAD3 -> onAttack.set("RedSlash");
                    case NUMPAD4 -> onAttack.set("BlueSlash");
                }
            }
        };
    }

    public EventHandler<KeyEvent> getKeyKeyReleasedEvent() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W, UP -> goUp = false;
                    case S, DOWN -> goDown = false;
                    case A, LEFT -> goLeft = false;
                    case D, RIGHT -> goRight = false;
                }
            }
        };
    }

    public void putBoom() throws IOException {
        if (hasPutBoom) return;
        hasPutBoom = true;
        Boom boom = new Boom(this, gameScreen);
        boom.setDamage(baseDamage.get());
        boom.setAllRange(2.5d);
        boom.setActions("QuaBoom");
        gameScreen.getMap().getChildren().add(boom);
        gameScreen.getManagement().addBoom(boom);
        boom.countdown(1500);
        toFront();
        countdownPutBoom();
    }


    public void setViewValue(double viewValue) {
        this.viewValue = viewValue;
    }


    public void setStand() {
        goUp = false;
        goDown = false;
        goLeft = false;
        goRight = false;
    }

    public void setGo(Direction direction, boolean b) {

        switch (direction) {
            case UP -> {
                goUp = b;
            }
            case DOWN -> {
                goDown = b;
            }
            case LEFT -> {
                goLeft = b;
            }
            case RIGHT -> {
                goRight = b;
            }
        }
    }

    public StringProperty getAttack() {
        return onAttack;
    }

    public void showPropertyInNewMap() {
        bloodBar.resetMap();
        for (String eff : iconSkill.keySet()) {
            iconSkill.get(eff).show();
        }
    }

    @Override
    public void die() {
        onAttacking = true;
        isDisableMoving.set(true);

        if (bloodBar != null) {
            bloodBar.setVisible(false);
            bloodBar.removeBloodBar();
            showIconSkill();
        }

        if (timer != null) {
            timer.stop();
        }
        if (attack != null) {
            attack.stop();
        }
        if (performComplete != null) {
            performComplete.stop();
        }
        if (countdownPutBoom != null) {
            countdownPutBoom.stop();
        }
        onAttacking = true;
        isDisableMoving.set(true);
        gameScreen.getManagement().removeFigureOutOfManage(this);

        for (String eff : iconSkill.keySet()) {
            iconSkill.get(eff).setVisible(false);
        }
        showBloodBar.set(false);

//        Player player = new Player(gameScreen, "FigureDie", Player.VOLUME_PLAYER);
//        player.play();
        playSound("FigureDie");
        super.die();
    }

    @Override
    public void addSounds() {
        sounds.put("Attack", new Player(gameScreen, "Attack", Player.VOLUME_FIGURE_ATTACK));
        sounds.put("BoomPow", new Player(gameScreen, "BoomPow", Player.VOLUME_FIGURE_WEAPON));
        sounds.put("EatItem", new Player(gameScreen, "EatItem", Player.VOLUME_PLAYER));
        sounds.put("FigureDie", new Player(gameScreen, "FigureDie", Player.VOLUME_PLAYER));
    }

    public abstract void setAttack(int i);
}
