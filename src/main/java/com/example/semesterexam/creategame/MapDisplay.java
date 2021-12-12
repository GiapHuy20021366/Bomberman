package com.example.semesterexam.creategame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MapDisplay {
    private final List<Image> maps = new ArrayList<>();
    private int curMap = 0;
    private String folderMaps = "";
    public static int typeGrass = 1;


    public void loadMaps() {
        maps.add(new Image(new File(".\\Picture\\Logo\\Map\\GrassMap.png").toURI().toString()));
        maps.add(new Image(new File(".\\Picture\\Logo\\Map\\StoneMap.png").toURI().toString()));
        maps.add(new Image(new File(".\\Picture\\Logo\\Map\\LavaMap.png").toURI().toString()));
        definedMap();
        typeGrass = 1;
    }

    private void definedMap() {
        switch (curMap) {
            case 0 -> {
                folderMaps = ".\\Map\\GrassMap";
                typeGrass = 1;
            }
            case 1 -> {
                folderMaps = ".\\Map\\StoneMap";
                typeGrass = 2;
            }
            case 2 -> {
                folderMaps = ".\\Map\\LavaMap";
                typeGrass = 3;
            }
        }
    }

    public Image nextMap() {
        ++curMap;
        if (curMap == maps.size()) {
            curMap = 0;
        }
        definedMap();
        return maps.get(curMap);
    }

    public Image prevMap() {
        --curMap;
        if (curMap == -1) {
            curMap = maps.size() - 1;
        }
        definedMap();
        return maps.get(curMap);
    }

    private int getCurMap() {
        return curMap;
    }

    public Image curMap() {
        return maps.get(curMap);
    }

    public String getCurFolderMap() {
        return folderMaps;
    }

}
