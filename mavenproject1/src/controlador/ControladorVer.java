package controlador;

import modelo.Paciente;
import modelo.RepositorioPacientes;

import java.util.List;

/**
 * CONTROLADOR - VER
 * Provee a la vista la información de los pacientes (listar y buscar),
 * usada por la pestaña "Vista de pacientes" y por los selectores de
 * paciente en Egreso y Registro de síntomas.
 */
public class ControladorVer {

    public List<Paciente> verTodos() {
        return RepositorioPacientes.getInstancia().listarTodos();
    }

    public List<Paciente> buscarPorNombre(String nombre) {
        return RepositorioPacientes.getInstancia().buscarPorNombre(nombre);
    }
}
