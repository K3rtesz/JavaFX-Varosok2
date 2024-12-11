module com.example.javafxvarosok {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires httpcore;
    requires httpclient;
    requires java.net.http;
    requires com.google.gson;
    requires v20;


    opens com.example.javafxvarosok to javafx.fxml;
    exports com.example.javafxvarosok;
}