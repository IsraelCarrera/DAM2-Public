package org.example.clientsecretos.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.example.clientsecretos.config.util.Constantes;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

@Getter
@Setter
@Log4j2
@Singleton
public class ConfigClient {
    private String pathBase;

    public ConfigClient() {
        try {
            Yaml y = new Yaml();
            Iterable<Object> it;
            it = y.loadAll(new FileInputStream(Constantes.CONFIG_CONFIG_YAML));
            Map<String, String> m = (Map) it.iterator().next();
            this.pathBase = m.get(Constantes.PATH_BASE);
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
