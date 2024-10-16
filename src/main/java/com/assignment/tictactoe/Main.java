package com.assignment.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/board-view.fxml"));
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
       /* root.prefWidthProperty().bind(stage.widthProperty());
        root.prefHeightProperty().bind(stage.heightProperty());*/
        stage.setTitle("Tic-Tac-Toe Championship Edition");
        Image image = new Image(getClass().getResourceAsStream("/icons/game.png"));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}