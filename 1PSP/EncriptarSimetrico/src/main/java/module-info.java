module EncriptarSimetrico {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires lombok;
    requires java.logging;
    requires io.vavr;
    requires org.apache.logging.log4j;
    requires java.base;
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;
    requires java.sql;
    requires java.naming;
    requires spring.jdbc;
    requires com.zaxxer.hikari;
    requires spring.tx;
    requires com.google.common;

    exports org.example.encriptar.gui;
    exports org.example.encriptar.config;
    exports org.example.encriptar.utils;
    exports org.example.encriptar.dao;
    exports org.example.encriptar.servicios;
    exports org.example.encriptar.dao.model;
    opens org.example.encriptar.gui;
    opens org.example.encriptar.modelo;
}