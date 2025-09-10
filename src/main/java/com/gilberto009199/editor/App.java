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

    static {
        try {
            LogManager.getLogManager().readConfiguration(App.class.getResourceAsStream("/logging.properties"));
        } catch (IOException | SecurityException e) {
            logger.log(Level.SEVERE, "Erro ao carregar configuração de logging", e);
        }
    }

    private IPoliglot runner;
    private CodeArea codeArea;
    private StackPane btnJavaScript;
    private StackPane btnPython;
    private Button btnRunner;

    public static void main(String[] args) {
        logger.info("Aplicação iniciando...");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Inicializando interface gráfica...");

        HBox root = new HBox();

        // Menu lateral
        VBox layoutMenu = new VBox(10);
        layoutMenu.setPadding(new Insets(10));
        layoutMenu.setAlignment(Pos.TOP_CENTER);
        layoutMenu.setPrefWidth(50);
        layoutMenu.setStyle("-fx-background-color: black;");

        btnJavaScript = createHoverIcon(IconType.JAVASCRIPT.getImageView(32));
        layoutMenu.getChildren().add(btnJavaScript);

        var runnerJavaScript = new PoliglotBuilder(PoliglotType.JAVASCRIPT).build();
        runner = runnerJavaScript;

        btnJavaScript.setOnMouseClicked(event -> {
            btnRunner.setGraphic(IconType.JAVASCRIPT.getImageView(16));
            codeArea.replaceText(PoliglotType.JAVASCRIPT.example);
            runner = runnerJavaScript;
            logger.info("JavaScript selecionado.");
        });

        btnPython = createHoverIcon(IconType.PYTHON.getImageView(32));
        var runnerPython = new PoliglotBuilder(PoliglotType.PYTHON).build();
        layoutMenu.getChildren().add(btnPython);

        btnPython.setOnMouseClicked(event -> {
            btnRunner.setGraphic(IconType.PYTHON.getImageView(16));
            codeArea.replaceText(PoliglotType.PYTHON.example);
            runner = runnerPython;
            logger.info("Python selecionado.");
        });

        VBox layoutMain = new VBox();
        layoutMain.setStyle("-fx-background-color: #f0f0f0;");

        codeArea = new CodeArea();
        codeArea.replaceText(PoliglotType.JAVASCRIPT.example);
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        ScrollPane scrollPane = new ScrollPane(codeArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(
                "Grafics Input&OutPut",
                "Text input&OutPut",
                "CLI input&OutPut",
                "File Input&OutPut"
        );
        comboBox.setValue("Grafics Input&OutPut");

        btnRunner = new Button("Run");
        btnRunner.setGraphic(IconType.JAVASCRIPT.getImageView(16));

        btnRunner.setOnAction((eventClick) -> {
            logger.info("Botão Run clicado.");
            try {
                runner.onListener(eventRunner -> {
                    logger.info("Código executado.");
                    logger.info("Linha: " + eventRunner.getLocation().getEndLine());
                    logger.info("Coluna: " + eventRunner.getLocation().getEndColumn());
                    // logger.warning("Exceção: " + eventRunner.getException().getMessage()); // se quiser
                });

                String code = codeArea.getText();
                logger.info("Código sendo executado:\n" + code);

                runner.execute(code, System.in, System.out);
            } catch (Exception ex) {
            	
            	logger.severe("Erro durante execução do código"+ ex.toString());
            	
                showError("Erro na execução", ex.getMessage());
            }
        });

        HBox footer = new HBox(5, comboBox, btnRunner);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(5));
        footer.setStyle(
                """
                        -fx-border-color: gray;
                        -fx-border-width: 1 0 0 0;
                        """
        );

        layoutMain.getChildren().addAll(scrollPane, footer);
        root.getChildren().addAll(layoutMenu, layoutMain);
        HBox.setHgrow(layoutMain, Priority.ALWAYS);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        primaryStage.setTitle("My Editor");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(500);
        primaryStage.show();

        logger.info("Interface carregada com sucesso.");
    }

    private StackPane createHoverIcon(ImageView iconView) {
        StackPane container = new StackPane(iconView);
        container.setPadding(new Insets(8));
        container.setStyle("-fx-background-color: transparent;");

        container.setOnMouseEntered(e -> container.setStyle("-fx-background-color: rgba(255,255,255,0.1);"));
        container.setOnMouseExited(e -> container.setStyle("-fx-background-color: transparent;"));
        container.setCursor(Cursor.HAND);
        return container;
    }

    private void showError(String title, String message) {
    	logger.severe(message);
        logger.warning("Exibindo erro: " + title + " - " + message);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
