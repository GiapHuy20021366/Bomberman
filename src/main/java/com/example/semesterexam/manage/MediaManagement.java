package com.example.semesterexam.manage;

import javafx.scene.media.Media;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class MediaManagement {
    private final HashMap<String, Media> sounds = new HashMap<>();

    public MediaManagement(String filePath) {
        try {
            loadAllSounds(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Media getSound(String soundName) {
        return sounds.get(soundName);
    }

    private void loadAllSounds(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            sounds.put(scanner.next(), new Media(new File(scanner.next()).toURI().toString()));
        }
        scanner.close();
    }
}
