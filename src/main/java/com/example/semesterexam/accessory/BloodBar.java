package com.example.semesterexam.accessory;

import com.example.semesterexam.core.Figure;
import com.example.semesterexam.core.Subject;
import com.example.semesterexam.manage.GameScreen;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.IOException;

public class BloodBar {
    protected DoubleProperty rate = new SimpleDoubleProperty(5d);
    protected DoubleProperty oldValue = new SimpleDoubleProperty(100d);
    protected DoubleProperty newValue = new SimpleDoubleProperty(100d);
    protected DoubleProperty maxValue = new SimpleDoubleProperty(100d);
    protected GameScreen gameScreen;
    protected Subject s;

    protected Bar currentBlood;
    protected Bar prevBlood;
    protected Bar maxBlood;
    protected Bar bar;

    public BloodBar(GameScreen gameScreen, Subject s) throws IOException {
        this.gameScreen = gameScreen;
        this.s = s;
    }

    public void setAll() throws IOException {
        bidingValues();
        setBars();
        setActionForBars();
        setVisible(true);
    }

    public void bidingValues() {
        maxValue.bind(s.getMaxBlood());
        newValue.bind(s.getBlood());

        newValue.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                oldValue.setValue(Math.min(maxValue.doubleValue(), number.doubleValue()));
            }
        });
    }

    public void setBars() throws IOException {
        currentBlood = new Bar(this.gameScreen, this.s);
        currentBlood.getMaxValue().bind(this.maxValue);
        currentBlood.getValue().bind(this.newValue);
        currentBlood.rate().bind(this.rate);

        prevBlood = new Bar(this.gameScreen, this.s);
        prevBlood.getMaxValue().bind(this.maxValue);
        prevBlood.getValue().bind(this.oldValue);
        prevBlood.rate().bind(this.rate);

        maxBlood = new Bar(this.gameScreen, this.s);
        maxBlood.getMaxValue().bind(this.maxValue);
        maxBlood.getValue().bind(this.maxValue);
        maxBlood.rate().bind(this.rate);

        bar = new Bar(this.gameScreen, this.s);
        bar.getMaxValue().bind(this.maxValue);
        bar.getValue().bind(this.maxValue);
        bar.rate().bind(this.rate);
    }

    public void setActionForBars() {
        if (s instanceof Figure) {
            currentBlood.addActions("Default", gameScreen.getAction("BloodPack:GreenBlood"));
        } else {
            currentBlood.addActions("Default", gameScreen.getAction("BloodPack:RedBlood"));
        }
        currentBlood.setActions("Default");

        prevBlood.earnAction(gameScreen.getAction("BloodPack:YellowBlood"));

        maxBlood.earnAction(gameScreen.getAction("BloodPack:GrayBlood"));

        bar.earnAction(gameScreen.getAction("BloodPack:Bar"));
    }

    public void setOldValue(double value) {
        this.oldValue.set(value);
    }

    public void setNewValue(double value) {
        this.newValue.set(value);
    }

    public DoubleProperty getMaxValue() {
        return this.maxValue;
    }

    public void setVisible(boolean visible) {
        currentBlood.setVisible(visible);
        prevBlood.setVisible(visible);
        maxBlood.setVisible(visible);
        bar.setVisible(visible);


        maxBlood.toFront();
        prevBlood.toFront();
        currentBlood.toFront();
        bar.toFront();

    }

    public void resetMap() {
        currentBlood.resetMap();
        prevBlood.resetMap();
        maxBlood.resetMap();
        bar.resetMap();

        maxBlood.toFront();
        prevBlood.toFront();
        currentBlood.toFront();
        bar.toFront();

    }

    public void removeBloodBar() {
        currentBlood.removeBar();
        prevBlood.removeBar();
        maxBlood.removeBar();
        bar.removeBar();
    }
}
