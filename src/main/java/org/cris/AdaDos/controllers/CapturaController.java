package org.cris.AdaDos.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.cris.AdaDos.utils.UIEfectos;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CapturaController {

    @FXML private VBox viewCarga;
    @FXML private Button btnCargarArchivo;

    // Guardamos el archivo para usarlo luego
    private File archivoSeleccionado;

    @FXML
    public void initialize() {
        System.out.println("DEBUG: Controlador CapturaController inicializado.");
        try {
            // Si esto falla es porque UIEfectos no está en el paquete correcto,
            // pero no debería romper el botón.
            UIEfectos.estilarBotonAzul(btnCargarArchivo);
        } catch (Exception e) {
            System.err.println("Advertencia: No se pudieron cargar los estilos: " + e.getMessage());
        }
    }

    @FXML
    public void cargarArchivo() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar lista de alumnos");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));

        File file = fileChooser.showOpenDialog(btnCargarArchivo.getScene().getWindow());

        if (file != null) {
            System.out.println("Archivo seleccionado: " + file.getAbsolutePath());
            validarYProcesar(file);
        } else {
            System.out.println("Carga de archivo cancelada por el usuario.");
        }
    }

    private void validarYProcesar(File file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String primeraLinea = br.readLine();

            if (primeraLinea == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Archivo Vacío", "El archivo seleccionado no tiene contenido.");
                return;
            }

            String[] columnas = primeraLinea.split(",");

            if (columnas.length < 4) {
                mostrarAlerta(Alert.AlertType.ERROR, "Formato Incorrecto",
                        "El archivo no cumple con los campos requeridos:\n- Matrícula\n- Primer Apellido\n- Segundo Apellido\n- Nombres");
                return;
            }

            this.archivoSeleccionado = file;
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Archivo cargado exitosamente.");


        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Lectura", "No se pudo leer el archivo: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        if (btnCargarArchivo.getScene() != null) {
            alerta.initOwner(btnCargarArchivo.getScene().getWindow());
        }

        alerta.showAndWait();
    }
}