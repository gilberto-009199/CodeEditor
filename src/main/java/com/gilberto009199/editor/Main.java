package com.gilberto009199.editor;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {

        LogManager.getLogManager().readConfiguration(App.class.getResourceAsStream("/logging.properties"));

        logger.info("app init");

        App.launch(args);

    }
}
