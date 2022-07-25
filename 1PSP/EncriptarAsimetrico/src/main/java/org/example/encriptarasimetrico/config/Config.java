package org.example.encriptarasimetrico.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Singleton;
import java.io.FileInputStream;
import java.util.Map;

@Getter
@Setter
@Log4j2
@Singleton
public class Config {
    private String ruta;
    private String user_bd;
    private String password_bd;
    private String driver;
    private String passKeyStore;

    public Config() {
        try {
            Yaml y = new Yaml();
            Iterable<Object> it;
            it = y.loadAll(new FileInputStream(Constantes.CONFIG_CONFIG_YAML));
            Map<String, String> m = (Map) it.iterator().next();
            this.ruta = m.get(Constantes.RUTA);
            this.password_bd = m.get(Constantes.PASSWORD_BD);
            this.user_bd = m.get(Constantes.USER_BD);
            this.driver = m.get(Constantes.DRIVER);
            this.passKeyStore = m.get(Constantes.PASS_KEY_STORE);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
