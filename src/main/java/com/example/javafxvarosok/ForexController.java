package com.example.javafxvarosok;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ForexController {
    private static final String API_URL = "https://api-fxpractice.oanda.com/v3/accounts";
    private static final String API_KEY = "fcd9d50c2832d73cee90e55aeaed0849-5f750ec1d1393fdd6821f97cc469f3b0"; // Helyettesítsd az API kulcsoddal

    public void showAccountInfo(BorderPane root) {
        TableView<AccountInfo> table = new TableView<>();

        TableColumn<AccountInfo, String> column1 = new TableColumn<>("Számla ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<AccountInfo, String> column2 = new TableColumn<>("Valuta");
        column2.setCellValueFactory(new PropertyValueFactory<>("currency"));

        table.getColumns().addAll(column1, column2);

        // Adatok lekérése
        fetchAccountInfo(); // Hívja meg a fenti metódust

        // Táblázat hozzáadása a fő elrendezéshez
        root.setCenter(table);
    }


    private void fetchAccountInfo() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL)) // Az API_URL-t előzetesen definiáltuk
                .header("Authorization", "Bearer " + API_KEY) // Az API_KEY az Oanda API kulcs
                .build();

        HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    try {
                        Gson gson = new Gson();
                        JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);

                        if (jsonResponse.has("accounts") && jsonResponse.get("accounts").isJsonArray()) {
                            JsonArray accounts = jsonResponse.getAsJsonArray("accounts");
                            JsonObject firstAccount = accounts.get(0).getAsJsonObject();

                            Platform.runLater(() -> {
                                // Példa: Írd ki az első számla adatait konzolra
                                System.out.println("Első számla adatai: " + firstAccount.toString());
                            });
                        } else {
                            System.out.println("Hiba: Az 'accounts' kulcs nem található, vagy nem egy tömb.");
                        }
                    } catch (Exception e) {
                        System.err.println("Hiba történt az API válasz feldolgozása közben: " + e.getMessage());
                        e.printStackTrace();
                    }
                })
                .exceptionally(e -> {
                    System.err.println("Hiba az API hívás közben: " + e.getMessage());
                    return null;
                });
    }

    // Adattípus a táblázathoz
    public static class AccountInfo {
        private final String id;
        private final String currency;
        private final String balance;

        public AccountInfo(String id, String currency, String balance) {
            this.id = id;
            this.currency = currency;
            this.balance = balance;
        }

        public String getId() {
            return id;
        }

        public String getCurrency() {
            return currency;
        }

        public String getBalance() {
            return balance;
        }
    }
}
