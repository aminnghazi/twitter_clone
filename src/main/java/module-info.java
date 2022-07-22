module com.example.twitter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.twitter to javafx.fxml;
    exports com.example.twitter;
}