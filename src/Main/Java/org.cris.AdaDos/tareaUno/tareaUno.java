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

    // /***************MIRANDA MODIFICA LA RUTA DONDE LO VAYAS A ALMACENAR*******************************************************/
    private String rutaEntrada = "/home/cristopher/Cuarto_Semestre/DisenoDeSoftware/ADA2/ArchivosCSV/tabla_alumnos.csv";
    private String rutaSalida = "/home/cristopher/Cuarto_Semestre/DisenoDeSoftware/ADA2/ArchivosCSV/calificaciones_alumnos.csv";
    private List<Alumno> alumnos = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void generacionCalificaciones() {
        //Seleccionar archivo
        System.out.println("Ruta actual: " + rutaEntrada);
        System.out.print("Presione Enter para usarla o ingrese una nueva ruta:");
        String nuevaRuta = scanner.nextLine();
        if (!nuevaRuta.isEmpty()){
            rutaEntrada = nuevaRuta;
        }
        //*************+VALIDADOR ARCHIVO***********
        if (!rutaEntrada.endsWith(".csv")) {
            System.err.println("Error: El archivo debe ser .csv");
            return;
        }
        /******NO BORRAR!!!!!!!!*******/
        if (!cargarDatos()){
            return;
        }
        //***********Captura de calificaciones******
        System.out.println("CAPTURA DE CALIFICACIONES (Disenio de Sofatware)");
        for (Alumno alumno : alumnos) {
            boolean calificacionValida = false; /*bandera.oty*/

            while (!calificacionValida) {
                System.out.print("Ingresa la calificación de " + alumno.getNombreAlumno() + " (" + alumno.getMatricula() + "): ");
                try {
                    int calificacionTemp = Integer.parseInt(scanner.nextLine());
                    if (calificacionTemp >= 1 && calificacionTemp <= 100) {
                        alumno.calificacion = calificacionTemp;
                        calificacionValida = true;
                    } else {
                        System.out.println("La calificación debe ser entre 1 y 100.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Debes ingresar un número entero");
                }
            }
        }
        //******Confirmacion de salida******
        System.out.print("\n¿Desea generar el archivo csv con las calificaciones (S/N)? ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            generarArchivoSalida();
        } else {
            System.out.println("Proceso cancelado.");
        }
    }
    private boolean cargarDatos() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaEntrada), StandardCharsets.UTF_8))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] dato = linea.split(",");
                if (dato.length != 4) {
                    System.err.println("El archivo NO tiene las 4 columnas requeridas.");
                    return false;
                }
                alumnos.add(new Alumno(dato[0], dato[1], dato[2], dato[3]));
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error al leer el archivo:" + e.getMessage());
            return false;
        }
    }

    private void generarArchivoSalida() {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(rutaSalida), StandardCharsets.UTF_8))) {
            pw.println("Matricula,Nombre Asignatura,Calificacion");
            for (Alumno a : alumnos) {
                pw.println(a.getMatricula() + ",Disenio de Software," + a.calificacion);
            }
            System.out.println("Archivo generado en: " + rutaSalida);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo.");
        }
    }
}
