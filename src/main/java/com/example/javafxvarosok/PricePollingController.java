package com.example.javafxvarosok;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.pricing.PricingGetResponse;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PricePollingController {
    private volatile boolean running = false; // A folyamatos lekérdezés vezérlése
    private Thread pollingThread;

    public void showInMainView(BorderPane root) {
        // UI elemek
        ComboBox<String> instrumentBox = new ComboBox<>();
        Label currentPriceLabel = new Label("Ár: ");

        // Devizapárok hozzáadása a lenyíló menühöz
        instrumentBox.getItems().addAll("EUR_USD", "USD_JPY", "GBP_USD", "USD_CHF");
        instrumentBox.setValue("EUR_USD"); // Alapértelmezett érték

        // Gombnyomásra történő lekérdezés
        instrumentBox.setOnAction(event -> {
            if (pollingThread != null && pollingThread.isAlive()) {
                running = false; // Leállítjuk az előző szálat
            }
            String selectedInstrument = instrumentBox.getValue();
            startPolling(selectedInstrument, currentPriceLabel);
        });

        // Layout beállítása
        VBox layout = new VBox(instrumentBox, currentPriceLabel);
        layout.setSpacing(10);
        root.setCenter(layout);
    }

    private void startPolling(String instrument, Label priceLabel) {
        running = true;
        pollingThread = new Thread(() -> {
            Context ctx = new ContextBuilder(Config.URL)
                    .setToken(Config.TOKEN)
                    .setApplication("PricePolling")
                    .build();

            AccountID accountId = Config.ACCOUNTID;
            List<String> instruments = new ArrayList<>(Arrays.asList(instrument));
            try {
                PricingGetRequest request = new PricingGetRequest(accountId, instruments);
                while (running) {
                    PricingGetResponse resp = ctx.pricing.get(request);
                    for (ClientPrice price : resp.getPrices()) {
                        // Az aktuális ár megjelenítése
                        Platform.runLater(() -> priceLabel.setText(
                                "Ár: " + price.getInstrument() + " - " +
                                        "BID: " + price.getBids().get(0).getPrice() + ", " +
                                        "ASK: " + price.getAsks().get(0).getPrice()
                        ));
                    }
                    Thread.sleep(1000); // 1 másodperces várakozás
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> priceLabel.setText("Hiba: " + e.getMessage()));
            }
        });
        pollingThread.setDaemon(true); // Szál leáll, ha az alkalmazás bezárul
        pollingThread.start();
    }
}
