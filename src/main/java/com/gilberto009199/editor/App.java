package com.gilberto009199.editor;

import java.util.logging.*;

import com.gilberto009199.editor.state.AppState;
import com.gilberto009199.editor.ui.MainUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    private final AppState appState;

    private MainUI mainUI;

    public App(){
        super();
        appState = AppState.getInstance();
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Inicializando interface gráfica...");


        mainUI = new MainUI(this);

        Scene scene = new Scene(mainUI, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        primaryStage.setTitle("My Editor");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(500);
        primaryStage.show();


        Thread.setDefaultUncaughtExceptionHandler(
                (thread, exception) ->
                {
                    showError("Exceção ",
                            "Ocorreu um erro: " + thread.getName());
                    logger.finest(exception.getMessage());
                    exception.printStackTrace();
                }

        );
    }

    public static void showError(String title, String message) {
        Platform.runLater(()->{
            logger.severe(message);
            logger.warning("Exibindo erro: " + title + " - " + message);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

}
