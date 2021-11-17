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

    public enum Go {
        GO_LEFT, GO_RIGHT, GO_UP, GO_DOWN;
    }

    public Monster(GameScreen gameScreen) throws IOException {
        super(gameScreen);
        setName(++count + "Monster");
        HP.set(1000);
        defaultSpeed.set(1d);

    }

    public abstract void addActionAttack();

    public void setDefaultDirection(Go direction) {
        switch (direction) {
            case GO_LEFT -> {
                goLeft = true;
                break;
            }
            case GO_DOWN -> {
                goDown = true;
                break;
            }
            case GO_RIGHT -> {
                goRight = true;
                break;
            }
            case GO_UP -> {
                goUp = true;
                break;
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
            } else {
                goUp = false;
                boolean leftIsOverlapping = isOverlapping(getX() - 10 * d, getY(), "LEFT");
                boolean rightIsOverlapping = isOverlapping(getX() + 10 * d, getY(), "RIGHT");

                if (!leftIsOverlapping) {
                    goLeft = true;
                } else if (!rightIsOverlapping) {
                    goRight = true;
                } else {
                    goDown = true;
                }
            }

        } else {
            if (!actions.get("GoUp").equals(getOnAction())) setActions("GoUp");
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
            } else {
                goDown = false;
                boolean rightIsOverlapping = isOverlapping(getX() + 10 * d, getY(), "RIGHT");
                boolean leftIsOverlapping = isOverlapping(getX() - 10 * d, getY(), "LEFT");

                if (!rightIsOverlapping) {
                    goRight = true;
                } else if (!leftIsOverlapping) {
                    goLeft = true;
                } else {
                    goUp = true;
                }
            }
        } else {
            if (!actions.get("GoDown").equals(getOnAction())) setActions("GoDown");
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

        Wall wall = (Wall) gameScreen.getManagement().getOverlapping(this, getX() + d, getY(), "RIGHT");
        if (wall != null) {


            Point2D down = gameScreen.getManagement().getNearestWall(wall, "DOWN", "RIGHT");
            Point2D up = gameScreen.getManagement().getNearestWall(wall, "UP", "RIGHT");

            if (down != null && up == null) goDown();
            else if (down == null && up != null) goUp();
            else if (down != null && up != null) {
                if (Math.abs(down.getY() * gameScreen.getComponentSize() - this.getY())
                        < Math.abs(this.getY() - up.getY() * gameScreen.getComponentSize())) goDown();
                else goUp = true;
            } else {
                goRight = false;
                boolean upIsOverLapping = isOverlapping(getX(), getY() - 10 * d, "UP");
                boolean downIsOverLapping = isOverlapping(getX(), getY() + 10 * d, "DOWN");

                if (!upIsOverLapping) {
                    goUp = true;
                } else if (!downIsOverLapping) {
                    goDown = true;
                } else {
                    goLeft = true;
                }

            }
        } else {
            if (!actions.get("GoRight").equals(getOnAction())) setActions("GoRight");
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
            } else {
                goLeft = false;

                boolean downIsOverlapping = isOverlapping(getX(), getY() + 10 * d, "DOWN");
                boolean upIsOverlapping = isOverlapping(getX(), getY() - 10 * d, "UP");

                if (!downIsOverlapping) {
                    goDown = true;
                } else if (!upIsOverlapping) {
                    goUp = true;
                } else {
                    goRight = true;
                }
            }
        } else {
            if (!actions.get("GoLeft").equals(getOnAction())) setActions("GoLeft");
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

    public void attack() {
        if (actions.get("GoRight").equals(getOnAction())) setActions("AttackRight");
        else if (actions.get("GoLeft").equals(getOnAction())) setActions("AttackLeft");
        else if (actions.get("GoUp").equals(getOnAction())) setActions("AttackUp");
        else if (actions.get("GoDown").equals(getOnAction())) setActions("AttackDown");
    }

    public String getDirection() {
        if (goUp) return "UP";
        if (goDown) return "DOWN";
        if (goRight) return "RIGHT";
        if (goLeft) return "LEFT";
        if (stand) return "STAND";
        return null;
    }

    @Override
    public void die() {
        causeDamage.stop();
        disableCauseDamage = true;
        timer.stop();

        super.die();
        if (actions.get("AttackRight").equals(getOnAction())) setActions("DieRight");
        else if (actions.get("AttackLeft").equals(getOnAction())) setActions("DieLeft");
        else if (actions.get("AttackUp").equals(getOnAction())) setActions("DieUp");
        else if (actions.get("AttackDown").equals(getOnAction())) setActions("DieDown");
        gameScreen.getManagement().removeMonsterOutOfManage(this);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ev -> {
            this.setVisible(false);
            gameScreen.getManagement().removeMonster(this);
        }));
        timeline.play();
    }
}
