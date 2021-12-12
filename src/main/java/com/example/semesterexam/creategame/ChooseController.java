package com.example.semesterexam.creategame;

import com.example.semesterexam.core.Direction;
import com.example.semesterexam.manage.GamePlay;
import com.example.semesterexam.tool.FigureInformation;
import com.example.semesterexam.tool.TransitionService;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseController implements Initializable {
    private final double BUTTON_WIDTH = 200;
    private final double BUTTON_MOVE_WIDTH = 100;
    private final double LOGO_WIDTH = 400;
    private final MapDisplay mapDisplay = new MapDisplay();
    private final FigureDisplay figureDisplay1 = new FigureDisplay();
    private final FigureDisplay figureDisplay2 = new FigureDisplay();
    private final IntegerProperty onSetupFigure = new SimpleIntegerProperty(1);
    private final DoubleProperty rateGain = new SimpleDoubleProperty(1d);
    private final IntegerProperty onChoose = new SimpleIntegerProperty(0);



    @FXML
    AnchorPane anchorPane;

    @FXML
    ImageView mapChoose;

    @FXML
    ImageView figureChoose;

    @FXML
    AnchorPane chooseMap;

    @FXML
    AnchorPane chooseFigure;

    @FXML
    ImageView curMap;

    @FXML
    ImageView nextMap;

    @FXML
    ImageView prevMap;

    @FXML
    ImageView nextFir;

    @FXML
    ImageView prevFir;

    @FXML
    TextField nameFir1;

    @FXML
    TextField nameFir2;

    @FXML
    ImageView moveFir;

    @FXML
    ImageView curFir;

    @FXML
    ImageView start;

    @FXML
    Text firType;

    @FXML
    ImageView playerN;

    @FXML
    ImageView nameMap;

    @FXML
    ImageView logoGame;

    MediaPlayer soundGame;
    MediaPlayer soundOnEnterStartIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Media media1 = new Media(new File(".\\Media\\Theme\\Theme.mp3").toURI().toString());
        Media media2 = new Media(new File(".\\Media\\Theme\\Start.mp3").toURI().toString());

        soundGame = new MediaPlayer(media1);
        soundGame.play();
        soundGame.setVolume(soundGame.getVolume() / 4);

        soundOnEnterStartIcon = new MediaPlayer(media2);


        setBackgroundAnchorPane(anchorPane, ".\\Background\\B4.jpg");

        Image logoBomberman = ImagesStart.LOGO_BOMBERMAN;
        logoGame.setImage(logoBomberman);
        double rate0 = LOGO_WIDTH / logoBomberman.getWidth();
        logoGame.setFitWidth(LOGO_WIDTH);
        logoGame.setFitHeight(logoBomberman.getHeight() * rate0);

        TransitionService.fadeAndMove(logoGame, 800, Direction.RIGHT, 1500);

        chooseFigure.setVisible(false);

        Image iconChooseMap = ImagesStart.CHOOSE_MAP_ON_EXIT;
        mapChoose.setImage(iconChooseMap);
        double rate = BUTTON_WIDTH / iconChooseMap.getWidth();
        mapChoose.setFitWidth(BUTTON_WIDTH);
        mapChoose.setFitHeight(iconChooseMap.getHeight() * rate);

        Image iconChooseFigure = ImagesStart.CHOOSE_FIGURE_ON_EXIT;
        figureChoose.setImage(iconChooseFigure);
        double rate2 = BUTTON_WIDTH / iconChooseFigure.getWidth();
        figureChoose.setFitWidth(BUTTON_WIDTH);
        figureChoose.setFitHeight(iconChooseFigure.getHeight() * rate2);

//        setBackgroundAnchorPane(chooseMap, ".\\Background\\1.png");

//        setBackgroundAnchorPane(chooseFigure, ".\\Background\\1.jpg");


//        Image iconNextMap = new Image(new File(".\\Picture\\Logo\\Start\\RightArrow.png").toURI().toString());
        Image iconNextMap = ImagesStart.NEXT_ON_EXIT;
        nextMap.setImage(iconNextMap);
        double rate3 = BUTTON_MOVE_WIDTH / iconNextMap.getWidth();
        nextMap.setFitWidth(BUTTON_MOVE_WIDTH);
        nextMap.setFitHeight(iconNextMap.getHeight() * rate3);

//        Image iconPrevMap = new Image(new File(".\\Picture\\Logo\\Start\\LeftArrow.png").toURI().toString());
        Image iconPrevMap = ImagesStart.PREV_ON_EXIT;
        prevMap.setImage(iconPrevMap);
        double rate4 = BUTTON_MOVE_WIDTH / iconPrevMap.getWidth();
        prevMap.setFitWidth(BUTTON_MOVE_WIDTH);
        prevMap.setFitHeight(iconPrevMap.getHeight() * rate4);

        mapDisplay.loadMaps();

        curMap.setImage(mapDisplay.curMap());

//        Image iconNextFir = new Image(new File(".\\Picture\\Logo\\Start\\RightArrow.png").toURI().toString());
        Image iconNextFir = ImagesStart.NEXT_ON_EXIT;
        nextFir.setImage(iconNextFir);
        double rate5 = BUTTON_MOVE_WIDTH / iconNextFir.getWidth();
        nextFir.setFitWidth(BUTTON_MOVE_WIDTH);
        nextFir.setFitHeight(iconNextFir.getHeight() * rate5);

//        Image iconPrevFir = new Image(new File(".\\Picture\\Logo\\Start\\LeftArrow.png").toURI().toString());
        Image iconPrevFir = ImagesStart.PREV_ON_EXIT;
        prevFir.setImage(iconPrevFir);
        double rate6 = BUTTON_MOVE_WIDTH / iconPrevFir.getWidth();
        prevFir.setFitWidth(BUTTON_MOVE_WIDTH);
        prevFir.setFitHeight(iconPrevFir.getHeight() * rate6);


        Image iconMoveFir = ImagesStart.TOP_ARROW;
        moveFir.setImage(iconMoveFir);
        double rate7 = BUTTON_MOVE_WIDTH / iconMoveFir.getWidth();
        moveFir.setFitWidth(BUTTON_MOVE_WIDTH);
        moveFir.setFitHeight(iconMoveFir.getHeight() * rate7);

        curFir.setImage(figureDisplay1.curFir());

        nameFir2.setVisible(false);

        Image iconStart = ImagesStart.START_ICON_ON_EXIT;
        start.setImage(iconStart);

        double rate8 = BUTTON_WIDTH * 3 / iconStart.getWidth();
        start.setFitWidth(BUTTON_WIDTH * 3);
        start.setFitHeight(iconStart.getHeight() * rate8);


        playerN.setImage(ImagesStart.PLAYER1);
        double rate9 = BUTTON_WIDTH / ImagesStart.PLAYER1.getWidth();
        playerN.setFitWidth(BUTTON_WIDTH);
        playerN.setFitHeight(ImagesStart.PLAYER1.getHeight() * rate9);

        onChoose.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                switch (newVal.intValue()) {
                    case 1 -> {
                        TransitionService.scale(mapChoose, 0.35, 1, 200).play();
                    }
                    case 2 -> {
                        TransitionService.scale(figureChoose, 0.35, 1, 200).play();
                    }
                }
                switch (oldVal.intValue()) {
                    case 1 -> {
                        TransitionService.scale(mapChoose, -0.35, 1, 200).play();
                    }
                    case 2 -> {
                        TransitionService.scale(figureChoose, -0.35, 1, 200).play();
                    }
                }
            }
        });

        onChoose.set(1);


    }


    private void setBackgroundAnchorPane(AnchorPane anchorPane, String path) {
        Image background = new Image(new File(path).toURI().toString(), anchorPane.getPrefWidth(), anchorPane.getPrefHeight(), false, true);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));
    }

    //    @FXML
    public void pressMapChoose(MouseEvent event) {
        chooseFigure.setVisible(false);
        chooseMap.setVisible(true);
        onChoose.set(1);
    }

    //    @FXML
    public void pressFigureChoose(MouseEvent event) {
        chooseFigure.setVisible(true);
        chooseMap.setVisible(false);
        onChoose.set(2);
    }

    public void nextMap(MouseEvent event) {
        Image image = mapDisplay.nextMap();
        if (image != null) {
            curMap.setVisible(false);
            curMap.setImage(image);
            TransitionService.fadeAndMove(curMap, 100, Direction.UP, 500);
        }
    }

    public void prevMap(MouseEvent event) {
        Image image = mapDisplay.prevMap();
        if (image != null) {
            curMap.setVisible(false);
            curMap.setImage(image);
            TransitionService.fadeAndMove(curMap, 100, Direction.DOWN, 500);
        }
    }

    public void nextFir(MouseEvent event) {
        Image image;
        if (onSetupFigure.get() == 1) {
            image = figureDisplay1.nextFir();
            firType.setText(figureDisplay1.getTypeFir());
        } else {
            image = figureDisplay2.nextFir();
            firType.setText(figureDisplay2.getTypeFir());
        }
        if (image != null) {
            curFir.setImage(image);
        }
    }

    public void moveSetupNextFir(MouseEvent event) {
        if (onSetupFigure.get() == 1) {
            onSetupFigure.set(2);
            nameFir1.setVisible(false);
            nameFir2.setVisible(true);
            curFir.setImage(figureDisplay2.curFir());
            playerN.setImage(ImagesStart.PLAYER2);
            firType.setText(figureDisplay2.getTypeFir());
        } else {
            onSetupFigure.set(1);
            nameFir1.setVisible(true);
            nameFir2.setVisible(false);
            curFir.setImage(figureDisplay1.curFir());
            playerN.setImage(ImagesStart.PLAYER1);
            firType.setText(figureDisplay1.getTypeFir());
        }
    }

    public void start(MouseEvent event) {
        soundGame.stop();
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        GamePlay gamePlay = new GamePlay(stage, figureDisplay1.getCurFir(), figureDisplay2.getCurFir());
        gamePlay.setFolderMap(mapDisplay.getCurFolderMap());
        String name1 = nameFir1.getText();
        if (name1.equals("")) {
            name1 = null;
        }
        String name2 = nameFir2.getText();
        if (name2.equals("")) {
            name2 = null;
        }
        if (name1 == null && name2 == null) {
            name1 = "Player1";
        }
        gamePlay.setNames(name1, name2);
        gamePlay.play();
    }

    public void onEnterChooseMapIcon(MouseEvent event) {
        mapChoose.setImage(ImagesStart.CHOOSE_MAP_ON_ENTER);
    }

    public void onExitChooseMapIcon(MouseEvent event) {
        mapChoose.setImage(ImagesStart.CHOOSE_MAP_ON_EXIT);
    }

    public void onEnterChooseFigureIcon(MouseEvent event) {
        figureChoose.setImage(ImagesStart.CHOOSE_FIGURE_ON_ENTER);
    }

    public void onExitChooseFigureIcon(MouseEvent event) {
        figureChoose.setImage(ImagesStart.CHOOSE_FIGURE_ON_EXIT);
    }

    public void onEnterStartIcon(MouseEvent event) {
        start.setImage(ImagesStart.START_ICON_ON_ENTER);
        soundOnEnterStartIcon.stop();
        soundOnEnterStartIcon.play();
    }

    public void onExitStartIcon(MouseEvent event) {
        start.setImage(ImagesStart.START_ICON_ON_EXIT);
        soundOnEnterStartIcon.stop();
    }

    public void onEnterNextMapIcon(MouseEvent event) {
        nextMap.setImage(ImagesStart.NEXT_ON_ENTER);
    }

    public void onExitNextMapIcon(MouseEvent event) {
        nextMap.setImage(ImagesStart.NEXT_ON_EXIT);
    }

    public void onEnterPrevMapIcon(MouseEvent event) {
        prevMap.setImage(ImagesStart.PREV_ON_ENTER);
    }

    public void onExitPrevMapIcon(MouseEvent event) {
        prevMap.setImage(ImagesStart.PREV_ON_EXIT);
    }

    public void onEnterNextFirIcon(MouseEvent event) {
        nextFir.setImage(ImagesStart.NEXT_ON_ENTER);
    }

    public void onExitNextFirIcon(MouseEvent event) {
        nextFir.setImage(ImagesStart.NEXT_ON_EXIT);
    }

    public void onEnterPrevFirIcon(MouseEvent event) {
        prevFir.setImage(ImagesStart.PREV_ON_ENTER);
    }

    public void onExitPrevFirIcon(MouseEvent event) {
        prevFir.setImage(ImagesStart.PREV_ON_EXIT);
    }

}
