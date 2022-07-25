module ClientPrimerREST {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires lombok;
    requires java.logging;
    requires retrofit2;
    requires okhttp3;
    requires io.vavr;
    requires retrofit2.adapter.rxjava2;
    requires retrofit2.converter.gson;
    requires retrofit2.converter.scalars;
    requires org.apache.logging.log4j;
    requires com.google.gson;
    requires modelmapper;
    requires java.base;
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;

    opens gui.controllers;
    opens gui.controllers.trabajador;
    opens gui.controllers.empresa;
    opens gui.controllers.verAll;
    opens gui;


    exports gui.controllers;
    exports gui.controllers.trabajador;
    exports gui.controllers.verAll;
    exports gui.controllers.empresa;
    exports gui;
    exports utils;
    exports dao;
    exports dao.data;
    exports dao.utils;
    exports dao.retrofit;
    exports config;
    exports servicios;
    exports gui.utils;
}