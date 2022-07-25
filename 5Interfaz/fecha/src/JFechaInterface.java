import java.util.Date;

/**
 * Interfaz para implementar los métodos de la clase Fecha.java. Podrá ver sus métodos en la clase correspondiente.
 * @author Israel.
 */
public interface JFechaInterface {

     Date getDate() throws JFechaException;
     void setDate(Date d);

}
