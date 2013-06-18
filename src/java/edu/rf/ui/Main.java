package edu.rf.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/layout/trial.fxml"));

        primaryStage.setTitle("RichFeed");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(500);
        populateFolderList();
        primaryStage.show();
    }

    public void populateFolderList() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/layout/trial.fxml"));
        ListView<String> folderList = (ListView<String>) root.lookup("#folderList");
        ObservableList<String> list = FXCollections.observableArrayList(
                "Item 1", "Item 2", "Item 3", "Item 4");

    }

    public static void main(String[] args) {
        launch(args);
    }
}
