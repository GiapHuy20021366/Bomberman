package com.example.semesterexam.creategame;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FigureDisplay {
    private final List<Image> figures = new ArrayList<>();
    private int curFir = 0;

    public static final int SUPERHUMAN = 0;
    public static final int MAGICIAN = 1;
    private String typeFir = "SuperHuMan";

    public FigureDisplay() {
        loadFigures();
    }

    public void loadFigures() {
        figures.add(new Image(new File(".\\Picture\\Logo\\Start\\SuperHuMan.png").toURI().toString()));
        figures.add(new Image(new File(".\\Picture\\Logo\\Start\\Magician.png").toURI().toString()));
    }

    public Image nextFir() {
        ++curFir;
        if (curFir == figures.size()) {
            curFir = 0;
        }
        return figures.get(curFir);
    }

    public Image prevFir() {
        --curFir;
        if (curFir == -1) {
            curFir = figures.size() - 1;
        }
        return figures.get(curFir);
    }

    public int getCurFir() {
        return curFir;
    }

    public String getTypeFir() {
        switch (curFir) {
            case 0 -> {
                return "SuperHuMan";
            }
            case 1 -> {
                return "Magician";
            }
        }
        return null;
    }

    public Image curFir() {
        return figures.get(curFir);
    }
}
