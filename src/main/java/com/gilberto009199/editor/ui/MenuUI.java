package com.gilberto009199.editor.ui;

import com.gilberto009199.editor.assets.IconType;
import com.gilberto009199.editor.providers.PoliglotType;
import com.gilberto009199.editor.state.AppState;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

import static com.gilberto009199.editor.assets.IconType.createHoverIcon;

public class MenuUI extends VBox {

    private static final Logger logger = Logger.getLogger(MenuUI.class.getName());

    private final StackPane btnJavaScript;
    private final StackPane btnPython;

    public MenuUI(MainUI main){
        super();

        setPadding(new Insets(10));
        setAlignment(Pos.TOP_CENTER);
        setPrefWidth(50);
        setStyle("-fx-background-color: black;");

        btnJavaScript = createHoverIcon(IconType.JAVASCRIPT.getImageView(32));
        getChildren().add(btnJavaScript);
        btnJavaScript.setDisable(true);

        btnPython = createHoverIcon(IconType.PYTHON.getImageView(32));
        getChildren().add(btnPython);


        btnJavaScript.setOnMouseClicked(event -> {

            if(btnJavaScript.isDisable())return;

            main.editorUI.changeRunner(PoliglotType.JAVASCRIPT);

            logger.info("JavaScript selecionado.");

            btnJavaScript.setDisable(true);
            btnPython.setDisable(false);
        });

        btnPython.setOnMouseClicked(event -> {

            if(btnPython.isDisable())return;

            main.editorUI.changeRunner(PoliglotType.PYTHON);

            logger.info("Python selecionado.");

            btnPython.setDisable(true);
            btnJavaScript.setDisable(false);
        });
    }
}
