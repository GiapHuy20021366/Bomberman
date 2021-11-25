package com.example.semesterexam.manage;

import com.example.semesterexam.core.Direction;
import com.example.semesterexam.core.Monster;
import com.example.semesterexam.core.Wall;
import com.example.semesterexam.lanscape.ConcreteWall;
import com.example.semesterexam.lanscape.Grass;
import com.example.semesterexam.lanscape.SoftWall;
import com.example.semesterexam.monster.Orc;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Map extends AnchorPane {
    private double HEIGHT = 0;
    private double WIDTH = 0;
    private int MAX_COLUMN = 0;
    private int MAX_ROW = 0;
//    public static double defaultSize = 1d;

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

        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        int i = -1;
        int maxColumns = 0;
        while (scanner.hasNextLine()) {
            i++;
            String line = scanner.nextLine().trim();
            for (int k = 0; k < line.length(); k++) {
                if (k > maxColumns) maxColumns = k;
                char c = line.charAt(k);
                if (c == '1') {
                    Wall wall = new ConcreteWall(k * gameScreen.getComponentSize(), gameScreen.getComponentSize() * i, gameScreen);

                    wall.setPoint2D(new Point2D((double) k, (double) i));

                    wall.setFitWidth(gameScreen.getComponentSize());
                    wall.setFitHeight(gameScreen.getComponentSize());

                    wall.addActions("Default", gameScreen.getAction("WallCungPack:Default"));
                    wall.setActions("Default");
//                    wall.setDefaultSize(Map.defaultSize);

                    getChildren().add(wall);

                    gameScreen.getManagement().addWall(wall);
                } else if (c == '2') {
                    Wall wall = new SoftWall(k * gameScreen.getComponentSize(), gameScreen.getComponentSize() * i, gameScreen);

                    wall.setPoint2D(new Point2D((double) k, (double) i));

                    wall.setFitWidth(gameScreen.getComponentSize());
                    wall.setFitHeight(gameScreen.getComponentSize());
                    wall.addActions("Default", gameScreen.getAction("WallMemPack:Default"));
                    wall.setActions("Default");

                    getChildren().add(wall);

                    gameScreen.getManagement().addWall(wall);
                } else if (c == 'b') {
                    Monster monster1 = new Orc(gameScreen);
//                    monster1.setName(i + "" + k);
                    monster1.setDefaultSize(1d);
                    monster1.setDefaultLocation(k, i);
                    monster1.addAllActions();
                    monster1.setDefaultDirection(Direction.RIGHT);
                    monster1.startCauseDamage();
                    monster1.startMoving();
                    gameScreen.getManagement().addMonster(monster1);
                    this.getChildren().add(monster1);
                }

                Grass grass = new Grass(k * gameScreen.getComponentSize(), gameScreen.getComponentSize() * i, gameScreen);

                grass.setPoint2D(new Point2D((double) k, (double) i));

                grass.setFitWidth(gameScreen.getComponentSize());
                grass.setFitHeight(gameScreen.getComponentSize());
                grass.addActions("Default", gameScreen.getAction("GrassPack:Default"));
                grass.setActions("Default");

                getChildren().add(grass);

                gameScreen.getManagement().addGrass(grass);
                grass.toBack();
            }
        }

        MAX_COLUMN = maxColumns + 1;
        MAX_ROW = i + 1;

        defineHeightWidth();
    }


    public void setBackgroundBy(String filePath) {
        if (filePath == null) filePath = ".\\Backgorund\\backgroundMoon.jpg";

        Image background = new Image(new File(filePath).toURI().toString());

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
        WIDTH = MAX_COLUMN * gameScreen.getComponentSize();
        HEIGHT = MAX_ROW * gameScreen.getComponentSize();
    }

    public int getMAX_COLUMN() {
        return MAX_COLUMN;
    }

    public int getMAX_ROW() {
        return MAX_ROW;
    }

    public void setWIDTH(double width) {
        WIDTH = width;
    }

    public void setHEIGHT(double height) {
        HEIGHT = height;
    }
}
