package com.example.semesterexam.tool;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class MultiSpriteAnimation extends SpriteAnimation {
    private int count;
    private Image[] images;
    private ImageView imageView;
    public MultiSpriteAnimation(MultiAction action, ImageView imageView, Duration duration) {
        this.images = action.getImages();
        this.imageView = imageView;
        this.count = images.length;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }
    @Override
    protected void interpolate(double v) {
        if (images.length < 2) {
            imageView.setImage(images[0]);
            return;
        }
        final int i = (int) Math.min(Math.round(v*count), count - 1);
        imageView.setImage(images[i]);
    }
}
