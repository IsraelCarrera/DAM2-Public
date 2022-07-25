package EE.rest;

import EE.utils.Constantes;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import servicios.PersonasServicios;

@Path(Constantes.DEFUNCION)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestDefunciones {
    private final PersonasServicios ps;

    @Inject
    public RestDefunciones(PersonasServicios ps) {
        this.ps = ps;
    }

    @DELETE
    public Response defuncion(@QueryParam(Constantes.ID_PERSONA) String idPersona) {
        Response response;
        int resultado = ps.registrarDefuncion(idPersona);
        if (resultado == 200) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado)
                    .build();
        } else if (resultado == 489) {
            response = Response.status(489, Constantes.PERSONA_ES_HIJO_DE_ALGUIEN)
                    .entity(resultado)
                    .build();
        } else {
            response = Response.status(488, Constantes.FALLO_EN_ALGUN_PUNTO_DEL_REGISTRO_DE_DEFUNCION)
                    .entity(resultado)
                    .build();
        }
        return response;
    }
}
