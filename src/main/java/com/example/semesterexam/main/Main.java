package com.example.semesterexam.main;


import com.example.semesterexam.manage.GamePlay;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
//        stage.setX(100);
//        stage.setY(0);
//        stage.setTitle("Bomberman");
//        stage.show();

//        GamePlay gamePlay = new GamePlay(stage);
//        gamePlay.setFolderMap(".\\Map\\Stage");
//        gamePlay.play();

        FXMLLoader fxmlLoader = new FXMLLoader(new File(".\\src\\main\\java\\com\\example\\semesterexam\\creategame\\choose.fxml").toURI().toURL());
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}