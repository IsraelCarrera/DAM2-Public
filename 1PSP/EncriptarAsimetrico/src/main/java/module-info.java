module EncriptarAsimetrico {
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
    requires org.bouncycastle.provider;
    requires com.nimbusds.jose.jwt;


    exports org.example.encriptarasimetrico.gui;
    exports org.example.encriptarasimetrico.config;
    exports org.example.encriptarasimetrico.dao.utils;
    exports org.example.encriptarasimetrico.dao;
    exports org.example.encriptarasimetrico.security;
    exports org.example.encriptarasimetrico.servicios;
    exports org.example.encriptarasimetrico.gui.utils;
    exports org.example.encriptarasimetrico.modelo;
    opens org.example.encriptarasimetrico.gui;
    opens org.example.encriptarasimetrico.modelo;
    exports org.example.encriptarasimetrico.security.utils;
    exports org.example.encriptarasimetrico.servicios.utils;
}