package vista;

import controlador.ControladorVer;
import modelo.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * VISTA (form) - Pestaña 4: Vista de Pacientes
 * Permite buscar pacientes por nombre y los muestra en una tabla.
 * Aquí SÍ se implementa el JScrollPane, envolviendo la JTable.
 */
public class PanelVistaPacientes extends JPanel {

    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tablaPacientes;
    private DefaultTableModel modeloTabla;

    private final ControladorVer controladorVer;
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    private static final String[] COLUMNAS = {
            "ID", "Nombre completo", "Género", "Edad", "Fecha ingreso", "Fecha egreso", "Salida autorizada"
    };

    public PanelVistaPacientes() {
        controladorVer = new ControladorVer();
        construirVista();
        actualizar();
    }

    private void construirVista() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel panelBusqueda = new JPanel(new BorderLayout(6, 6));
        txtBuscar = new JTextField();
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(new JLabel("Buscar por nombre:"), BorderLayout.WEST);
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);
        panelBusqueda.add(btnBuscar, BorderLayout.EAST);
        add(panelBusqueda, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPacientes = new JTable(modeloTabla);
        tablaPacientes.setRowHeight(22);
        tablaPacientes.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // *** JScrollPane requerido para esta pestaña ***
        JScrollPane scrollPane = new JScrollPane(tablaPacientes);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscar());
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscar();
            }
        });

        // Doble clic en una fila -> muestra el detalle completo (paciente.ver())
        tablaPacientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    mostrarDetalleSeleccionado();
                }
            }
        });
    }

    private void buscar() {
        String texto = txtBuscar.getText();
        List<Paciente> resultado = controladorVer.buscarPorNombre(texto);
        cargarTabla(resultado);
    }

    /** Se llama al entrar a la pestaña, para refrescar con todos los pacientes. */
    public void actualizar() {
        cargarTabla(controladorVer.verTodos());
    }

    private void cargarTabla(List<Paciente> pacientes) {
        modeloTabla.setRowCount(0);
        for (Paciente p : pacientes) {
            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getNombreCompleto().trim(),
                    p.getGenero(),
                    p.getEdad(),
                    p.getFechaIngreso() != null ? formatoFecha.format(p.getFechaIngreso()) : "-",
                    p.getFechaEgreso() != null ? formatoFecha.format(p.getFechaEgreso()) : "-",
                    p.isSalidaAutorizada() ? "Sí" : "No"
            });
        }
    }

    private void mostrarDetalleSeleccionado() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila < 0) {
            return;
        }
        int id = (Integer) modeloTabla.getValueAt(fila, 0);
        for (Paciente p : controladorVer.verTodos()) {
            if (p.getId() == id) {
                JOptionPane.showMessageDialog(this, p.ver(), "Detalle del paciente",
                        JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
    }
}
