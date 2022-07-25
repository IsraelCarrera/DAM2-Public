package org.example.serverbasket.config;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.example.serverbasket.config.utils.Constantes;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Getter
@Log4j2
@Singleton
public class Configuration {

    private String ruta;
    private String user_bd;
    private String password_bd;
    private String driver;
    private String host;
    private String password_email;
    private String user_email;

    void cargar(InputStream file) {

        try {
            Yaml yaml = new Yaml();
            Iterable<Object> it = yaml.loadAll(file);
            Map<String, String> m = (Map) it.iterator().next();
            this.ruta = m.get(Constantes.RUTA);
            this.password_bd = m.get(Constantes.PASSWORD_BD);
            this.user_bd = m.get(Constantes.USER_BD);
            this.driver = m.get(Constantes.DRIVER);
            this.host = m.get(Constantes.HOST);
            this.password_email = m.get(Constantes.PASSWORD_MAIL);
            this.user_email = m.get(Constantes.USER_MAIL);


        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
