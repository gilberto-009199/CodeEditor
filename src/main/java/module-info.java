module org.openjfx {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    
    requires org.fxmisc.richtext;
    
    requires org.graalvm.polyglot;
    
    
    requires java.logging;
	requires java.desktop;
	requires java.management;

    exports com.gilberto009199.editor;
    opens com.gilberto009199.editor to javafx.fxml;
    exports com.gilberto009199.editor.assets;
    opens com.gilberto009199.editor.assets to javafx.fxml;
    exports com.gilberto009199.editor.config;
    opens com.gilberto009199.editor.config to javafx.fxml;
}
