package com.gilberto009199.editor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.management.ExecutionListener;
import org.graalvm.polyglot.proxy.ProxyExecutable;

import com.gilberto009199.editor.providers.IPoliglot;
import com.gilberto009199.editor.providers.JavaScriptPoliglot;
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

/**
 * JavaFX App
 */
public class App extends Application {

	private IPoliglot runner;
	
	private TextArea textArea;
	private StackPane btnJavaScript;
	private StackPane btnPython;
	
	private Button btnRunner;
	
    @Override
    public void start(Stage primaryStage) {
        // Layout raiz horizontal: menu + conteúdo
        HBox root = new HBox();

        // Menu lateral (M, E, N, U)
        VBox layoutMenu = new VBox(10);
        layoutMenu.setPadding(new Insets(10));
        layoutMenu.setAlignment(Pos.TOP_CENTER);
        layoutMenu.setPrefWidth(50);
        layoutMenu.setStyle("-fx-background-color: black;");

        btnJavaScript = createHoverIcon(IconType.JAVASCRIPT.getImageView(32));
        layoutMenu.getChildren().add( btnJavaScript );
        var runnerJavaScript = new PoliglotBuilder(PoliglotType.JAVASCRIPT).build();
        runner = runnerJavaScript;
        btnJavaScript.setOnMouseClicked(event -> {
        	btnRunner.setGraphic(IconType.JAVASCRIPT.getImageView(16));
        	textArea.setText(PoliglotType.JAVASCRIPT.example);
        	runner = runnerJavaScript;
        });

        btnPython = createHoverIcon(IconType.PYTHON.getImageView(32));
        var runnerPython = new PoliglotBuilder(PoliglotType.PYTHON).build();
        layoutMenu.getChildren().add( btnPython );
        btnPython.setOnMouseClicked(event ->{
        	btnRunner.setGraphic(IconType.PYTHON.getImageView(16));
        	textArea.setText(PoliglotType.PYTHON.example);
        	runner = runnerPython;
        });

        // Layout principal com TextArea e rodapé (VBox)
        VBox layoutMain = new VBox();
        layoutMain.setStyle("-fx-background-color: #f0f0f0;");

        // TextArea com scroll
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setStyle("-fx-font-size: 1.3em;");
        textArea.setText(PoliglotType.JAVASCRIPT.example);
        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        // Rodapé com ComboBox + Botão
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
        	// set in
        	// set out
        	runner.onListener(eventRunner -> {
        		System.out.println("hi!!! ");
        		System.out.println("Location Line ? "+ eventRunner.getLocation().getEndLine() );
        		System.out.println("Location Column ? "+ eventRunner.getLocation().getEndColumn() );
        		//System.out.println("Exception? "+ eventRunner.getException().getMessage() );
        	});
        	runner.execute(textArea.getText(), System.in, System.out);

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

        // Adiciona menu + conteúdo no root
        root.getChildren().addAll(layoutMenu, layoutMain);
        HBox.setHgrow(layoutMain, Priority.ALWAYS);

        // Cena e palco
        
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("My Editor");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(500);
        primaryStage.show();
        
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
    
    public static void main(String[] args) {
        launch(args);
    }
    
    

    class PolyglotJavaScript {
	    public static void javascript() {
	    	Context polyglot = Context.create();
	    	
	        Value array = polyglot.eval("js", "[1,2,42,4]");
	        int result = array.getArrayElement(0).asInt();
	        System.out.println(result);
	    }
	    
	    public static void javascriptv2() {
	    	Context polyglot = Context.create();
	    	Value function = polyglot.eval("js", "x => x+1");
	        assert function.canExecute();
	        int x = function.execute(41).asInt();
	        assert x == 42;
	        System.out.println(x);
	    }
	    
	    public static void javascriptv3() {
	    	Context polyglot = Context.create();
	    	polyglot.getEngine();
	    	Value function = polyglot.eval("js",
	    			"""
	    				console.log("Olá gil!!!");	
	    			"""
	    	);
	        assert function.canExecute();
	    }
	
	    public static void javascriptv4() {
	    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        PrintStream printStream = new PrintStream(outputStream);
	
	        Context context = Context.newBuilder("js")
	                .out(printStream)
	                .build();
	
	
	        context.eval("js", """
	            console.log("Olá gil!!!");
	            console.log("Olá gil 2 !!!");
	            console.log("Olá gil 3 !!!");
	            console.log("Olá gil 4 !!!");
	            console.log("Olá gil 5 !!!");
	        """);
	
	        String result = outputStream.toString();
	        System.out.println("Capturado do JS: " + result);
	    }
	    
	
	    public static void javascriptv5() {
	    	 String input = "Gilberto\n";
	         ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
	
	         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	         PrintStream printStream = new PrintStream(outputStream);
	
	         Context context = Context.newBuilder("js")
	                 .in(inputStream) 
	                 .out(printStream)
	                 .allowAllAccess(true)
	                 .build();
	         
	         PromptFunction.applyPromptFunction(new Scanner(inputStream), context);
	         AlertFunction.applyAlertFunction(new Scanner(inputStream), context);
	                  
	         context.eval("js", """
	             const name = prompt("Qual é o seu nome?");
	             console.log("Olá, " + name + "!");
	             alert("oi, "+ name +"!!!");
	         """);
	
	         String result = outputStream.toString();
	         System.out.println("Capturado do JS:\n" + result);
	    }
	    
	    
	    public static void javascriptv6() {
	    	try (Context context = Context.create("js")) {
	            ExecutionListener listener = ExecutionListener.newBuilder()
	                      .onEnter((e) -> System.out.println(
	                              e.getLocation().getCharacters()))
	                      .statements(true)
	                      .attach(context.getEngine());
	            context.eval("js", "for (var i = 0; i < 10; i++);");
	            listener.close();
	        }
	    }
	    
	    public static class PromptFunction {
	        private Scanner scanner;
	        private Context context;
	        
	        public static PromptFunction applyPromptFunction(Scanner scanner, Context context) {
	        	var promptFunction = new PromptFunction();
	        	promptFunction.scanner = scanner;
	        	promptFunction.context = context; 
	        	            context
	            .getBindings("js").putMember("prompt", (ProxyExecutable) argsJs -> {
	                String msg = argsJs.length > 0 ? argsJs[0].asString() : "";
	
	                return promptFunction.prompt(msg);
	            });
	        	            
	        	return promptFunction; 
	        }
	        
	        public String prompt(String message) {
	            System.out.print(message + " ");
	            
	            return JOptionPane.showInputDialog(
	        			null,
	        			new JLabel(message),
	        			message,
	        			JOptionPane.QUESTION_MESSAGE);
	        }
	    }
	    
	
	    public static class AlertFunction {
	        private Scanner scanner;
	        private Context context;
	
	        public static AlertFunction applyAlertFunction(Scanner scanner, Context context) {
	        	var alertFunction = new AlertFunction();
	        	alertFunction.scanner = scanner;
	        	alertFunction.context = context; 
	        	            context
	            .getBindings("js").putMember("alert", (ProxyExecutable) argsJs -> {
	                String msg = argsJs.length > 0 ? argsJs[0].asString() : "";
	                
	                alertFunction.alert(msg);
	                
	                return "";
	            });
	        	            
	        	return alertFunction; 
	        }
	        
	        public void alert(String message) {
	        	JOptionPane.showMessageDialog(
	        			null,
	        			new JLabel(message),
	        			message,
	        			JOptionPane.INFORMATION_MESSAGE);
	            
	        }
	    }
	    
	}
    
    
	    
}
