package org.cris.AdaDos.models;

public class Alumno {
    private String matricula;
    private String primerApellido;
    private String segundoApellido;
    private String nombres;
    private String calificacion;

    public Alumno(String matricula, String primerApellido, String segundoApellido, String nombres) {
        this.matricula = matricula;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.nombres = nombres;
        this.calificacion = "";
    }

    // GETTERS
    public String getMatricula() { return matricula; }
    public String getPrimerApellido() { return primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public String getNombres() { return nombres; }
    public String getCalificacion() { return calificacion; }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    // Metodo helper para saber si ya tiene calificación válida
    public boolean tieneCalificacionValida() {
        if (calificacion == null || calificacion.trim().isEmpty()) return false;
        try {
            int nota = Integer.parseInt(calificacion);
            return nota >= 0 && nota <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public String getNombreCompleto() {
        return nombres + " " + primerApellido + " " + segundoApellido;
    }
}