package vista;

import com.toedter.calendar.JDateChooser;
import controlador.ControladorCrear;
import modelo.Paciente;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * VISTA (form) - Pestaña 1: Ingreso
 * Campos: Nombre, Apellido Paterno, Apellido Materno, Género (combo box), Edad.
 * Se agrega además "Fecha de ingreso" usando JCalendar (JDateChooser),
 * ya que el sistema debe usar la librería jcalendar-1.4.
 *
 * "Información de la vista": los métodos getXxx() públicos exponen
 * lo que el usuario capturó, y el controlador los lee para crear el Paciente.
 */
public class PanelIngreso extends JPanel {

    private JTextField txtNombre;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JComboBox<String> comboGenero;
    private JSpinner spinnerEdad;
    private JDateChooser dateFechaIngreso;
    private JButton btnGuardar;

    private final ControladorCrear controladorCrear;

    public PanelIngreso() {
        controladorCrear = new ControladorCrear();
        construirFormulario();
    }

    private void construirFormulario() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        txtNombre = new JTextField(18);
        txtApellidoPaterno = new JTextField(18);
        txtApellidoMaterno = new JTextField(18);
        comboGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        spinnerEdad = new JSpinner(new SpinnerNumberModel(0, 0, 120, 1));
        dateFechaIngreso = new JDateChooser();
        dateFechaIngreso.setDateFormatString("dd/MM/yyyy");
        dateFechaIngreso.setDate(new Date());
        btnGuardar = new JButton("Guardar ingreso");

        int fila = 0;
        agregarCampo(gbc, fila++, "Nombre:", txtNombre);
        agregarCampo(gbc, fila++, "Apellido paterno:", txtApellidoPaterno);
        agregarCampo(gbc, fila++, "Apellido materno:", txtApellidoMaterno);
        agregarCampo(gbc, fila++, "Género:", comboGenero);
        agregarCampo(gbc, fila++, "Edad:", spinnerEdad);
        agregarCampo(gbc, fila++, "Fecha de ingreso:", dateFechaIngreso);

        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> guardarPaciente());
    }

    private void agregarCampo(GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        add(new JLabel(etiqueta), gbc);
        gbc.gridx = 1;
        add(campo, gbc);
    }

    private void guardarPaciente() {
        String nombre = txtNombre.getText().trim();
        String apellidoPaterno = txtApellidoPaterno.getText().trim();
        String apellidoMaterno = txtApellidoMaterno.getText().trim();

        if (nombre.isEmpty() || apellidoPaterno.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y apellido paterno son obligatorios.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String genero = (String) comboGenero.getSelectedItem();
        int edad = (Integer) spinnerEdad.getValue();
        Date fechaIngreso = dateFechaIngreso.getDate();

        Paciente paciente = controladorCrear.crearPaciente(nombre, apellidoPaterno, apellidoMaterno,
                genero, edad, fechaIngreso);

        JOptionPane.showMessageDialog(this,
                "Paciente registrado correctamente:\n\n" + paciente.ver(),
                "Ingreso exitoso", JOptionPane.INFORMATION_MESSAGE);

        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        comboGenero.setSelectedIndex(0);
        spinnerEdad.setValue(0);
        dateFechaIngreso.setDate(new Date());
        txtNombre.requestFocus();
    }
}
