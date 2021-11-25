package com.example.semesterexam.main;

import com.example.semesterexam.manage.GameScreen;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Controller {
    @FXML
    BorderPane borderPane;
    @FXML
    private Pane game;

    @FXML
    private Button button;

    @FXML
    protected void display() {
        System.out.println("OK");
    }

    @FXML
    protected void setGame() {
        button.setVisible(false);
        GameScreen gameScreen = new GameScreen();
//        try {
//            borderPane.setCenter(gameScreen.getGame());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        borderPane.setOnKeyPressed(gameScreen.getKeyPressedEvent());
        borderPane.setOnKeyReleased(gameScreen.getKeyReleasedEvent());


    }
}
