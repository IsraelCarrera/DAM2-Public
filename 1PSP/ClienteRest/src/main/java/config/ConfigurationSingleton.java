package config;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class ConfigurationSingleton {
    private static ConfigurationSingleton config;
    private String path_base;
    private String api_key_value;

    private ConfigurationSingleton() {

    }

    public static synchronized ConfigurationSingleton getInstance() {
        if (config == null) {
            try {
                Yaml y = new Yaml();
                Iterable<Object> it = null;
                it = y.loadAll(new FileInputStream("config/config.yaml"));
                Map<String, String> m = (Map) it.iterator().next();
                config = new ConfigurationSingleton();
                config.setPath_Base(m.get("path_base"));
                config.setApi_key_value(m.get("api_key_value"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ConfigurationSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return config;
    }

    private void setPath_Base(String path_base) {
        this.path_base = path_base;
    }

    private void setApi_key_value(String api_key_value) {
        this.api_key_value = api_key_value;
    }
}
