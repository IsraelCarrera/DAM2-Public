package org.example.serverserie.ee.rest;


import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.example.serverserie.ee.utils.Constantes;

@ApplicationPath("/api")
@DeclareRoles({Constantes.USER})
public class JAXKRSApplication extends Application {
}
