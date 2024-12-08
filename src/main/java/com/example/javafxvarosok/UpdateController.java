package com.example.javafxvarosok;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class UpdateController {
    // Város mezők
    @FXML
    private TextField cityIdField;
    @FXML
    private TextField cityNameField;
    @FXML
    private TextField cityCountyIdField;
    @FXML
    private CheckBox cityCountySeatBox;
    @FXML
    private CheckBox cityCountyAuthorityBox;
    @FXML
    private Button updateCityButton;

    // Megye mezők
    @FXML
    private TextField countyIdField;
    @FXML
    private TextField countyNameField;
    @FXML
    private Button updateCountyButton;

    // Lélekszám mezők
    @FXML
    private TextField populationCityIdField;
    @FXML
    private TextField populationYearField;
    @FXML
    private TextField populationFemaleField;
    @FXML
    private TextField populationTotalField;
    @FXML
    private Button updatePopulationButton;

    private CityDAO cityDAO = new CityDAO();
    private CountyDAO countyDAO = new CountyDAO();
    private PopulationDAO populationDAO = new PopulationDAO();

    @FXML
    public void initialize() {
        // Város módosítása
        updateCityButton.setOnAction(event -> {
            try {
                int id = Integer.parseInt(cityIdField.getText());
                String name = cityNameField.getText();
                int countyId = Integer.parseInt(cityCountyIdField.getText());
                boolean isCountySeat = cityCountySeatBox.isSelected();
                boolean isCountyAuthority = cityCountyAuthorityBox.isSelected();

                cityDAO.updateCity(id, name, countyId, isCountySeat, isCountyAuthority);

                showSuccess("A város sikeresen módosítva!");
            } catch (Exception e) {
                showError("Hiba történt a város módosítása során!");
            }
        });

        // Megye módosítása
        updateCountyButton.setOnAction(event -> {
            try {
                int id = Integer.parseInt(countyIdField.getText());
                String name = countyNameField.getText();

                countyDAO.updateCounty(id, name);

                showSuccess("A megye sikeresen módosítva!");
            } catch (Exception e) {
                showError("Hiba történt a megye módosítása során!");
            }
        });

        // Lélekszám módosítása
        updatePopulationButton.setOnAction(event -> {
            try {
                int cityId = Integer.parseInt(populationCityIdField.getText());
                int year = Integer.parseInt(populationYearField.getText());
                int females = Integer.parseInt(populationFemaleField.getText());
                int total = Integer.parseInt(populationTotalField.getText());

                populationDAO.updatePopulation(cityId, year, females, total);

                showSuccess("A lélekszám sikeresen módosítva!");
            } catch (Exception e) {
                showError("Hiba történt a lélekszám módosítása során!");
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
