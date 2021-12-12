package com.example.semesterexam.manage;

import com.example.semesterexam.core.Direction;
import com.example.semesterexam.core.Figure;
import com.example.semesterexam.tool.ViewPlayer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Control {
    Figure figure1;
    Figure figure2;
    ViewPlayer viewPlayer;

    public Control(Figure figure1, Figure figure2, ViewPlayer viewPlayer) {
        this.figure1 = figure1;
        this.figure2 = figure2;
        this.viewPlayer = viewPlayer;
    }

    public void setGo(Figure figure, Direction direction, boolean b) {
        if (figure != null) {
            figure.setGo(direction, b);
        } else {
            if (figure1 == null) {
                if (figure2 != null) {
                    figure2.setGo(direction, b);
                }
            } else if (figure2 == null) {
                figure1.setGo(direction, b);
            }
        }
    }

    public void moveViewPort(Figure figure) {
        if (figure != null) {
            viewPlayer.moveViewportBy(figure);
        } else {
            if (figure1 == null) {
                if (figure2 != null) {
                    viewPlayer.moveViewportBy(figure2);
                }
            } else if (figure2 == null) {
                viewPlayer.moveViewportBy(figure1);
            }
        }
    }

    private void setAttack(Figure figure, int i) {
        if (figure != null) {
            figure.setAttack(i);
        } else {
            if (figure1 == null) {
                if (figure2 != null) {
                    figure2.setAttack(i);
                }
            } else if (figure2 == null) {
                figure1.setAttack(i);
            }
        }
    }

    private void attack(Figure figure) {
        if (figure != null) {
            figure.attack();
        } else {
            if (figure1 == null) {
                if (figure2 != null) {
                    figure2.attack();
                }
            } else if (figure2 == null) {
                figure1.attack();
            }
        }
    }

    public EventHandler<KeyEvent> getKeyPressedEvent() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case N -> viewPlayer.getMap().getGameScreen().getManagement().hideAll();

                    case W -> setGo(figure1, Direction.UP, true);
                    case UP -> setGo(figure2, Direction.UP, true);

                    case S -> setGo(figure1, Direction.DOWN, true);
                    case DOWN -> setGo(figure2, Direction.DOWN, true);

                    case A -> setGo(figure1, Direction.LEFT, true);
                    case LEFT -> setGo(figure2, Direction.LEFT, true);

                    case D -> setGo(figure1, Direction.RIGHT, true);
                    case RIGHT -> setGo(figure2, Direction.RIGHT, true);

                    case Y -> moveViewPort(figure1);
                    case U -> moveViewPort(figure2);

                    case B -> attack(figure1);
                    case SPACE -> attack(figure2);

                    case NUMPAD1 -> setAttack(figure1, 1);

                    case NUMPAD2 -> setAttack(figure1, 2);

                    case NUMPAD3 -> setAttack(figure1, 3);

                    case NUMPAD4 -> setAttack(figure1, 4);

                    case DIGIT1 -> setAttack(figure2, 1);

                    case DIGIT2 -> setAttack(figure2, 2);

                    case DIGIT3 -> setAttack(figure2, 3);

                    case DIGIT4 -> setAttack(figure2, 4);


                }
            }
        };
    }

    public EventHandler<KeyEvent> getKeyKeyReleasedEvent() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W -> setGo(figure1, Direction.UP, false);
                    case UP -> setGo(figure2, Direction.UP, false);

                    case S -> setGo(figure1, Direction.DOWN, false);
                    case DOWN -> setGo(figure2, Direction.DOWN, false);

                    case A -> setGo(figure1, Direction.LEFT, false);
                    case LEFT -> setGo(figure2, Direction.LEFT, false);

                    case D -> setGo(figure1, Direction.RIGHT, false);
                    case RIGHT -> setGo(figure2, Direction.RIGHT, false);


                }
            }
        };
    }
}
