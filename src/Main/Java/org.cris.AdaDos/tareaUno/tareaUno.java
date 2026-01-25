package org.cris.AdaDos.tareaUno;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
/*
1. Entrada: un archivo csv con lista de alumnos y 4 columnas
2. El programa permitirá capturar las calificaciones de "Diseño de software"
de todos los estudiantes de la lista (del 1 al 100 puros enteros)
3. Capturadas todas las calificaciones el usuario tendrá una opción para
generar un archivo CSV con 3 columnas: matricula, nombre asignatura y calificación.
4. No se podrá generar el archivo de calificaciones, punto 3, si existe uno o
más alumnos a los que no se les haya capturado calificación. (try catch?)
*/
public class tareaUno {

    //ALUMNOS (Define caracteristicas)******************
    private static class Alumno {
        String matricula, primerApellido, segundoApellido, nombres;
        Integer calificacion;

        public Alumno(String matricula, String primerApellido, String segundoApellido, String nombres) {
            this.matricula = matricula;
            this.primerApellido = primerApellido;
            this.segundoApellido = segundoApellido;
            this.nombres = nombres;
            this.calificacion = null;
        }

        public String getNombreAlumno() {
            return nombres + " " + primerApellido + " " + segundoApellido;
        }

        public String getMatricula() {
            return matricula;
        }
    }

}
