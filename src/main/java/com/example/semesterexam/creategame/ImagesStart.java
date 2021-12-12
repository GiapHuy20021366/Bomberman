package com.example.semesterexam.creategame;

import com.example.semesterexam.tool.FigureInformation;
import javafx.scene.image.Image;

import java.io.File;

public class ImagesStart {
    public static final Image PLAYER1 = new Image(new File(FigureInformation.PLAYER1).toURI().toString());
    public static final Image PLAYER2 = new Image(new File(FigureInformation.PLAYER2).toURI().toString());
    public static final Image CHOOSE_FIGURE_ON_ENTER = new Image(new File(".\\Picture\\Logo\\Start\\ChooseFigure2.png").toURI().toString());
    public static final Image CHOOSE_MAP_ON_ENTER = new Image(new File(".\\Picture\\Logo\\Start\\ChooseMap2.png").toURI().toString());
    public static final Image CHOOSE_MAP_ON_EXIT = new Image(new File(".\\Picture\\Logo\\Start\\ChooseMap.png").toURI().toString());
    public static final Image CHOOSE_FIGURE_ON_EXIT = new Image(new File(".\\Picture\\Logo\\Start\\ChooseFigure.png").toURI().toString());
    public static final Image START_ICON_ON_EXIT = new Image(new File(".\\Picture\\Logo\\Start\\startIcon.png").toURI().toString());
    public static final Image START_ICON_ON_ENTER = new Image(new File(".\\Picture\\Logo\\Start\\StartIcon2.png").toURI().toString());
    public static final Image LOGO_BOMBERMAN = new Image(new File(".\\Picture\\Logo\\Start\\Bomberman.png").toURI().toString());
    public static final Image TOP_ARROW = new Image(new File(".\\Picture\\Logo\\Start\\TopArrow.png").toURI().toString());
    public static final Image PREV_ON_EXIT = new Image(new File(".\\Picture\\Logo\\Start\\Prev0.png").toURI().toString());
    public static final Image PREV_ON_ENTER = new Image(new File(".\\Picture\\Logo\\Start\\Prev1.png").toURI().toString());
    public static final Image NEXT_ON_EXIT = new Image(new File(".\\Picture\\Logo\\Start\\Next0.png").toURI().toString());
    public static final Image NEXT_ON_ENTER = new Image(new File(".\\Picture\\Logo\\Start\\Next1.png").toURI().toString());



}
