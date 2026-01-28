package org.cris.AdaDos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static final String LOGIN_VIEW = "/org.cris.AdaDos.Views/MainView.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(LOGIN_VIEW));

        Scene Login = new Scene(fxmlLoader.load(), 400, 350); // Definimos un tamaño inicial
        stage.setTitle("Login - AdaDos");
        stage.setScene(Login);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}