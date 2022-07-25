module ClientBasket {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires lombok;
    requires java.logging;
    requires retrofit2;
    requires okhttp3;
    requires io.vavr;

    requires retrofit2.converter.gson;
    requires org.apache.logging.log4j;
    requires com.google.gson;
    requires java.base;
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;
    requires common;
    requires io.reactivex.rxjava3;
    requires retrofit2.adapter.rxjava3;
    requires org.pdfsam.rxjavafx;

    opens org.example.clientbasket.gui.controllers;
    opens org.example.clientbasket.gui;

    exports org.example.clientbasket.gui;
    exports org.example.clientbasket.utils;
    exports org.example.clientbasket.dao;
    exports org.example.clientbasket.dao.utils;
    exports org.example.clientbasket.dao.retrofit;
    exports org.example.clientbasket.config;
    exports org.example.clientbasket.servicios;
    exports org.example.clientbasket.gui.utils;
}