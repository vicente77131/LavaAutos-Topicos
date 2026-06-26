package modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MODELO
 * Representa a un paciente del sistema.
 * Contiene los datos que se capturan en las 4 pestañas:
 *  - Ingreso, Egreso, Registro de síntomas y Vista de pacientes.
 *
 * Incluye:
 *  - Atributos + getters/setters
 *  - crear(...)  -> método "fábrica" que arma un Paciente con los datos de ingreso
 *  - ver()       -> regresa la información del paciente en formato de texto
 */
public class Paciente {

    // Contador simple para generar un ID único por paciente (en memoria)
    private static int contadorId = 0;

    private int id;

    // --- Datos de Ingreso ---
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String genero;     // Masculino / Femenino / Otro
    private int edad;
    private Date fechaIngreso; // capturado con JCalendar (JDateChooser)

    // --- Datos de Egreso ---
    private Date fechaEgreso;     // capturado con JCalendar (JDateChooser)
    private Date horaEgreso;      // capturado con JSpinner (hora)
    private String observacionesEgreso;

    // --- Datos de Registro de síntomas ---
    private String alergias;
    private String observacionesSintomas;
    private String diagnostico;
    private boolean salidaAutorizada; // capturado con radio buttons (Sí/No)

    public Paciente() {
        this.id = ++contadorId;
    }

    // ===================== GETTERS Y SETTERS =====================

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public Date getHoraEgreso() {
        return horaEgreso;
    }

    public void setHoraEgreso(Date horaEgreso) {
        this.horaEgreso = horaEgreso;
    }

    public String getObservacionesEgreso() {
        return observacionesEgreso;
    }

    public void setObservacionesEgreso(String observacionesEgreso) {
        this.observacionesEgreso = observacionesEgreso;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getObservacionesSintomas() {
        return observacionesSintomas;
    }

    public void setObservacionesSintomas(String observacionesSintomas) {
        this.observacionesSintomas = observacionesSintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public boolean isSalidaAutorizada() {
        return salidaAutorizada;
    }

    public void setSalidaAutorizada(boolean salidaAutorizada) {
        this.salidaAutorizada = salidaAutorizada;
    }

    // ===================== MÉTODOS DEL MODELO =====================

    /**
     * Nombre completo, útil para mostrarlo en combos y tablas.
     */
    public String getNombreCompleto() {
        return (nombre == null ? "" : nombre) + " "
                + (apellidoPaterno == null ? "" : apellidoPaterno) + " "
                + (apellidoMaterno == null ? "" : apellidoMaterno);
    }

    /**
     * crear(): método "fábrica" que arma un nuevo Paciente con los datos
     * capturados en la pestaña de Ingreso.
     */
    public static Paciente crear(String nombre, String apellidoPaterno, String apellidoMaterno,
                                  String genero, int edad, Date fechaIngreso) {
        Paciente p = new Paciente();
        p.setNombre(nombre);
        p.setApellidoPaterno(apellidoPaterno);
        p.setApellidoMaterno(apellidoMaterno);
        p.setGenero(genero);
        p.setEdad(edad);
        p.setFechaIngreso(fechaIngreso);
        return p;
    }

    /**
     * ver(): regresa toda la información del paciente en texto,
     * usado por la Vista de Pacientes para mostrar el detalle.
     */
    public String ver() {
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");

        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Nombre completo: ").append(getNombreCompleto()).append("\n");
        sb.append("Género: ").append(genero).append("\n");
        sb.append("Edad: ").append(edad).append("\n");
        sb.append("Fecha de ingreso: ").append(fechaIngreso != null ? fecha.format(fechaIngreso) : "-").append("\n");
        sb.append("Fecha de egreso: ").append(fechaEgreso != null ? fecha.format(fechaEgreso) : "-").append("\n");
        sb.append("Hora de egreso: ").append(horaEgreso != null ? hora.format(horaEgreso) : "-").append("\n");
        sb.append("Observaciones de egreso: ").append(observacionesEgreso != null ? observacionesEgreso : "-").append("\n");
        sb.append("Alergias: ").append(alergias != null ? alergias : "-").append("\n");
        sb.append("Observaciones (síntomas): ").append(observacionesSintomas != null ? observacionesSintomas : "-").append("\n");
        sb.append("Diagnóstico: ").append(diagnostico != null ? diagnostico : "-").append("\n");
        sb.append("Salida autorizada: ").append(salidaAutorizada ? "Sí" : "No").append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        // Se usa para mostrar al paciente en JComboBox de forma legible
        return "#" + id + " - " + getNombreCompleto().trim();
    }
}
