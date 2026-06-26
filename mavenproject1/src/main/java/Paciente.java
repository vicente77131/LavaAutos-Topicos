import java.util.Date;

/**
 * MODELO: representa los datos de un paciente.
 * Esta clase va como archivo separado dentro del mismo paquete
 * que tu Ejemplo_2_jframe.java.
 */
public class Paciente {

    private static int contador = 0;

    private int id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String genero;
    private int edad;
    private Date fechaIngreso;

    private String horaSalida;
    private String observacionesEgreso;

    private String alergias;
    private String observacionesSintomas;
    private String diagnostico;
    private boolean salidaAutorizada;

    public Paciente() {
        this.id = ++contador;
    }

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

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
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

    // ---------- Métodos del modelo ----------

    public String getNombreCompleto() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }

    public String ver() {
        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Nombre completo: ").append(getNombreCompleto()).append("\n");
        sb.append("Género: ").append(genero).append("\n");
        sb.append("Edad: ").append(edad).append("\n");
        sb.append("Fecha de ingreso: ").append(fechaIngreso != null ? formato.format(fechaIngreso) : "-");
        return sb.toString();
    }

    @Override
    public String toString() {
        return getNombreCompleto();
    }
}
