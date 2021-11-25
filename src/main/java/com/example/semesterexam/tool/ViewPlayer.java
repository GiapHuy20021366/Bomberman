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
    private ScrollPane scrollPane = new ScrollPane();
    private Figure player = null;
    private AnimationTimer moveView;
    private Map map;
    private boolean disableViewport = false;
//    private Node borderPane;
//    private Scale scale = new Scale(1d, 1d);
//    private DoubleProperty zoom = new SimpleDoubleProperty(1d);

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
        scrollPane.setContent(map);
//        map.getTransforms().add(scale);

        // SetSize
        setViewSize(600, 600);

        // ViewPlayer with scrollPane
        getChildren().add(scrollPane);

//        zoom.addListener((observable, oldValue, newValue) -> {
//            scale.setX(newValue.doubleValue());
//            scale.setY(newValue.doubleValue());
//            scale.setPivotX(player.getX() + player.getFitWidth() / 2d);
//            scale.setPivotY(player.getY() + player.getFitHeight() / 2d);
////            player.setViewValue(player.getViewValue() * newValue.doubleValue());
//            map.setWIDTH(map.getWIDTH() / newValue.doubleValue());
//            map.setHEIGHT(map.getHEIGHT() / newValue.doubleValue());
//            scrollPane.requestLayout();
//            moveViewport();
//        });

    }

//    public void setZoom(double rate) {
//        zoom.set(zoom.get() * rate);
//    }

    public void setViewSize(double width, double height) {
        scrollPane.setPrefSize(width, height);
    }

    public void moveViewport() {

        double hValue = (player.getX() + player.getFitHeight() / 2d - scrollPane.getWidth() / 2d)
                / (map.getWIDTH() - scrollPane.getWidth());

        double vValue = (player.getY() + player.getFitWidth() / 2d - scrollPane.getHeight() / 2d)
                / (map.getHEIGHT() - scrollPane.getHeight());

        scrollPane.hvalueProperty().set(hValue);
        scrollPane.vvalueProperty().set(vValue);
    }


    public void moveViewportBy(Figure character) {
        this.player = character;

        moveView = new AnimationTimer() {
            @Override
            public void handle(long l) {
//                if (character.isMoving() && !disableViewport) {
//                    moveViewport();
//                }
                if (!disableViewport) {
                    moveViewport();
                }

            }
        };
        moveView.start();

        setViewSize(character.getViewValue() * 2, character.getViewValue() * 2);
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

//    public Scale getScale() {
//        return scale;
//    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }
}
