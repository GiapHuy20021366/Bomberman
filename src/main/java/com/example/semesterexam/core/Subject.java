package com.example.semesterexam.core;

import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.tool.*;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

public abstract class Subject extends ImageView {

    protected IntegerProperty HP = new SimpleIntegerProperty(10000);
    protected DoubleProperty defaultSpeed = new SimpleDoubleProperty();
    protected DoubleProperty rateSpeed = new SimpleDoubleProperty(1d);
    protected DoubleProperty speedUp = new SimpleDoubleProperty(1.0);
    protected ImageViewProperties imageViewProperties;
    protected SpriteAnimation onAction = new SpriteAnimation();
    protected long cycle = 1000;
    protected String subjectName = "";
    protected GameScreen gameScreen;
    protected HashMap<String, Action> actions = new HashMap<>();
    protected Direction onDirection;
    protected DoubleProperty componentSize = new SimpleDoubleProperty();
    protected DoubleProperty rateSize = new SimpleDoubleProperty(1d);

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

        setDirectionDependOnAction(actionName);


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

    public void setDirectionDependOnAction(String actionName) {
        if (actionName.endsWith("Up")) onDirection = Direction.UP;
        else if (actionName.endsWith("Down")) onDirection = Direction.DOWN;
        else if (actionName.endsWith("Left")) onDirection = Direction.LEFT;
        else if (actionName.endsWith("Right")) onDirection = Direction.RIGHT;
    }

    public void setActions(String actionName, long cycle, int repeat) {
        if (actions.get(actionName).equals(getOnAction())) return;
        if (onAction != null) onAction.stop();

        setDirectionDependOnAction(actionName);

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

        bidingSize();
        bindingSpeed();

        rateSize.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                bidingSize();
            }
        });

        HP.addListener(((observableValue, oldValue, newValue) -> {
            if (HP.get() <= 0) {
                die();
            }
        }));

        defaultSpeed.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                bindingSpeed();
            }
        });
    }

    public void bindingSpeed() {
        defaultSpeed.unbind();

        defaultSpeed.bind(gameScreen.getSizeProperties().divide(20d));
    }

    public void bidingSize() {
        componentSize.unbind();

        componentSize.bind(gameScreen.getSizeProperties().multiply(rateSize));

        componentSize.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                updateLocationWhenSizeChanged(oldValue.doubleValue(), newValue.doubleValue());
            }
        });
    }

    public void updateLocationWhenSizeChanged(double oldValue, double newValue) {
        setX(getX() * newValue / oldValue);
        setY(getY() * newValue / oldValue);
        setFitWidth(newValue);
        setFitHeight(newValue);
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

    public Direction getOnDirection() {
        return onDirection;
    }


    public void draw() {
        setX(getX() * gameScreen.getComponentSize() / getFitWidth());
        setY(getY() * gameScreen.getComponentSize() / getFitHeight());
        setFitWidth(gameScreen.getComponentSize());
        setFitHeight(gameScreen.getComponentSize());
    }


    @Deprecated
    public void setDefaultSpeed(double rate) {
        defaultSpeed.set(defaultSpeed.get() * rate);
    }

    public double getDefaultSpeed() {
        return defaultSpeed.get();
    }

    public void die() {
        setVisible(false);
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

    public void setSpeedUp(double speedUp) {
        this.speedUp.set(speedUp);
    }

    public double getSpeedUp() {
        return speedUp.get();
    }

}
