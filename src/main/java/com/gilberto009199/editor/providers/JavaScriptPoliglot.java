package com.gilberto009199.editor.providers;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.management.ExecutionEvent;
import org.graalvm.polyglot.management.ExecutionListener;
import org.graalvm.polyglot.proxy.ProxyExecutable;

public class JavaScriptPoliglot implements IPoliglot {

    private final static String LANG = "js";

    private static final String[] KEYWORDS = new String[] {
            "prompt", "alert", "let", "var", "const"
    };

    private Context context;
    private Consumer<ExecutionEvent> listener;

    public JavaScriptPoliglot() {
        context = Context.create(LANG);

    }

    @Override
    public void execute(String code, InputStream input, OutputStream output) {
        if (listener != null) ExecutionListener
                .newBuilder()
                    .onReturn(listener)
                    .statements(true)
                    .attach(context.getEngine());

        PromptFunction.applyPromptFunction(new Scanner(input), context);
        AlertFunction.applyAlertFunction(output, context);

        context.eval(LANG, code);
    }

    @Override
    public void clear() {
        try {
            context.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        context = Context.create(LANG);
    }

    @Override
    public void onListener(Consumer<ExecutionEvent> listener) {  this.listener = listener;  }

    @Override
    public String[] keyworkds() {   return KEYWORDS;  }

    public static class PromptFunction {
        private Scanner scanner;
        private Context context;

        public static void applyPromptFunction(Scanner scanner, Context context) {
            // Usar approach mais segura para definir a função prompt
            try {
                // Verificar se já existe para evitar sobrescrever múltiplas vezes
                Value existingPrompt = context.getBindings(LANG).getMember("prompt");
                if (existingPrompt == null || !existingPrompt.canExecute()) {
                    context.getBindings(LANG).putMember("prompt", (ProxyExecutable) argsJs -> {
                        String msg = argsJs.length > 0 ? argsJs[0].asString() : "";
                        return JOptionPane.showInputDialog(
                                null,
                                new JLabel(msg),
                                "Prompt",
                                JOptionPane.QUESTION_MESSAGE
                        );
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static class AlertFunction {
        public static void applyAlertFunction(OutputStream out, Context context) {
            // Usar approach mais segura para definir a função alert
            try {
                // Verificar se já existe para evitar sobrescrever múltiplas vezes
                Value existingAlert = context.getBindings(LANG).getMember("alert");
                if (existingAlert == null || !existingAlert.canExecute()) {
                    context.getBindings(LANG).putMember("alert", (ProxyExecutable) argsJs -> {
                        String msg = argsJs.length > 0 ? argsJs[0].asString() : "";
                        JOptionPane.showMessageDialog(
                                null,
                                new JLabel(msg),
                                "Alert",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        return "";
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            if (context != null) {
                context.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}