package com.example.semesterexam.manage;

import com.example.semesterexam.core.Direction;
import com.example.semesterexam.core.Monster;
import com.example.semesterexam.core.Wall;
import com.example.semesterexam.creategame.MapDisplay;
import com.example.semesterexam.lanscape.*;
import com.example.semesterexam.monster.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Map extends AnchorPane {
    private double HEIGHT = 0;
    private double WIDTH = 0;
    private IntegerProperty MAX_COLUMN = new SimpleIntegerProperty(0);
    private IntegerProperty MAX_ROW = new SimpleIntegerProperty(0);

    private GameScreen gameScreen;

    public Map(String filePath, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        try {
            createMap(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map() {

    }

    public void createMap(String filePath) throws IOException {

        if (gameScreen == null) return;
        this.getChildren().clear();

        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        int i = -1;
        int maxColumns = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals("")) continue;
            i++;
            for (int k = 0; k < line.length(); k++) {
                if (k > maxColumns) maxColumns = k;
                char c = line.charAt(k);
                if (c != '1') {
                    Grass grass;
                    switch (MapDisplay.typeGrass) {
                        case 1 -> {
                            grass = new Grass(k * gameScreen.getComponentSize(), gameScreen.getComponentSize() * i, gameScreen);
                        }
                        case 2 -> {
                            grass = new Stone(k * gameScreen.getComponentSize(), gameScreen.getComponentSize() * i, gameScreen);
                        }
                        case 3 -> {
                            grass = new Lava(k * gameScreen.getComponentSize(), gameScreen.getComponentSize() * i, gameScreen);
                        }
                        default -> grass = new Grass(k * gameScreen.getComponentSize(), gameScreen.getComponentSize() * i, gameScreen);
                    }
                    grass.setPoint2D(new Point2D(k, i));
                    getChildren().add(grass);
                    gameScreen.getManagement().addGrass(grass);
                    grass.toBack();
                }
                switch (c) {
                    case '1' -> {
                        Wall wall = new ConcreteWall(k * gameScreen.getComponentSize(), gameScreen.getComponentSize() * i, gameScreen);
                        wall.setPoint2D(new Point2D(k, i));
                        getChildren().add(wall);
                        gameScreen.getManagement().addWall(wall);
                    }
                    case '2' -> {
                        Wall wall = new SoftWall(k * gameScreen.getComponentSize(), gameScreen.getComponentSize() * i, gameScreen);
                        wall.setPoint2D(new Point2D(k, i));
                        getChildren().add(wall);
                        gameScreen.getManagement().addWall(wall);
                    }
                    case 'B' -> {
                        Monster m = new BossHuMan(gameScreen);
                        setupMonster(m, k, i, 1d);
                    }
                    case 'O' -> {
                        Monster m = new Orc(gameScreen);
                        setupMonster(m, k, i, 1d);
                    }
                    case 'S' -> {
                        Monster m = new Satan(gameScreen);
                        setupMonster(m, k, i, 1d);
                    }
                    case 'K' -> {
                        Monster m = new Skeleton(gameScreen);
                        setupMonster(m, k, i, 1d);
                    }
                    case 'D' -> {
                        Monster m = new Winged(gameScreen);
                        setupMonster(m, k, i, 1d);
                    }
                    case 'F' -> {
                        Monster m = new Wolf(gameScreen);
                        setupMonster(m, k, i, 1d);
                    }
                    case 'Z' -> {
                        Monster m = new Zombie(gameScreen);
                        setupMonster(m, k, i, 1d);
                    }
                    case 'G' -> {
                        Gate gate = new Gate(k * gameScreen.getComponentSize(), gameScreen.getComponentSize() * i, gameScreen);
                        gate.setPoint2D(new Point2D(k, i));
                        getChildren().add(gate);
                        gameScreen.getManagement().addWall(gate);
                        gameScreen.getManagement().setGate(gate);
                        gate.toFront();
                    }
                }
            }
        }

        MAX_COLUMN.set(maxColumns + 1);
        MAX_ROW.set(i + 1);

//        System.out.println(MAX_COLUMN + " " + MAX_ROW);

        defineHeightWidth();
    }

    private void setupMonster(Monster monster1, int x, int y, double size) {
        monster1.setDefaultSize(1d);
        monster1.setDefaultLocation(x, y);
        monster1.addAllActions();
        monster1.addAllWeapon();
        monster1.setDefaultDirection(monster1.randomDirection());
        monster1.setCanChangeDirection(true);
        monster1.startMoving();
        monster1.showBloodBar();
        gameScreen.getManagement().addMonster(monster1);
        this.getChildren().add(monster1);
    }


    public void setBackgroundBy(String filePath) {
        if (filePath == null) filePath = ".\\Backgorund\\backgroundMoon.jpg";

        Image background = new Image(new File(filePath).toURI().toString(), 2400, 2400, false, true);

        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);

        setBackground(new Background(backgroundImage));

    }

    public double getHEIGHT() {
        return HEIGHT;
    }

    public double getWIDTH() {
        return WIDTH;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void defineHeightWidth() {
        WIDTH = MAX_COLUMN.get() * gameScreen.getComponentSize();
        HEIGHT = MAX_ROW.get() * gameScreen.getComponentSize();
    }

    public int getMAX_COLUMN() {
        return MAX_COLUMN.get();
    }

    public int getMAX_ROW() {
        return MAX_ROW.get();
    }

    public IntegerProperty maxColumnsProperty() {
        return MAX_COLUMN;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public IntegerProperty maxRowsProperty() {
        return MAX_ROW;
    }

}
