package com.example.javafxvarosok;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class WriteController {
    // Város mezők
    @FXML
    private TextField cityNameField;
    @FXML
    private TextField cityCountyIdField;
    @FXML
    private CheckBox cityCountySeatBox;
    @FXML
    private CheckBox cityCountyAuthorityBox;
    @FXML
    private Button addCityButton;

    // Megye mezők
    @FXML
    private TextField countyNameField;
    @FXML
    private Button addCountyButton;

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
    private Button addPopulationButton;

    private CityDAO cityDAO = new CityDAO();
    private CountyDAO countyDAO = new CountyDAO();
    private PopulationDAO populationDAO = new PopulationDAO();

    @FXML
    public void initialize() {
        // Város hozzáadása
        addCityButton.setOnAction(event -> {
            try {
                String name = cityNameField.getText();
                int countyId = Integer.parseInt(cityCountyIdField.getText());
                boolean isCountySeat = cityCountySeatBox.isSelected();
                boolean isCountyAuthority = cityCountyAuthorityBox.isSelected();

                cityDAO.insertCity(name, countyId, isCountySeat, isCountyAuthority);

                showSuccess("A város sikeresen hozzáadva!");
                clearCityFields();
            } catch (Exception e) {
                showError("Hiba történt a város hozzáadása során!");
            }
        });

        // Megye hozzáadása
        addCountyButton.setOnAction(event -> {
            try {
                String name = countyNameField.getText();

                countyDAO.insertCounty(name);

                showSuccess("A megye sikeresen hozzáadva!");
                clearCountyFields();
            } catch (Exception e) {
                showError("Hiba történt a megye hozzáadása során!");
            }
        });

        // Lélekszám hozzáadása
        addPopulationButton.setOnAction(event -> {
            try {
                int cityId = Integer.parseInt(populationCityIdField.getText());
                int year = Integer.parseInt(populationYearField.getText());
                int females = Integer.parseInt(populationFemaleField.getText());
                int total = Integer.parseInt(populationTotalField.getText());

                populationDAO.insertPopulation(cityId, year, females, total);

                showSuccess("A lélekszám sikeresen hozzáadva!");
                clearPopulationFields();
            } catch (Exception e) {
                showError("Hiba történt a lélekszám hozzáadása során!");
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

    private void clearCityFields() {
        cityNameField.clear();
        cityCountyIdField.clear();
        cityCountySeatBox.setSelected(false);
        cityCountyAuthorityBox.setSelected(false);
    }

    private void clearCountyFields() {
        countyNameField.clear();
    }

    private void clearPopulationFields() {
        populationCityIdField.clear();
        populationYearField.clear();
        populationFemaleField.clear();
        populationTotalField.clear();
    }
}
