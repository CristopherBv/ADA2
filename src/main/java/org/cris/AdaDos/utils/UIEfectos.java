package org.cris.AdaDos.utils;
/**
Reutilización de codigo de otro proyecto
 */
import javafx.animation.ScaleTransition;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class UIEfectos {

    // BOTON VERDE
    private static final String VERDE_NORMAL = "-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold;";
    private static final String VERDE_HOVER  = "-fx-background-color: #218838; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold;";

    // BOTON ROJO
    private static final String ROJO_NORMAL  = "-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold;";
    private static final String ROJO_HOVER   = "-fx-background-color: #c82333; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold;";

    //BOTON GRIS
    private static final String GRIS_DISABLED = "-fx-background-color: #6c757d; -fx-text-fill: #e2e6ea; -fx-background-radius: 5; -fx-opacity: 0.7;";

    //BOTON AZUL
    private static final String AZUL_NORMAL  = "-fx-background-color: #007bff; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold;";
    private static final String AZUL_HOVER   = "-fx-background-color: #0069d9; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold;";

    public static void estilarBotonVerde(Button btn) {
        aplicarAnimacion(btn, VERDE_NORMAL, VERDE_HOVER);
    }

    public static void estilarBotonRojo(Button btn) {
        aplicarAnimacion(btn, ROJO_NORMAL, ROJO_HOVER);
    }

    public static void estilarBotonAzul(Button btn) {
        aplicarAnimacion(btn, AZUL_NORMAL, AZUL_HOVER);
    }

    // Metodo especial para deshabilitar visualmente el botón CSV
    public static void estilarBotonDeshabilitado(Button btn) {
        btn.setStyle(GRIS_DISABLED);
        btn.setCursor(Cursor.DEFAULT);
        // Quitamos los eventos de mouse para que no anime si está gris
        btn.setOnMouseEntered(null);
        btn.setOnMouseExited(null);
    }

    private static void aplicarAnimacion(Button btn, String normal, String hover) {
        if (btn == null) return;

        btn.setCursor(Cursor.HAND);
        btn.setStyle(normal);

        btn.setOnMouseEntered(e -> {
            btn.setStyle(hover);
            escalarBoton(btn, 1.05);
        });

        btn.setOnMouseExited(e -> {
            btn.setStyle(normal);
            escalarBoton(btn, 1.0);
        });
    }

    private static void escalarBoton(Button btn, double scale) {
        ScaleTransition st = new ScaleTransition(Duration.millis(100), btn);
        st.setToX(scale);
        st.setToY(scale);
        st.play();
    }
}