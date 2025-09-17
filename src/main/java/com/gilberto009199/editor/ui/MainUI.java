package com.gilberto009199.editor.ui;

import com.gilberto009199.editor.App;
import com.gilberto009199.editor.assets.IconType;
import com.gilberto009199.editor.providers.IPoliglot;
import com.gilberto009199.editor.providers.PoliglotType;
import com.gilberto009199.editor.state.AppState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.util.logging.Logger;

import static com.gilberto009199.editor.assets.IconType.createHoverIcon;
import static com.gilberto009199.editor.App.showError;

public class MainUI extends HBox{

    private static final Logger logger = Logger.getLogger(MainUI.class.getName());

    protected MenuUI menuUI;
    protected EditorUI editorUI;

    public MainUI(App app){

        super();

        menuUI = new MenuUI(this);

        editorUI = new EditorUI(this);

        getChildren()
        .addAll(
            menuUI, editorUI
        );

        HBox.setHgrow(editorUI, Priority.ALWAYS);

        logger.info("Interface carregada com sucesso.");

    }

}
