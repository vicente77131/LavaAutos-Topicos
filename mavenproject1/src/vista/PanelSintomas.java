package vista;

import controlador.ControladorCrear;
import modelo.Paciente;

import javax.swing.*;
import java.awt.*;

/**
 * VISTA (form) - Pestaña 3: Registro de síntomas
 * Campos: Paciente (selector con datos del paciente), Alergias,
 * Observaciones, Diagnóstico y Salida (grupo de radio buttons Sí/No).
 */
public class PanelSintomas extends JPanel {

    private SelectorPacientePanel selectorPaciente;
    private JTextField txtAlergias;
    private JTextArea txtObservaciones;
    private JTextArea txtDiagnostico;
    private JRadioButton rbSalidaSi;
    private JRadioButton rbSalidaNo;
    private JButton btnGuardar;

    private final ControladorCrear controladorCrear;

    public PanelSintomas() {
        controladorCrear = new ControladorCrear();
        construirFormulario();
    }

    private void construirFormulario() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        selectorPaciente = new SelectorPacientePanel();
        txtAlergias = new JTextField(25);

        txtObservaciones = new JTextArea(3, 25);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);

        txtDiagnostico = new JTextArea(3, 25);
        txtDiagnostico.setLineWrap(true);
        txtDiagnostico.setWrapStyleWord(true);

        rbSalidaSi = new JRadioButton("Sí");
        rbSalidaNo = new JRadioButton("No", true);
        ButtonGroup grupoSalida = new ButtonGroup();
        grupoSalida.add(rbSalidaSi);
        grupoSalida.add(rbSalidaNo);
        JPanel panelRadios = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelRadios.add(rbSalidaSi);
        panelRadios.add(rbSalidaNo);

        btnGuardar = new JButton("Guardar registro");

        int fila = 0;

        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(selectorPaciente, gbc);
        fila++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0; gbc.gridy = fila;
        add(new JLabel("Alergias:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtAlergias, gbc);
        gbc.fill = GridBagConstraints.NONE;
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1;
        add(new JScrollPane(txtObservaciones), gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        add(new JLabel("Diagnóstico:"), gbc);
        gbc.gridx = 1;
        add(new JScrollPane(txtDiagnostico), gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("¿Autoriza salida?:"), gbc);
        gbc.gridx = 1;
        add(panelRadios, gbc);
        fila++;

        gbc.gridx = 1; gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> guardarSintomas());
    }

    private void guardarSintomas() {
        Paciente paciente = selectorPaciente.getPacienteSeleccionado();
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "Primero registre o seleccione un paciente.",
                    "Falta paciente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String alergias = txtAlergias.getText().trim();
        String observaciones = txtObservaciones.getText().trim();
        String diagnostico = txtDiagnostico.getText().trim();
        boolean salidaAutorizada = rbSalidaSi.isSelected();

        controladorCrear.registrarSintomas(paciente, alergias, observaciones, diagnostico, salidaAutorizada);

        JOptionPane.showMessageDialog(this,
                "Registro de síntomas guardado para " + paciente.getNombreCompleto().trim(),
                "Registro guardado", JOptionPane.INFORMATION_MESSAGE);

        txtAlergias.setText("");
        txtObservaciones.setText("");
        txtDiagnostico.setText("");
        rbSalidaNo.setSelected(true);
    }

    /** Se llama cuando el usuario entra a esta pestaña, para refrescar la lista de pacientes. */
    public void actualizar() {
        selectorPaciente.actualizar();
    }
}
