package com.gilberto009199.editor;

import java.io.IOException;
import java.util.logging.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import com.gilberto009199.editor.providers.IPoliglot;
import com.gilberto009199.editor.providers.PoliglotBuilder;
import com.gilberto009199.editor.providers.PoliglotType;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    private IPoliglot runnerJavaScript;
    private IPoliglot runnerPython;

    public App(String... args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Inicializando interface gráfica...");

        runnerJavaScript = new PoliglotBuilder(PoliglotType.JAVASCRIPT).build();
        runnerPython = new PoliglotBuilder(PoliglotType.PYTHON).build();


    }

    public static void showError(String title, String message) {
    	logger.severe(message);
        logger.warning("Exibindo erro: " + title + " - " + message);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
