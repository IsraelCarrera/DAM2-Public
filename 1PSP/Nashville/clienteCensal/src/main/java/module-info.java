module clienteCensal {
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
    requires org.apache.logging.log4j;
    requires com.google.gson;
    requires java.base;
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;
    requires common;

    opens gui.controllers;
    opens gui;

    exports gui;
    exports gui.utils;
    exports utils;
    exports dao;
    exports dao.data;
    exports dao.utils;
    exports dao.retrofit;
    exports config;
    exports servicios;


}