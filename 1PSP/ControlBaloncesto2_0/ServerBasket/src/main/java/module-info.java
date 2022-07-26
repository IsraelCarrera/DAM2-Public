module ServerBasket {
    requires io.vavr;
    requires jakarta.jakartaee.api;
    requires lombok;
    requires common;

    requires org.apache.logging.log4j;
    requires org.yaml.snakeyaml;

    requires java.sql;
    requires java.naming;
    requires spring.jdbc;
    requires com.zaxxer.hikari;
    requires spring.tx;
    requires jjwt.api;
}