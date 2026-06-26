import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * VERSIÓN BÁSICA - TODO EN UNA SOLA CLASE
 * JFrame con JTabbedPane de 4 pestañas: Ingreso, Egreso,
 * Registro de síntomas y Vista de pacientes (con JScrollPane).
 *
 * No usa paquetes ni clases de controlador separadas: todo está aquí,
 * incluida la clase Paciente (al final, como clase interna).
 */
public class Main extends JFrame {

    // Lista de pacientes guardados en memoria (mientras el programa está abierto)
    private final ArrayList<Paciente> pacientes = new ArrayList<>();
    private final JTabbedPane tabbedPane = new JTabbedPane();

    // ---------- Pestaña 1: Ingreso ----------
    private JTextField txtNombre, txtApellidoPaterno, txtApellidoMaterno;
    private JComboBox<String> comboGenero;
    private JSpinner spinnerEdad;
    private JDateChooser dateIngreso;

    // ---------- Pestaña 2: Egreso ----------
    private JComboBox<Paciente> comboPacienteEgreso;
    private JLabel lblGeneroEgreso, lblEdadEgreso;
    private JDateChooser dateEgreso;
    private JSpinner spinnerHoraEgreso;
    private JTextArea txtObsEgreso;

    // ---------- Pestaña 3: Registro de síntomas ----------
    private JComboBox<Paciente> comboPacienteSintomas;
    private JLabel lblGeneroSintomas, lblEdadSintomas;
    private JTextField txtAlergias;
    private JTextArea txtObsSintomas, txtDiagnostico;
    private JRadioButton rbSalidaSi, rbSalidaNo;

    // ---------- Pestaña 4: Vista de pacientes ----------
    private JTextField txtBuscar;
    private DefaultTableModel modeloTabla;

    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    public Main() {
        super("Sistema de Pacientes");

        tabbedPane.addTab("Ingreso", crearPanelIngreso());
        tabbedPane.addTab("Egreso", crearPanelEgreso());
        tabbedPane.addTab("Registro de síntomas", crearPanelSintomas());
        tabbedPane.addTab("Vista de pacientes", crearPanelVistaPacientes());

        // Cada vez que se cambia de pestaña, se refresca con los datos más recientes
        tabbedPane.addChangeListener(e -> {
            int indice = tabbedPane.getSelectedIndex();
            if (indice == 1) actualizarCombo(comboPacienteEgreso);
            if (indice == 2) actualizarCombo(comboPacienteSintomas);
            if (indice == 3) actualizarTabla(pacientes);
        });

        setContentPane(tabbedPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
    }

    // ===================== PESTAÑA 1: INGRESO =====================

    private JPanel crearPanelIngreso() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        txtNombre = new JTextField(18);
        txtApellidoPaterno = new JTextField(18);
        txtApellidoMaterno = new JTextField(18);
        comboGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        spinnerEdad = new JSpinner(new SpinnerNumberModel(0, 0, 120, 1));
        dateIngreso = new JDateChooser();
        dateIngreso.setDateFormatString("dd/MM/yyyy");
        dateIngreso.setDate(new Date());
        JButton btnGuardar = new JButton("Guardar ingreso");

        int fila = 0;
        agregarFila(panel, gbc, fila++, "Nombre:", txtNombre);
        agregarFila(panel, gbc, fila++, "Apellido paterno:", txtApellidoPaterno);
        agregarFila(panel, gbc, fila++, "Apellido materno:", txtApellidoMaterno);
        agregarFila(panel, gbc, fila++, "Género:", comboGenero);
        agregarFila(panel, gbc, fila++, "Edad:", spinnerEdad);
        agregarFila(panel, gbc, fila++, "Fecha de ingreso:", dateIngreso);

        gbc.gridx = 1; gbc.gridy = fila; gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> guardarIngreso());
        return panel;
    }

    private void guardarIngreso() {
        String nombre = txtNombre.getText().trim();
        String apellidoPaterno = txtApellidoPaterno.getText().trim();
        String apellidoMaterno = txtApellidoMaterno.getText().trim();

        if (nombre.isEmpty() || apellidoPaterno.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y apellido paterno son obligatorios.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Paciente p = new Paciente();
        p.nombre = nombre;
        p.apellidoPaterno = apellidoPaterno;
        p.apellidoMaterno = apellidoMaterno;
        p.genero = (String) comboGenero.getSelectedItem();
        p.edad = (Integer) spinnerEdad.getValue();
        p.fechaIngreso = dateIngreso.getDate();

        pacientes.add(p);

        JOptionPane.showMessageDialog(this, "Paciente registrado:\n\n" + p.ver(),
                "Ingreso exitoso", JOptionPane.INFORMATION_MESSAGE);

        txtNombre.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        comboGenero.setSelectedIndex(0);
        spinnerEdad.setValue(0);
        dateIngreso.setDate(new Date());
    }

    // ===================== PESTAÑA 2: EGRESO =====================

    private JPanel crearPanelEgreso() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        comboPacienteEgreso = new JComboBox<>();
        lblGeneroEgreso = new JLabel("-");
        lblEdadEgreso = new JLabel("-");
        comboPacienteEgreso.addActionListener(e -> {
            Paciente p = (Paciente) comboPacienteEgreso.getSelectedItem();
            lblGeneroEgreso.setText(p != null ? p.genero : "-");
            lblEdadEgreso.setText(p != null ? String.valueOf(p.edad) : "-");
        });

        dateEgreso = new JDateChooser();
        dateEgreso.setDateFormatString("dd/MM/yyyy");
        dateEgreso.setDate(new Date());

        spinnerHoraEgreso = new JSpinner(new SpinnerDateModel());
        spinnerHoraEgreso.setEditor(new JSpinner.DateEditor(spinnerHoraEgreso, "HH:mm"));
        spinnerHoraEgreso.setValue(new Date());

        txtObsEgreso = new JTextArea(4, 25);
        txtObsEgreso.setLineWrap(true);
        txtObsEgreso.setWrapStyleWord(true);

        JButton btnGuardar = new JButton("Guardar egreso");

        int fila = 0;
        agregarFila(panel, gbc, fila++, "Paciente:", comboPacienteEgreso);
        agregarFila(panel, gbc, fila++, "Género:", lblGeneroEgreso);
        agregarFila(panel, gbc, fila++, "Edad:", lblEdadEgreso);
        agregarFila(panel, gbc, fila++, "Fecha de salida:", dateEgreso);
        agregarFila(panel, gbc, fila++, "Hora de salida:", spinnerHoraEgreso);

        gbc.gridx = 0; gbc.gridy = fila; gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1;
        panel.add(new JScrollPane(txtObsEgreso), gbc);
        fila++;

        gbc.gridx = 1; gbc.gridy = fila; gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> guardarEgreso());
        return panel;
    }

    private void guardarEgreso() {
        Paciente p = (Paciente) comboPacienteEgreso.getSelectedItem();
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Primero registre o seleccione un paciente.",
                    "Falta paciente", JOptionPane.WARNING_MESSAGE);
            return;
        }
        p.fechaEgreso = dateEgreso.getDate();
        p.horaEgreso = combinarFechaYHora(dateEgreso.getDate(), (Date) spinnerHoraEgreso.getValue());
        p.observacionesEgreso = txtObsEgreso.getText().trim();

        JOptionPane.showMessageDialog(this, "Egreso registrado para " + p.nombreCompleto(),
                "Egreso guardado", JOptionPane.INFORMATION_MESSAGE);
        txtObsEgreso.setText("");
    }

    // ===================== PESTAÑA 3: REGISTRO DE SÍNTOMAS =====================

    private JPanel crearPanelSintomas() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        comboPacienteSintomas = new JComboBox<>();
        lblGeneroSintomas = new JLabel("-");
        lblEdadSintomas = new JLabel("-");
        comboPacienteSintomas.addActionListener(e -> {
            Paciente p = (Paciente) comboPacienteSintomas.getSelectedItem();
            lblGeneroSintomas.setText(p != null ? p.genero : "-");
            lblEdadSintomas.setText(p != null ? String.valueOf(p.edad) : "-");
        });

        txtAlergias = new JTextField(25);

        txtObsSintomas = new JTextArea(3, 25);
        txtObsSintomas.setLineWrap(true);
        txtObsSintomas.setWrapStyleWord(true);

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

        JButton btnGuardar = new JButton("Guardar registro");

        int fila = 0;
        agregarFila(panel, gbc, fila++, "Paciente:", comboPacienteSintomas);
        agregarFila(panel, gbc, fila++, "Género:", lblGeneroSintomas);
        agregarFila(panel, gbc, fila++, "Edad:", lblEdadSintomas);
        agregarFila(panel, gbc, fila++, "Alergias:", txtAlergias);

        gbc.gridx = 0; gbc.gridy = fila; gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1;
        panel.add(new JScrollPane(txtObsSintomas), gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel("Diagnóstico:"), gbc);
        gbc.gridx = 1;
        panel.add(new JScrollPane(txtDiagnostico), gbc);
        fila++;

        gbc.gridx = 0; gbc.gridy = fila; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("¿Autoriza salida?:"), gbc);
        gbc.gridx = 1;
        panel.add(panelRadios, gbc);
        fila++;

        gbc.gridx = 1; gbc.gridy = fila; gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> guardarSintomas());
        return panel;
    }

    private void guardarSintomas() {
        Paciente p = (Paciente) comboPacienteSintomas.getSelectedItem();
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Primero registre o seleccione un paciente.",
                    "Falta paciente", JOptionPane.WARNING_MESSAGE);
            return;
        }
        p.alergias = txtAlergias.getText().trim();
        p.observacionesSintomas = txtObsSintomas.getText().trim();
        p.diagnostico = txtDiagnostico.getText().trim();
        p.salidaAutorizada = rbSalidaSi.isSelected();

        JOptionPane.showMessageDialog(this, "Registro guardado para " + p.nombreCompleto(),
                "Registro guardado", JOptionPane.INFORMATION_MESSAGE);

        txtAlergias.setText("");
        txtObsSintomas.setText("");
        txtDiagnostico.setText("");
        rbSalidaNo.setSelected(true);
    }

    // ===================== PESTAÑA 4: VISTA DE PACIENTES =====================

    private JPanel crearPanelVistaPacientes() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel panelBusqueda = new JPanel(new BorderLayout(6, 6));
        txtBuscar = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        panelBusqueda.add(new JLabel("Buscar por nombre:"), BorderLayout.WEST);
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);
        panelBusqueda.add(btnBuscar, BorderLayout.EAST);
        panel.add(panelBusqueda, BorderLayout.NORTH);

        String[] columnas = {"ID", "Nombre completo", "Género", "Edad", "Fecha ingreso", "Fecha egreso", "Salida autorizada"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(22);

        // *** JScrollPane requerido en esta pestaña ***
        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscarPacientes());
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarPacientes();
            }
        });

        return panel;
    }

    private void buscarPacientes() {
        String filtro = txtBuscar.getText().trim().toLowerCase();
        List<Paciente> resultado = new ArrayList<>();
        for (Paciente p : pacientes) {
            if (filtro.isEmpty() || p.nombreCompleto().toLowerCase().contains(filtro)) {
                resultado.add(p);
            }
        }
        actualizarTabla(resultado);
    }

    // ===================== MÉTODOS DE APOYO (compartidos) =====================

    private void agregarFila(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(etiqueta), gbc);
        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    private void actualizarCombo(JComboBox<Paciente> combo) {
        Paciente seleccionado = (Paciente) combo.getSelectedItem();
        combo.removeAllItems();
        for (Paciente p : pacientes) {
            combo.addItem(p);
        }
        if (seleccionado != null) {
            combo.setSelectedItem(seleccionado);
        }
    }

    private void actualizarTabla(List<Paciente> lista) {
        modeloTabla.setRowCount(0);
        for (Paciente p : lista) {
            modeloTabla.addRow(new Object[]{
                    p.id,
                    p.nombreCompleto(),
                    p.genero,
                    p.edad,
                    p.fechaIngreso != null ? formatoFecha.format(p.fechaIngreso) : "-",
                    p.fechaEgreso != null ? formatoFecha.format(p.fechaEgreso) : "-",
                    p.salidaAutorizada ? "Sí" : "No"
            });
        }
    }

    private Date combinarFechaYHora(Date fecha, Date hora) {
        if (fecha == null) fecha = new Date();
        if (hora == null) hora = new Date();
        Calendar calFecha = Calendar.getInstance();
        calFecha.setTime(fecha);
        Calendar calHora = Calendar.getInstance();
        calHora.setTime(hora);
        calFecha.set(Calendar.HOUR_OF_DAY, calHora.get(Calendar.HOUR_OF_DAY));
        calFecha.set(Calendar.MINUTE, calHora.get(Calendar.MINUTE));
        calFecha.set(Calendar.SECOND, 0);
        return calFecha.getTime();
    }

    // ===================== CLASE PACIENTE (simple, sin paquete propio) =====================

    public static class Paciente {
        private static int contador = 0;

        int id;
        String nombre;
        String apellidoPaterno;
        String apellidoMaterno;
        String genero;
        int edad;
        Date fechaIngreso;
        Date fechaEgreso;
        Date horaEgreso;
        String observacionesEgreso;
        String alergias;
        String observacionesSintomas;
        String diagnostico;
        boolean salidaAutorizada;

        Paciente() {
            this.id = ++contador;
        }

        String nombreCompleto() {
            return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
        }

        String ver() {
            return "ID: " + id + "\nNombre completo: " + nombreCompleto()
                    + "\nGénero: " + genero + "\nEdad: " + edad;
        }

        @Override
        public String toString() {
            // Así se ve el paciente dentro de los JComboBox
            return "#" + id + " - " + nombreCompleto();
        }
    }

    // ===================== MAIN =====================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
