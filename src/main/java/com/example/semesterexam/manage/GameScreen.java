package com.example.semesterexam.manage;

import com.example.semesterexam.core.Figure;
import com.example.semesterexam.core.Monster;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.figure.HuMan;
import com.example.semesterexam.figure.Magician;
import com.example.semesterexam.monster.Orc;
import com.example.semesterexam.monster.Skeleton;
import com.example.semesterexam.tool.Action;
import com.example.semesterexam.tool.ViewPlayer;
import com.example.semesterexam.weapon.Boom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class GameScreen {

    private ObjectManagement management = new ObjectManagement();
    private Map map;
    private Figure character;
    private ViewPlayer viewPlayer;
    private ActionsManagement actions = new ActionsManagement();

    private DoubleProperty componentSize = new SimpleDoubleProperty();


    public GameScreen() {

    }

    public Scene getGame() throws IOException {
        // Default size everything = 45
        componentSize.set(40);

        // Load Actions
        actions.loadAllActionsFrom(".\\Action\\BasicActions.txt");
        actions.loadAllActionsFrom(".\\Action\\OrcPack.txt");
        actions.loadAllActionsFrom(".\\Action\\SkeletonPack.txt");
        actions.loadAllActionsFrom(".\\Action\\FirePack.txt");
        actions.loadAllActionsFrom(".\\Action\\ArrowPack.txt");
        actions.loadAllActionsFrom(".\\Action\\HuManPack.txt");
        actions.loadAllActionsFrom(".\\Action\\MagicianPack.txt");


        // Set management
        management.gameScreen = this;

        // Create Character to control
        character = new HuMan(this);
        character.setName("Magician");
        character.setDefaultLocation(1, 1);
        character.setDefaultSize(1d);
        character.addAllActions();

//        character.addWeapon(new Archery(character, this));
        character.addAllWeapon();

        management.addCharacter("Main player", character);

        // Set Control for Character
        character.startMoving();

        // Create Map
        map = new Map(".\\Map\\DefaultMap\\Basicmap.txt", this);
//        map.setGameScreen(this);

        // Add Character to Map
        map.getChildren().add(character);

        // Set Background of map
        map.setBackgroundBy(".\\Background\\GreenBackground.png");

        // Create a view play of map
        viewPlayer = new ViewPlayer(map);

        // Move viewport of map by character
        viewPlayer.moveViewportBy((Figure) character);


//        for (int i = 15 ;i < 30 ; i ++) {
//            insertBoss(i, 1, i + "");
//        }

        Monster monster1 = new Skeleton(this);
        monster1.setName("Orc");
        monster1.setDefaultSize(1.2d);
        monster1.setDefaultLocation(1, 10);
        monster1.addAllActions();
        monster1.setDefaultDirection(Monster.Go.GO_UP);
        monster1.startCauseDamage();
        monster1.startMoving();
        management.addMonster(monster1);
        map.getChildren().add(monster1);
        // Create Game
        Scene game = new Scene(viewPlayer);


        // Set control for character
        game.setOnKeyPressed(character.getKeyPressedEvent());
        game.setOnKeyReleased(character.getKeyKeyReleasedEvent());

        // Using for resize
        componentSize.addListener(((observableValue, oldValue, newValue) -> {
            map.defineHeightWidth();
            management.reDraw();
            viewPlayer.moveViewport();
        }));

        monsters();

        Action action = getAction("Huy @ test!");

        return game;
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

    public void setVision(double rateVision) {
        double newSize = componentSize.get() * rateVision;
        if (newSize * map.getMAX_ROW() < character.getViewValue() * 2) return;
        if (newSize * map.getMAX_COLUMN() < character.getViewValue() * 2) return;

        componentSize.set(newSize);
        management.resetDefaultSpeed(rateVision);

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
        monster1.setDefaultDirection(Monster.Go.GO_RIGHT);
        monster1.startCauseDamage();
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

    public Character getCharacter() {
        return character;
    }

    public void testBooms(double i, double j) throws IOException {
        Boom boom = new Boom(i * componentSize.get(), j * componentSize.get(), this);
        boom.setDamage(100000);
        boom.setRange(5d);
        map.getChildren().add(boom);
        management.addBoom(boom);
    }

    // For test
    public void monsters() {
        Timeline h = new Timeline(new KeyFrame(Duration.millis(1500), ev -> {
            Monster monster1 = null;
            Random random = new Random();
            try {
                int hex = random.nextInt(100);
                if (hex < 50) monster1 = new Orc(this);
                else monster1 = new Skeleton(this);

            } catch (IOException e) {
                e.printStackTrace();
            }
            monster1.setDefaultSize(1d);
            monster1.setDefaultSpeed(character.getDefaultSpeed() / 2d);
            monster1.setDefaultLocation(2, 1);
            monster1.addAllActions();
            monster1.setDefaultDirection(Monster.Go.GO_RIGHT);
            monster1.startCauseDamage();
            monster1.startMoving();
            management.addMonster(monster1);
            map.getChildren().add(monster1);
        }));

        h.setCycleCount(-1);
        h.play();
    }
}
