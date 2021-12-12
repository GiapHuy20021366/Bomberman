package com.example.semesterexam.manage;

import com.example.semesterexam.core.Direction;
import com.example.semesterexam.core.Figure;
import com.example.semesterexam.figure.SuperHuMan;
import com.example.semesterexam.task.MyTask;
import com.example.semesterexam.tool.FigureInformation;
import com.example.semesterexam.tool.Player;
import com.example.semesterexam.tool.TransitionService;
import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GamePlay {
    private final DoubleProperty width = new SimpleDoubleProperty(1280);
    private final DoubleProperty height = new SimpleDoubleProperty(720);
    private final Stage stage;
    private final Text messageProgress = new Text();
    private ProgressBar bar;
    private final GameScreen gameScreen;
    private Scene game;
    private File[] maps;
    private int currentMap = 0;
    private AnimationTimer timer;
    private String name1 = "Player1";
    private String name2 = "Player2";
    private long startTime;
    private long endTime;

    private final Image finish = new Image(new File(".\\Picture\\Logo\\Finish.png").toURI().toString());
    private final Image GO_HOME_NORMAL = new Image(new File(".\\Picture\\Logo\\GoHome\\GoHomeRelease.png").toURI().toString());
    private final Image GO_HOME_FIRE = new Image(new File(".\\Picture\\Logo\\GoHome\\GoHomeThrill.png").toURI().toString());
    private final Image GO_HOME_CLICK = new Image(new File(".\\Picture\\Logo\\GoHome\\GoHomeEnter.png").toURI().toString());
    private final Image SETUP_VIEW = new Image(new File(".\\Picture\\Logo\\SetupIcon\\Setup.png").toURI().toString());

    private final DoubleProperty volumeBackground = new SimpleDoubleProperty(0.05d);
    private final DoubleProperty volumePlayer = new SimpleDoubleProperty(1d);
    private final DoubleProperty figureAttackVolume = new SimpleDoubleProperty(1d);
    private final DoubleProperty figureWalkVolume = new SimpleDoubleProperty(1d);
    private final DoubleProperty figureWeaponVolume = new SimpleDoubleProperty(1d);
    private final DoubleProperty monsterAttackVolume = new SimpleDoubleProperty(1d);
    private final DoubleProperty monsterWalkVolume = new SimpleDoubleProperty(1d);
    private final DoubleProperty monsterWeaponVolume = new SimpleDoubleProperty(1d);

    private final ImageView setupView = new ImageView();
    private final BooleanProperty setupMode = new SimpleBooleanProperty(false);



    private Player theme;
    private Player end;
    private AdjustVolumeManagement volumeView;


    public GamePlay(Stage stage, int type1, int type2) {
        this.stage = stage;
        this.gameScreen = new GameScreen();
        gameScreen.setTypeFir(type1, type2);

        setupMode.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    volumeView.setVisible(true);
                    volumeView.show();
                } else {
                    volumeView.setVisible(false);
                }
            }
        });
    }

    public void setupButton() {

        setupView.setFitWidth(50);
        setupView.setFitHeight(50);
        setupView.xProperty().bind(setupView.fitWidthProperty().multiply(-1).add(1280));
        setupView.setImage(SETUP_VIEW);
        gameScreen.getViewPlayer().getChildren().add(setupView);

        setupView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    setupMode.set(!setupMode.get());
                }
            }
        });
//
//        setupView.setOnMouseReleased(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
//                    home.setImage(GO_HOME_NORMAL);
//                }
//            }
//        });

//        setupView.setOnMouseEntered(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                setupMode.set(!setupMode.get());
//            }
//        });

//        home.setOnMouseExited(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                home.setImage(GO_HOME_NORMAL);
//                comeHome();
//            }
//        });

    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setNames(String name1, String name2) {
        if (maps == null) {
            return;
        }
        this.name1 = name1;
        this.name2 = name2;
    }

    public void setFolderMap(String folderMap) {
        File file = new File(folderMap);
        maps = file.listFiles();
    }

    public GamePlay(Stage stage, double width, double height) {
        this.stage = stage;
        this.width.set(width);
        this.height.set(height);
        this.gameScreen = new GameScreen();
    }

    public double getVolumeBackground() {
        return volumeBackground.get();
    }

    public DoubleProperty volumeBackgroundProperty() {
        return volumeBackground;
    }

    public double getVolumePlayer() {
        return volumePlayer.get();
    }

    public DoubleProperty volumePlayerProperty() {
        return volumePlayer;
    }

    public void play() {
        gameScreen.setGamePlay(this);



        if (maps == null) {
            System.out.println("Setup Map Folder Yet");
            return;
        }
        stage.setScene(loading());

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (gameScreen.dieAll()) {
                    currentMap++;
                    endGame();
                    return;
                }
                if (gameScreen.getManagement().countOfMonster() == 0) {
                    gameScreen.getManagement().getGate().setOpen(true);
                }
                if (gameScreen.getManagement().getGate().canMoveStage()) {
                    if (currentMap == maps.length) {
                        endGame();
                        stop();
                        return;
                    }
                    nextStage();
                    stop();
                }
            }
        };

        MyTask<Scene> task = new MyTask<>() {
            @Override
            protected Scene call() throws Exception {
                gameScreen.setTask(this);
                game = gameScreen.getGame(maps[currentMap++].toString(), name1, name2);
                volumeView = new AdjustVolumeManagement(gameScreen.getGamePlay());
                Thread.sleep(1000L);



//                volumeView.setVisible(true);
                return game;
            }

            @Override
            public void whatNext() {
                stage.setScene(this.getValue());
                timer.start();
                startTime = System.currentTimeMillis();
                theme = new Player(gameScreen, "Theme", Player.VOLUME_BACKGROUND);
                theme.autoRepeat();
                theme.play();
            }
        };

        messageProgress.textProperty().bind(task.messageProperty());

        bar.progressProperty().bind(task.progressProperty());

        task.execute();


    }

    public void endGame() {
        endTime = System.currentTimeMillis();
        timer.stop();
        theme.stop();
        finish();
        if (currentMap == maps.length && gameScreen.getManagement().countOfMonster() == 0) {
            end = new Player(gameScreen, "Win", Player.VOLUME_PLAYER);
        } else {
            end = new Player(gameScreen, "Lose", Player.VOLUME_PLAYER);
        }
        end.play();
        MyTask<Scene> task = new MyTask<>() {
            @Override
            protected Scene call() throws Exception {
                Thread.sleep(3500);
                return summary();
            }

            @Override
            public void whatNext() {
                stage.setScene(this.getValue());
            }
        };
        task.execute();

    }

    static String getTime(long startTime, long endTime) {
        SimpleDateFormat sdf3 = new SimpleDateFormat("mm:ss");
        Timestamp timestamp = new Timestamp(endTime - startTime);
        return sdf3.format(timestamp);
    }

    private void nextStage() {
        Group root = new Group();
        Scene scene = new Scene(root, width.get(), height.get());

        AnchorPane gameLoad = new AnchorPane();
        gameLoad.setPrefSize(scene.getWidth(), scene.getHeight());
        root.getChildren().add(gameLoad);

        messageProgress.setX(30);
        messageProgress.setY(height.get() - 70);
        messageProgress.setStyle("-fx-font-size: 40px;" +
                "-fx-font-weight: bold;");
        messageProgress.setFill(Color.RED);

        gameLoad.getChildren().add(messageProgress);

        Image image = new Image(new File(".\\Picture\\Logo\\Bomberman.gif").toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(image.getWidth() * 1.5d);
        imageView.setFitHeight(image.getHeight() * 1.5d);
        imageView.setX((gameLoad.getPrefWidth() - imageView.getFitWidth()) / 2d);
        imageView.setY(10);
        gameLoad.getChildren().add(imageView);

        bar = new ProgressBar();
        bar.setLayoutX(40);
        bar.setPrefWidth(1200);
        bar.setLayoutY(height.get() - 40);
        bar.setStyle("-fx-accent: yellow");
        gameLoad.getChildren().add(bar);


        Image background = new Image(new File(".\\Picture\\Logo\\ico.jpg").toURI().toString(), width.get(), height.get(), false, true);

        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);

        gameLoad.setBackground(new Background(backgroundImage));

        stage.setScene(scene);

        timer.stop();
        MyTask<Scene> task = new MyTask<>() {
            @Override
            protected Scene call() throws Exception {
                gameScreen.setTask(this);
                gameScreen.loadNextGame(maps[currentMap++].toString());
                Thread.sleep(1000L);
                return game;
            }

            @Override
            public void whatNext() {
                stage.setScene(this.getValue());
                timer.start();
            }
        };
        messageProgress.textProperty().bind(task.messageProperty());

        bar.progressProperty().bind(task.progressProperty());

        task.execute();

    }

    private void score(AnchorPane gameEnd, HashMap<String, Node> needToShow, Figure figure, double startX, int playerN) {
        if (figure == null) {
            System.out.println(playerN);
            return;
        }
        Image imageF;
        if (figure instanceof SuperHuMan) {
            imageF = new Image(new File(FigureInformation.SUPERHUMAN_IMAGE).toURI().toString());
        } else {
            imageF = new Image(new File(FigureInformation.MAGICIAN_IMAGE).toURI().toString());
        }
        ImageView imageView = new ImageView(imageF);
        imageView.setFitWidth(190);
        imageView.setFitHeight(190);
        imageView.setX(startX + 220 - imageView.getFitWidth() / 2);
        imageView.setY(75);
        imageView.setVisible(false);
        gameEnd.getChildren().add(imageView);
        needToShow.put("ImageFigure" + playerN, imageView);

        Image frameImg = new Image(new File(FigureInformation.FRAME).toURI().toString());
        ImageView imgFrame = new ImageView(frameImg);
        imgFrame.setFitWidth(imageView.getFitWidth());
        imgFrame.setFitHeight(imageView.getFitWidth());
        imgFrame.setX(imageView.getX());
        imgFrame.setY(imageView.getY() + 2);
        imgFrame.setVisible(false);
        gameEnd.getChildren().add(imgFrame);
        needToShow.put("ImageFrame" + playerN, imgFrame);


        Image player;
        if (playerN == 1) {
            player = new Image(new File(FigureInformation.PLAYER1).toURI().toString());
        } else {
            player = new Image(new File(FigureInformation.PLAYER2).toURI().toString());
        }
        ImageView imgPlayer = new ImageView(player);
        double rate = imageView.getFitWidth() / player.getWidth();
        imgPlayer.setFitWidth(player.getWidth() * rate);
        imgPlayer.setFitHeight(player.getHeight() * rate);
        imgPlayer.setX(imgFrame.getX() + (imgFrame.getFitWidth() - imgPlayer.getFitWidth()) / 2d);
        imgPlayer.setY(20);
        imgPlayer.setVisible(false);
        gameEnd.getChildren().add(imgPlayer);
        needToShow.put("ImagePlayer" + playerN, imgPlayer);


        Text name;
        String nameF = figure.getName();
        if (nameF.length() > 10) {
            name = new Text(nameF.substring(0, 10));
        } else {
            name = new Text(nameF);
        }
        name.setFont(Font.font("DejaVu Sans Mono", 25));
        name.setFill(Color.DARKOLIVEGREEN);
        name.setX(imageView.getX() + imageView.getFitWidth() / 2 - name.getBoundsInLocal().getWidth() / 2);
        name.setY(300);
        name.setVisible(false);
        gameEnd.getChildren().add(name);
        needToShow.put("NamePlayer" + playerN, name);
//        fadeAndMove(name, 100, Direction.RIGHT, 1200);


        Image frameName = new Image(new File(FigureInformation.FRAME_NAME).toURI().toString());
        ImageView imgFrameName = new ImageView(frameName);
        double rate2 = imageView.getFitWidth() / frameName.getWidth();
        imgFrameName.setFitWidth(frameName.getWidth() * rate2);
        imgFrameName.setFitHeight(frameName.getHeight() * rate2);
        imgFrameName.setX(imageView.getX() + imageView.getFitWidth() / 2d - imgFrameName.getFitWidth() / 2d);
        imgFrameName.setY(name.getY() - 45);
        imgFrameName.setVisible(false);
        gameEnd.getChildren().add(imgFrameName);
        needToShow.put("ImageFrameName" + playerN, imgFrameName);
        imgFrameName.toBack();


        Text score = new Text();
        score.setText("Tổng điểm: " + (int) figure.getScore());
        score.setFont(Font.font("DejaVu Sans Mono", 25));
        score.setFill(Color.YELLOW);
        score.setX(imageView.getX() + imageView.getFitWidth() / 2 - score.getBoundsInLocal().getWidth() / 2);
        score.setY(330);
        score.setVisible(false);
        gameEnd.getChildren().add(score);
        needToShow.put("ScorePlayer" + playerN, score);

        Text kill = new Text();
        kill.setText("Hạ gục: " + figure.getTotalKill());
        kill.setFont(Font.font("DejaVu Sans Mono", 25));
        kill.setFill(Color.YELLOW);
        kill.setX(imageView.getX() + imageView.getFitWidth() / 2 - kill.getBoundsInLocal().getWidth() / 2);
        kill.setY(360);
        kill.setVisible(false);
        gameEnd.getChildren().add(kill);
        needToShow.put("KillPlayer" + playerN, kill);

        Text damage = new Text();
        damage.setText("Sát thương: " + figure.getTotalDamage());
        damage.setFont(Font.font("DejaVu Sans Mono", 25));
        damage.setFill(Color.YELLOW);
        damage.setX(imageView.getX() + imageView.getFitWidth() / 2 - damage.getBoundsInLocal().getWidth() / 2);
        damage.setY(390);
        damage.setVisible(false);
        gameEnd.getChildren().add(damage);
        needToShow.put("DamagePlayer" + playerN, damage);

        double mvpPoint = figure.getScore() / Math.max(totalScore(), 1)
                + (double) figure.getTotalDamage() / Math.max(totalDamage(), 1)
                + (double) figure.getTotalKill() / Math.max(totalKill(), 1);
        Text mvp = new Text();
        mvp.setText("MVP Point: " + String.format("%.2f", mvpPoint * 10d).replace(',', '.'));
        mvp.setFont(Font.font("DejaVu Sans Mono", 25));
        mvp.setFill(Color.YELLOW);
        mvp.setX(imageView.getX() + imageView.getFitWidth() / 2 - mvp.getBoundsInLocal().getWidth() / 2);
        mvp.setY(420);
        mvp.setVisible(false);
        gameEnd.getChildren().add(mvp);
        needToShow.put("MvpPointPlayer" + playerN, mvp);


    }

    private void finish() {
        ImageView imageView = new ImageView(finish);
        double rate = 800 / finish.getWidth();
        imageView.setFitWidth(finish.getWidth() * rate);
        imageView.setFitHeight(finish.getHeight() * rate);
        imageView.setX((gameScreen.getViewPlayer().getScrollPane().getPrefWidth() - imageView.getFitWidth()) / 2d);
        imageView.setY((gameScreen.getViewPlayer().getScrollPane().getPrefHeight() - imageView.getFitHeight()) / 2d);
        imageView.setVisible(false);
        gameScreen.getViewPlayer().getChildren().add(imageView);

        TransitionService.fadeAndMove(imageView, 400, Direction.DOWN, 1200);


    }

    private Scene summary() {
        Group root = new Group();
        Scene scene = new Scene(root, width.get(), height.get());
        AnchorPane gameEnd = new AnchorPane();
        HashMap<String, Node> needToShow = new HashMap<>();
        gameEnd.setPrefSize(scene.getWidth(), scene.getHeight());
        root.getChildren().add(gameEnd);

        score(gameEnd, needToShow, gameScreen.getFigure1(), 0, 1);
        score(gameEnd, needToShow, gameScreen.getFigure2(), 840, 2);


        Image image;
        if (currentMap == maps.length && gameScreen.getManagement().countOfMonster() == 0) {
            image = new Image(new File(".\\Picture\\Logo\\Victory.png").toURI().toString());
        } else {
            image = new Image(new File(".\\Picture\\Logo\\Lose.png").toURI().toString());
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(image.getWidth() * 1.5d);
        imageView.setFitHeight(image.getHeight() * 1.5d);
        imageView.setX((gameEnd.getPrefWidth() - imageView.getFitWidth()) / 2d);
        imageView.setY(10);
        imageView.setVisible(false);
        gameEnd.getChildren().add(imageView);
        needToShow.put("LoseOrWin", imageView);

        Image background = new Image(new File(".\\Picture\\Logo\\MoonMove.gif").toURI().toString(), width.get(), height.get(), false, true);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gameEnd.setBackground(new Background(backgroundImage));


        Text time = new Text("Time: " + getTime(startTime, endTime));
        time.setFont(Font.font("DejaVu Sans Mono", 25));
        time.setFill(Color.DARKOLIVEGREEN);
        time.setX(imageView.getX() + imageView.getFitWidth() / 2 - time.getBoundsInLocal().getWidth() / 2);
        time.setY(160);
        time.setVisible(false);
        gameEnd.getChildren().add(time);
        needToShow.put("Time", time);


        Text totalScore = new Text("Tổng điểm: " + totalScore());
        totalScore.setFont(Font.font("DejaVu Sans Mono", 25));
        totalScore.setFill(Color.DARKOLIVEGREEN);
        totalScore.setX(imageView.getX() + imageView.getFitWidth() / 2 - totalScore.getBoundsInLocal().getWidth() / 2);
        totalScore.setY(190);
        totalScore.setVisible(false);
        gameEnd.getChildren().add(totalScore);
        needToShow.put("TotalScore", totalScore);


        Text totalKill = new Text("Hạ gục: " + totalKill());
        totalKill.setFont(Font.font("DejaVu Sans Mono", 25));
        totalKill.setFill(Color.DARKOLIVEGREEN);
        totalKill.setX(imageView.getX() + imageView.getFitWidth() / 2 - totalKill.getBoundsInLocal().getWidth() / 2);
        totalKill.setY(220);
        totalKill.setVisible(false);
        gameEnd.getChildren().add(totalKill);
        needToShow.put("TotalKill", totalKill);


        Text totalDamage = new Text("Sát thương: " + totalDamage());
        totalDamage.setFont(Font.font("DejaVu Sans Mono", 25));
        totalDamage.setFill(Color.DARKOLIVEGREEN);
        totalDamage.setX(imageView.getX() + imageView.getFitWidth() / 2 - totalDamage.getBoundsInLocal().getWidth() / 2);
        totalDamage.setY(250);
        totalDamage.setVisible(false);
        needToShow.put("TotalDamage", totalDamage);
        gameEnd.getChildren().add(totalDamage);

        MyTask<Void> summary = new MyTask<Void>() {
            @Override
            public void whatNext() {
            }

            @Override
            protected Void call() throws Exception {
                TransitionService.fadeAndMove(100, Direction.DOWN, 300, 300L,
                        needToShow.get("LoseOrWin"),
                        needToShow.get("Time"),
                        needToShow.get("TotalScore"),
                        needToShow.get("TotalKill"),
                        needToShow.get("TotalDamage")
                );

                if (gameScreen.getFigure1() != null) {
                    TransitionService.fadeAndMove(100, Direction.RIGHT, 300, 1L,
                            needToShow.get("ImagePlayer" + 1),
                            needToShow.get("ImageFigure" + 1),
                            needToShow.get("ImageFrame" + 1)
                    );
                    TransitionService.fadeAndMove(100, Direction.RIGHT, 300, 300L,
                            needToShow.get("ImageFrameName" + 1),
                            needToShow.get("NamePlayer" + 1),
                            needToShow.get("ScorePlayer" + 1),
                            needToShow.get("KillPlayer" + 1),
                            needToShow.get("DamagePlayer" + 1),
                            needToShow.get("MvpPointPlayer" + 1)
                    );
                }

                if (gameScreen.getFigure2() != null) {
                    TransitionService.fadeAndMove(100, Direction.LEFT, 300, 1L,
                            needToShow.get("ImagePlayer" + 2),
                            needToShow.get("ImageFigure" + 2),
                            needToShow.get("ImageFrame" + 2)
                    );
                    TransitionService.fadeAndMove(100, Direction.LEFT, 300, 300L,
                            needToShow.get("ImageFrameName" + 2),
                            needToShow.get("NamePlayer" + 2),
                            needToShow.get("ScorePlayer" + 2),
                            needToShow.get("KillPlayer" + 2),
                            needToShow.get("DamagePlayer" + 2),
                            needToShow.get("MvpPointPlayer" + 2)
                    );
                }
                return null;
            }
        };
        summary.execute();

//        show(needToShow);

        ImageView home = new ImageView(GO_HOME_NORMAL);
        double rate = 300 / GO_HOME_NORMAL.getWidth();
        home.setFitWidth(GO_HOME_NORMAL.getWidth() * rate);
        home.setFitHeight(GO_HOME_NORMAL.getHeight() * rate);
        home.setX(getWidth() / 2d - home.getFitWidth() / 2d);
        home.setY(getHeight() - home.getFitHeight() - 40);
        home.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    home.setImage(GO_HOME_CLICK);
                }
                try {
                    comeHome();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        home.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    home.setImage(GO_HOME_NORMAL);
                }
            }
        });

        home.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                home.setImage(GO_HOME_FIRE);
            }
        });

        home.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                home.setImage(GO_HOME_NORMAL);
            }
        });


        gameEnd.getChildren().add(home);


//        for (String n : needToShow.keySet()) {
//            needToShow.get(n).setVisible(true);
//        }


        return scene;
    }

    private void show(HashMap<String, Node> needToShow) {
        //
        TransitionService.fadeAndMove(100, Direction.DOWN, 300, 500L,
                needToShow.get("LoseOrWin"),
                needToShow.get("Time"),
                needToShow.get("TotalScore"),
                needToShow.get("TotalKill"),
                needToShow.get("TotalDamage")
        );


        if (gameScreen.getFigure1() != null) {
            TransitionService.fadeAndMove(100, Direction.RIGHT, 300, 1L,
                    needToShow.get("ImagePlayer" + 1),
                    needToShow.get("ImageFigure" + 1),
                    needToShow.get("ImageFrame" + 1)
            );
        }


        if (gameScreen.getFigure1() != null) {
            TransitionService.fadeAndMove(100, Direction.RIGHT, 300, 500L,
                    needToShow.get("ImageFrameName" + 1),
                    needToShow.get("NamePlayer" + 1),
                    needToShow.get("ScorePlayer" + 1),
                    needToShow.get("KillPlayer" + 1),
                    needToShow.get("DamagePlayer" + 1),
                    needToShow.get("MvpPointPlayer" + 1)
            );
        }


        if (gameScreen.getFigure2() != null) {
            TransitionService.fadeAndMove(100, Direction.LEFT, 300, 1L,
                    needToShow.get("ImagePlayer" + 2),
                    needToShow.get("ImageFigure" + 2),
                    needToShow.get("ImageFrame" + 2)
            );
        }


        if (gameScreen.getFigure2() != null) {
            TransitionService.fadeAndMove(100, Direction.LEFT, 300, 500L,
                    needToShow.get("ImageFrameName" + 2),
                    needToShow.get("NamePlayer" + 2),
                    needToShow.get("ScorePlayer" + 2),
                    needToShow.get("KillPlayer" + 2),
                    needToShow.get("DamagePlayer" + 2),
                    needToShow.get("MvpPointPlayer" + 2)
            );
        }


    }



    private Scene loading() {

        Group root = new Group();
        Scene scene = new Scene(root, width.get(), height.get());

        AnchorPane gameLoad = new AnchorPane();
        gameLoad.setPrefSize(scene.getWidth(), scene.getHeight());
        root.getChildren().add(gameLoad);

        messageProgress.setX(30);
        messageProgress.setY(height.get() - 70);
        messageProgress.setStyle("-fx-font-size: 40px;" +
                "-fx-font-weight: bold;");
        messageProgress.setFill(Color.RED);

        gameLoad.getChildren().add(messageProgress);

        Image image = new Image(new File(".\\Picture\\Logo\\Bomberman.gif").toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(image.getWidth() * 1.5d);
        imageView.setFitHeight(image.getHeight() * 1.5d);
        imageView.setX((gameLoad.getPrefWidth() - imageView.getFitWidth()) / 2d);
        imageView.setY(10);
        gameLoad.getChildren().add(imageView);

        bar = new ProgressBar();
        bar.setLayoutX(40);
        bar.setPrefWidth(1200);
        bar.setLayoutY(height.get() - 40);
        bar.setStyle("-fx-accent: yellow");
        gameLoad.getChildren().add(bar);


        Image background = new Image(new File(".\\Picture\\Logo\\ico.jpg").toURI().toString(), width.get(), height.get(), false, true);

        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);

        gameLoad.setBackground(new Background(backgroundImage));

        return scene;
    }

    public double getWidth() {
        return width.get();
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public double getHeight() {
        return height.get();
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    private int totalScore() {
        int score = 0;
        if (gameScreen.getFigure1() != null) {
            score += gameScreen.getFigure1().getScore();
        }
        if (gameScreen.getFigure2() != null) {
            score += gameScreen.getFigure2().getScore();
        }
        return score;
    }

    private int totalKill() {
        int kill = 0;
        if (gameScreen.getFigure1() != null) {
            kill += gameScreen.getFigure1().getTotalKill();
        }
        if (gameScreen.getFigure2() != null) {
            kill += gameScreen.getFigure2().getTotalKill();
        }
        return kill;
    }

    private int totalDamage() {
        int damage = 0;
        if (gameScreen.getFigure1() != null) {
            damage += gameScreen.getFigure1().getTotalDamage();
        }
        if (gameScreen.getFigure2() != null) {
            damage += gameScreen.getFigure2().getTotalDamage();
        }
        return damage;
    }



    private void comeHome() throws IOException {
        theme.stop();
        end.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(new File(".\\src\\main\\java\\com\\example\\semesterexam\\creategame\\choose.fxml").toURI().toURL());
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    public double getFigureAttackVolume() {
        return figureAttackVolume.get();
    }

    public DoubleProperty figureAttackVolumeProperty() {
        return figureAttackVolume;
    }

    public double getFigureWalkVolume() {
        return figureWalkVolume.get();
    }

    public DoubleProperty figureWalkVolumeProperty() {
        return figureWalkVolume;
    }

    public double getFigureWeaponVolume() {
        return figureWeaponVolume.get();
    }

    public DoubleProperty figureWeaponVolumeProperty() {
        return figureWeaponVolume;
    }

    public double getMonsterAttackVolume() {
        return monsterAttackVolume.get();
    }

    public DoubleProperty monsterAttackVolumeProperty() {
        return monsterAttackVolume;
    }

    public double getMonsterWalkVolume() {
        return monsterWalkVolume.get();
    }

    public DoubleProperty monsterWalkVolumeProperty() {
        return monsterWalkVolume;
    }

    public double getMonsterWeaponVolume() {
        return monsterWeaponVolume.get();
    }

    public DoubleProperty monsterWeaponVolumeProperty() {
        return monsterWeaponVolume;
    }
}
