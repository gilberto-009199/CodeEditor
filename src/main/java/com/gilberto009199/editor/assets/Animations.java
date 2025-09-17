package com.gilberto009199.editor.assets;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.concurrent.CompletableFuture;

public interface Animations {

    static Transition createRotate(ImageView imageView) {
        var transition = new RotateTransition(Duration.millis(1000), imageView);

        transition.setByAngle(360);
        transition.setCycleCount(Animation.INDEFINITE);

        return transition;
    }

}