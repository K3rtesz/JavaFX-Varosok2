/*package com.example.javafxvarosok;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;

public class SoapDownloadController {
    @FXML
    private Button downloadAllButton;
    @FXML
    private Button downloadFilteredButton;
    @FXML
    private TextField startDateField;
    @FXML
    private TextField endDateField;
    @FXML
    private TextField currencyField;

    private MNBSoapClient soapClient = new MNBSoapClient();

    @FXML
    public void initialize() {
        // Összes árfolyam letöltése
        downloadAllButton.setOnAction(event -> {
            try {
                String data = soapClient.getAllExchangeRates();
                saveToFile("MNB_All.txt", data);
                showSuccess("Az összes árfolyam sikeresen letöltve!");
            } catch (Exception e) {
                showError("Hiba történt az összes árfolyam letöltése során!");
            }
        });

        // Szűrt árfolyam letöltése
        downloadFilteredButton.setOnAction(event -> {
            try {
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                String currency = currencyField.getText();

                String data = soapClient.getExchangeRates(startDate, endDate, currency);
                saveToFile("MNB_Filtered.txt", data);
                showSuccess("A szűrt árfolyamok sikeresen letöltve!");
            } catch (Exception e) {
                showError("Hiba történt a szűrt árfolyamok letöltése során!");
            }
        });
    }

    private void saveToFile(String fileName, String data) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(data);
        }
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Siker");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
*/