module principal.lojamvc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires java.desktop;
    requires java.logging;

    opens controller to javafx.fxml;
    
    exports principal;
}
