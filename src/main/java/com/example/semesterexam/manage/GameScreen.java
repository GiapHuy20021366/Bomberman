package com.example.semesterexam.manage;

import com.example.semesterexam.core.Direction;
import com.example.semesterexam.core.Figure;
import com.example.semesterexam.core.Monster;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.figure.Magician;
import com.example.semesterexam.figure.SuperHuMan;
import com.example.semesterexam.monster.*;
import com.example.semesterexam.task.MyTask;
import com.example.semesterexam.tool.Action;
import com.example.semesterexam.tool.FigureInformation;
import com.example.semesterexam.tool.ViewPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import com.example.semesterexam.creategame.FigureDisplay;

import java.io.IOException;
import java.util.HashMap;


public class GameScreen {

    private final ObjectManagement management = new ObjectManagement();
    private final Map map = new Map();
    private Figure character1;
    private Figure character2;

    public static final String SUPERHUMAN = "SuperHuMan";
    public static final String MAGICIAN = "Magician";

    private ViewPlayer viewPlayer;
    private final ActionsManagement actions = new ActionsManagement();

    private final DoubleProperty componentSize = new SimpleDoubleProperty(80);
    private final HashMap<String, Timeline> loadingProcess = new HashMap<>();
    private MyTask task;
    private MiniMap miniMap;
    private MediaManagement mediaManagement;
    private GamePlay gamePlay;
    private int typeFir1 = 0;
    private int typeFir2 = 1;

    public void setGamePlay(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    public GamePlay getGamePlay() {
        return gamePlay;
    }

    public void setTypeFir(int type1, int type2) {
        this.typeFir1 = type1;
        this.typeFir2 = type2;
    }

    public MediaManagement getMediaManagement() {
        return mediaManagement;
    }

    public void loadNextGame(String fileMap) throws IOException {
        task.updateProgress(0d, 1d);
        task.updateMessage("Đang khởi tạo màn chơi tiếp theo...");
        setLoadingProcess("NextStage", 30, 1000, 0, 99);
        management.setAccess(false);
        management.clear();
        management.setMiniMap(null);
        map.createMap(fileMap);
        management.setAccess(true);

        if (character1 != null) {
            character1.setDefaultLocation(1, 3);
            character1.showPropertyInNewMap();
            character1.setStand();
            map.getChildren().add(character1);
        }
        if (character2 != null) {
            character2.setDefaultLocation(1, 2);
            character2.showPropertyInNewMap();
            character2.setStand();
            map.getChildren().add(character2);
        }


        management.setMiniMap(miniMap);

        miniMap.load();
        stopProgress("NextStage", 99);
    }

    public void loadFigure(String figure1, String figure2, Map map) throws IOException {
        if (figure1.equals(SUPERHUMAN)) {
            character1 = new SuperHuMan(this);
        } else {
            character1 = new Magician(this);
        }
        character1.setName(figure1 + character1);
        character1.setDefaultLocation(1, 1);
        character1.setDefaultSize(1d);
        character1.addAllActions();
        character1.showBloodBar();
        character1.addAllWeapon();
        management.addCharacter(character1);
        if (!map.getChildren().contains(character1)) {
            map.getChildren().add(character1);
        }

        // Set Control for Character
        character1.startMoving();


        if (figure2.equals(SUPERHUMAN)) {
            character2 = new SuperHuMan(this);
        } else {
            character2 = new Magician(this);
        }
        character2.setName(figure2 + character2);
        character2.setDefaultLocation(1, 2);
        character2.setDefaultSize(1d);
        character2.addAllActions();
        character2.showBloodBar();
        character2.addAllWeapon();
        management.addCharacter(character2);
        if (!map.getChildren().contains(character2)) {
            map.getChildren().add(character2);
        }

        // Set Control for Character
        character2.startMoving();


    }

    public void loadMap(String mapFilePath) throws IOException {
        map.setGameScreen(this);
        map.createMap(".\\Map\\Stage\\Stage1.txt");

    }


    public void setTask(MyTask task) {
        this.task = task;
    }

    public Scene getGame(String fileMap, String name1, String name2) throws IOException {
        if (gamePlay == null) {
            throw new UnsupportedOperationException("Set up GamePlay Yet");
        }
        // Default size everything = 45
        componentSize.set(80);
        management.setGameScreen(this);
        mediaManagement = new MediaManagement(".\\Media\\AllPathSound.txt");
        System.out.println("Here");

        task.updateProgress(0d, 1d);

        // Loading actions
        task.updateMessage("Đang thiết lập đồ họa");
        setLoadingProcess("Graphic", 30, 1000, 0, 50);
        actions.loadAllActionsFolder(".\\Action");
        stopProgress("Graphic", 50);
        task.updateMessage("Thành công");


        // Create Map
        task.updateMessage("Đang khởi tạo bản đồ chính...");
        setLoadingProcess("Map", 30, 500, 50, 60);
        map.setGameScreen(this);
        map.createMap(fileMap);
        // Set Background of map
        map.setBackgroundBy(".\\Background\\GreenBackground.png");
        stopProgress("Map", 60);
        task.updateMessage("Thành công");


        task.updateMessage("Đang khởi tạo bản đồ thu nhỏ...");
        setLoadingProcess("MiniMap", 30, 500, 60, 70);
        miniMap = new MiniMap(this);
        miniMap.load();
        miniMap.setLayoutX(10);
        miniMap.setLayoutY(360);
        management.setMiniMap(miniMap);
        stopProgress("MiniMap", 70);
        task.updateMessage("Thành công");
        // Set management


        task.updateMessage("Đang khởi tạo nhân vật");
        setLoadingProcess("Figure", 30, 500, 70, 80);
        if (name1 != null) {
            switch (typeFir1) {
                case FigureDisplay.SUPERHUMAN -> {
                    character1 = new SuperHuMan(this);
                }
                case FigureDisplay.MAGICIAN -> {
                    character1 = new Magician(this);
                }
            }
            character1.setName(name1);
            character1.setDefaultLocation(1, 3);
            character1.setDefaultSize(1d);
            character1.addAllActions();
            character1.showBloodBar();
            character1.addAllWeapon();
            management.addCharacter(character1);
            map.getChildren().add(character1);
            miniMap.addFigures(character1);
            // Set Control for Character
            character1.startMoving();

        }

        if (name2 != null) {
            switch (typeFir2) {
                case FigureDisplay.SUPERHUMAN -> {
                    character2 = new SuperHuMan(this);
                }
                case FigureDisplay.MAGICIAN -> {
                    character2 = new Magician(this);
                }
            }
            character2.setName(name2);
            character2.setDefaultLocation(1, 4);
            character2.setDefaultSize(1d);
            character2.addAllActions();
            character2.showBloodBar();
            character2.addAllWeapon();
            management.addCharacter(character2);
            map.getChildren().add(character2);
            miniMap.addFigures(character2);
            // Set Control for Character
            character2.startMoving();
        }


        stopProgress("Figure", 80);
        task.updateMessage("Thành công");


        task.updateMessage("Đang thiết lập điều khiển nhân vật");
        setLoadingProcess("Control", 30, 500, 80, 90);
        // Create a view play of map
        viewPlayer = new ViewPlayer(map);

        // Move viewport of map by character
        viewPlayer.moveViewportBy(character1);

//        monsters();

        // Create Game
        Scene game = new Scene(viewPlayer, 1280, 720);


        // Control
        Control control = new Control(character1, character2, viewPlayer);
        game.setOnKeyPressed(control.getKeyPressedEvent());
        game.setOnKeyReleased(control.getKeyKeyReleasedEvent());

        stopProgress("Control", 90);


        task.updateMessage("Đang thiết lập màn chơi...");
        setLoadingProcess("Stage", 30, 500, 90, 100);

        // Using for resize
        componentSize.addListener(((observableValue, oldValue, newValue) -> {
            map.defineHeightWidth();
            viewPlayer.moveViewport();
        }));

        FigureInformation information = new FigureInformation(character1, character2);
        viewPlayer.getChildren().add(information);
        information.show();

        // Add miniMap
        information.getChildren().add(miniMap);
        miniMap.toFront();
        stopProgress("Stage", 100);

        task.updateMessage("Khởi tạo hoàn tất, đang tiến hành vào game...");

        return game;
    }

    public EventHandler<KeyEvent> getKeyPressedEvent() {
        return character1.getKeyPressedEvent();
    }

    public EventHandler<KeyEvent> getKeyReleasedEvent() {
        return character1.getKeyKeyReleasedEvent();
    }


    public ObjectManagement getManagement() {
        return this.management;
    }

    public Map getMap() {
        return map;
    }

    public double getComponentSize() {
        return componentSize.get();
    }

    public DoubleProperty getSizeProperties() {
        return componentSize;
    }

    public void setVision(double rateVision) {
        double newSize = componentSize.get() * rateVision;
        if (newSize * map.getMAX_ROW() < character1.getViewValue() * 2) return;
        if (newSize * map.getMAX_COLUMN() < character1.getViewValue() * 2) return;

        componentSize.set(newSize);
    }

    public void sacking() {
        viewPlayer.shacking(0, 2000);
    }

    public void insertBoss(int x, int y, String name) throws IOException {
        Monster monster1 = new Orc(this);
        monster1.setName(name);
        monster1.setDefaultSize(1d);
        monster1.setDefaultLocation(x, y);
        monster1.addAllActions();
        monster1.setDefaultDirection(Direction.RIGHT);
        monster1.startMoving();
        management.addMonster(monster1);
        map.getChildren().add(monster1);
    }

    public ViewPlayer getViewPlayer() {
        return viewPlayer;
    }

    public Action getAction(String actionName) {
        Action action = actions.getAction(actionName);
        if (action == null) {
            System.out.println("Warning!!! Action: " + actionName + " doesn't exist!");
        }
        return action;
    }

    public boolean dieAll() {
        if (character1 != null && character2 != null) {
            return character1.getHP() <= 0 && character2.getHP() <= 0;
        }
        if (character1 != null) {
            return character1.getHP() <= 0;
        }
        return character2.getHP() <= 0;
    }

    private void setLoadingProcess(String name, int count, long time, double min, double max) {

        final double amount = (max - min) / count;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time / (double) count), ev -> {
            task.updateProgress(Math.min(task.getProgress() * 100d + amount, max), 100);
        }));
        timeline.setCycleCount(count);
        loadingProcess.put(name, timeline);
        timeline.play();
    }

    private void stopProgress(String name, double finalProcess) {
        loadingProcess.get(name).stop();
        loadingProcess.remove(name);
        task.updateProgress(finalProcess, 100);
    }

    // For test
    public void monsters() {
        Monster m = null;
        try {
            m = new BossHuMan(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert m != null;
        m.setDefaultSize(1d);
        m.setDefaultLocation(9, 1);
        m.addAllActions();
        m.addAllWeapon();
        m.setDefaultDirection(Direction.RIGHT);
        m.setCanChangeDirection(true);
        m.startMoving();
        m.showBloodBar();
        getManagement().addMonster(m);
        getMap().getChildren().add(m);
    }

    public MiniMap getMiniMap() {
        return miniMap;
    }

    public Figure getFigure1() {
        return character1;
    }

    public Figure getFigure2() {
        return character2;
    }
}
