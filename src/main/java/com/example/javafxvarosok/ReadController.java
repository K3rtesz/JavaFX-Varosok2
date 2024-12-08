package com.example.javafxvarosok;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ReadController {
    @FXML
    private TableView<City> cityTable;
    @FXML
    private TableColumn<City, Integer> cityIdColumn;
    @FXML
    private TableColumn<City, String> cityNameColumn;
    @FXML
    private TableColumn<City, Integer> countyIdColumn;
    @FXML
    private TableColumn<City, Boolean> countySeatColumn;
    @FXML
    private TableColumn<City, Boolean> countyAuthorityColumn;

    @FXML
    private TableView<County> countyTable;
    @FXML
    private TableColumn<County, Integer> countyIdColumn2;
    @FXML
    private TableColumn<County, String> countyNameColumn;

    @FXML
    private TableView<Population> populationTable;
    @FXML
    private TableColumn<Population, Integer> populationCityIdColumn;
    @FXML
    private TableColumn<Population, Integer> yearColumn;
    @FXML
    private TableColumn<Population, Integer> femalesColumn;
    @FXML
    private TableColumn<Population, Integer> totalColumn;

    private CityDAO cityDAO = new CityDAO();
    private CountyDAO countyDAO = new CountyDAO();
    private PopulationDAO populationDAO = new PopulationDAO();

    @FXML
    public void initialize() {
        // Város
        cityIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cityNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countyIdColumn.setCellValueFactory(new PropertyValueFactory<>("countyId"));
        countySeatColumn.setCellValueFactory(new PropertyValueFactory<>("countySeat"));
        countyAuthorityColumn.setCellValueFactory(new PropertyValueFactory<>("countyAuthority"));

        // Megye
        countyIdColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
        countyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Lélekszám
        populationCityIdColumn.setCellValueFactory(new PropertyValueFactory<>("cityId"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        femalesColumn.setCellValueFactory(new PropertyValueFactory<>("females"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            cityTable.getItems().addAll(cityDAO.getAllCities());
            countyTable.getItems().addAll(countyDAO.getAllCounties());
            populationTable.getItems().addAll(populationDAO.getAllPopulations());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
