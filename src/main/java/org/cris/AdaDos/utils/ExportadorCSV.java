package org.cris.AdaDos.utils;

import org.cris.AdaDos.models.Alumno;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ExportadorCSV {

    public static void generarReporte(List<Alumno> listaAlumnos, String rutaDestino) throws IOException {
        // Usamos try-with-resources para asegurar que se cierre el archivo
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(rutaDestino), StandardCharsets.UTF_8))) {

            // 1. Escribir Encabezados
            pw.println("Matricula,Nombre Asignatura,Calificacion");

            // 2. Escribir Datos
            for (Alumno alumno : listaAlumnos) {
                String notaTexto;

                // Validamos si está vacío o nulo
                if (alumno.getCalificacion() == null || alumno.getCalificacion().trim().isEmpty()) {
                    notaTexto = "S/C"; // Sin Calificación
                } else {
                    notaTexto = alumno.getCalificacion();
                }

                // Formato: Matricula,Materia,Nota
                pw.println(alumno.getMatricula() + ",Disenio de Software," + notaTexto);
            }
        }
    }
}