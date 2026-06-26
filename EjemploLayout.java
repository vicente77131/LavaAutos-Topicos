import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;

public class EjemploLayout {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("EJEMPLO DE LAYOUT");
        ventana.setSize(300, 200);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new FlowLayout());
        ventana.add(new JButton("Boton 1"));
        ventana.add(new JButton("Boton 2"));
        ventana.add(new JButton("Boton 3"));
        ventana .setVisible(true);

    }
    
    
}
