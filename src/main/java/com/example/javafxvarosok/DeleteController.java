package com.example.javafxvarosok;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class DeleteController {
    // Város mezők
    @FXML
    private TextField cityIdField;
    @FXML
    private Button deleteCityButton;

    // Megye mezők
    @FXML
    private TextField countyIdField;
    @FXML
    private Button deleteCountyButton;

    // Lélekszám mezők
    @FXML
    private TextField populationCityIdField;
    @FXML
    private TextField populationYearField;
    @FXML
    private Button deletePopulationButton;

    private CityDAO cityDAO = new CityDAO();
    private CountyDAO countyDAO = new CountyDAO();
    private PopulationDAO populationDAO = new PopulationDAO();

    @FXML
    public void initialize() {
        // Város törlése
        deleteCityButton.setOnAction(event -> {
            try {
                int id = Integer.parseInt(cityIdField.getText());
                cityDAO.deleteCity(id);
                showSuccess("A város sikeresen törölve!");
            } catch (Exception e) {
                showError("Hiba történt a város törlése során!");
            }
        });

        // Megye törlése
        deleteCountyButton.setOnAction(event -> {
            try {
                int id = Integer.parseInt(countyIdField.getText());
                countyDAO.deleteCounty(id);
                showSuccess("A megye sikeresen törölve!");
            } catch (Exception e) {
                showError("Hiba történt a megye törlése során!");
            }
        });

        // Lélekszám törlése
        deletePopulationButton.setOnAction(event -> {
            try {
                int cityId = Integer.parseInt(populationCityIdField.getText());
                int year = Integer.parseInt(populationYearField.getText());
                populationDAO.deletePopulation(cityId, year);
                showSuccess("A lélekszám sikeresen törölve!");
            } catch (Exception e) {
                showError("Hiba történt a lélekszám törlése során!");
            }
        });
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Siker");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
