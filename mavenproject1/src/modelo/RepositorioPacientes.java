package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * MODELO
 * Almacén en memoria (lista) de los pacientes registrados.
 * Es compartido por ControladorCrear y ControladorVer (patrón Singleton).
 */
public class RepositorioPacientes {

    private static RepositorioPacientes instancia;
    private final List<Paciente> pacientes;

    private RepositorioPacientes() {
        pacientes = new ArrayList<>();
    }

    public static RepositorioPacientes getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioPacientes();
        }
        return instancia;
    }

    public void agregar(Paciente paciente) {
        pacientes.add(paciente);
    }

    public List<Paciente> listarTodos() {
        return pacientes;
    }

    public List<Paciente> buscarPorNombre(String texto) {
        List<Paciente> resultado = new ArrayList<>();
        if (texto == null || texto.trim().isEmpty()) {
            resultado.addAll(pacientes);
            return resultado;
        }
        String filtro = texto.trim().toLowerCase();
        for (Paciente p : pacientes) {
            if (p.getNombreCompleto().toLowerCase().contains(filtro)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}
