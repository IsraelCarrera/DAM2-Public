package EE.rest;

import EE.utils.Constantes;
import error.ApiError;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Persona;
import servicios.PersonasServicios;

import java.time.LocalDate;
import java.util.List;

@Path(Constantes.EMPAREJAR)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestEmparejar {
    private final PersonasServicios ps;

    @Inject
    public RestEmparejar(PersonasServicios ps) {
        this.ps = ps;
    }

    @GET
    public Response getPorSexo(@QueryParam(Constantes.SEXO) String sexo) {
        Response response;
        Either<ApiError, List<Persona>> resultado = ps.getPersonaPorSexo(sexo);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    @PUT
    public Response realizarCasamiento(@QueryParam(Constantes.ID_HOMBRE) String idHombre, @QueryParam(Constantes.ID_MUJER) String idMujer) {
        Response response;
        boolean seHanCasado = ps.realizarCasamiento(idHombre, idMujer);
        if (seHanCasado) {
            response = Response.status(Response.Status.OK)
                    .entity(true)
                    .build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiError(Constantes.NO_SE_HA_PODIDO_REALIZAR_LA_CASACION_POR_ALGUN_ERROR_INTERNO, LocalDate.now()))
                    .build();
        }
        return response;
    }
}
