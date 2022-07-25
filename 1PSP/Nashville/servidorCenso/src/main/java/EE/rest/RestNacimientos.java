package EE.rest;


import EE.utils.Constantes;
import error.ApiError;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Persona;
import servicios.PersonasServicios;

import java.time.LocalDate;

@Path(Constantes.NACIMIENTO)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestNacimientos {
    private final PersonasServicios ps;

    @Inject
    public RestNacimientos(PersonasServicios ps) {
        this.ps = ps;
    }

    @POST
    public Response registrarNacimiento(@QueryParam(Constantes.ID_PADRE) String idPadre, @QueryParam(Constantes.ID_MADRE) String idMadre, Persona p) {
        Response response;
        int resultado = ps.registrarNacimiento(idPadre, idMadre, p);
        if (resultado == 200) {
            response = Response.status(Response.Status.CREATED)
                    .entity(p)
                    .build();
        } else if (resultado == 498) {
            response = Response.status(498, Constantes.NO_SE_HA_PODIDO_ANADIR)
                    .entity(new ApiError(Constantes.NO_SE_HA_PODIDO_ANADIR_EL_NACIMIENTO_PRUEBE_MAS_TARDE, LocalDate.now()))
                    .build();
        } else {
            response = Response.status(499, Constantes.NO_ESTAN_CASADOS)
                    .entity(Constantes.NO_ESTAN_CASADOS_EXC)
                    .build();
        }
        return response;
    }

    @DELETE
    public Response deletearALosPromiscuos(@QueryParam(Constantes.ID_P_1) String idP1, @QueryParam(Constantes.ID_P_2) String idP2) {
        Response response;
        boolean resultado = ps.borrarImpuros(idP1, idP2);
        if (resultado) {
            response = Response.status(Response.Status.NO_CONTENT)
                    .build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiError(Constantes.NO_SE_HA_PODIDO_BORRAR_A_ESTOS_IMPUROS, LocalDate.now()))
                    .build();
        }
        return response;
    }
}
