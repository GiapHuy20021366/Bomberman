package com.example.semesterexam.tool;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ImageViewProperties {
    public int columns;
    public int count;
    public double offsetX;
    public double offsetY;
    public double width;
    public double height;

    public ImageViewProperties(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File Path : " + filePath + " Not found!");
        }

        Scanner scanner = new Scanner(file);
        columns = scanner.nextInt();
        count = scanner.nextInt();
        offsetX = scanner.nextDouble();
        offsetY = scanner.nextDouble();
        width = scanner.nextDouble();
        height = scanner.nextDouble();
    }

    public ImageViewProperties() {

    }

}
