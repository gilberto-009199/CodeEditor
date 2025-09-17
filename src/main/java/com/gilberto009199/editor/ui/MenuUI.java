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

    private final AppState appState;

    private StackPane btnJavaScript;
    private StackPane btnPython;

    public MenuUI(MainUI main){
        super();

        this.appState = AppState.getInstance();

        setPadding(new Insets(10));
        setAlignment(Pos.TOP_CENTER);
        setPrefWidth(50);
        setStyle("-fx-background-color: black;");

        btnJavaScript = createHoverIcon(IconType.JAVASCRIPT.getImageView(32));
        getChildren().add(btnJavaScript);
        btnJavaScript.setOnMouseClicked(event -> {
            main.editorUI.btnRunner.setGraphic(IconType.JAVASCRIPT.getImageView(16));
            main.editorUI.codeArea.replaceText(PoliglotType.JAVASCRIPT.example);

            main.editorUI.runner = appState.getRunnerJavaScript();
            logger.info("JavaScript selecionado.");
        });

        btnPython = createHoverIcon(IconType.PYTHON.getImageView(32));
        getChildren().add(btnPython);
        btnPython.setOnMouseClicked(event -> {
            main.editorUI.btnRunner.setGraphic(IconType.PYTHON.getImageView(16));
            main.editorUI.codeArea.replaceText(PoliglotType.PYTHON.example);

            main.editorUI.runner = appState.getRunnerPython();
            logger.info("Python selecionado.");
        });
    }
}
