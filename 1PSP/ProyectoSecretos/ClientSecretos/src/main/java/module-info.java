module ClientSecretos {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires lombok;
    requires java.logging;
    requires retrofit2;
    requires okhttp3;
    requires io.vavr;
    requires org.bouncycastle.provider;
    requires Common;
    requires retrofit2.converter.gson;
    requires org.apache.logging.log4j;
    requires com.google.gson;
    requires java.base;
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;
    requires io.reactivex.rxjava3;
    requires retrofit2.adapter.rxjava3;
    requires org.pdfsam.rxjavafx;
    requires retrofit2.converter.scalars;
    requires com.nimbusds.jose.jwt;
    requires com.google.common;


    exports org.example.clientsecretos.config;
    exports org.example.clientsecretos.config.util;
    exports org.example.clientsecretos.dao;
    exports org.example.clientsecretos.dao.utils;
    exports org.example.clientsecretos.dao.retrofit;
    exports org.example.clientsecretos.domain;
    exports org.example.clientsecretos.gui;
    exports org.example.clientsecretos.gui.utils;
    exports org.example.clientsecretos.gui.controllers;
    exports org.example.clientsecretos.security;
    exports org.example.clientsecretos.security.utils;
    exports org.example.clientsecretos.servicios;
    exports org.example.clientsecretos.servicios.Modelo;
    exports org.example.clientsecretos.servicios.utils;


    opens org.example.clientsecretos.gui;
    opens org.example.clientsecretos.gui.controllers;
    opens org.example.clientsecretos.domain;
    opens org.example.clientsecretos.servicios.Modelo;
    opens org.example.clientsecretos.gui.utils;
}