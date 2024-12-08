module com.example.javafxvarosok {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.javafxvarosok to javafx.fxml;
    exports com.example.javafxvarosok;
}