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
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.util.HashMap;

public abstract class Subject extends ImageView {

    protected DoubleProperty HP = new SimpleDoubleProperty(10000);
    protected DoubleProperty maxHP = new SimpleDoubleProperty(10000);
    protected DoubleProperty reduceDamage = new SimpleDoubleProperty(0d);
    protected DoubleProperty increaseDamage = new SimpleDoubleProperty(1d);
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
    protected DoubleProperty componentSize = new SimpleDoubleProperty(40d);
    protected DoubleProperty subjectSize = new SimpleDoubleProperty();
    protected DoubleProperty rateSize = new SimpleDoubleProperty(1d);
    protected Subject justDamage;
    protected IntegerProperty dieScore = new SimpleIntegerProperty(100);
    protected IntegerProperty score = new SimpleIntegerProperty(0);
    protected IntegerProperty totalDamage = new SimpleIntegerProperty(0);
    protected IntegerProperty totalKill = new SimpleIntegerProperty(0);



    public Subject(GameScreen gameScreen) throws IOException {
        this.gameScreen = gameScreen;
        this.componentSize.bind(gameScreen.getSizeProperties());

        setup();

        bindingNecessary();
    }

    public void plusScore(Subject s) {
        if (s == this) return;
        score.set(score.get() + s.getDieScore());
    }

    public int getDieScore() {
        return dieScore.get();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public double getScore() {
        return this.score.get();
    }


    private void setup() {
        setDefaultSize(1d);

        HP.addListener(((observableValue, oldValue, newValue) -> {
            if (HP.get() <= 0) {
                die();
            }
            if (HP.get() > maxHP.get()) {
                HP.set(maxHP.get());
            }
        }));
        defaultSpeed.addListener((observableValue, number, t1) -> bindingSpeed());
    }

    public Subject(GameScreen gameScreen, boolean bindingDefault) throws IOException {
        this.gameScreen = gameScreen;
        this.componentSize.bind(gameScreen.getSizeProperties());

        setup();

        if (bindingDefault) {
            bindingNecessary();
        }

    }

    public void bindingNecessary() {
        bidingSize();
        bindingSpeed();
    }

    public void bindingSpeed() {
        defaultSpeed.unbind();

        defaultSpeed.bind(gameScreen.getSizeProperties().divide(20d).multiply(rateSpeed));
    }

    public void bidingSize() {
        subjectSize.unbind();

        subjectSize.bind(gameScreen.getSizeProperties().multiply(rateSize));

        subjectSize.addListener((ChangeListener<Number>) (observableValue, oldValue, newValue) -> {
            setFitWidth(newValue.doubleValue());
            setFitHeight(newValue.doubleValue());
        });

        componentSize.addListener((ChangeListener<Number>) (observableValue, oldValue, newValue) -> updateLocationWhenSizeChanged(oldValue.doubleValue(), newValue.doubleValue()));
    }

    public void updateLocationWhenSizeChanged(double oldValue, double newValue) {
        setX(getX() * newValue / oldValue);
        setY(getY() * newValue / oldValue);
    }

    public void setRateSize(double rateSize) {
        this.rateSize.set(rateSize);
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
        Action action = actions.get(actionName);
        if (action == null) {
//            System.out.println(actionName);
            return;
        }
        if (action.equals(getOnAction())) return;

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


    public double getHP() {
        return HP.get();
    }

    public void setHP(double HP) {
        this.HP.set(HP);
    }

    public void plusHP(double hp) {
        HP.set(HP.get() + hp);
    }

    public double getMaxHP() {
        return maxHP.get();
    }

    public DoubleProperty getMaxBlood() {
        return maxHP;
    }

    public DoubleProperty getBlood() {
        return HP;
    }

    public void setMaxHP(double maxHP) {
        this.maxHP.set(maxHP);
    }

    public void setDefaultSize(double rateSize) {
        setFitWidth(gameScreen.getComponentSize() * rateSize);
        setFitHeight(gameScreen.getComponentSize() * rateSize);
    }

    public void getDamage(double damage) {
        if (HP.get() <= 0) {
            return;
        }
        double realDamage = damage * (1 - reduceDamage.get());
        if (realDamage > HP.get()) {
            realDamage = HP.get();
        }
        HP.set(HP.get() - realDamage);
        if (justDamage != null) {
            justDamage.plusTotalDamage((int) realDamage);
        }
    }

    public void setJustDamage(Subject justDamage) {
        if (HP.get() > 0) {
            this.justDamage = justDamage;
        }
    }

    @Deprecated
    public void getDamage(double damage, Subject justDamage) {
        getDamage(damage);
        if (HP.get() <= 0) {
            return;
        }
        this.justDamage = justDamage;

    }

    public void setSpeedUp(double speedUp) {
        this.speedUp.set(speedUp);
    }

    public double getSpeedUp() {
        return speedUp.get();
    }

    public void setReduceDamage(double reduce) {
        reduceDamage.set(reduce);
    }

    public double getIncreaseDamage() {
        return increaseDamage.get();
    }

    public void setIncreaseDamage(double increase) {
        increaseDamage.set(increase);
    }

    public void plusTotalDamage(int damage) {
        totalDamage.set(totalDamage.get() + damage);
    }

    public void plusTotalKill() {
        totalKill.set(totalKill.get() + 1);
    }

    public int getTotalDamage() {
        return totalDamage.get();
    }

    public int getTotalKill() {
        return totalKill.get();
    }

}
