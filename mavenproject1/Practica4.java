import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

public class Practica4 {
    public static boolean esMayorDeEdad(LocalDate fechaN) {
        int edad = Period.between(fechaN, LocalDate.now()).getYears();
        return edad >= 18;
    }
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Practica 4");
        ventana.setSize(400, 250);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new FlowLayout());
        JLabel Nombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField(10);
        JLabel ApellidoP = new JLabel("ApellidoP:");
        JTextField txtApellidoP = new JTextField(10);
        JLabel ApellidoM = new JLabel("ApellidoM:");
        JTextField txtApellidoM = new JTextField(10);
        JLabel FechaN = new JLabel("fechaN:");
        JDateChooser dateChooser = new JDateChooser();
        JButton Guardar = new JButton("Guardar");
        Guardar.addActionListener(e -> {
            if (dateChooser.getDate() != null) {
                LocalDate fechaNac = dateChooser.getDate()
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                boolean mayorEdad = esMayorDeEdad(fechaNac);

                String mensaje = "Nombre: " + txtNombre.getText() +
                                 "\nApellidoP: " + txtApellidoP.getText() +
                                 "\nApellidoM: " + txtApellidoM.getText() +
                                 "\nFechaN: " + fechaNac +
                                 "\nMayor de 18: " + (mayorEdad ? "Sí" : "No");

                JOptionPane.showMessageDialog(ventana, mensaje);
            } else {
                JOptionPane.showMessageDialog(ventana,
                        "Selecciona una fecha de nacimiento",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(Nombre); panel.add(txtNombre);
        panel.add(ApellidoP); panel.add(txtApellidoP);
        panel.add(ApellidoM); panel.add(txtApellidoM);
        panel.add(FechaN); panel.add(dateChooser);
        panel.add(Guardar);
        ventana.add(panel);
        ventana.setVisible(true);
    }
}
