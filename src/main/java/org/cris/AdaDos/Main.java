package org.cris.AdaDos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    public static final String LOGIN_VIEW = "/org.cris.AdaDos.Views/MainView.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(LOGIN_VIEW));
        Parent root = fxmlLoader.load();

        Scene Login = new Scene(root);
        Login.setFill(Color.TRANSPARENT);

        stage.setTitle("Login - AdaDos");
        stage.setScene(Login);

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}