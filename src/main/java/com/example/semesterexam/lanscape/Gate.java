package com.example.semesterexam.lanscape;

import com.example.semesterexam.core.Subject;
import com.example.semesterexam.manage.GameScreen;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.IOException;

public class Gate extends SoftWall {
    private final BooleanProperty open = new SimpleBooleanProperty(false);
    private AnimationTimer look;
    private final BooleanProperty nextStage = new SimpleBooleanProperty(false);

    public Gate(double x, double y, GameScreen gameScreen) throws IOException {
        super(x, y, gameScreen);

        HP.set(1);

        open.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (HP.get() > 0) {
                    return;
                }
                if (t1) {
                    open();
                } else {
                    lock();
                }
            }
        });

        addActions("Default", gameScreen.getAction("GatePack:Default"));
        addActions("Lock", gameScreen.getAction("GatePack:Lock"));
        addActions("Open", gameScreen.getAction("GatePack:Open"));

        setActions("Default");

    }

    public void lock() {
        if (look != null) {
            look.stop();
        }
        setActions("Lock");
    }

    public void open() {
        setActions("Open");
        gameScreen.getManagement().removeWallOutOfManage(this);
        look = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (gameScreen.getManagement().lookupFigure(getThis())) {
                    nextStage.set(true);
//                    System.out.println("Can move stage");
                    stop();
                }
            }
        };
        look.start();
    }

    private Gate getThis() {
        return this;
    }

    public void setOpen(boolean open) {
        this.open.set(open);
    }

    public boolean isOpen() {
        return open.get();
    }

    public boolean canMoveStage() {
        return nextStage.get();
    }

    @Override
    public void getDamage(double damage) {
        if (HP.get() <= 0) {
            return;
        }
        HP.set(-1);
    }

    @Override
    public void die() {
        if (open.get()) {
            open();
        } else {
            lock();
        }
    }
}
