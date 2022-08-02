module com.example.twitter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens com.example.twitter to javafx.fxml;
    exports com.example.twitter;

    opens views to javafx.fxml;
    exports views;
}