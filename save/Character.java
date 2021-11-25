package com.example.semesterexam;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

public  class Character extends Subject {

    protected boolean goUp = false, goDown = false, goRight = false, goLeft = false, stand = false;
    protected BooleanProperty isDisableMoving = new SimpleBooleanProperty(false);
    protected AnimationTimer timer;
    protected double viewValue = 300d;

    public Character(GameScreen gameScreen) throws IOException {
        super(gameScreen);
        HP.set(10000000);
    }


    public void setDefaultLocation(int x, int y) {
        setX(gameScreen.getComponentSize() * x);
        setY(gameScreen.getComponentSize() * y);
    }

    public void setActionMove(String folder) throws IOException {

        addActions("GoDown", gameScreen.getAction("HuManPack:GoDown"));
        addActions("GoUp", gameScreen.getAction("HuManPack:GoUp"));
        addActions("GoLeft", gameScreen.getAction("HuManPack:GoLeft"));
        addActions("GoRight", gameScreen.getAction("HuManPack:GoRight"));
        addActions("StandUp", gameScreen.getAction("HuManPack:StandUp"));
        addActions("StandDown", gameScreen.getAction("HuManPack:StandDown"));
        addActions("StandLeft", gameScreen.getAction("HuManPack:StandLeft"));
        addActions("StandRight", gameScreen.getAction("HuManPack:StandRight"));
        addActions("DieUp", gameScreen.getAction("HuManPack:DieUp"));
        addActions("DieRight", gameScreen.getAction("HuManPack:DieRight"));
        addActions("DieLeft", gameScreen.getAction("HuManPack:DieLeft"));
        addActions("DieDown", gameScreen.getAction("HuManPack:DieDown"));
        addActions("ArcheryRight", gameScreen.getAction("HuManPack:ArcheryRight"));
        addActions("ArcheryLeft", gameScreen.getAction("HuManPack:ArcheryLeft"));
        addActions("ArcheryUp", gameScreen.getAction("HuManPack:ArcheryUp"));
        addActions("ArcheryDown", gameScreen.getAction("HuManPack:ArcheryDown"));


        // Call default Action
        setActions("StandRight");


    }

    @Override
    public void die(Monster dieBy) {

        if (actions.get("GoRight").equals(getOnAction()) || actions.get("StandRight").equals(getOnAction()))
            setActions("DieRight");
        else if (actions.get("GoLeft").equals(getOnAction()) || actions.get("StandLeft").equals(getOnAction()))
            setActions("DieLeft");
        else if (actions.get("GoUp").equals(getOnAction()) || actions.get("StandUp").equals(getOnAction()))
            setActions("DieUp");
        else if (actions.get("GoDown").equals(getOnAction()) || actions.get("StandDown").equals(getOnAction()))
            setActions("DieDown");

        isDisableMoving.set(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ev -> {
            this.setVisible(false);
//            gameScreen.getViewPlayer().moveViewportBy(dieBy);

        }));
        timeline.play();

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
                        speedUp.set(2.0);
                        break;
                    case B:
                    case SPACE:
                        try {
//                            putBoom();
                            attack();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
                        onWeapon = null;
                        break;
                    case NUMPAD2:
                        setWeapon("Archery");
                        break;


                    // Using for test:
                    case NUMPAD5:
                        try {
                            putBoom2();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case NUMPAD4:
                        try {
                            putMonster();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
//                    case N:
//                        gameScreen.getViewPlayer().setZoom(1.1d);
//                        break;
//                    case B:
//                        gameScreen.getViewPlayer().setZoom(0.9d);
//                        break;
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
                        speedUp.set(1.0);
                        break;
                }
            }
        };
    }

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

    public void goUp() {
        double d = defaultSpeed.get() * speedUp.get();
//        Wall wall = (Wall) overlapping(getX(), getY() - d);
        Wall wall = (Wall) gameScreen.getManagement().getOverlapping(this, getX(), getY() - d, "UP");
        if (wall != null) {
            Point2D r = gameScreen.getManagement().getNearestWall(wall, "RIGHT", "UP");
            Point2D l = gameScreen.getManagement().getNearestWall(wall, "LEFT", "UP");

            if (r != null && l == null) goRight();
            else if (r == null && l != null) goLeft();
            else if (r != null && l != null) {
                if (Math.abs(r.getX() * gameScreen.getComponentSize() - this.getX())
                        < Math.abs(this.getX() - l.getX() * gameScreen.getComponentSize())) goRight();
                else goLeft();
            } else if (!isOverlapping(getX() - d, getY(), "LEFT")) goLeft();

        } else {
            if (!actions.get("GoUp").equals(getOnAction())) setActions("GoUp");
            setY(getY() - d);
        }

    }

    public void goDown() {
        double d = defaultSpeed.get() * speedUp.get();
//        Wall wall = (Wall) overlapping(getX(), getY() + d);
        Wall wall = (Wall) gameScreen.getManagement().getOverlapping(this, getX(), getY() + d, "DOWN");
        if (wall != null) {
            Point2D r = gameScreen.getManagement().getNearestWall(wall, "RIGHT", "DOWN");
            Point2D l = gameScreen.getManagement().getNearestWall(wall, "LEFT", "DOWN");

            if (r != null && l == null) goRight();
            else if (r == null && l != null) goLeft();
            else if (r != null && l != null) {
                if (Math.abs(r.getX() * gameScreen.getComponentSize() - this.getX())
                        < Math.abs(this.getX() - l.getX() * gameScreen.getComponentSize())) goRight();
                else goLeft();
            } else if (!isOverlapping(getX() + d, getY(), "RIGHT")) goRight();


        } else {
            if (!actions.get("GoDown").equals(getOnAction())) setActions("GoDown");
            setY(getY() + d);
        }

    }


    public void goRight() {
        double d = defaultSpeed.get() * speedUp.get();

        Wall wall = (Wall) gameScreen.getManagement().getOverlapping(this, getX() + d, getY(), "RIGHT");
        if (wall != null) {
            Point2D down = gameScreen.getManagement().getNearestWall(wall, "DOWN", "RIGHT");
            Point2D up = gameScreen.getManagement().getNearestWall(wall, "UP", "RIGHT");

            if (down != null && up == null) goDown();
            else if (down == null && up != null) goUp();
            else if (down != null && up != null) {
                if (Math.abs(down.getY() * gameScreen.getComponentSize() - this.getY())
                        < Math.abs(this.getY() - up.getY() * gameScreen.getComponentSize())) goDown();
                else goUp();
            } else if (!isOverlapping(getX(), getY() - d, "UP")) goUp();

        } else {
            if (!actions.get("GoRight").equals(getOnAction())) setActions("GoRight");
//            if (!actions.get("ArcheryRight").equals(getOnAction())) setActions("ArcheryRight");
            setX(getX() + d);
        }

    }

    public void goLeft() {
        double d = defaultSpeed.get() * speedUp.get();

        Wall wall = (Wall) gameScreen.getManagement().getOverlapping(this, getX() - d, getY(), "LEFT");
        if (wall != null) {
            Point2D down = gameScreen.getManagement().getNearestWall(wall, "DOWN", "LEFT");
            Point2D up = gameScreen.getManagement().getNearestWall(wall, "UP", "LEFT");

            if (down != null && up == null) goDown();
            else if (down == null && up != null) goUp();
            else if (down != null && up != null) {
                if (Math.abs(down.getY() * gameScreen.getComponentSize() - this.getY())
                        < Math.abs(this.getY() - up.getY() * gameScreen.getComponentSize())) goDown();
                else goUp();
            } else if (!isOverlapping(getX(), getY() + d, "DOWN")) goDown();

        } else {
            if (!actions.get("GoLeft").equals(getOnAction())) setActions("GoLeft");
//            if (!actions.get("ArcheryLeft").equals(getOnAction())) setActions("ArcheryLeft");
            setX(getX() - d);
        }

    }

    public void stand() {
        if (!actions.get("StandRight").equals(getOnAction())) {
            if (actions.get("GoRight").equals(getOnAction())) setActions("StandRight");
            else if (actions.get("GoLeft").equals(getOnAction())) setActions("StandLeft");
            else if (actions.get("GoUp").equals(getOnAction())) setActions("StandUp");
            else if (actions.get("GoDown").equals(getOnAction())) setActions("StandDown");
        }
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

    @Override
    public void getDamage(int damage) {
        super.getDamage(damage);
        System.out.println(HP.get());
    }

    public void move() {

        if (!isMoving()) {
            stand = true;
        } else {
            stand = false;
        }

//        double d = defaultSpeed.get() * speedUp.get();

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


    protected boolean isOverlapping(double newX, double newY, String direction) {
        return gameScreen.getManagement().isOverlapping(this, newX, newY, direction);
    }


    public void die() {
        if (actions.get("GoRight").equals(getOnAction()) || actions.get("StandRight").equals(getOnAction()))
            setActions("DieRight");
        else if (actions.get("GoLeft").equals(getOnAction()) || actions.get("StandLeft").equals(getOnAction()))
            setActions("DieLeft");
        else if (actions.get("GoUp").equals(getOnAction()) || actions.get("StandUp").equals(getOnAction()))
            setActions("DieUp");
        else if (actions.get("GoDown").equals(getOnAction()) || actions.get("StandDown").equals(getOnAction()))
            setActions("DieDown");

        isDisableMoving.set(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ev -> {
            this.setVisible(false);
            gameScreen.getManagement().removeCharacter(this);

        }));
        timeline.play();
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

    protected HashMap<String, Weapon> weapons = new HashMap<>();
    protected Weapon onWeapon;

    public void attack() throws IOException {

        if (onWeapon == null) {
            putBoom();
            return;
        }

        if (!actions.get("GoLeft").equals(getOnAction())
                && !actions.get("GoRight").equals(getOnAction())
                && !actions.get("GoUp").equals(getOnAction())
                && !actions.get("GoDown").equals(getOnAction())
                && !actions.get("StandUp").equals(getOnAction())
                && !actions.get("StandDown").equals(getOnAction())
                && !actions.get("StandLeft").equals(getOnAction())
                && !actions.get("StandRight").equals(getOnAction())) {
            return;
        }

        isDisableMoving.set(true);

        Action action = getOnAction();


        String direction = "";

        if (actions.get("GoRight").equals(getOnAction()) || actions.get("StandRight").equals(getOnAction())) {
            setActions(onWeapon.getName() + "Right");
            direction = "RIGHT";
        }
        else if (actions.get("GoLeft").equals(getOnAction()) || actions.get("StandLeft").equals(getOnAction())) {
            setActions(onWeapon.getName() + "Left");
            direction = "LEFT";
        }
        else if (actions.get("GoUp").equals(getOnAction()) || actions.get("StandUp").equals(getOnAction())) {
            setActions(onWeapon.getName() +"Up");
            direction = "UP";
        }
        else if (actions.get("GoDown").equals(getOnAction()) || actions.get("StandDown").equals(getOnAction())) {
            setActions(onWeapon.getName() +"Down");
            direction = "DOWN";
        }

        onWeapon.setDirection(direction);
        onWeapon.conduct();

        Timeline performComplete = new Timeline(new KeyFrame(Duration.millis(onWeapon.cycle), ev -> {
            isDisableMoving.set(false);
            setActions(action);
        }));
        performComplete.play();




    }

    public void setWeapon(String name) {
        onWeapon = weapons.get(name);
    }
    public void addWeapon(Weapon weapon) {
        weapons.put(weapon.getName(), weapon);
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
        Monster monster1 = new Monster(gameScreen);
        monster1.setName("" + (i++));
        monster1.setDefaultSize(1d);
        monster1.setX(getX());
        monster1.setY(getY());
        monster1.setActionMove("HuMan");
        monster1.setDefaultDirection(Monster.Go.GO_RIGHT);
        monster1.startCauseDamage();
        monster1.startMoving();
        gameScreen.getManagement().addMonster(monster1);
        gameScreen.getMap().getChildren().add(monster1);
    }
}
