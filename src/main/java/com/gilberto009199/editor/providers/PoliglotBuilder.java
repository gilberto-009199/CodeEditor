package com.gilberto009199.editor.providers;

public class PoliglotBuilder {

	private final PoliglotType language;

	public PoliglotBuilder(PoliglotType language) {
        this.language = language;
    }

	public IPoliglot build() {
        IPoliglot poliglot = switch (language) {
            case JAVASCRIPT -> new JavaScriptPoliglot();
            case PYTHON -> new PythonPoliglot();
        };

        return poliglot;
    }
    
}
