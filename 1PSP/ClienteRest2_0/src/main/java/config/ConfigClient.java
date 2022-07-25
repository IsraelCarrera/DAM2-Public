package config;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

@Getter
@Singleton
@Log4j2
public class ConfigClient {
    private String pathBase;
    private String apiKeyValue;

    public ConfigClient() {
        try {
            Yaml y = new Yaml();
            Iterable<Object> it;
            it = y.loadAll(new FileInputStream("config/config.yaml"));
            Map<String, String> m = (Map) it.iterator().next();
            this.pathBase = m.get("path_base");
            this.apiKeyValue = m.get("api_key_value");
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
