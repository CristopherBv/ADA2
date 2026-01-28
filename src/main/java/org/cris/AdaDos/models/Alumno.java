package org.cris.AdaDos.models;

public class Alumno {
    private String matricula;
    private String primerApellido;
    private String segundoApellido;
    private String nombres;
    private String calificacion; // Lo manejo como String inicialmente para validar lo que escriben en tiempo real

    public Alumno(String matricula, String primerApellido, String segundoApellido, String nombres) {
        this.matricula = matricula;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.nombres = nombres;
        this.calificacion = ""; // Inicialmente vacía
    }

    // Getters y Setters
    public String getMatricula() { return matricula; }
    public String getPrimerApellido() { return primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public String getNombres() { return nombres; }

    public String getCalificacion() { return calificacion; }
    public void setCalificacion(String calificacion) { this.calificacion = calificacion; }

    public String getNombreCompleto() {
        return nombres + " " + primerApellido + " " + segundoApellido;
    }
}