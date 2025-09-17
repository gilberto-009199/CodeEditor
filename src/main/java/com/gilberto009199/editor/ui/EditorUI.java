package com.gilberto009199.editor.ui;

import com.gilberto009199.editor.assets.IconType;
import com.gilberto009199.editor.providers.IPoliglot;
import com.gilberto009199.editor.providers.PoliglotType;
import com.gilberto009199.editor.state.AppState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.util.logging.Logger;

import static com.gilberto009199.editor.App.showError;

public class EditorUI extends VBox {

    private static final Logger logger = Logger.getLogger(EditorUI.class.getName());

    private final AppState appState;

    protected CodeArea codeArea;
    protected Button btnRunner;

    protected IPoliglot runner;

    public EditorUI(MainUI mainUI){
        super();

        setStyle("-fx-background-color: #f0f0f0;");

        this.appState = AppState.getInstance();

        runner = appState.getRunnerJavaScript();

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

                    if(eventRunner.getException() != null) logger.warning("Exceção: " + eventRunner.getException().getMessage());

                });

                String code = codeArea.getText();
                logger.info("Código sendo executado:\n" + code);

                //btnRunner.setGraphic(create);
                runner.execute(code, System.in, System.out);
                runner.clear();

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

        getChildren().addAll(
                scrollPane,
                footer
        );
    }

}
