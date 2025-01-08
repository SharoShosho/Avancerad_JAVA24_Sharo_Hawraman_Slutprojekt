module org.example.slutuppgiftfe {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.google.gson;
    requires java.net.http;
    requires java.desktop;

    opens org.example.slutuppgiftfe to javafx.fxml;
    exports org.example.slutuppgiftfe;
}