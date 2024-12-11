package com.example.javafxvarosok;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ForexPriceController {

    private static final String API_URL = "https://api-fxpractice.oanda.com/v3/pricing";
    private static final String API_KEY = "fcd9d50c2832d73cee90e55aeaed0849-5f750ec1d1393fdd6821f97cc469f3b0"; // Helyettesítsd a saját API-kulcsoddal

    private ComboBox<String> instrumentComboBox;
    private Label priceLabel;

    public void showInMainView(BorderPane root) {
        // Layout beállítások
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        // Devizapár választó lenyíló lista
        instrumentComboBox = new ComboBox<>();
        instrumentComboBox.getItems().addAll("EUR/USD", "GBP/USD", "USD/JPY", "AUD/USD", "USD/CHF"); // Tetszőleges devizapárok
        instrumentComboBox.setPromptText("Válassz devizapárt");

        // Ár kijelző label
        priceLabel = new Label("Válassz devizapárt és kattints a gombra!");

        // Lekérdezés indító gomb
        Button fetchPriceButton = new Button("Ár lekérése");
        fetchPriceButton.setOnAction(event -> fetchPrice(instrumentComboBox.getValue()));

        // Elemek hozzáadása a layout-hoz
        vbox.getChildren().addAll(instrumentComboBox, fetchPriceButton, priceLabel);

        // Layout megjelenítése a főablakban
        Platform.runLater(() -> root.setCenter(vbox));
    }

    private void fetchPrice(String instrument) {
        if (instrument == null || instrument.isEmpty()) {
            priceLabel.setText("Kérlek, válassz egy devizapárt!");
            return;
        }

        String apiUrl = API_URL + "?instruments=" + instrument;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + API_KEY)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> processResponse(response, instrument))
                .exceptionally(e -> {
                    e.printStackTrace();
                    Platform.runLater(() -> priceLabel.setText("Hiba: Nem sikerült lekérni az adatokat."));
                    return null;
                });
    }

    private void processResponse(String response, String instrument) {
        try {
            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();

            // Ellenőrizzük, hogy a "prices" kulcs elérhető és nem üres
            if (!jsonResponse.has("prices")) {
                Platform.runLater(() -> priceLabel.setText("Hiba: Az 'prices' kulcs hiányzik."));
                return;
            }

            JsonArray prices = jsonResponse.getAsJsonArray("prices");
            if (prices.size() == 0) {
                Platform.runLater(() -> priceLabel.setText("Hiba: Nincs elérhető árfolyam adat."));
                return;
            }

            // Az első elem feldolgozása
            JsonObject priceInfo = prices.get(0).getAsJsonObject();
            String receivedInstrument = priceInfo.get("instrument").getAsString();
            double bidPrice = priceInfo.get("bid").getAsDouble();

            Platform.runLater(() -> priceLabel.setText("Devizapár: " + receivedInstrument + ", Ár: " + bidPrice));
        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> priceLabel.setText("Hiba történt az adatok feldolgozása közben."));
        }
    }
}
