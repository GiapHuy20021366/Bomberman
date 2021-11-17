package com.example.semesterexam.tool;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MultiAction extends Action {
    private Image[] images;

    public MultiAction(String folder) {
        File file = new File(folder);
        File[] files = file.listFiles();
        List<Image> img = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            if (files[i].toString().endsWith(".png") || files[i].toString().endsWith(".jpg"))
                img.add(new Image(files[i].toURI().toString()));
        }
        images = new Image[img.size()];
        for (int i = 0; i < images.length;i++ ) {
            images[i] = img.get(i);
        }
//        System.out.println( folder +  " " + images.length);
    }

    public Image[] getImages() {
        return images;
    }
}
