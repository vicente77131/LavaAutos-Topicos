import vista.VentanaPrincipal;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la aplicación.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
