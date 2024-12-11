package com.example.javafxvarosok;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.trade.Trade;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class OpenPositionsController {

    public void showInMainView(BorderPane root) {
        // Táblázat létrehozása
        TableView<Trade> tableView = new TableView<>();

        // Oszlopok definiálása
        TableColumn<Trade, String> idColumn = new TableColumn<>("Trade ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Trade, String> instrumentColumn = new TableColumn<>("Instrumentum");
        instrumentColumn.setCellValueFactory(new PropertyValueFactory<>("instrument"));

        TableColumn<Trade, String> openTimeColumn = new TableColumn<>("Nyitási idő");
        openTimeColumn.setCellValueFactory(new PropertyValueFactory<>("openTime"));

        TableColumn<Trade, String> unitsColumn = new TableColumn<>("Mennyiség");
        unitsColumn.setCellValueFactory(new PropertyValueFactory<>("currentUnits"));

        TableColumn<Trade, String> priceColumn = new TableColumn<>("Ár");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Trade, String> unrealizedPLColumn = new TableColumn<>("Nem realizált P/L");
        unrealizedPLColumn.setCellValueFactory(new PropertyValueFactory<>("unrealizedPL"));

        // Oszlopok hozzáadása a táblázathoz
        tableView.getColumns().addAll(idColumn, instrumentColumn, openTimeColumn, unitsColumn, priceColumn, unrealizedPLColumn);

        // Nyitott tradek lekérése és megjelenítése
        List<Trade> trades = null;
        try {
            trades = getOpenTrades();
            tableView.getItems().addAll(trades);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!trades.isEmpty()) {
            tableView.getItems().clear();
            tableView.getItems().addAll(trades);
            System.out.println("Táblázat feltöltve.");
        } else {
            System.out.println("Nincsenek nyitott pozíciók.");
        }


        // Táblázat hozzáadása az elrendezéshez
        root.setCenter(tableView);
    }

    private List<Trade> getOpenTrades() throws ExecuteException, RequestException {
        // API kapcsolat
        Context ctx = new ContextBuilder(Config.URL)
                .setToken(Config.TOKEN)
                .setApplication("OpenPositions")
                .build();

        AccountID accountId = Config.ACCOUNTID;

        // Nyitott tradek lekérése
        return ctx.trade.listOpen(accountId).getTrades();
    }
}
