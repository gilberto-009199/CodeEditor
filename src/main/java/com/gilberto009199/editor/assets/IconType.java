package com.gilberto009199.editor.assets;


import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

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

    public static StackPane createHoverIcon(ImageView iconView) {
        StackPane container = new StackPane(iconView);
        container.setPadding(new Insets(8));
        container.setStyle("-fx-background-color: transparent;");

        container.setOnMouseEntered(e -> container.setStyle("-fx-background-color: rgba(255,255,255,0.1);"));
        container.setOnMouseExited(e -> container.setStyle("-fx-background-color: transparent;"));
        container.setCursor(Cursor.HAND);
        return container;
    }
}
