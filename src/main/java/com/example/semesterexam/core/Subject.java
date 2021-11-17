package com.example.semesterexam.core;

import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.tool.*;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.beans.property.*;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

public abstract class Subject extends ImageView {

    protected IntegerProperty HP = new SimpleIntegerProperty(10000);
    protected Monster damageBy;

    protected DoubleProperty defaultSpeed = new SimpleDoubleProperty(2);
    protected DoubleProperty speedUp = new SimpleDoubleProperty(1.0);

    ImageViewProperties imageViewProperties;
    protected SpriteAnimation onAction = new SpriteAnimation();
    protected long cycle = 1000;

    protected String subjectName = "";

    protected GameScreen gameScreen;

    protected HashMap<String, Action> actions = new HashMap<>();

    public void addActions(String actionName, String filePath) throws IOException {
        Action newAction;
        if (filePath.endsWith(".png")) {
            newAction = new Action(filePath);
        } else {
            newAction = new MultiAction(filePath);
        }
        actions.put(actionName, newAction);

    }

    public void addActions(String actionName, Action newAction) {
        actions.put(actionName, newAction);
    }

    public void setActions(Action action) {
        if (onAction != null) onAction.stop();

        if (action.getClass() == Action.class) {
            imageViewProperties = action.imageViewProperties;
            onAction.setProperties(imageViewProperties);
            setImage(action.getImage());
            createAnimation();
        } else {
            onAction = new MultiSpriteAnimation((MultiAction) action, this, Duration.millis(1000));
            onAction.setCycleCount(Transition.INDEFINITE);
        }

        onAction.setAction(action);
        onAction.play();
    }
    public void setActions(String actionName) {
        if (actions.get(actionName).equals(getOnAction())) return;

        if (onAction != null) onAction.stop();

        if (actions.get(actionName).getClass() == Action.class) {
            imageViewProperties = actions.get(actionName).imageViewProperties;
            onAction.setProperties(imageViewProperties);
            setImage(actions.get(actionName).getImage());
            createAnimation();
        } else {
            onAction = new MultiSpriteAnimation((MultiAction) actions.get(actionName), this, Duration.millis(1000));
            onAction.setCycleCount(Transition.INDEFINITE);
        }

        onAction.setAction(actions.get(actionName));
        onAction.play();
    }

    public void setActions(String actionName, long cycle, int repeat) {
        if (actions.get(actionName).equals(getOnAction())) return;
        if (onAction != null) onAction.stop();

        if (actions.get(actionName).getClass() == Action.class) {
            imageViewProperties = actions.get(actionName).imageViewProperties;
            onAction.setProperties(imageViewProperties);
            setImage(actions.get(actionName).getImage());

            onAction = new SpriteAnimation(this, Duration.millis(cycle), imageViewProperties);

        } else {
            onAction = new MultiSpriteAnimation((MultiAction) actions.get(actionName), this, Duration.millis(cycle));
        }

        onAction.setCycleCount(repeat);
        onAction.setAction(actions.get(actionName));
        onAction.play();
    }

    public Subject(GameScreen gameScreen) throws IOException {
        this.gameScreen = gameScreen;
        HP.addListener(((observableValue, oldValue, newValue) -> {
            if (HP.get() <= 0) {
                die();
            }
        }));
    }

    public Subject() {
    }

    public void createAnimation() {
        onAction = new SpriteAnimation(this, Duration.millis(cycle), imageViewProperties);
        onAction.setCycleCount(Animation.INDEFINITE);

    }


    public Action getOnAction() {
        return onAction.getAction();
    }


    public void setName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getName() {
        return subjectName;
    }


    public void draw() {
        setX(getX() * gameScreen.getComponentSize() / getFitWidth());
        setY(getY() * gameScreen.getComponentSize() / getFitHeight());
        setFitWidth(gameScreen.getComponentSize());
        setFitHeight(gameScreen.getComponentSize());
    }

    public void setDefaultSpeed(double rate) {
        defaultSpeed.set(defaultSpeed.get() * rate);
    }
    public double getDefaultSpeed() {
        return defaultSpeed.get();
    }

    public void die(Monster dieBy) {
        // to override
    }

    public void die() {
        setVisible(false);
    }

    public void getDamage(int damage, Monster m) {
        damageBy = m;
        HP.set(HP.get() - damage);
    }

    public int getHP() {
        return HP.get();
    }


    public void setDefaultSize(double rateSize) {
        setFitWidth(gameScreen.getComponentSize() * rateSize);
        setFitHeight(gameScreen.getComponentSize() * rateSize);
    }

    public void getDamage(int damage) {
        HP.set(HP.get() - damage);
    }

}
