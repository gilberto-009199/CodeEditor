package com.gilberto009199.editor.providers;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.graalvm.polyglot.management.ExecutionEvent;

public interface IPoliglot {

    Pattern pattern();

	void execute(String code, InputStream input, OutputStream output);
    void clear();
    void onListener(Consumer<ExecutionEvent> listener);
	
}
