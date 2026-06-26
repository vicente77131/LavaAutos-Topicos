import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Hospital_Jframe extends JFrame {

    private JTabbedPane tabbedPane;
    private JTextField txtNombre, txtApellido;
    private JTextArea areaPacientes;
    private JTextField txtEliminar;
    private ArrayList<String> pacientes;

    public Hospital_Jframe() {
        pacientes = new ArrayList<>();
        initComponents();
    }

    private void initComponents() {
        setTitle("Hospital - Gestión de Pacientes");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        // --- Panel Agregar Paciente ---
        JPanel panelAgregar = new JPanel(new GridLayout(3, 2, 5, 5));
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        JButton btnAgregar = new JButton("Agregar");

        panelAgregar.add(new JLabel("Nombre:"));
        panelAgregar.add(txtNombre);
        panelAgregar.add(new JLabel("Apellido:"));
        panelAgregar.add(txtApellido);
        panelAgregar.add(new JLabel(""));
        panelAgregar.add(btnAgregar);

        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            if (!nombre.isEmpty() && !apellido.isEmpty()) {
                pacientes.add(nombre + " " + apellido);
                JOptionPane.showMessageDialog(this, "Paciente agregado correctamente");
                txtNombre.setText("");
                txtApellido.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Completa todos los campos");
            }
        });

        // --- Panel Ver Pacientes ---
        JPanel panelVer = new JPanel(new BorderLayout());
        areaPacientes = new JTextArea();
        areaPacientes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaPacientes);
        JButton btnActualizar = new JButton("Actualizar lista");

        panelVer.add(scrollPane, BorderLayout.CENTER);
        panelVer.add(btnActualizar, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> {
            areaPacientes.setText("");
            for (int i = 0; i < pacientes.size(); i++) {
                areaPacientes.append((i+1) + ". " + pacientes.get(i) + "\n");
            }
        });

        // --- Panel Eliminar Paciente ---
        JPanel panelEliminar = new JPanel(new GridLayout(2, 2, 5, 5));
        txtEliminar = new JTextField();
        JButton btnEliminar = new JButton("Eliminar");

        panelEliminar.add(new JLabel("Número de paciente:"));
        panelEliminar.add(txtEliminar);
        panelEliminar.add(new JLabel(""));
        panelEliminar.add(btnEliminar);

        btnEliminar.addActionListener(e -> {
            try {
                int index = Integer.parseInt(txtEliminar.getText().trim()) - 1;
                if (index >= 0 && index < pacientes.size()) {
                    pacientes.remove(index);
                    JOptionPane.showMessageDialog(this, "Paciente eliminado");
                } else {
                    JOptionPane.showMessageDialog(this, "Número inválido");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un número válido");
            }
        });

        // --- Agregar pestañas ---
        tabbedPane.addTab("Agregar", panelAgregar);
        tabbedPane.addTab("Ver", panelVer);
        tabbedPane.addTab("Eliminar", panelEliminar);

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Hospital_Jframe().setVisible(true));
    }
}
