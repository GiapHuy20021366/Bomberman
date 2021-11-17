package com.example.semesterexam.main;

import com.example.semesterexam.manage.GameScreen;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        GameScreen gameScreen = new GameScreen();
        stage.setScene(gameScreen.getGame());

        stage.setTitle("Bomberman");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}