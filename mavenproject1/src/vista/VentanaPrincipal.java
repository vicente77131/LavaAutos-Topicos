package vista;

import javax.swing.*;
import java.awt.*;

/**
 * VISTA principal: JFrame con el JTabbedPane de 4 pestañas:
 *  1. Ingreso
 *  2. Egreso
 *  3. Registro de síntomas
 *  4. Vista de pacientes
 *
 * Cuando el usuario cambia de pestaña, se refrescan los combos/tabla
 * para que siempre muestren los pacientes más recientes.
 */
public class VentanaPrincipal extends JFrame {

    private final PanelIngreso panelIngreso;
    private final PanelEgreso panelEgreso;
    private final PanelSintomas panelSintomas;
    private final PanelVistaPacientes panelVistaPacientes;

    public VentanaPrincipal() {
        super("Sistema de Pacientes");

        panelIngreso = new PanelIngreso();
        panelEgreso = new PanelEgreso();
        panelSintomas = new PanelSintomas();
        panelVistaPacientes = new PanelVistaPacientes();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Ingreso", panelIngreso);
        tabbedPane.addTab("Egreso", panelEgreso);
        tabbedPane.addTab("Registro de síntomas", panelSintomas);
        tabbedPane.addTab("Vista de pacientes", panelVistaPacientes);

        tabbedPane.addChangeListener(e -> {
            int indice = tabbedPane.getSelectedIndex();
            switch (indice) {
                case 1:
                    panelEgreso.actualizar();
                    break;
                case 2:
                    panelSintomas.actualizar();
                    break;
                case 3:
                    panelVistaPacientes.actualizar();
                    break;
                default:
                    break;
            }
        });

        setContentPane(tabbedPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
    }
}
