package com.example.semesterexam.core;

import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.monster.Orc;
import com.example.semesterexam.monster.Skeleton;
import com.example.semesterexam.monster.Zombie;
import com.example.semesterexam.tool.Player;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public abstract class Monster extends Character {
    private static int count = 0;

    protected boolean disableCauseDamage = false;

    protected DoubleProperty rangeFar = new SimpleDoubleProperty(0d);
    protected DoubleProperty eyeFar = new SimpleDoubleProperty(6d);

    protected SimpleBooleanProperty canChangeDirection = new SimpleBooleanProperty(false);
    protected long cycleChangeDirection = 5000L;
    protected Timeline changeDirection = null;
    protected AnimationTimer detectFigure = null;

    protected long cycleHideBloodBar = 2500L;


    public Monster(GameScreen gameScreen) throws IOException {
        super(gameScreen);
        setName(++count + "Monster");
        HP.set(2000);
        maxHP.set(2000);
        canChangeDirection.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    changeDirection();
                } else {
                    stopChangeDirection();
                }
            }
        });

    }

    @Override
    public void addIconSkills() {

    }

    @Override
    public void showBloodBar() {
        super.showBloodBar();
//        bloodBar.setVisible(false);
        showBloodBar.set(false);
    }

    public void makeDetectFigure() {
        if (detectFigure != null) detectFigure.stop();

        detectFigure = new AnimationTimer() {
            @Override
            public void handle(long l) {
                Direction next = gameScreen.getManagement().detectFigure(getThis());
                if (next != null) {
                    setDefaultDirection(next);
                }
            }
        };

        detectFigure.start();
    }

    public void setEyeFar(double far) {
        this.eyeFar.set(far);
    }

    public double getEyeFar() {
        return eyeFar.get() * gameScreen.getComponentSize();
    }

    private Monster getThis() {
        return this;
    }

    public void setCanChangeDirection(boolean canChange) {
        this.canChangeDirection.set(canChange);
    }

    public void changeDirection() {
        stopChangeDirection();

        changeDirection = new Timeline(new KeyFrame(Duration.millis(cycleChangeDirection), ev -> {
            setDefaultDirection(randomDirection());
        }));

        changeDirection.setCycleCount(-1);
        changeDirection.play();
    }

    public void stopChangeDirection() {
        if (changeDirection != null) changeDirection.stop();
    }

    public void setRangeFar(double range) {
        this.rangeFar.set(range);
    }

    public double getRangeFar() {
        return rangeFar.get() * gameScreen.getComponentSize();
    }

    @Override
    public void countdownPutBoom() {
        // DoNothing
    }

    @Override
    public void bindingSpeed() {
        defaultSpeed.unbind();

        defaultSpeed.bind(gameScreen.getSizeProperties().divide(40d));
    }

    public void setDisableCauseDamage(boolean disableCauseDamage) {
        this.disableCauseDamage = disableCauseDamage;
    }

    @Override
    public void defaultAttack() {
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
    }

    public abstract void addActionAttack();

    protected void disableAllDirection() {
        goUp = false;
        goDown = false;
        goLeft = false;
        goRight = false;
    }

    public void setDefaultDirection(Direction direction) {
        disableAllDirection();
        switch (direction) {
            case LEFT -> {
                goLeft = true;
            }
            case DOWN -> {
                goDown = true;
            }
            case RIGHT -> {
                goRight = true;
            }
            case UP -> {
                goUp = true;
            }
        }
    }


    public void countTimeForNextDamage(long time) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time), ev -> {
            disableCauseDamage = false;
        }));
        timeline.play();
    }

    @Override
    public void doAfterCompleteAttack() {
        canChangeDirection.set(true);
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
            causeDamage(s, baseDamage.get() * increaseDamage.get());
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
        } else {
            goUp = false;
            boolean leftIsOverlapping = isOverlapping(getX() - 10 * d, getY(), Direction.LEFT);
            boolean rightIsOverlapping = isOverlapping(getX() + 10 * d, getY(), Direction.RIGHT);

            if (!leftIsOverlapping) {
                goLeft = true;
            } else if (!rightIsOverlapping) {
                goRight = true;
            } else {
                goDown = true;
            }
        }

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
            causeDamage(s, baseDamage.get() * increaseDamage.get());
            return;
        }

        Point2D r = gameScreen.getManagement().getNearestWall(wall, Direction.RIGHT, Direction.DOWN);
        Point2D l = gameScreen.getManagement().getNearestWall(wall, Direction.LEFT, Direction.DOWN);

        if (r != null && l == null) goRight();
        else if (r == null && l != null) goLeft();
        else if (r != null) {
            if (Math.abs(r.getX() * gameScreen.getComponentSize() - this.getX())
                    < Math.abs(this.getX() - l.getX() * gameScreen.getComponentSize())) goRight();
            else goLeft();
        } else {
            goDown = false;
            boolean rightIsOverlapping = isOverlapping(getX() + 10 * d, getY(), Direction.RIGHT);
            boolean leftIsOverlapping = isOverlapping(getX() - 10 * d, getY(), Direction.LEFT);

            if (!rightIsOverlapping) {
                goRight = true;
            } else if (!leftIsOverlapping) {
                goLeft = true;
            } else {
                goUp = true;
            }
        }
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
            causeDamage(s, baseDamage.get() * increaseDamage.get());
            return;
        }

        Point2D down = gameScreen.getManagement().getNearestWall(wall, Direction.DOWN, Direction.RIGHT);
        Point2D up = gameScreen.getManagement().getNearestWall(wall, Direction.UP, Direction.RIGHT);

        if (down != null && up == null) goDown();
        else if (down == null && up != null) goUp();
        else if (down != null) {
            if (Math.abs(down.getY() * gameScreen.getComponentSize() - this.getY())
                    < Math.abs(this.getY() - up.getY() * gameScreen.getComponentSize())) goDown();
            else goUp();
        } else {
            goRight = false;
            boolean upIsOverLapping = isOverlapping(getX(), getY() - 10 * d, Direction.UP);
            boolean downIsOverLapping = isOverlapping(getX(), getY() + 10 * d, Direction.DOWN);

            if (!upIsOverLapping) {
                goUp = true;
            } else if (!downIsOverLapping) {
                goDown = true;
            } else {
                goLeft = true;
            }

        }
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
            causeDamage(s, baseDamage.get() * increaseDamage.get());
            return;
        }

        Point2D down = gameScreen.getManagement().getNearestWall(wall, Direction.DOWN, Direction.LEFT);
        Point2D up = gameScreen.getManagement().getNearestWall(wall, Direction.UP, Direction.LEFT);

        if (down != null && up == null) goDown();
        else if (down == null && up != null) goUp();
        else if (down != null) {
            if (Math.abs(down.getY() * gameScreen.getComponentSize() - this.getY())
                    < Math.abs(this.getY() - up.getY() * gameScreen.getComponentSize())) goDown();
            else goUp();
        } else {
            goLeft = false;

            boolean downIsOverlapping = isOverlapping(getX(), getY() + 10 * d, Direction.DOWN);
            boolean upIsOverlapping = isOverlapping(getX(), getY() - 10 * d, Direction.UP);

            if (!downIsOverlapping) {
                goDown = true;
            } else if (!upIsOverlapping) {
                goUp = true;
            } else {
                goRight = true;
            }
        }
    }

    @Override
    public void attack() {
        canChangeDirection.set(false);
        super.attack();
    }

    public void causeDamage(Subject s, double damage) {
        if (disableCauseDamage) {
            return;
        }

        if (disappearBloodBar != null) {
            disappearBloodBar.stop();
        }

        disappearBloodBar = new Timeline(new KeyFrame(Duration.millis(cycleHideBloodBar), ev -> {
            showBloodBar.set(false);
        }));
        disappearBloodBar.play();

        disableCauseDamage = true;
        attack();


        if (attacks.get(getOnAttack()) != null) {  // move cause damage to weapon
            countTimeForNextDamage(1000);
            return;
        }

        if (s.getHP() > 0) {
            s.getDamage(damage);
            countTimeForNextDamage(1500);
        }
    }


    @Deprecated
    public void damage(int damage) {
        if (disableCauseDamage) return;

        Character character = gameScreen.getManagement().characterIntersect(this);

        if (character != null) {
            if (character.getHP() > 0) {
                character.getDamage(damage);
            }

        }
    }


    protected AnimationTimer causeDamage;

    @Deprecated
    public void startCauseDamage() {
        causeDamage = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!disableCauseDamage) {
                    damage(500);
                    disableCauseDamage = true;
                    countTimeForNextDamage(1000);
                }
            }
        };
        causeDamage.start();

    }

    @Override
    public void die() {
//        Player player = new Player(gameScreen, gameScreen.getMediaManagement().getSound("MonsterDie"), Player.VOLUME_PLAYER);
//        player.play();

        playSound("MonsterDie");
        if (timer != null) {
            timer.stop();
        }
        if (attack != null) {
            attack.stop();
        }
        if (performComplete != null) {
            performComplete.stop();
        }

        if (bloodBar != null) {
            bloodBar.setVisible(false);
            bloodBar.removeBloodBar();
        }

        if (changeDirection != null) {
            changeDirection.stop();
        }

        if (detectFigure != null) {
            detectFigure.stop();
        }

        isDisableMoving.set(true);
        disableCauseDamage = true;
        timer.stop();


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

        gameScreen.getManagement().removeMonsterOutOfManage(this);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1300), ev -> {
            this.setVisible(false);
            gameScreen.getManagement().removeMonster(this);
        }));
        timeline.play();

        showBloodBar.set(false);
        if (justDamage != null) {
            justDamage.plusScore(this);
            justDamage.plusTotalKill();
        }

        freeze();



    }

    Timeline disappearBloodBar;

    @Override
    public void getDamage(double damage) {
        showBloodBar.set(true);

        if (disappearBloodBar != null) {
            disappearBloodBar.stop();
        }

        disappearBloodBar = new Timeline(new KeyFrame(Duration.millis(cycleHideBloodBar), ev -> {
            showBloodBar.set(false);
        }));
        disappearBloodBar.play();

        super.getDamage(damage);
    }

    @Override
    public void startMoving() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isDisableMoving.get()) {
                    move();
                } else if (!onAttacking) {
                    stand();
                }
            }
        };
        timer.start();
    }

    public void freeze() {
        if (performComplete != null) {
            performComplete.stop();
        }
        setIsDisableMoving(true);
        setDisableCauseDamage(true);
        stand();
    }

    Timeline moveAgain = null;

    public void outOfFreeze(long cycle) {
        if (moveAgain != null) moveAgain.stop();

        moveAgain = new Timeline(new KeyFrame(Duration.millis(cycle), ev -> {
            setIsDisableMoving(false);
            setDisableCauseDamage(false);
            if (performComplete != null) {
                performComplete.play();
            }
        }));

        moveAgain.setCycleCount(1);
        moveAgain.play();

    }

    @Override
    public void addSounds() {
        sounds.put("MonsterDie", new Player(gameScreen, "MonsterDie", Player.VOLUME_PLAYER));
        sounds.put("BoomPow", new Player(gameScreen, "BoomPow", Player.VOLUME_MONSTER_WEAPON));
        sounds.put("Attack", new Player(gameScreen, "Attack", Player.VOLUME_MONSTER_ATTACK));
        sounds.put("Fire", new Player(gameScreen, "Fire", Player.VOLUME_MONSTER_WEAPON));
    }

    public Direction randomDirection() {
        int dir = new Random().nextInt(4);
        switch (dir) {
            case 0 -> {
                return Direction.UP;
            }
            case 1 -> {
                return Direction.DOWN;
            }
            case 2 -> {
                return Direction.LEFT;
            }
            case 3 -> {
                return Direction.RIGHT;
            }
        }
        return null;
    }




}
