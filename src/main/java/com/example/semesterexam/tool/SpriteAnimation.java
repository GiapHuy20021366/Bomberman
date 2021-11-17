package com.example.semesterexam.tool;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {

    private ImageView imageView;
    private ImageViewProperties properties;
    private int lastIndex;
    private Action action;

    public ImageViewProperties getProperties() {
        return properties;
    }

    public void setProperties(ImageViewProperties properties) {
        this.properties = properties;
    }

    public SpriteAnimation() {

    }

    public SpriteAnimation(
            ImageView imageView,
            Duration duration,
            ImageViewProperties properties) {
        this.imageView = imageView;
        this.properties = properties;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    public SpriteAnimation(
            ImageView imageView,
            Duration duration,
            MultiAction multiAction) {

        this.imageView = imageView;
        setCycleDuration(duration);
        action = multiAction;

    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return this.action;
    }

    protected void interpolate(double k) {
        if (properties.count < 2) {
            imageView.setViewport(new Rectangle2D(properties.offsetX, properties.offsetY, properties.width, properties.height));
            return;
        }
        final int index = Math.min((int) Math.floor(k * properties.count), properties.count - 1);
        if (index != lastIndex) {
            final double x = (index % properties.columns) * properties.width + properties.offsetX;
            final double y = (index / properties.columns) * properties.height + properties.offsetY;
            imageView.setViewport(new Rectangle2D(x, y, properties.width, properties.height));
            lastIndex = index;
        }
    }
}