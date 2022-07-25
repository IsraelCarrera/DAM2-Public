module ClienteRest {
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



    opens dao.data;
    opens gui;
    opens config;
    opens modelos;
    opens gui.controllers;
    opens dao;
    opens dao.utils;

    exports dao.retrofit;
    exports dao.utils;
    exports gui.controllers;
    exports dao.data;
    exports modelos;
    exports gui;
    exports utils;
    exports servicios;
    exports dao;
}