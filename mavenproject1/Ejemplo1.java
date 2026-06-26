import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
public class Ejemplo1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mi primera ventana");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JLabel etiqueta = new JLabel("Hola, mundo desde swing");
        JButton boton = new JButton("Haz clic");
        panel.add(panel);
        panel.add(boton);
        frame.add(panel);
        frame.setVisible(true);

    }

    
}