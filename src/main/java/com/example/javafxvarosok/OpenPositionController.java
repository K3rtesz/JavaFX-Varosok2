package com.example.javafxvarosok;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.primitives.InstrumentName;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class OpenPositionController {

    public void showInMainView(BorderPane root) {
        // Devizapár kiválasztása
        ComboBox<String> currencyPairBox = new ComboBox<>();
        currencyPairBox.getItems().addAll("EUR_USD", "GBP_USD", "USD_JPY");

        // Mennyiség kiválasztása (Spinner)
        Spinner<Integer> quantitySpinner = new Spinner<>();
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 10));

        // Irány kiválasztása (vétel/eladás)
        ComboBox<String> directionBox = new ComboBox<>();
        directionBox.getItems().addAll("Vétel (Buy)", "Eladás (Sell)");

        // Pozíció nyitása gomb
        Button openPositionButton = new Button("Pozíció nyitása");

        openPositionButton.setOnAction(event -> {
            String instrument = currencyPairBox.getValue();
            Integer quantity = quantitySpinner.getValue();
            String direction = directionBox.getValue();

            if (instrument == null || quantity == null || direction == null) {
                System.out.println("Hiba: Minden mezőt ki kell tölteni!");
                return;
            }

            // Vétel esetén pozitív, eladás esetén negatív mennyiség
            int units = direction.equals("Vétel (Buy)") ? quantity : -quantity;

            // Pozíció nyitása
            openPosition(instrument, units);
        });

        // Layout beállítása
        VBox layout = new VBox(currencyPairBox, quantitySpinner, directionBox, openPositionButton);
        layout.setSpacing(10);
        root.setCenter(layout);
    }

    private void openPosition(String instrument, int units) {
        System.out.println("Pozíció nyitása: " + instrument + ", Mennyiség: " + units);

        Context ctx = new ContextBuilder(Config.URL)
                .setToken(Config.TOKEN)
                .setApplication("OpenPosition")
                .build();

        AccountID accountId = Config.ACCOUNTID;

        try {
            // Market Order létrehozása
            OrderCreateRequest request = new OrderCreateRequest(accountId);
            MarketOrderRequest marketOrderRequest = new MarketOrderRequest();
            marketOrderRequest.setInstrument(new InstrumentName(instrument));
            marketOrderRequest.setUnits(units);

            request.setOrder(marketOrderRequest);

            // API hívás a pozíció létrehozására
            OrderCreateResponse response = ctx.order.create(request);

            System.out.println("Pozíció sikeresen megnyitva. Transaction ID: " +
                    response.getOrderFillTransaction().getId());
        } catch (Exception e) {
            System.err.println("Hiba történt a pozíció nyitása során: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
