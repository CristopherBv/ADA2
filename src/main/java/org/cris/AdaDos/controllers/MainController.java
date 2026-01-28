package org.cris.AdaDos.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.cris.AdaDos.TareaDos.Security;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainController {

    // Vinculamos los elementos del FXML usando sus fx:id
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContrasenia;
    @FXML
    private Label lblMensaje;

    // Ruta absoluta del CSV (Misma que usabas en ControlAcceso.java)
    private final String usuariosEncriptados = "/home/cristopher/Cuarto_Semestre/DisenoDeSoftware/ADA2/ArchivosCSV/tabla_usuarios_encriptados.csv";

    @FXML
    protected void handleLogin() {
        String usuarioIngresado = txtUsuario.getText();
        String passIngresado = txtContrasenia.getText();

        if (usuarioIngresado.isEmpty() || passIngresado.isEmpty()) {
            mostrarMensaje("Por favor llena todos los campos.");
            return;
        }

        if (validarCredenciales(usuarioIngresado, passIngresado)) {
            mostrarMensaje("¡Acceso Concedido!");
            lblMensaje.setStyle("-fx-text-fill: green;");

            try {
                txtUsuario.getScene().getWindow().hide();

                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/org.cris.AdaDos.Views/CapturaCalificaciones.fxml"));
                javafx.scene.Scene scene = new javafx.scene.Scene(loader.load());

                javafx.stage.Stage stage = new javafx.stage.Stage();
                stage.setTitle("Captura de Calificaciones - AdaDos");
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                mostrarMensaje("Error al abrir la app: " + e.getMessage());
            }

        } else {
            mostrarMensaje("Usuario o contraseña incorrectos.");
            lblMensaje.setStyle("-fx-text-fill: red;");
        }
    }

    private void mostrarMensaje(String msg) {
        lblMensaje.setText(msg);
        lblMensaje.setVisible(true);
    }

    // Lógica extraída y adaptada de ControlAcceso.java para no usar Scanner
    private boolean validarCredenciales(String usuario, String password) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(usuariosEncriptados), StandardCharsets.UTF_8))) {
            String linea;
            br.readLine(); // Saltar cabecera

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 2) continue; // Evitar errores de índice

                String usuarioCSV = datos[0];
                String contraseniaAlmacenada = datos[1];

                if (usuarioCSV.equals(usuario)) {
                    // Usamos tu clase Security existente
                    return Security.validarContrasenia(password, contraseniaAlmacenada);
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo CSV: " + e.getMessage());
            mostrarMensaje("Error de sistema: No se encuentra la base de datos.");
        }
        return false;
    }
}