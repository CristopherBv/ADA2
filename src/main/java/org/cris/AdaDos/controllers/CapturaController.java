package org.cris.AdaDos.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality; // Importante para el pop-up
import javafx.stage.Stage;

import org.cris.AdaDos.models.Alumno;
import org.cris.AdaDos.TareaTres.GeneradorPDF;
import org.cris.AdaDos.utils.ExportadorCSV; // Tu nueva clase
import org.cris.AdaDos.utils.UIEfectos;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CapturaController {

    @FXML private VBox viewCarga;
    @FXML private VBox viewTabla;
    @FXML private Button btnCargarArchivo;

    @FXML private TableView<Alumno> tablaAlumnos;
    @FXML private TableColumn<Alumno, String> colMatricula;
    @FXML private TableColumn<Alumno, String> colApellido1;
    @FXML private TableColumn<Alumno, String> colApellido2;
    @FXML private TableColumn<Alumno, String> colNombres;
    @FXML private TableColumn<Alumno, String> colCalificacion;

    @FXML private Button btnGenerarPDF;
    @FXML private Button btnGenerarCSV;

    private ObservableList<Alumno> listaAlumnos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        UIEfectos.estilarBotonAzul(btnCargarArchivo);
        UIEfectos.estilarBotonRojo(btnGenerarPDF);

        viewCarga.setVisible(true);
        viewTabla.setVisible(false);

        configurarTabla();
        actualizarEstadoBotonCSV();
    }

    private void configurarTabla() {
        // La magia de PropertyValueFactory requiere el cambio en module-info.java
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colCalificacion.setCellValueFactory(new PropertyValueFactory<>("calificacion"));

        tablaAlumnos.setEditable(true);
        colCalificacion.setCellFactory(TextFieldTableCell.forTableColumn());

        colCalificacion.setOnEditCommit(event -> {
            Alumno alumno = event.getRowValue();
            String nuevoValor = event.getNewValue();

            if (esCalificacionValida(nuevoValor)) {
                alumno.setCalificacion(nuevoValor);
                actualizarEstadoBotonCSV();
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Dato Inválido", "Ingresa un número entero entre 0 y 100.");
                tablaAlumnos.refresh();
            }
        });
    }

    private boolean esCalificacionValida(String valor) {
        if (valor == null || valor.trim().isEmpty()) return false;
        try {
            int nota = Integer.parseInt(valor);
            return nota >= 0 && nota <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void actualizarEstadoBotonCSV() {
        boolean todoCompleto = true;
        for (Alumno al : listaAlumnos) {
            if (!al.tieneCalificacionValida()) {
                todoCompleto = false;
                break;
            }
        }

        if (todoCompleto && !listaAlumnos.isEmpty()) {
            btnGenerarCSV.setDisable(false);
            UIEfectos.estilarBotonVerde(btnGenerarCSV);
        } else {
            UIEfectos.estilarBotonDeshabilitado(btnGenerarCSV);
        }
    }

    @FXML
    public void cargarArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar lista de alumnos");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));

        // Obtener Stage de forma segura
        Stage stage = (Stage) btnCargarArchivo.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            procesarArchivo(file);
        }
    }

    private void procesarArchivo(File file) {
        listaAlumnos.clear();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String linea = br.readLine();

            if (linea == null || linea.split(",").length < 4) {
                mostrarAlerta(Alert.AlertType.ERROR, "Formato Incorrecto", "El archivo debe tener al menos 4 columnas.");
                return;
            }

            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                if (d.length >= 4) {
                    listaAlumnos.add(new Alumno(d[0], d[1], d[2], d[3]));
                }
            }

            tablaAlumnos.setItems(listaAlumnos);
            viewCarga.setVisible(false);
            viewTabla.setVisible(true);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Carga Exitosa", "Se cargaron " + listaAlumnos.size() + " alumnos.");
            actualizarEstadoBotonCSV();

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo leer el archivo: " + e.getMessage());
        }
    }

    @FXML
    public void generarPDF() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar PDF");
        fc.setInitialFileName("ReporteCalificaciones.pdf");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));

        File destino = fc.showSaveDialog(tablaAlumnos.getScene().getWindow());

        if (destino != null) {
            try {
                GeneradorPDF generador = new GeneradorPDF();
                generador.generarPdfDesdeLista(listaAlumnos, destino.getAbsolutePath());
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "PDF generado correctamente.");
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Falló el PDF: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void generarCSV() {
        // Validación final
        for (Alumno al : listaAlumnos) {
            if (!al.tieneCalificacionValida()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Incompleto", "Faltan calificaciones por asignar.");
                return;
            }
        }

        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar CSV");
        fc.setInitialFileName("CalificacionesFinales.csv");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));

        File destino = fc.showSaveDialog(tablaAlumnos.getScene().getWindow());

        if (destino != null) {
            try {
                // LLAMADA A TU NUEVA CLASE
                ExportadorCSV.generarReporte(listaAlumnos, destino.getAbsolutePath());
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "CSV generado correctamente.");
            } catch (IOException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar el CSV: " + e.getMessage());
            }
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        // --- CORRECCIÓN DE POP-UP TÍMIDO ---
        // 1. Asignar dueño (Owner)
        if (tablaAlumnos.getScene() != null) {
            alerta.initOwner(tablaAlumnos.getScene().getWindow());
        } else if (btnCargarArchivo.getScene() != null) {
            alerta.initOwner(btnCargarArchivo.getScene().getWindow());
        }

        // 2. Hacerlo MODAL DE APLICACIÓN (Bloquea todo lo demás)
        alerta.initModality(Modality.APPLICATION_MODAL);

        alerta.showAndWait();
    }
}