package com.example.semesterexam.core;

import com.example.semesterexam.manage.GameScreen;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.io.IOException;

public  abstract class Monster extends Character {
    private static int count = 0;

    protected boolean disableCauseDamage = false;


    public Monster(GameScreen gameScreen) throws IOException {
        super(gameScreen);
        setName(++count + "Monster");
        HP.set(1000);
//        defaultSpeed.set(1d);

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

    public void setDefaultDirection(Direction direction) {
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
    public void goUp() {
        double d = defaultSpeed.get() * speedUp.get();

        Character c = gameScreen.getManagement().intersect(this, getX(), getY() - d);
        if (c != null) {
            if (c.getHP() > 0) {
                attack();
                return;
            }
        }

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

        } else {
            setActionGoDirection(Direction.UP);
            setY(getY() - d);
        }
    }


    @Override
    public void goDown() {
        double d = defaultSpeed.get() * speedUp.get();
        Character c = gameScreen.getManagement().intersect(this, getX(), getY() + d);
        if (c != null) {
            if (c.getHP() > 0) {
                attack();
                return;
            }
        }

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
        } else {
            setActionGoDirection(Direction.DOWN);
            setY(getY() + d);
        }
    }


    @Override
    public void goRight() {
        double d = defaultSpeed.get() * speedUp.get();

        Character c = gameScreen.getManagement().intersect(this, getX() + d, getY());
        if (c != null) {
            if (c.getHP() > 0) {
                attack();
                return;
            }
        }

        Wall wall = (Wall) gameScreen.getManagement().getOverlapping(this, getX() + d, getY(), Direction.RIGHT);
        if (wall != null) {


            Point2D down = gameScreen.getManagement().getNearestWall(wall, Direction.DOWN, Direction.RIGHT);
            Point2D up = gameScreen.getManagement().getNearestWall(wall, Direction.UP, Direction.RIGHT);

            if (down != null && up == null) goDown();
            else if (down == null && up != null) goUp();
            else if (down != null) {
                if (Math.abs(down.getY() * gameScreen.getComponentSize() - this.getY())
                        < Math.abs(this.getY() - up.getY() * gameScreen.getComponentSize())) goDown();
                else goUp = true;
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
        } else {
            setActionGoDirection(Direction.RIGHT);
            setX(getX() + d);
        }
    }


    @Override
    public void goLeft() {
        double d = defaultSpeed.get() * speedUp.get();

        Character c = gameScreen.getManagement().intersect(this, getX() - d, getY());
        if (c != null) {
            if (c.getHP() > 0) {
                attack();
                return;
            }
        }
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
        } else {
            setActionGoDirection(Direction.LEFT);
            setX(getX() - d);
        }
    }


    public void damage(int damage) {
        if (disableCauseDamage) return;

        Character character = gameScreen.getManagement().intersect(this);

        if (character != null) {
            if (character.getHP() > 0) {
                character.getDamage(damage);
            }

        }
    }


    protected AnimationTimer causeDamage;

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
        causeDamage.stop();
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

        isDisableMoving.set(true);
        gameScreen.getManagement().removeMonsterOutOfManage(this);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ev -> {
            this.setVisible(false);
            gameScreen.getManagement().removeMonster(this);
        }));
        timeline.play();
    }

    @Override
    public void startMoving() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isDisableMoving.get()) {
                    move();
                } else if (!onAttacking){
                    stand();
                }
            }
        };
        timer.start();
    }

    Timeline moveAgain = null;

    public void outOfFreeze(long cycle) {
        if (moveAgain != null) moveAgain.stop();

        moveAgain = new Timeline(new KeyFrame(Duration.millis(cycle), ev -> {
            setIsDisableMoving(false);
            setDisableCauseDamage(false);
        }));

        moveAgain.setCycleCount(1);
        moveAgain.play();
    }

}
