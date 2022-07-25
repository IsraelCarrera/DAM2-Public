package org.example.serverbasket.ee.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.example.serverbasket.ee.utils.Constantes;

@ApplicationPath(Constantes.API)
@DeclareRoles({Constantes.USER})
public class JAXRSApplication extends Application {

}
