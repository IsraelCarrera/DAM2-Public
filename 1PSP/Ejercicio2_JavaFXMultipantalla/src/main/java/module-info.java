module Ejercicio2{
    //Para JAVAFX
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires lombok;
    requires java.logging;
    requires MaterialFX;

    opens controllers to javafx.fxml;

    exports main;
    exports modelo;
    exports servicios.persona;

}