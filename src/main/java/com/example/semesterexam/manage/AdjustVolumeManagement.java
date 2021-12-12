package com.example.semesterexam.manage;

import com.example.semesterexam.core.Direction;
import com.example.semesterexam.manage.GamePlay;
import com.example.semesterexam.tool.TransitionService;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;

public class AdjustVolumeManagement extends AnchorPane {
    private final GamePlay gamePlay;
    private final double MAX_VOLUME = 40d;
    private final double CUR_VALUE = 8d;

    private final Slider backgroundVolume = new Slider();

    private final Slider figureAttackVolume = new Slider();
    private final Slider figureWalkVolume = new Slider();
    private final Slider figureWeaponVolume = new Slider();

    private final Slider monsterAttackVolume = new Slider();
    private final Slider monsterWalkVolume = new Slider();
    private final Slider monsterWeaponVolume = new Slider();

    private final Slider playerVolume = new Slider();


    private final ImageView close = new ImageView();
    private final ImageView title = new ImageView();

    private final Image END_GAME_NORMAL = new Image(new File(".\\Picture\\Logo\\GoHome\\EndGame0.png").toURI().toString());
    private final Image END_GAME_ENTER = new Image(new File(".\\Picture\\Logo\\GoHome\\EndGame1.png").toURI().toString());
    private final Image SOUNDS_SYSTEM = new Image(new File(".\\Picture\\Logo\\SetupIcon\\Sounds.png").toURI().toString());

    private final ImageView endGame = new ImageView(END_GAME_NORMAL);
    private final ImageView soundsSystem = new ImageView(SOUNDS_SYSTEM);

    public AdjustVolumeManagement(GamePlay gamePlay) {
        this.gamePlay = gamePlay;


        this.setLayoutX(900);
        this.setLayoutY(0);
        this.setVisible(false);

        this.setMaxSize(380, 720);

        double rateX = 250 / END_GAME_ENTER.getWidth();
        endGame.setFitWidth(END_GAME_NORMAL.getWidth() * rateX);
        endGame.setFitHeight(END_GAME_NORMAL.getHeight() * rateX);
        endGame.setX(180 - endGame.getFitWidth() / 2d);
        endGame.setY(650);
        this.getChildren().add(endGame);


        double rateY = 300 / SOUNDS_SYSTEM.getWidth();
        soundsSystem.setFitWidth(SOUNDS_SYSTEM.getWidth() * rateY);
        soundsSystem.setFitHeight(SOUNDS_SYSTEM.getHeight() * rateY);
        soundsSystem.setX(180 - endGame.getFitWidth() / 2d - 30);
        soundsSystem.setY(5);
        this.getChildren().add(soundsSystem);


        endGame.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    gamePlay.endGame();
                }
            }
        });

        endGame.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                endGame.setImage(END_GAME_ENTER);
            }
        });

        endGame.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                endGame.setImage(END_GAME_NORMAL);
            }
        });

        ImageView frame = new ImageView();
        frame.setFitWidth(380);
        frame.setFitHeight(720);
        this.getChildren().add(frame);
        frame.toBack();

        Image background = new Image(new File(".\\Picture\\Logo\\SetupIcon\\SoundBackgound.jpg").toURI().toString(), 380, 720, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.setBackground(new Background(backgroundImage));
        setup();
    }

    public void setup() {
        gamePlay.getGameScreen().getViewPlayer().getChildren().add(this);

        createSlider(playerVolume, "Âm thanh người chơi:", 120, gamePlay.volumePlayerProperty());
        createSlider(backgroundVolume, "Âm thanh nền:", 170, gamePlay.volumeBackgroundProperty());
        createSlider(figureAttackVolume, "Âm thanh nhân vật tấn công:",220, gamePlay.figureAttackVolumeProperty());
        createSlider(figureWalkVolume, "Âm thanh nhân vật di chuyển:", 270, gamePlay.figureWalkVolumeProperty());
        createSlider(figureWeaponVolume, "Âm thanh vũ khí nhân vật:",320, gamePlay.figureWeaponVolumeProperty());
        createSlider(monsterAttackVolume, "Âm thanh quái tấn công:", 370, gamePlay.monsterAttackVolumeProperty());
        createSlider(monsterWalkVolume, "Âm thanh quái di chuyển:", 420, gamePlay.monsterWalkVolumeProperty());
        createSlider(monsterWeaponVolume, "Âm thanh vũ khí quái vật:", 470, gamePlay.monsterWeaponVolumeProperty());

        gamePlay.setupButton();

    }

    private void createSlider(Slider slider, String name, double layoutY, DoubleProperty volumeBind) {
        Text text = new Text(name);
        text.setFont(Font.font("DejaVu Sans Mono", 20));
        text.setFill(Color.YELLOW);
        text.setLayoutX(5);
        text.setLayoutY(layoutY);
        this.getChildren().add(text);

        slider.setPrefWidth(360d);
        if (!this.getChildren().contains(slider)) {
            this.getChildren().add(slider);
        }
        slider.setValue(CUR_VALUE);
        slider.setLayoutY(layoutY + 10);
        slider.setLayoutX(10);
        slider.setMax(MAX_VOLUME);
        slider.setMin(0d);
        volumeBind.bind(slider.valueProperty().divide(100d));
    }


    public void show() {
        hideAllSlider();
//        setDefaultLayoutX();
        TransitionService.moveDir(100, Direction.LEFT, 200, 50,
                playerVolume,
                backgroundVolume,
                figureAttackVolume,
                figureWalkVolume,
                figureWeaponVolume,
                monsterAttackVolume,
                monsterWalkVolume,
                monsterWeaponVolume
        );
    }

    private void setDefaultLayoutX() {
        setLayoutX(backgroundVolume);
        setLayoutX(figureAttackVolume);
        setLayoutX(figureWalkVolume);
        setLayoutX(figureWeaponVolume);
        setLayoutX(monsterAttackVolume);
        setLayoutX(monsterWalkVolume);
        setLayoutX(monsterWeaponVolume);
        setLayoutX(playerVolume);
    }

    private void setLayoutX(Slider slider) {
        slider.setLayoutX(10);
    }

    private void hideAllSlider() {
        hideSlider(backgroundVolume);
        hideSlider(figureAttackVolume);
        hideSlider(figureWalkVolume);
        hideSlider(figureWeaponVolume);
        hideSlider(monsterAttackVolume);
        hideSlider(monsterWalkVolume);
        hideSlider(monsterWeaponVolume);
        hideSlider(playerVolume);
    }

    private void hideSlider(Slider slider) {
        if (slider != null) {
            slider.setVisible(false);
        }
    }


}
