package com.gilberto009199.editor.state;

import com.gilberto009199.editor.providers.IPoliglot;
import com.gilberto009199.editor.providers.PoliglotBuilder;
import com.gilberto009199.editor.providers.PoliglotType;


public class AppState {

    private static AppState instance;

    private IPoliglot runnerJavaScript;
    private IPoliglot runnerPython;

    private AppState(){};
    public static AppState getInstance(){

        if(instance != null)return instance;

        instance = new AppState();

        instance.runnerJavaScript = new PoliglotBuilder(PoliglotType.JAVASCRIPT).build();
        instance.runnerPython = new PoliglotBuilder(PoliglotType.PYTHON).build();

        return instance;
    }


    public IPoliglot getRunnerJavaScript() { return runnerJavaScript;  }

    public IPoliglot getRunnerPython() {  return runnerPython; }
}
