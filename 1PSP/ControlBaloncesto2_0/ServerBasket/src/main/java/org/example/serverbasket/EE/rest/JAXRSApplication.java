package org.example.serverbasket.EE.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.example.serverbasket.EE.utils.Constantes;

@ApplicationPath(Constantes.API)
@DeclareRoles({Constantes.ADMIN, Constantes.USER})
public class JAXRSApplication extends Application {
    }
