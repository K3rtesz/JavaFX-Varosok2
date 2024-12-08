package com.example.javafxvarosok;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class Read2Controller {
    // Város szűrés
    @FXML
    private TextField cityNameFilter;
    @FXML
    private TextField cityCountyIdFilter;
    @FXML
    private Button filterCityButton;
    @FXML
    private TableView<City> cityTable;
    @FXML
    private TableColumn<City, Integer> cityIdColumn;
    @FXML
    private TableColumn<City, String> cityNameColumn;
    @FXML
    private TableColumn<City, Integer> cityCountyIdColumn;
    @FXML
    private TableColumn<City, Boolean> citySeatColumn;
    @FXML
    private TableColumn<City, Boolean> cityAuthorityColumn;

    // Megye szűrés
    @FXML
    private TextField countyNameFilter;
    @FXML
    private Button filterCountyButton;
    @FXML
    private TableView<County> countyTable;
    @FXML
    private TableColumn<County, Integer> countyIdColumn;
    @FXML
    private TableColumn<County, String> countyNameColumn;

    // Lélekszám szűrés
    @FXML
    private TextField populationCityIdFilter;
    @FXML
    private TextField populationYearFilter;
    @FXML
    private Button filterPopulationButton;
    @FXML
    private TableView<Population> populationTable;
    @FXML
    private TableColumn<Population, Integer> populationCityIdColumn;
    @FXML
    private TableColumn<Population, Integer> populationYearColumn;
    @FXML
    private TableColumn<Population, Integer> populationFemaleColumn;
    @FXML
    private TableColumn<Population, Integer> populationTotalColumn;

    private CityDAO cityDAO = new CityDAO();
    private CountyDAO countyDAO = new CountyDAO();
    private PopulationDAO populationDAO = new PopulationDAO();

    @FXML
    public void initialize() {
        // Város táblázat oszlopai
        cityIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cityNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cityCountyIdColumn.setCellValueFactory(new PropertyValueFactory<>("countyId"));
        citySeatColumn.setCellValueFactory(new PropertyValueFactory<>("countySeat"));
        cityAuthorityColumn.setCellValueFactory(new PropertyValueFactory<>("countyAuthority"));

        // Megye táblázat oszlopai
        countyIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        countyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Lélekszám táblázat oszlopai
        populationCityIdColumn.setCellValueFactory(new PropertyValueFactory<>("cityId"));
        populationYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        populationFemaleColumn.setCellValueFactory(new PropertyValueFactory<>("females"));
        populationTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Város szűrés
        filterCityButton.setOnAction(event -> {
            try {
                String name = cityNameFilter.getText();
                String countyId = cityCountyIdFilter.getText();
                List<City> results = cityDAO.filterCities(name, countyId);
                cityTable.getItems().setAll(results);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Megye szűrés
        filterCountyButton.setOnAction(event -> {
            try {
                String name = countyNameFilter.getText();
                List<County> results = countyDAO.filterCounties(name);
                countyTable.getItems().setAll(results);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Lélekszám szűrés
        filterPopulationButton.setOnAction(event -> {
            try {
                String cityId = populationCityIdFilter.getText();
                String year = populationYearFilter.getText();
                List<Population> results = populationDAO.filterPopulations(cityId, year);
                populationTable.getItems().setAll(results);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
