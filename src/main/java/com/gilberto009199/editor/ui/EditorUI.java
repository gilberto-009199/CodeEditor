package com.gilberto009199.editor.ui;

import com.gilberto009199.editor.assets.Animations;
import com.gilberto009199.editor.assets.IconType;
import com.gilberto009199.editor.providers.IPoliglot;
import com.gilberto009199.editor.providers.PoliglotType;
import com.gilberto009199.editor.state.AppState;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gilberto009199.editor.App.showError;

public class EditorUI extends VBox {

    private static final Logger logger = Logger.getLogger(EditorUI.class.getName());

    private final AppState appState;

    private final String ID_CODE_AREA = "editor_codeArea";
    protected CodeArea codeArea;
    protected Button btnRunner;

    protected IPoliglot runner;
    private ChangeListener<String> listener;

    public EditorUI(MainUI mainUI){
        super();

        setStyle("-fx-background-color: #f0f0f0;");

        this.appState = AppState.getInstance();

        runner = appState.getRunnerJavaScript();

        codeArea = new CodeArea();

        syntaxHighlighter();
        codeArea.setId(ID_CODE_AREA);
        codeArea.replaceText(PoliglotType.JAVASCRIPT.example);
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));


        ScrollPane scrollPane = new ScrollPane(codeArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);


        btnRunner = new Button("Run");
        btnRunner.setGraphic(IconType.JAVASCRIPT.getImageView(16));

        btnRunner.setOnAction((eventClick) -> {
            logger.info("Botão Run clicado.");

            var animation = Animations.createRotate((ImageView) btnRunner.getGraphic());

            animation.play();

            execRunner(codeArea.getText())
                    .thenRun(() -> Platform.runLater(animation::stop));

        });

        HBox footer = new HBox(5, btnRunner);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(5));
        footer.setStyle(
                """
                        -fx-border-color: gray;
                        -fx-border-width: 1 0 0 0;
                        """
        );

        getChildren()
        .addAll(
                scrollPane,
                footer
        );
    }

    private void syntaxHighlighter() {

        var pattern = runner.pattern();

        if(listener != null) codeArea.textProperty().removeListener(listener);

        listener = (obs, oldText, newText) -> {

            Matcher matcher = pattern.matcher(newText);
            int lastKwEnd = 0;
            StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

            while (matcher.find()) {
                String styleClass = null;

                if (matcher.group("KEYWORD") != null) {
                    styleClass = "keyword";
                } else if (matcher.group("OPERATOR") != null) {
                    styleClass = "operator";
                } else if (matcher.group("STRING") != null) {
                    styleClass = "string";
                } else if (matcher.group("COMMENT") != null) {
                    styleClass = "comment";
                } else if (matcher.group("NUMBER") != null) {
                    styleClass = "number";
                } else if (matcher.group("BRACKET") != null) {
                    styleClass = "bracket";
                }

                // Adiciona texto normal antes do match
                spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);

                // Adiciona texto com estilo
                if (styleClass != null) {
                    spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
                } else {
                    spansBuilder.add(Collections.emptyList(), matcher.end() - matcher.start());
                }

                lastKwEnd = matcher.end();
            }

            spansBuilder.add(Collections.emptyList(), newText.length() - lastKwEnd);

            codeArea.setStyleSpans(0, spansBuilder.create());
        };

        codeArea.textProperty().addListener(listener);

    }

    private CompletableFuture<Void> execRunner(String code) {
        return CompletableFuture.runAsync(()->{
            try {
                runner.onListener(eventRunner -> {
                    logger.info("Código executado.");
                    logger.info("Linha: " + eventRunner.getLocation().getEndLine());
                    logger.info("Coluna: " + eventRunner.getLocation().getEndColumn());

                    if (eventRunner.getException() != null)
                        logger.warning("aaaaaaaaaaaaa: " + eventRunner.getException().getCause().getMessage());

                });

                logger.info("Código sendo executado:\n" + code);

                runner.execute(code, System.in, System.out);
                runner.clear();
            } catch (Exception e) {

                showError("Erro durante a execução!", e.getCause().getMessage());

            }
        });

    }

    public void changeRunner(PoliglotType poliglotType) {

        runner = switch(poliglotType){
            case PoliglotType.JAVASCRIPT -> appState.getRunnerJavaScript();
            case PoliglotType.PYTHON -> appState.getRunnerPython();
        };

        syntaxHighlighter();

        btnRunner.setGraphic(poliglotType.iconType.getImageView(16));
        codeArea.replaceText(poliglotType.example);
    }
}
