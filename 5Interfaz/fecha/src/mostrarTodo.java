import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class mostrarTodo extends JFrame{
    private JButton btnGet, btnSet;
    private JFrame f;
    private Fecha fechaPanel;
    mostrarTodo(){
        f = new JFrame();
        btnGet = new JButton();
        btnSet = new JButton();
        fechaPanel = new Fecha();
        fechaPanel.setLayout(null);
        fechaPanel.setBackground(Color.red);
        fechaPanel.setSize(250, 150);
        btnSet.setText("Set");
        btnSet.setBounds(85, 150, 65, 20);
        btnGet.setText("Get");
        btnGet.setBounds(150, 150, 65, 20);
        f.add(btnGet);
        f.add(fechaPanel);
        f.add(btnSet);
        f.setLayout(null);
        f.add(fechaPanel);
        f.setSize(400,300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setResizable(true);
        f.setVisible(true);

        btnGet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Date d = fechaPanel.getDate();
                    System.out.println("Fecha registrada con éxito: " + d);
                } catch (JFechaException ex) {
                    System.err.println(ex);
                }
            }
        });

        btnSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date(116, 5,3);
                fechaPanel.setDate(d);
            }
        });
    }


    public static void main(String[] args) {
        new mostrarTodo();
    }

}
