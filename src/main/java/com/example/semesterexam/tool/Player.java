package com.example.semesterexam.tool;

import com.example.semesterexam.manage.GameScreen;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Player {
    private final MediaPlayer mediaPlayer;

    public static final int VOLUME_BACKGROUND = 1;
    public static final int VOLUME_PLAYER = 2;
    public static final int VOLUME_FIGURE_ATTACK = 3;
    public static final int VOLUME_FIGURE_WALK = 4;
    public static final int VOLUME_FIGURE_WEAPON = 5;
    public static final int VOLUME_MONSTER_ATTACK = 6;
    public static final int VOLUME_MONSTER_WALK = 7;
    public static final int VOLUME_MONSTER_WEAPON = 8;

    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player(GameScreen gameScreen, String mediaName, int type) {
        mediaPlayer = new MediaPlayer(gameScreen.getMediaManagement().getSound(mediaName));

        switch (type) {
            case VOLUME_BACKGROUND -> {
                mediaPlayer.volumeProperty().bind(gameScreen.getGamePlay().volumeBackgroundProperty());
            }
            case VOLUME_PLAYER -> {
                mediaPlayer.volumeProperty().bind(gameScreen.getGamePlay().volumePlayerProperty());
            }
            case VOLUME_FIGURE_ATTACK -> {
                mediaPlayer.volumeProperty().bind(gameScreen.getGamePlay().figureAttackVolumeProperty());
            }
            case VOLUME_FIGURE_WALK -> {
                mediaPlayer.volumeProperty().bind(gameScreen.getGamePlay().figureWalkVolumeProperty());
            }
            case VOLUME_FIGURE_WEAPON -> {
                mediaPlayer.volumeProperty().bind(gameScreen.getGamePlay().figureWeaponVolumeProperty());
            }
            case VOLUME_MONSTER_ATTACK -> {
                mediaPlayer.volumeProperty().bind(gameScreen.getGamePlay().monsterAttackVolumeProperty());
            }
            case VOLUME_MONSTER_WALK -> {
                mediaPlayer.volumeProperty().bind(gameScreen.getGamePlay().monsterWalkVolumeProperty());
            }
            case VOLUME_MONSTER_WEAPON -> {
                mediaPlayer.volumeProperty().bind(gameScreen.getGamePlay().monsterWeaponVolumeProperty());
            }
        }


    }

    public void play() {
        mediaPlayer.play();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void reset() {
        mediaPlayer.seek(Duration.millis(0));
    }

    public void autoRepeat() {
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
