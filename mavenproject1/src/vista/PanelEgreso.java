package vista;

import com.toedter.calendar.JDateChooser;
import controlador.ControladorCrear;
import modelo.Paciente;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

/**
 * VISTA (form) - Pestaña 2: Egreso
 * Campos: Paciente (selector con datos del paciente), Fecha de salida
 * (JCalendar / JDateChooser), Hora de salida (JSpinner) y Observaciones.
 */
public class PanelEgreso extends JPanel {

    private SelectorPacientePanel selectorPaciente;
    private JDateChooser dateFechaSalida;
    private JSpinner spinnerHoraSalida;
    private JTextArea txtObservaciones;
    private JButton btnGuardar;

    private final ControladorCrear controladorCrear;

    public PanelEgreso() {
        controladorCrear = new ControladorCrear();
        construirFormulario();
    }

    private void construirFormulario() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        selectorPaciente = new SelectorPacientePanel();
        dateFechaSalida = new JDateChooser();
        dateFechaSalida.setDateFormatString("dd/MM/yyyy");
        dateFechaSalida.setDate(new Date());

        spinnerHoraSalida = new JSpinner(new SpinnerDateModel());
        spinnerHoraSalida.setEditor(new JSpinner.DateEditor(spinnerHoraSalida, "HH:mm"));
        spinnerHoraSalida.setValue(new Date());

        txtObservaciones = new JTextArea(4, 25);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);

        btnGuardar = new JButton("Guardar egreso");

        int fila = 0;

        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(selectorPaciente, gbc);
        fila++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0; gbc.gridy = fila;
        add(new JLabel("Fecha de salida:"), gbc);
        gbc.gridx = 1;
        add(dateFechaSalida, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        add(new JLabel("Hora de salida:"), gbc);
        gbc.gridx = 1;
        add(spinnerHoraSalida, gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1;
        add(new JScrollPane(txtObservaciones), gbc);
        fila++;

        gbc.gridx = 1; gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> guardarEgreso());
    }

    private void guardarEgreso() {
        Paciente paciente = selectorPaciente.getPacienteSeleccionado();
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "Primero registre o seleccione un paciente.",
                    "Falta paciente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date fechaSalida = dateFechaSalida.getDate();
        Date horaSalida = combinarFechaYHora(fechaSalida, (Date) spinnerHoraSalida.getValue());
        String observaciones = txtObservaciones.getText().trim();

        controladorCrear.registrarEgreso(paciente, fechaSalida, horaSalida, observaciones);

        JOptionPane.showMessageDialog(this,
                "Egreso registrado para " + paciente.getNombreCompleto().trim(),
                "Egreso guardado", JOptionPane.INFORMATION_MESSAGE);

        txtObservaciones.setText("");
    }

    /** Combina la fecha elegida en el JDateChooser con la hora elegida en el JSpinner. */
    private Date combinarFechaYHora(Date fecha, Date hora) {
        if (fecha == null) {
            fecha = new Date();
        }
        Calendar calFecha = Calendar.getInstance();
        calFecha.setTime(fecha);

        Calendar calHora = Calendar.getInstance();
        calHora.setTime(hora);

        calFecha.set(Calendar.HOUR_OF_DAY, calHora.get(Calendar.HOUR_OF_DAY));
        calFecha.set(Calendar.MINUTE, calHora.get(Calendar.MINUTE));
        calFecha.set(Calendar.SECOND, 0);
        return calFecha.getTime();
    }

    /** Se llama cuando el usuario entra a esta pestaña, para refrescar la lista de pacientes. */
    public void actualizar() {
        selectorPaciente.actualizar();
    }
}
