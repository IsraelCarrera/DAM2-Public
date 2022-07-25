package org.example.serverbasket.config;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener()
public class ListenerConfig implements ServletContextListener {
    Configuration conf;


    @Inject
    public ListenerConfig(Configuration conf) {
        this.conf = conf;
    }

    public ListenerConfig() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        conf.cargar();
    }


    public void contextDestroyed(ServletContextEvent sce) {
        //Cerrar el DBPool
    }
}
