package com.example.semesterexam.tool;

import javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;

public class Action {
    public ImageViewProperties imageViewProperties;
    private Image image;

    public Action() {

    }
    public Action(String filePath) throws IOException {
        image = new Image(new File(filePath).toURI().toString());
        imageViewProperties = new ImageViewProperties(filePath.replace(".png", ".txt"));
    }

    public Image getImage() {
        return image;
    }
}
