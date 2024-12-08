package com.example.javafxvarosok;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();

        Menu databaseMenu = new Menu("Adatbázis");
        MenuItem readMenu = new MenuItem("Olvas");
        MenuItem readMenu2 = new MenuItem("Olvas2");
        MenuItem writeMenu = new MenuItem("Ír");
        MenuItem updateMenu = new MenuItem("Módosít");
        MenuItem deleteMenu = new MenuItem("Töröl");

        databaseMenu.getItems().addAll(readMenu, readMenu2, writeMenu, updateMenu, deleteMenu);
        menuBar.getMenus().add(databaseMenu);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Város Projekt");
        primaryStage.setScene(scene);
        primaryStage.show();

        readMenu.setOnAction(e -> {
            try {
                Parent newRoot = FXMLLoader.load(getClass().getResource("read-view.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Adatok megjelenítése");
                stage.setScene(new Scene(newRoot));
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        writeMenu.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxvarosok/write-view.fxml"));
                Parent newRoot = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Adatok hozzáadása");
                stage.setScene(new Scene(newRoot));
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        readMenu2.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxvarosok/read2-view.fxml"));
                Parent newRoot = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Szűrt adatok megjelenítése");
                stage.setScene(new Scene(newRoot));
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        updateMenu.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxvarosok/update-view.fxml"));
                Parent newRoot = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Adatok módosítása");
                stage.setScene(new Scene(newRoot));
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        deleteMenu.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxvarosok/delete-view.fxml"));
                Parent newRoot = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Adatok törlése");
                stage.setScene(new Scene(newRoot));
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        /*Menu soapMenu = new Menu("SOAP kliens");
        MenuItem downloadMenuItem = new MenuItem("Letöltés");
        soapMenu.getItems().add(downloadMenuItem);

        downloadMenuItem.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxvarosok/soap-download-view.fxml"));
                Parent newRoot = loader.load();
                Stage stage = new Stage();
                stage.setTitle("SOAP árfolyam letöltés");
                stage.setScene(new Scene(newRoot));
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        menuBar.getMenus().add(soapMenu);
    */

        Menu parallelMenu = new Menu("Párhuzamos");
        MenuItem parallelTaskMenuItem = new MenuItem("Párhuzamos feladatok");
        parallelMenu.getItems().add(parallelTaskMenuItem);

        parallelTaskMenuItem.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxvarosok/parallel-view.fxml"));
                Parent newRoot = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Párhuzamos programvégrehajtás");
                stage.setScene(new Scene(newRoot));
                stage.show();

                // Alkalmazás bezárásakor a szálakat megszakítjuk
                ParallelController controller = loader.getController();
                stage.setOnCloseRequest(event -> controller.shutdown());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        menuBar.getMenus().add(parallelMenu);



    }

    public static void main(String[] args) {
        launch(args);
    }
}
