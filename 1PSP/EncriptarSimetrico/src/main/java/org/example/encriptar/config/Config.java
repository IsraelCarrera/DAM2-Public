package org.example.encriptar.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Singleton;
import java.io.FileInputStream;
import java.util.Map;

//Coges un secreto y la encriptas de forma simetrica con una contraseña aleatoria.
//Esa contraseña aleatoria se cifra de forma asimetrica con la clavePública en otro BBDD.
//Si quieres compartir. Esta la compartes con otro, haciendo que se firme esa contraseña aleatoria con su clavePública


@Getter
@Setter
@Log4j2
@Singleton
public class Config {
    private String ruta;
    private String user_bd;
    private String password_bd;
    private String driver;

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
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
