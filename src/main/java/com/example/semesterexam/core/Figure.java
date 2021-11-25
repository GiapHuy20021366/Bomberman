package com.example.semesterexam.core;

import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.monster.Orc;
import com.example.semesterexam.weapon.Boom;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.HashMap;

public abstract class Figure extends Character {

    protected HashMap<ItemsName, Item> items = new HashMap<>();

    public void removeEffectByItem(ItemsName name) {
        Item item = items.get(name);
        if (item != null) {
            item.disableEffect();
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
        onAttack.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                setAttacks(newValue);
            }
        });
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

        Wall wall = (Wall) gameScreen.getManagement().getOverlapping(this, getX(), getY() - d, Direction.UP);
        if (wall != null) {
            Point2D r = gameScreen.getManagement().getNearestWall(wall, Direction.RIGHT, Direction.UP);
            Point2D l = gameScreen.getManagement().getNearestWall(wall, Direction.LEFT, Direction.UP);

            if (r != null && l == null) goRight();
            else if (r == null && l != null) goLeft();
            else if (r != null) {
                if (Math.abs(r.getX() * gameScreen.getComponentSize() - this.getX())
                        < Math.abs(this.getX() - l.getX() * gameScreen.getComponentSize())) goRight();
                else goLeft();
            } else if (!isOverlapping(getX() - d, getY(), Direction.LEFT)) goLeft();

        } else {
            setActionGoDirection(Direction.UP);
            setY(getY() - d);
        }

    }

    @Override
    public void goDown() {
        double d = defaultSpeed.get() * speedUp.get();
        Wall wall = (Wall) gameScreen.getManagement().getOverlapping(this, getX(), getY() + d, Direction.DOWN);
        if (wall != null) {
            Point2D r = gameScreen.getManagement().getNearestWall(wall, Direction.RIGHT, Direction.DOWN);
            Point2D l = gameScreen.getManagement().getNearestWall(wall, Direction.LEFT, Direction.DOWN);

            if (r != null && l == null) goRight();
            else if (r == null && l != null) goLeft();
            else if (r != null) {
                if (Math.abs(r.getX() * gameScreen.getComponentSize() - this.getX())
                        < Math.abs(this.getX() - l.getX() * gameScreen.getComponentSize())) goRight();
                else goLeft();
            } else if (!isOverlapping(getX() + d, getY(), Direction.RIGHT)) goRight();


        } else {
            setActionGoDirection(Direction.DOWN);
            setY(getY() + d);
        }

    }


    @Override
    public void goRight() {
        double d = defaultSpeed.get() * speedUp.get();

        Wall wall = (Wall) gameScreen.getManagement().getOverlapping(this, getX() + d, getY(), Direction.RIGHT);
        if (wall != null) {
            Point2D down = gameScreen.getManagement().getNearestWall(wall, Direction.DOWN, Direction.RIGHT);
            Point2D up = gameScreen.getManagement().getNearestWall(wall, Direction.UP, Direction.RIGHT);

            if (down != null && up == null) goDown();
            else if (down == null && up != null) goUp();
            else if (down != null) {
                if (Math.abs(down.getY() * gameScreen.getComponentSize() - this.getY())
                        < Math.abs(this.getY() - up.getY() * gameScreen.getComponentSize())) goDown();
                else goUp();
            } else if (!isOverlapping(getX(), getY() - d, Direction.UP)) goUp();

        } else {
            setActionGoDirection(Direction.RIGHT);
            setX(getX() + d);
        }

    }

    @Override
    public void goLeft() {
        double d = defaultSpeed.get() * speedUp.get();

        Wall wall = (Wall) gameScreen.getManagement().getOverlapping(this, getX() - d, getY(), Direction.LEFT);
        if (wall != null) {
            Point2D down = gameScreen.getManagement().getNearestWall(wall, Direction.DOWN, Direction.LEFT);
            Point2D up = gameScreen.getManagement().getNearestWall(wall, Direction.UP, Direction.LEFT);

            if (down != null && up == null) goDown();
            else if (down == null && up != null) goUp();
            else if (down != null) {
                if (Math.abs(down.getY() * gameScreen.getComponentSize() - this.getY())
                        < Math.abs(this.getY() - up.getY() * gameScreen.getComponentSize())) goDown();
                else goUp();
            } else if (!isOverlapping(getX(), getY() + d, Direction.DOWN)) goDown();

        } else {
            setActionGoDirection(Direction.LEFT);
            setX(getX() - d);
        }

    }


    public EventHandler<KeyEvent> getKeyPressedEvent() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                    case UP:
                        goUp = true;
                        break;
                    case S:
                    case DOWN:
                        goDown = true;
                        break;
                    case A:
                    case LEFT:
                        goLeft = true;
                        break;
                    case D:
                    case RIGHT:
                        goRight = true;
                        break;
                    case SHIFT:
//                        speedUp.set(speedUp.get() * 2d);
                        break;
                    case B:
                    case SPACE:
                        //                            putBoom();
                        attack();
                        break;
                    case L:
                        gameScreen.setVision(0.95d);
                        break;
                    case P:
                        gameScreen.setVision(1.05d);
                        break;
                    case O:
                        gameScreen.sacking();
                        break;
                    case M:
                        gameScreen.getViewPlayer().moveViewport();
                        break;
                    case NUMPAD1:
                        onAttack.set("Normal");
                        break;
                    case NUMPAD2:
                        onAttack.set("Archery");
                        break;
                    case NUMPAD3:
                        onAttack.set("RedSlash");
                        break;
                    case NUMPAD4:
                        onAttack.set("BlueSlash");
                        break;


                    // Using for test:
                    case NUMPAD5:
                        try {
                            putBoom2();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
    }

    public EventHandler<KeyEvent> getKeyKeyReleasedEvent() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                    case UP:
                        goUp = false;
                        break;
                    case S:
                    case DOWN:
                        goDown = false;
                        break;
                    case A:
                    case LEFT:
                        goLeft = false;
                        break;
                    case D:
                    case RIGHT:
                        goRight = false;
                        break;
                    case SHIFT:
//                        speedUp.set(speedUp.get() / 2d);
                        break;
                }
            }
        };
    }

    public void putBoom() throws IOException {
        Boom boom = new Boom(getX(), getY(), gameScreen);
        boom.setDamage(100000);
        boom.setRange(3d);
        boom.setActions("QuaBoom");
        gameScreen.getMap().getChildren().add(boom);
        gameScreen.getManagement().addBoom(boom);
        boom.countdown(1500);
        toFront();
    }




    public void setViewValue(double viewValue) {
        this.viewValue = viewValue;
    }


    // Using for test
    public void putBoom2() throws IOException {
        Boom boom = new Boom(getX(), getY(), gameScreen);
        boom.setDamage(100000);
        boom.setRange(5d);
        boom.setActions("QuaBoom");
        gameScreen.getMap().getChildren().add(boom);
        gameScreen.getManagement().addBoom(boom);
        toFront();
    }

    public static int i = 0;

    public void putMonster() throws IOException {
        Monster monster1 = new Orc(gameScreen);
        monster1.setName("" + (i++));
        monster1.setDefaultSize(1d);
        monster1.setX(getX());
        monster1.setY(getY());
        monster1.addActionMoveNormal();
        monster1.setDefaultDirection(Direction.RIGHT);
        monster1.startCauseDamage();
        monster1.startMoving();
        gameScreen.getManagement().addMonster(monster1);
        gameScreen.getMap().getChildren().add(monster1);
    }

}
