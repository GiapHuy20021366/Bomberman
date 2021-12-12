package com.example.semesterexam.accessory;

import com.example.semesterexam.core.Subject;
import com.example.semesterexam.manage.GameScreen;
import com.example.semesterexam.tool.Action;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.IOException;

public class Bar extends Subject {
    protected DoubleProperty value = new SimpleDoubleProperty(100d);
    protected DoubleProperty maxValue = new SimpleDoubleProperty(100d);
    protected DoubleProperty ratio = new SimpleDoubleProperty(10d);
    protected Subject s;

    public Bar(GameScreen gameScreen, Subject s) throws IOException {
        super(gameScreen, false);
        this.s = s;

        bindLength();
        bindLocation();

        resetMap();
    }

    public void resetMap() {
        gameScreen.getMap().getChildren().add(this);
    }

    public void bindLength() {
        this.fitWidthProperty().bind(s.fitWidthProperty().multiply(0.75d).multiply(value.divide(maxValue)));
        this.fitHeightProperty().bind(s.fitHeightProperty().divide(ratio));
    }

    public void bindLocation() {
        this.xProperty().bind(s.xProperty().add(s.fitWidthProperty().divide(4d)));
        this.yProperty().bind(s.yProperty().subtract(s.fitHeightProperty().divide(ratio)));
    }

    public DoubleProperty getValue() {
        return value;
    }

    public DoubleProperty getMaxValue() {
        return maxValue;
    }

    public void earnAction(Action action) {
        addActions("Default", action);
        setActions("Default");
    }

    public DoubleProperty rate() {
        return ratio;
    }

    public void removeBar() {
        gameScreen.getMap().getChildren().remove(this);
    }
}
