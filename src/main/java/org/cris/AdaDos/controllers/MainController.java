package org.cris.AdaDos.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.cris.AdaDos.TareaDos.Security;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasenia;
    @FXML private Label lblMensaje;

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.cris.AdaDos.Views/CapturaCalificaciones.fxml"));
                Scene scene = new Scene(loader.load());
                Stage nuevaVentana = new Stage();
                nuevaVentana.setTitle("Captura de Calificaciones - AdaDos");
                nuevaVentana.setScene(scene);

                nuevaVentana.show();

                nuevaVentana.setAlwaysOnTop(true);
                nuevaVentana.toFront();
                nuevaVentana.requestFocus();
                nuevaVentana.setAlwaysOnTop(false); // Importante: la soltamos para que no estorbe después

                // 4. Ahora sí, matamos la ventana de Login vieja
                Stage ventanaLogin = (Stage) txtUsuario.getScene().getWindow();
                ventanaLogin.close(); // Usamos close() en vez de hide() para liberar memoria

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

    private boolean validarCredenciales(String usuario, String password) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(usuariosEncriptados), StandardCharsets.UTF_8))) {
            String linea;
            br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 2) continue;

                String usuarioCSV = datos[0];
                String contraseniaAlmacenada = datos[1];

                if (usuarioCSV.equals(usuario)) {
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