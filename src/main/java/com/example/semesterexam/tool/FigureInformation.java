package com.example.semesterexam.tool;

import com.example.semesterexam.core.Figure;
import com.example.semesterexam.figure.SuperHuMan;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.io.File;

public class FigureInformation extends AnchorPane {
    private final Figure figure1;
    private final Figure figure2;
    public static final String PLAYER1 = ".\\Picture\\Logo\\Player1.png";
    public static final String PLAYER2 = ".\\Picture\\Logo\\Player2.png";
    public static final String FRAME = ".\\Picture\\Logo\\Frame\\frame4.png";
    public static final String FRAME_NAME = ".\\Picture\\Logo\\Frame\\FrameName2.png";
    public static final String SUPERHUMAN_IMAGE = ".\\Picture\\Logo\\Frame\\SuperHuMan.png";
    public static final String MAGICIAN_IMAGE = ".\\Picture\\Logo\\Frame\\Magician.png";
    public static final String SUPERHUMAN_DIE_IMAGE = ".\\Picture\\Characters\\SuperHuMan\\Die\\Normal\\DieDown.png";
    public static final String MAGICIAN_DIE_IMAGE = ".\\Picture\\Characters\\Magician\\Die\\Normal\\DieDown.png";


    public FigureInformation(Figure figure1, Figure figure2) {
        this.figure1 = figure1;
        this.figure2 = figure2;

        this.setWidth(380);
        this.setHeight(720);
        this.setLayoutX(900);
        this.setLayoutY(0);

        Image background = new Image(new File(".\\Picture\\Logo\\FigureInf1.jpg").toURI().toString());
        ImageView overBackground = new ImageView(background);
        overBackground.setFitWidth(380);
        overBackground.setFitHeight(720);
        this.getChildren().add(overBackground);
        overBackground.toBack();
    }

    public void show() {

        if (figure1 != null) {
            if (figure1 instanceof SuperHuMan) {
                setup(figure1, 20, SUPERHUMAN_IMAGE, SUPERHUMAN_DIE_IMAGE, PLAYER1, FRAME);
            } else {
                setup(figure1, 20, MAGICIAN_IMAGE, MAGICIAN_DIE_IMAGE, PLAYER2, FRAME);
            }
        }
        if (figure2 != null) {
            if (figure2 instanceof SuperHuMan) {
                setup(figure2, 210, SUPERHUMAN_IMAGE, SUPERHUMAN_DIE_IMAGE, PLAYER1, FRAME);
            } else {
                setup(figure2, 210, MAGICIAN_IMAGE, MAGICIAN_DIE_IMAGE, PLAYER2, FRAME);
            }

        }

    }


    private void setup(Figure figure, double startX, String pathImage1, String pathImage2, String imgPlayerN, String frame) {



        Image image = new Image(new File(pathImage1).toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(190);
        imageView.setFitHeight(190);
        imageView.setX(startX - 20);
        imageView.setY(68);
        this.getChildren().add(imageView);

        Image frameImg = new Image(new File(frame).toURI().toString());
        ImageView imgFrame = new ImageView(frameImg);
        imgFrame.setFitWidth(190);
        imgFrame.setFitHeight(190);
        imgFrame.setX(startX - 20);
        imgFrame.setY(70);
        this.getChildren().add(imgFrame);



        Image player = new Image(new File(imgPlayerN).toURI().toString());
        ImageView imgPlayer = new ImageView(player);
        double rate = 150 / player.getWidth();
        imgPlayer.setFitWidth(player.getWidth() * rate);
        imgPlayer.setFitHeight(player.getHeight() * rate);
        imgPlayer.setX(startX);
        imgPlayer.setY(20);
        this.getChildren().add(imgPlayer);


        ProgressBar indicator = new ProgressBar();
        indicator.setPrefWidth(150);
        indicator.setLayoutX(startX);
        indicator.setLayoutY(250);
        indicator.progressProperty().bind(figure.getBlood().divide(figure.getMaxBlood()));
        indicator.setStyle("-fx-accent: green");
        indicator.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (t1.doubleValue() <= 0) {
                    imageView.setImage( new Image(new File(pathImage2).toURI().toString()));
                }
                if (t1.doubleValue() > 0.8d) {
                    indicator.setStyle("-fx-accent: green");
                } else if (t1.doubleValue() > 0.4d) {
                    indicator.setStyle("-fx-accent: yellow");
                } else {
                    indicator.setStyle("-fx-accent: red");
                }
            }
        });
        this.getChildren().add(indicator);


        Text name;
        String nameF = figure.getName();
        if (nameF.length() > 10) {
            name = new Text(nameF.substring(0, 10));
        } else {
            name = new Text(nameF);
        }
        name.setFont(Font.font("DejaVu Sans Mono", 20));
//        name.setStyle("-fx-font-size: 20px;" +
//                "-fx-font-weight: bold;");
        name.setFill(Color.YELLOWGREEN);
//        name.setX(startX + 75 - name.getBoundsInLocal().getWidth());
        name.setX(imageView.getX() + imageView.getFitWidth() / 2 - name.getBoundsInLocal().getWidth() / 2);
        name.setY(285);
        this.getChildren().add(name);

        Text score = new Text();
        Bindings.bindBidirectional(score.textProperty(), figure.scoreProperty(), new StringConverter<Number>() {

            @Override
            public String toString(Number number) {
                return "Score: " + number.intValue();
            }

            @Override
            public Number fromString(String s) {
                return Integer.parseInt(s);
            }

        });
//        score.setStyle("-fx-font-size: 20px;" +
//                "-fx-font-weight: bold;");
        score.setFont(Font.font("DejaVu Sans Mono", 25));
        score.setFill(Color.YELLOW);
//        score.setX(startX);
        score.xProperty().bind(imageView.xProperty().add(imageView.fitWidthProperty().divide(2)).subtract(score.boundsInLocalProperty().get().getWidth() / 2));
        score.setY(320);
        this.getChildren().add(score);



    }




}
