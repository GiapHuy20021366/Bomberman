package com.example.semesterexam.task;

import com.example.semesterexam.tool.ViewPlayer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class MyTask<T> extends Task<T> {
    private final BooleanProperty complete = new SimpleBooleanProperty(false);


    public MyTask() {
        this.setOnSucceeded(event -> {
            whatNext();
        });
    }

    public abstract void whatNext();

    public void execute() {
        Executor exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        exec.execute(this);
    }

    @Override
    public void updateMessage(String s) {
        super.updateMessage(s);
    }

    @Override
    public void updateProgress(double v, double v1) {
        super.updateProgress(v, v1);
    }

    public boolean isComplete() {
        return this.complete.get();
    }

    public BooleanProperty getCompleteProperty() {
        return this.complete;
    }

    public void setComplete(boolean complete) {
        this.complete.setValue(complete);
    }
}
