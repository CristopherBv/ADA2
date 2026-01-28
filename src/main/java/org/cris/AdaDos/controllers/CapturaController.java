package org.cris.AdaDos.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.cris.AdaDos.utils.UIEfectos;

public class CapturaController {

    @FXML
    private Button btnCargarArchivo;

    @FXML
    public void initialize() {
        UIEfectos.estilarBotonAzul(btnCargarArchivo);
        System.out.println("Vista de Captura cargada correctamente.");
    }

}