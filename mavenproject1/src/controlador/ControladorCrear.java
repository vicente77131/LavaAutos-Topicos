package controlador;

import modelo.Paciente;
import modelo.RepositorioPacientes;

import java.util.Date;

/**
 * CONTROLADOR - CREAR
 * Recibe la información que viene de la vista (formularios) y la usa
 * para crear/actualizar los datos del modelo (Paciente).
 */
public class ControladorCrear {

    /**
     * Pestaña 1 - Ingreso: crea un paciente nuevo y lo guarda en el repositorio.
     */
    public Paciente crearPaciente(String nombre, String apellidoPaterno, String apellidoMaterno,
                                   String genero, int edad, Date fechaIngreso) {
        Paciente paciente = Paciente.crear(nombre, apellidoPaterno, apellidoMaterno, genero, edad, fechaIngreso);
        RepositorioPacientes.getInstancia().agregar(paciente);
        return paciente;
    }

    /**
     * Pestaña 2 - Egreso: registra la salida de un paciente ya existente.
     */
    public void registrarEgreso(Paciente paciente, Date fechaEgreso, Date horaEgreso, String observaciones) {
        if (paciente == null) {
            throw new IllegalArgumentException("Debe seleccionar un paciente.");
        }
        paciente.setFechaEgreso(fechaEgreso);
        paciente.setHoraEgreso(horaEgreso);
        paciente.setObservacionesEgreso(observaciones);
    }

    /**
     * Pestaña 3 - Registro de síntomas: registra alergias, observaciones,
     * diagnóstico y si se autoriza la salida (radio button).
     */
    public void registrarSintomas(Paciente paciente, String alergias, String observaciones,
                                   String diagnostico, boolean salidaAutorizada) {
        if (paciente == null) {
            throw new IllegalArgumentException("Debe seleccionar un paciente.");
        }
        paciente.setAlergias(alergias);
        paciente.setObservacionesSintomas(observaciones);
        paciente.setDiagnostico(diagnostico);
        paciente.setSalidaAutorizada(salidaAutorizada);
    }
}
