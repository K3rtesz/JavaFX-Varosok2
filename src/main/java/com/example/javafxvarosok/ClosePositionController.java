package com.example.javafxvarosok;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.trade.TradeCloseRequest;
import com.oanda.v20.trade.TradeSpecifier;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ClosePositionController {

    public void showInMainView(BorderPane root) {
        TextField positionIdField = new TextField();
        positionIdField.setPromptText("Adja meg a pozíció ID-t");

        Button closeButton = new Button("Pozíció zárása");

        closeButton.setOnAction(event -> {
            String positionId = positionIdField.getText();
            if (positionId.isEmpty()) {
                System.out.println("Hiba: Pozíció ID nem lehet üres!");
                return;
            }
            try {
                closePosition(positionId);
                System.out.println("Pozíció sikeresen lezárva: " + positionId);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Hiba: Nem sikerült lezárni a pozíciót: " + e.getMessage());
            }
        });

        VBox layout = new VBox(10, positionIdField, closeButton);
        layout.setSpacing(10);

        root.setCenter(layout);
    }

    private void closePosition(String tradeId) {
        System.out.println("Pozíció zárása: " + tradeId);

        Context ctx = new ContextBuilder(Config.URL)
                .setToken(Config.TOKEN)
                .setApplication("ClosePosition")
                .build();

        AccountID accountId = Config.ACCOUNTID;

        try {
            TradeSpecifier tradeSpecifier = new TradeSpecifier(tradeId);
            TradeCloseRequest closeRequest = new TradeCloseRequest(accountId, tradeSpecifier);

            var response = ctx.trade.close(closeRequest);

            System.out.println("Pozíció sikeresen lezárva: " + tradeId);
            System.out.println("API válasz: " + response);
        } catch (ExecuteException e) {
            System.out.println("API hiba történt: " + e.getMessage());
            throw new RuntimeException("Nem sikerült lezárni a pozíciót: " + tradeId, e);
        } catch (RequestException e) {
            System.out.println("Kérés hiba történt: " + e.getMessage());
            throw new RuntimeException("Nem sikerült lezárni a pozíciót: " + tradeId, e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Váratlan hiba: " + e.getMessage());
        }
    }
}
