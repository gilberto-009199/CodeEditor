package com.gilberto009199.editor;

import java.io.InputStream;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public enum IconType {
	
    JAVASCRIPT("javascript.png"),
    
    PYTHON("python.png");

    private final String fileName;

    IconType(String fileName) {
        this.fileName = fileName;
    }

    public ImageView getImageView(double size) {
        Image image = new Image(getClass().getResourceAsStream("/icons/" + fileName));
        ImageView view = new ImageView(image);
        view.setFitWidth(size);
        view.setFitHeight(size);
        return view;
    }
}
