package com.example.semesterexam.manage;

import com.example.semesterexam.core.Figure;
import com.example.semesterexam.core.Monster;
import com.example.semesterexam.core.Subject;
import com.example.semesterexam.core.Wall;
import com.example.semesterexam.figure.SuperHuMan;
import com.example.semesterexam.lanscape.ConcreteWall;
import com.example.semesterexam.lanscape.Gate;
import com.example.semesterexam.lanscape.SoftWall;
import com.example.semesterexam.monster.*;
import com.example.semesterexam.tool.MiniCartoon;
import com.example.semesterexam.weapon.Boom;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

public class MiniMap extends AnchorPane {
    //    private final AnchorPane mini = new AnchorPane();
    private final GameScreen gameScreen;
    private final HashSet<MiniCartoon> miniCartoons = new HashSet<>();
    private final DoubleProperty nodeSize = new SimpleDoubleProperty(2);
    private final com.example.semesterexam.manage.Map map;
    private final DoubleProperty maxLengthWidth = new SimpleDoubleProperty(360d);
    private final Image COLOR1 = new Image(new File(".\\Picture\\color\\Color1.png").toURI().toString());
    private final Image COLOR2 = new Image(new File(".\\Picture\\color\\Color2.png").toURI().toString());
    private final Image COLOR3 = new Image(new File(".\\Picture\\color\\Color3.png").toURI().toString());
    private final Image COLOR4 = new Image(new File(".\\Picture\\color\\Color4.png").toURI().toString());
    private final Image COLOR5 = new Image(new File(".\\Picture\\color\\Color5.png").toURI().toString());
    private final Image COLOR6 = new Image(new File(".\\Picture\\color\\Color6.png").toURI().toString());
    private final Image COLOR7 = new Image(new File(".\\Picture\\color\\Color7.png").toURI().toString());
    private final Image COLOR8 = new Image(new File(".\\Picture\\color\\Color8.png").toURI().toString());
    private final Image COLOR9 = new Image(new File(".\\Picture\\color\\Color9.png").toURI().toString());
    private final Image COLOR10 = new Image(new File(".\\Picture\\color\\Color10.png").toURI().toString());
    private final Image COLOR11 = new Image(new File(".\\Picture\\color\\Color11.png").toURI().toString());
    private final Image COLOR12 = new Image(new File(".\\Picture\\color\\Color12.png").toURI().toString());


    public MiniMap(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.map = gameScreen.getMap();

//        nodeSize.bind(maxLengthWidth.divide(map.getMAX_COLUMN()));
//        this.getChildren().add(mini);

//        System.out.println(nodeSize.get());

        Image background = new Image(new File(".\\Background\\GreenBackground.png").toURI().toString(), maxLengthWidth.get(), maxLengthWidth.get(), false, true);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.setBackground(new Background(backgroundImage));
    }

    private void setNodeSize(Map map) {
        if (map.getMAX_COLUMN() > map.getMAX_ROW()) {
            nodeSize.set(maxLengthWidth.get() / map.getMAX_COLUMN());
        } else {
            nodeSize.set(maxLengthWidth.get() / map.getMAX_ROW());
        }
    }



    public void load() {

        getChildren().clear();


        setNodeSize(map);



        for (java.util.Map.Entry<String, Monster> m : gameScreen.getManagement().getMonsters().entrySet()) {
            addMonsters(m.getValue());
            m.getValue().makeDetectFigure();
        }

        for (java.util.Map.Entry<String, Figure> f : gameScreen.getManagement().getFigures().entrySet()) {
            addFigures(f.getValue());
        }

        for (java.util.Map.Entry<Point2D, Wall> w : gameScreen.getManagement().getWalls().entrySet()) {
            addWalls(w.getValue());
        }

        for (Boom b : gameScreen.getManagement().getBooms()) {
            addBooms(b);
        }

        makeMiniCartoon(gameScreen.getManagement().getGate(), ".\\Picture\\Logo\\Frame\\frame3.png");

        loadMatrix();
    }

    public void addMonsters(Monster m) {
        if (m instanceof BossHuMan) {
            makeMiniCartoon(m, COLOR9);
        } else if (m instanceof Orc) {
            makeMiniCartoon(m, COLOR7);
        } else if (m instanceof Satan) {
            makeMiniCartoon(m, COLOR6);
        } else if (m instanceof Skeleton) {
            makeMiniCartoon(m, COLOR5);
        } else if (m instanceof Winged) {
            makeMiniCartoon(m, COLOR8);
        } else if (m instanceof Wolf) {
            makeMiniCartoon(m, COLOR12);
        } else {
            makeMiniCartoon(m, COLOR3);
        }
    }

    public void addFigures(Figure f) {
        if (f instanceof SuperHuMan) {
            makeMiniCartoon(f, COLOR1);
        } else {
            makeMiniCartoon(f, COLOR2);
        }

    }

    public void addWalls(Wall w) {
        if (w.getClass() == ConcreteWall.class) {
            makeMiniCartoon(w, COLOR4);
        } else if (w.getClass() == SoftWall.class) {
            makeMiniCartoon(w, COLOR11);
        } else if (w.getClass() == Gate.class) {
            makeMiniCartoon(w, COLOR10);
        }

    }

    public void addBooms(Boom b) {
        makeMiniCartoon(b, COLOR10);
    }

    private void makeMiniCartoon(Subject s, String path) {
        MiniCartoon miniCartoon = new MiniCartoon(this, s);
        miniCartoon.setImage(path);
        this.getChildren().add(miniCartoon);
        miniCartoons.add(miniCartoon);
    }

    private void makeMiniCartoon(Subject s, Image image) {
        MiniCartoon miniCartoon = new MiniCartoon(this, s);
        miniCartoon.setImage(image);
        this.getChildren().add(miniCartoon);
        miniCartoons.add(miniCartoon);
    }


    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public DoubleProperty getNodeSize() {
        return nodeSize;
    }

    public void setMaxLengthWidth(double v) {
        maxLengthWidth.set(v);
    }

    public void remove(MiniCartoon miniCartoon) {
        miniCartoons.remove(miniCartoon);
        this.getChildren().remove(miniCartoon);
    }

    public void removeOutMatrix(Point2D p) {
        int i = (int) p.getX();
        int j = (int) p.getY();
        matrix[j][i] = 1;
    }

    private int[][] matrix;

    public void loadMatrix() {

        matrix = new int[gameScreen.getMap().getMAX_ROW()][gameScreen.getMap().getMAX_COLUMN()];
        for (int[] ints : matrix) {
            Arrays.fill(ints, 1);
        }

        for (MiniCartoon miniCartoon : miniCartoons) {
            Wall w = miniCartoon.getIfWall();
            if (w != null) {
                Point2D p = w.point2D;
                int i = (int) p.getX();
                int j = (int) p.getY();
                matrix[j][i] = 0;
            }
        }


    }

    public int[][] getMatrix() {
        return matrix;
    }


}
