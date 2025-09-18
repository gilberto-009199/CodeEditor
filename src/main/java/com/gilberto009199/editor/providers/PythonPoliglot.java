package com.gilberto009199.editor.providers;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.management.ExecutionEvent;
import org.graalvm.polyglot.management.ExecutionListener;
import org.graalvm.polyglot.proxy.ProxyExecutable;

import com.gilberto009199.editor.providers.JavaScriptPoliglot.AlertFunction;
import com.gilberto009199.editor.providers.JavaScriptPoliglot.PromptFunction;

public class PythonPoliglot implements IPoliglot{
	
	private final static String LANG = "python";

    private static final String KEYWORDS_PATTERN = "\\b(" + String.join("|", PoliglotType.PYTHON.KEYWORDS) + ")\\b";
    private static final String OPERATORS_PATTERN = "\\b(" + String.join("|", PoliglotType.PYTHON.OPERATORS) + ")\\b";
    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORDS_PATTERN + ")" +
                    "|(?<OPERATOR>" + OPERATORS_PATTERN + ")" +
                    "|(?<STRING>\"\"\"[\\s\\S]*?\"\"\"|\"([^\"\\\\]|\\\\.)*\"|'([^'\\\\]|\\\\.)*')" +
                    "|(?<COMMENT>#.*|'''[\\s\\S]*?'''|\"\"\"[\\s\\S]*?\"\"\")" +
                    "|(?<NUMBER>\\b\\d+(\\.\\d+)?([eE][+-]?\\d+)?\\b)" +
                    "|(?<BRACKET>[\\{\\}\\(\\)\\[\\]])"
    );


	private Context context;
	private Consumer<ExecutionEvent> listener;

	public PythonPoliglot(){
		context = Context.create(LANG);
	}
	
	@Override
	public void execute(String code, InputStream input, OutputStream output) {
		ExecutionListener.newBuilder()
				.onReturn(listener)
                .statements(true)
                .attach(context.getEngine());
		
		PromptFunction.applyPromptFunction(new Scanner(input), context);
        AlertFunction.applyAlertFunction(output, context);
        
		context.eval(LANG, code);
	}

    @Override
    public void clear(){
        try {
            context.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        context = Context.create(LANG);
    }

	@Override
	public void onListener(Consumer<ExecutionEvent> listener) { this.listener = listener; }

    @Override
    public Pattern pattern() {   return PATTERN;  }


	public static class PromptFunction {
        private Scanner scanner;
        private Context context;

        public static PromptFunction applyPromptFunction(Scanner scanner, Context context) {
            var promptFunction = new PromptFunction();
            promptFunction.scanner = scanner;
            promptFunction.context = context;
            context.getBindings(LANG).putMember("input", (ProxyExecutable) args -> {
                String msg = args.length > 0 ? args[0].asString() : "";
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
        private OutputStream out;
        private Context context;

        public static AlertFunction applyAlertFunction(OutputStream out, Context context) {
            var alertFunction = new AlertFunction();
            alertFunction.out = out;
            alertFunction.context = context;
            context.getBindings(LANG).putMember("print", (ProxyExecutable) args -> {
                String msg = args.length > 0 ? args[0].asString() : "";
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
