package com.example.semesterexam.tool;

import com.example.semesterexam.core.Figure;
import com.example.semesterexam.manage.Map;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

public class ViewPlayer extends Pane {
    private final ScrollPane scrollPane = new ScrollPane();
    private Figure player = null;
    private Map map;
    private boolean disableViewport = false;


    public ViewPlayer(Map map) {

        // Set map that class using for
        this.map = map;

        // Hide scroll
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPadding(Insets.EMPTY);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.addEventFilter(KeyEvent.ANY, (event) -> {
            if (event.getCode() == KeyCode.DOWN
                    || event.getCode() == KeyCode.UP
                    || event.getCode() == KeyCode.SPACE
                    || event.getCode() == KeyCode.LEFT
                    || event.getCode() == KeyCode.RIGHT) {
                event.consume();
                javafx.event.Event.fireEvent(this, event);
            }
        });


        // Set content
//        scrollPane.setContent(map);
        setMap(map);


        // SetSize
        setViewSize(900, 720);

        // ViewPlayer with scrollPane
        getChildren().add(scrollPane);


    }

    public void setMap(Map map) {
        disableViewport = true;
        this.map = map;
        scrollPane.setContent(map);
        disableViewport = false;
    }


    public void setViewSize(double width, double height) {
        scrollPane.setPrefSize(width, height);
    }

    public void moveViewport() {

//        System.out.println(scrollPane.getContent().getBoundsInLocal());

        double hValue = (player.getX() + player.getFitHeight() / 2d - scrollPane.getWidth() / 2d)
                / (map.getWIDTH() - scrollPane.getWidth());

        double vValue = (player.getY() + player.getFitWidth() / 2d - scrollPane.getHeight() / 2d)
                / (map.getHEIGHT() - scrollPane.getHeight());

        scrollPane.hvalueProperty().set(hValue);
        scrollPane.vvalueProperty().set(vValue);
    }


    public void moveViewportBy(Figure character) {
        this.player = character;

        AnimationTimer moveView = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!disableViewport) {
                    moveViewport();
                }

            }
        };
        moveView.start();
    }

    public void shacking(double rateSacking, long time) {
        disableViewport = true;

        final Integer[] x = {0};
        final Integer[] y = {0};

        Timeline timelineX = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (x[0] == 0) {
                    scrollPane.hvalueProperty().set(scrollPane.getHvalue() + 0.01d);
                    x[0] = 1;
                } else {
                    scrollPane.hvalueProperty().set(scrollPane.getHvalue() - 0.01d);
                    x[0] = 0;
                }
            }
        }));
        timelineX.setCycleCount(2);
        timelineX.setAutoReverse(false);
        timelineX.play();

        Timeline timelineY = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (y[0] == 0) {
                    scrollPane.vvalueProperty().set(scrollPane.getVvalue() + 0.01d);
                    y[0] = 1;
                } else {
                    scrollPane.vvalueProperty().set(scrollPane.getVvalue() - 0.01d);
                    y[0] = 0;
                }
            }
        }));

        timelineY.setCycleCount(2);
        timelineY.setAutoReverse(false);
        timelineY.play();

        Timeline active = new Timeline(new KeyFrame(Duration.millis(250), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                disableViewport = false;
            }
        }));
        active.setCycleCount(1);
        active.play();

    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public Map getMap() {
        return map;
    }
}
