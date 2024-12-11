package com.example.javafxvarosok;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CurrentPriceController {
    public void showInMainView(BorderPane root) {
        ComboBox<String> currencyPairBox = new ComboBox<>();
        Button fetchButton = new Button("Lekérés");
        Label priceLabel = new Label("Ár: ");

        fetchButton.setOnAction(event -> {
            String selectedPair = currencyPairBox.getValue();
            // Lekérés az Oanda API-ról
            double price = 1.23; // Példa ár
            priceLabel.setText("Ár: " + price);
        });

        VBox layout = new VBox(currencyPairBox, fetchButton, priceLabel);
        root.setCenter(layout);
    }
}
