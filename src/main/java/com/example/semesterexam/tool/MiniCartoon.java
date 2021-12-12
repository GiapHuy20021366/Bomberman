package com.example.semesterexam.tool;

import com.example.semesterexam.core.Subject;
import com.example.semesterexam.core.Wall;
import com.example.semesterexam.lanscape.Gate;
import com.example.semesterexam.manage.MiniMap;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class MiniCartoon extends ImageView {
    private final MiniMap miniMap;
    private final Subject s;
    private final DoubleProperty HP = new SimpleDoubleProperty();

    public MiniCartoon(MiniMap miniMap, Subject s) {
        this.miniMap = miniMap;
        this.s = s;
        HP.bind(s.getBlood());

        if (!(s instanceof Gate)) {
            HP.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    if (t1.doubleValue() <= 0) {
                        miniMap.remove(getThis());
                        if (s instanceof Wall) {
                            miniMap.removeOutMatrix(((Wall) s).point2D);
                        }
                    }
                }
            });
        }

        fitWidthProperty().bind(miniMap.getNodeSize());
        fitHeightProperty().bind(miniMap.getNodeSize());
        xProperty().bind(miniMap.getNodeSize().multiply(s.xProperty()).divide(miniMap.getGameScreen().getSizeProperties()));
        yProperty().bind(miniMap.getNodeSize().multiply(s.yProperty()).divide(miniMap.getGameScreen().getSizeProperties()));

    }

    public void setImage(String path) {
        this.setImage(new Image(new File(path).toURI().toString()));
    }

    private MiniCartoon getThis() {
        return this;
    }

    public Wall getIfWall() {
        if (!(s instanceof Wall)) {
            return null;
        }
        return (Wall) s;
    }


}
