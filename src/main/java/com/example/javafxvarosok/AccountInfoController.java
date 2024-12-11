package com.example.javafxvarosok;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class AccountInfoController {
    public void showInMainView(BorderPane root) {
        // Táblázat létrehozása a számlainformációk megjelenítéséhez
        TableView<AccountSummary> table = new TableView<>();

        // Táblázat oszlopok definiálása
        TableColumn<AccountSummary, String> idColumn = new TableColumn<>("Számla ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<AccountSummary, String> balanceColumn = new TableColumn<>("Egyenleg");
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        TableColumn<AccountSummary, String> currencyColumn = new TableColumn<>("Pénznem");
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        // Oszlopok hozzáadása a táblázathoz
        table.getColumns().addAll(idColumn, balanceColumn, currencyColumn);

        // API kapcsolat és adatok betöltése
        try {
            Context ctx = new ContextBuilder(Config.getApiUrl())
                    .setToken(Config.getApiKey())
                    .setApplication("AccountInfoApp")
                    .build();

            // Számla összegző adatok lekérése
            AccountSummary summary = ctx.account.summary(new AccountID("101-004-30534366-001")).getAccount();

            // Adatok hozzáadása a táblázathoz
            table.getItems().add(summary);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Táblázat megjelenítése az elrendezés közepén
        root.setCenter(table);
    }
}
