package com.example.javafxvarosok;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ParallelController {
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Button startButton;

    private ScheduledExecutorService scheduler;

    @FXML
    public void initialize() {
        scheduler = Executors.newScheduledThreadPool(2);

        startButton.setOnAction(event -> startParallelTasks());
    }

    private void startParallelTasks() {
        // Első Label frissítése 1 másodpercenként
        scheduler.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> label1.setText("Frissítés: " + System.currentTimeMillis()));
        }, 0, 1, TimeUnit.SECONDS);

        // Második Label frissítése 2 másodpercenként
        scheduler.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> label2.setText("Frissítés: " + System.currentTimeMillis()));
        }, 0, 2, TimeUnit.SECONDS);
    }

    // Alkalmazás leállításakor a szálak megszakítása
    public void shutdown() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
    }
}
