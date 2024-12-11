package com.example.javafxvarosok;

import com.oanda.v20.instrument.Candlestick;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoricalPricesController {
    public void showInMainView(BorderPane root) {
        // UI elemek létrehozása
        ComboBox<String> currencyPairBox = new ComboBox<>();
        currencyPairBox.getItems().addAll("EUR_USD", "GBP_USD", "USD_JPY");

        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();

        // Táblázat létrehozása
        TableView<Candlestick> tableView = new TableView<>();

        // Táblázat oszlopok
        TableColumn<Candlestick, String> timeColumn = new TableColumn<>("Idő");
        timeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTime().toString()));

        TableColumn<Candlestick, String> closeColumn = new TableColumn<>("Záró ár");
        closeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMid().getC().toString()));

        tableView.getColumns().addAll(timeColumn, closeColumn);

        // Grafikon létrehozása
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Historikus árak");

        // Eseménykezelő az adatok lekérésére
        currencyPairBox.setOnAction(event -> updateData(currencyPairBox, startDatePicker, endDatePicker, tableView, chart));
        startDatePicker.setOnAction(event -> updateData(currencyPairBox, startDatePicker, endDatePicker, tableView, chart));
        endDatePicker.setOnAction(event -> updateData(currencyPairBox, startDatePicker, endDatePicker, tableView, chart));

        // Layout beállítása
        VBox layout = new VBox(currencyPairBox, startDatePicker, endDatePicker, tableView, chart);
        layout.setSpacing(10);
        root.setCenter(layout);
    }

    private void updateData(ComboBox<String> currencyPairBox, DatePicker startDatePicker, DatePicker endDatePicker,
                            TableView<Candlestick> tableView, LineChart<Number, Number> chart) {
        String instrument = currencyPairBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (instrument != null && startDate != null && endDate != null) {
            // Dátumformátum konvertálása az OANDA API számára
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String start = startDate.atStartOfDay().format(formatter);
            String end = endDate.atStartOfDay().format(formatter);

            // Adatok lekérése
            List<Candlestick> data = HistorikusAdatok.fetchHistoricalData(instrument, start, end);

            // Táblázat feltöltése
            tableView.getItems().clear();
            tableView.getItems().addAll(data);

            // Grafikon feltöltése
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("Árfolyamok");

            for (int i = 0; i < data.size(); i++) {
                Candlestick candle = data.get(i);
                series.getData().add(new XYChart.Data<>(i, Double.parseDouble(candle.getMid().getC().toString())));
            }

            chart.getData().clear();
            chart.getData().add(series);
        }
    }
}
