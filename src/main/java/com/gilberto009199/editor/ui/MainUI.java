package com.gilberto009199.editor.ui;

import com.gilberto009199.editor.App;
import com.gilberto009199.editor.assets.IconType;
import com.gilberto009199.editor.providers.PoliglotType;
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

import static com.gilberto009199.editor.assets.IconType.createHoverIcon;
import static com.gilberto009199.editor.App.showError;

public class MainUI {

    private HBox root;
    private VBox layoutMenu;
    private CodeArea codeArea;
    private StackPane btnJavaScript;
    private StackPane btnPython;
    private Button btnRunner;

    private final App app;

    public MainUI(App app){
        this.app = app;

        root = new HBox();

        layoutMenu = new VBox(10);
        layoutMenu.setPadding(new Insets(10));
        layoutMenu.setAlignment(Pos.TOP_CENTER);
        layoutMenu.setPrefWidth(50);
        layoutMenu.setStyle("-fx-background-color: black;");
        btnJavaScript = createHoverIcon(IconType.JAVASCRIPT.getImageView(32));
        layoutMenu.getChildren().add(btnJavaScript);


        btnJavaScript.setOnMouseClicked(event -> {
            btnRunner.setGraphic(IconType.JAVASCRIPT.getImageView(16));
            codeArea.replaceText(PoliglotType.JAVASCRIPT.example);
            runner = app.runnerJavaScript;
            logger.info("JavaScript selecionado.");
        });

        btnPython = createHoverIcon(IconType.PYTHON.getImageView(32));


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

    public show(){








    }



}
