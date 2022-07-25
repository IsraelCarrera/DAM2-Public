package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainFX extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loaderMenu = new FXMLLoader(
                getClass().getResource("/fxml/principalPantalla.fxml"));
        BorderPane root = loaderMenu.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Pelis y series API");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}