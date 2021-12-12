package com.example.semesterexam.tool;

import com.example.semesterexam.core.Direction;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TransitionService {
    public static void fadeAndMove(double distance, Direction direction, long cycle, long space, Node... nodes) {
        List<Timeline> list = new ArrayList<>();
        System.out.println(nodes.length);
        for (int i = 0; i < nodes.length; i++) {
            final Node node = nodes[i];
            if (node == null) {
                System.out.println("Null");
                continue;
            }
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(Math.max(5, space * i)), ev -> {
                TransitionService.fadeAndMove(node, distance, direction, cycle);
            }));
            list.add(timeline);
        }
        for (Timeline timeline : list) {
            timeline.play();
        }
    }

    public static void moveDir(double distance, Direction direction, long cycle, long space, Node... nodes) {
        List<Timeline> list = new ArrayList<>();
        System.out.println(nodes.length);
        for (int i = 0; i < nodes.length; i++) {
            final Node node = nodes[i];
            if (node == null) {
                System.out.println("Null");
                continue;
            }
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(Math.max(5, space * i)), ev -> {
                node.setVisible(false);
                move(node, distance, 1, direction, cycle).play();
                node.setVisible(true);
            }));
            list.add(timeline);
        }
        for (Timeline timeline : list) {
            timeline.play();
        }
    }

    public static void fadeAndMove(Node node, double distance, Direction direction, long cycle) {
        Transition fade = fade(node, 0, 1, 1, cycle);
        Transition move = move(node, distance, 1, direction, cycle);
        fade.play();
        move.play();
        node.setVisible(true);
    }

    public static Transition fade(Node node, double from, double to, int count, long cycle) {
        FadeTransition fade = new FadeTransition();
        fade.setNode(node);
        fade.setDuration(Duration.millis(cycle));
        fade.setCycleCount(count);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(from);
        fade.setToValue(to);
        return fade;
    }

    public static Transition scale(Node node, double rate, int count, long cycle) {
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(node);
        scaleTransition.setDuration(Duration.millis(cycle));
        scaleTransition.setByX(rate);
        scaleTransition.setByY(rate);
        scaleTransition.setCycleCount(count);
        scaleTransition.setInterpolator(Interpolator.LINEAR);
        scaleTransition.setAutoReverse(true);
        return scaleTransition;
    }

    public static Transition move(Node node, double distance, int count, Direction direction, long cycle) {
        TranslateTransition move = new TranslateTransition();
        move.setNode(node);
        move.setDuration(Duration.millis(cycle));
        move.setCycleCount(count);
        move.setInterpolator(Interpolator.LINEAR);
        switch (direction) {
            case UP -> {
                node.setLayoutY(node.getLayoutY() + distance);
                move.setByY(-distance);
            }
            case DOWN -> {
                node.setLayoutY(node.getLayoutY() - distance);
                move.setByY(distance);
            }
            case LEFT -> {
                node.setLayoutX(node.getLayoutX() + distance);
                move.setByX(-distance);
            }
            case RIGHT -> {
                node.setLayoutX(node.getLayoutX() - distance);
                move.setByX(distance);
            }
        }

        return move;
    }
}
