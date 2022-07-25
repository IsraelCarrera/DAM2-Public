package main;


import com.iesquevedo.jcoche.Coche;
import com.iesquevedo.jcoche.CocheException;
import com.iesquevedo.jcoche.JCoche;
import componenteDatosUsuario.componente.JUserData;
import componenteDatosUsuario.model.User;
import jExcepciones.PrecioException;
import jfechacomponent.JFechaException;
import jincidentecomponent.JIncidente;

import javax.swing.*;
import java.awt.*;

/**
 * Descripción: Esta clase es un conjunto de componentes que tienen la finalidad de registrar
 * las incidencias dadas por un coche de una persona en concreto.
 * @author Israel Carrera Manzaneque
 * @version 1.0
 */
public class TestMain extends JFrame {

    private final JCoche jCoche;
    private final JIncidente jIncidente;
    private final JUserData jUserData;
    private final JButton buttonCoche;
    private final JButton buttonIncidencia;
    private final JButton buttonUserData;

    /**
     * En esta clase lo que se realiza es la colocación de los componentes añadiendo además botones que actuarán como listeners de
     * las funciones que tienen dichos componentes.
     */
    public TestMain() throws HeadlessException {
        jCoche = new JCoche();
        jIncidente = new JIncidente();
        jUserData = new JUserData();
        JFrame jFrame = new JFrame();
        buttonUserData = new JButton();
        buttonCoche = new JButton();
        buttonIncidencia = new JButton();
        buttonUserData.setText("Validar usuario");
        buttonCoche.setText("Validar vehiculo");
        buttonIncidencia.setText("Validar incidencia");
        buttonUserData.setBounds(525, 50, 150, 30);
        buttonCoche.setBounds(525, 200, 150, 30);
        buttonIncidencia.setBounds(525, 400, 150, 30);
        jFrame.add(buttonUserData);
        jFrame.add(buttonCoche);
        jFrame.add(buttonIncidencia);
        jFrame.setSize(750, 600);
        jUserData.setBounds(25, 25, 450, 250);
        jUserData.setLayout(null);
        jCoche.setBounds(25, 150, 450, 250);
        jCoche.setLayout(null);
        jIncidente.setBounds(25, 380, 450, 250);
        jIncidente.setLayout(null);
        jFrame.add(jCoche);
        jFrame.add(jIncidente);
        jFrame.add(jUserData);
        jFrame.setLayout(null);
        jFrame.setVisible(true);
        jFrame.setResizable(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        buttonUserData.addActionListener(e -> {
            listenerUserData();
        });

        buttonCoche.addActionListener(e -> {
            listenerCoche();
        });

        buttonIncidencia.addActionListener(action -> {
            listenerIncidencia();
        });
    }

    public static void main(String[] args) {
        new TestMain();
    }

    /**
     * Función que sirve para validar los datos que el usuario de la aplicación introduce del usuario que necesita.
     * Si hay algún fallo, saltará una alerta indicando el fallo en cuestión, para avisarle.
     */
    private void listenerUserData(){
        try {
            User user = jUserData.getUser();
            jUserData.validateUser(user);
        } catch (Exception eUser) {
            eUser.printStackTrace();
        }
    }

    /**
     * Función que valida todos los campos del coche. Si hay algún fallo, saldrá una alerta indicando el fallo en cuestión
     * Si los datos introducidos son correctos, el coche se guardará para que la siguiente clase pueda trabajar con él.
     */
    private void listenerCoche(){
        try {
            Coche c = jCoche.getCoche();
            JOptionPane.showMessageDialog(
                    buttonCoche,
                    "Los datos introducidos son correctos.");

        } catch (CocheException e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    buttonCoche,
                    "Los datos introducidos no son correctos: " + e.getMessage());
        }
    }

    /**
     * Función que sirve para validar la incidencia registrada. Si hay algún error, saltará una alerta indicando el error.
     */
    private void listenerIncidencia(){
        try {
            jIncidente.getPrecio();
            jIncidente.getFechaAlta();
            jIncidente.getFechaBaja();
            jIncidente.getDescription();
            JOptionPane.showMessageDialog(
                    buttonIncidencia,
                    "Los datos introducidos son correctos.");
        } catch (PrecioException | JFechaException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    buttonIncidencia,
                    "Los datos introducidos no son correctos: " + e.getMessage());
        }
    }
}
