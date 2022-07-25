module ClienteRest {
    //needed for JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires lombok;
//    requires MaterialFX;
    requires java.logging;
    requires io.reactivex.rxjava2;
    requires retrofit2;
    requires okhttp3;
    requires io.vavr;
    requires retrofit2.adapter.rxjava2;
    requires retrofit2.converter.gson;
    requires retrofit2.converter.scalars;
    requires java.validation;
    requires rxjavafx;
    requires org.apache.logging.log4j;
    requires com.google.gson;


    opens controllers to javafx.fxml;

    exports gui;
    exports utils;
}