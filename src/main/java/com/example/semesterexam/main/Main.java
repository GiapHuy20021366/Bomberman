package com.example.semesterexam.main;


import com.example.semesterexam.manage.GameScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException {

//        FXMLLoader fxmlLoader = new FXMLLoader(new File(".\\src\\main\\resources\\com\\example\\semesterexam\\view.fxml").toURI().toURL());
//        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
//
//
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();

        GameScreen gameScreen = new GameScreen();
        stage.setScene(gameScreen.getGame());

        stage.setTitle("Bomberman");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}