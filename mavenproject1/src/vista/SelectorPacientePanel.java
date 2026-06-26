package vista;

import controlador.ControladorVer;
import modelo.Paciente;

import javax.swing.*;
import java.awt.*;

/**
 * VISTA
 * Panel reutilizable que muestra un combo para elegir un paciente ya
 * registrado (pestaña Ingreso) y despliega sus datos básicos
 * (género, edad) de forma automática. Se usa en las pestañas de
 * Egreso y Registro de síntomas, donde se pide "Paciente (datos del paciente)".
 *
 * Expone "información de la vista" mediante getPacienteSeleccionado(),
 * que los controladores usan para leer el dato capturado en el formulario.
 */
public class SelectorPacientePanel extends JPanel {

    private final JComboBox<Paciente> comboPacientes;
    private final JLabel lblGenero;
    private final JLabel lblEdad;
    private final ControladorVer controladorVer;

    public SelectorPacientePanel() {
        controladorVer = new ControladorVer();
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Paciente"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        comboPacientes = new JComboBox<>();
        lblGenero = new JLabel("-");
        lblEdad = new JLabel("-");

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Seleccionar paciente:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        add(comboPacientes, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        add(lblGenero, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Edad:"), gbc);
        gbc.gridx = 1;
        add(lblEdad, gbc);

        comboPacientes.addActionListener(e -> mostrarDatos());

        actualizar();
    }

    /**
     * Vuelve a cargar la lista de pacientes desde el controlador
     * (se llama cada vez que se entra a la pestaña).
     */
    public void actualizar() {
        Paciente seleccionadoActual = getPacienteSeleccionado();
        comboPacientes.removeAllItems();
        for (Paciente p : controladorVer.verTodos()) {
            comboPacientes.addItem(p);
        }
        if (seleccionadoActual != null) {
            comboPacientes.setSelectedItem(seleccionadoActual);
        }
        mostrarDatos();
    }

    private void mostrarDatos() {
        Paciente p = getPacienteSeleccionado();
        if (p != null) {
            lblGenero.setText(p.getGenero() != null ? p.getGenero() : "-");
            lblEdad.setText(String.valueOf(p.getEdad()));
        } else {
            lblGenero.setText("-");
            lblEdad.setText("-");
        }
    }

    /** Información de la vista: paciente actualmente elegido en el combo. */
    public Paciente getPacienteSeleccionado() {
        return (Paciente) comboPacientes.getSelectedItem();
    }
}
