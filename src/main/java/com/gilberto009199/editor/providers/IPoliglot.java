package com.gilberto009199.editor.providers;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

import org.graalvm.polyglot.management.ExecutionEvent;

public interface IPoliglot {
	void execute(String code, InputStream input, OutputStream output);
    void onListener(Consumer<ExecutionEvent> listener);
	
}
