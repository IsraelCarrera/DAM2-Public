import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Esta clase tiene un contenedor con 3 box o cajas de texto y tres etiquetas. En las cuales, en estas últimas nos indica el dato a introducir en las cajas de texto.
 * Hay dos funciones:
 * getDate(). Las cajas de texto recibirán, unos números, que se comprobarán para ver si son numeros, y a su vez, si la fecha es la adecuada. Retornara un Date.
 * setDate(Date d). Se envia una fecha y el programa pondrá en las cajas de texto la fecha recibida. Poniendo el día, mes y año en sus respectivas cajas.
 * @author Israel Carrera 2DAM
 *
 */

public class Fecha extends JPanel implements JFechaInterface {
    private final String matchesNum = "[+-]?\\d*(\\.\\d+)?";
    JTextField tfDia, tfMes, tfAnno;
    JLabel lbDia, lbMes, lbAnno;

    public Fecha() {
        //Inicialización de las variables
        tfDia = new JTextField();
        tfMes = new JTextField();
        tfAnno = new JTextField();
        lbDia = new JLabel();
        lbMes = new JLabel();
        lbAnno = new JLabel();


        //Dandole formato a las variables
        tfDia.setBounds(50, 50, 25, 20);
        tfMes.setBounds(100, 50, 25, 20);
        tfAnno.setBounds(150, 50, 50, 20);
        lbDia.setBounds(50, 25, 25, 20);
        lbMes.setBounds(100, 25, 25, 20);
        lbAnno.setBounds(160, 25, 25, 20);
        lbDia.setText("día");
        lbMes.setText("mes");
        lbAnno.setText("año");


        //Colocamos las cosas en el panel.
        this.add(tfDia);
        this.add(tfMes);
        this.add(tfAnno);
        this.add(lbDia);
        this.add(lbMes);
        this.add(lbAnno);
        this.setSize(250, 150);
    }

    public static void main(String[] args) {
    }

    /**
     * Esta función sirve para recibir la fecha una vez el usuario ha escrito correctamente las fechas.
     * Se han tenido en cuenta todas las condiciones para que siempre retorne una fecha válida.
     * Si el usuario escribe una fecha incorrecta, saltará una excepción.
     * El formato de return es Date.
     *
     * @return Date
     * @throws JFechaException
     */
    @Override
    public Date getDate() throws JFechaException {
        //Para saber si lo introducido es numero:
        String diaString = tfDia.getText();
        String mesString = tfMes.getText();
        String annoString = tfAnno.getText();
        boolean esBisiesto = false;
        if (!diaString.matches(matchesNum)) {
            throw new JFechaException("El día no es un número.");
        }
        if (!mesString.matches(matchesNum)) {
            throw new JFechaException("El mes no es un número.");
        }
        if (!annoString.matches(matchesNum)) {
            throw new JFechaException("El año no es un número.");
        }
        //Pasamos los valores a numeros para tratarlos mejor.
        int dia = Integer.parseInt(diaString);
        int mes = Integer.parseInt(mesString);
        int anno = Integer.parseInt(annoString);

        //Comprobamos los datos están acotados.
        //Primero comprobamos si los años están entre 1900 y 2300.
        if (anno < 1900 || anno > 2300) {
            throw new JFechaException("El año está muy alejado de las fechas.");
        }
        //Comprobaciones de meses.
        if (mes <= 0 || mes >= 13) {
            throw new JFechaException("No ha escrito un mes correcto.");
        }
        //Comprobar si año es bisiesto.
        if ((anno % 4 == 0) && ((anno % 100 != 0) || (anno % 400 == 0))) {
            esBisiesto = true;
        }
        //Comprobaciones de día.
        if (dia <= 0) {
            throw new JFechaException("No ha escrito un día correcto.");
        } else {
            switch (mes) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if (dia > 31) {
                        throw new JFechaException("No ha escrito un día correcto.");
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if (dia > 30) {
                        throw new JFechaException("No ha escrito un día correcto.");
                    }
                    break;
                case 2:
                    if (dia >= 29 && !esBisiesto){
                        throw new JFechaException("No ha escrito un día correcto.");
                    } else if (dia >= 30) {
                        throw new JFechaException("No ha escrito un día correcto.");
                    }
                    break;
            }
        }
        //Una vez tengo las comprobaciones hechas, construyo el objeto date.
        //Como date empieza en 1900, hay que restarle a "anno" 1900. Además. a mes hay que restarle 1, ya que enero es 0 y diciembre, 11.
        anno -= 1900;
        mes -= 1;
        Date f = new Date(anno, mes, dia);
        return f;
    }

    /**
     * Esta funcion sirve para, según la fecha indicada, pintarla en las 3 boxes o cajas de texto que hay en el contenedor.
     * Al ya estar la fecha bien hecho, solo hay que updatearla.
     * @param d Parametro date.
     */
    @Override
    public void setDate(Date d) {
        int anno = d.getYear() + 1900;
        int mes = d.getMonth() + 1;
        int dia = d.getDay();
        //Y ahora, las escribimos.
        tfAnno.setText(String.valueOf(anno));
        tfMes.setText(String.valueOf(mes));
        tfDia.setText(String.valueOf(dia));
    }
}