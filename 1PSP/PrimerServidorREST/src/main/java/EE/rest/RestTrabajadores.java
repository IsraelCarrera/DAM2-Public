package EE.rest;

import EE.errores.ApiError;
import EE.utils.Constantes;
import dao.modelo.Trabajador;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import servicios.TrabajadorServicios;

import java.time.LocalDate;
import java.util.List;

@Path(Constantes.TRABAJADORES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestTrabajadores {
    private final TrabajadorServicios ts;

    @Inject
    public RestTrabajadores(TrabajadorServicios ts) {
        this.ts = ts;
    }

    @GET
    public Response getAllTrabajadores() {
        Response response;
        Either<ApiError, List<Trabajador>> resultado = ts.getAllTrabajadores();
        if (resultado.isLeft()) {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        } else {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        }
        return response;
    }

    @GET
    @Path(Constantes.ID_LLAVES)
    public Response getTrabajador(@PathParam(Constantes.ID) int id) {
        Response response;
        Either<ApiError, Trabajador> resultado = ts.getTrabajador(id);
        if (resultado.isLeft()) {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        } else {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        }
        return response;
    }

    //Get todos los trabajadores de una empresa en concreto.
    @GET
    @Path(Constantes.EMPRESA)
    public Response getTrabajadorPorEmpresa(@QueryParam(Constantes.IDEMPRESA) int idEmpresa) {
        Response response;
        Either<ApiError, List<Trabajador>> resultado = ts.getTrabajadoresPorEmpresa(idEmpresa);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK).entity(resultado.get()).build();
        } else {
            response = Response.status(Response.Status.NO_CONTENT).entity(resultado.getLeft()).build();
        }
        return response;
    }

    @POST
    public Response addTrabajador(Trabajador t) {
        Response response;
        if (ts.addTrabajador(t) != null) {
            response = Response.status(Response.Status.OK)
                    .entity(t).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(Constantes.TRABAJADOR_NOADD, LocalDate.now())).build();
        }
        return response;
    }

    @DELETE
    public Response borrarTrabajador(@QueryParam(Constantes.ID) int id) {
        Response response;
        if (ts.borrarTrabajador(id)) {
            response = Response.status(Response.Status.OK)
                    .entity(true).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(Constantes.TRABAJADOR_NOBORRADO, LocalDate.now())).build();
        }
        return response;
    }

    @PUT
    public Response updateTrabajador(Trabajador t) {
        Response response;
        Either<ApiError, Trabajador> resultado = ts.updateTrabajador(t);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK).entity(resultado.get()).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).entity(resultado.getLeft()).build();
        }
        return response;
    }
}
