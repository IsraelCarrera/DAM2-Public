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

import java.util.List;

@Path(Constantes.PERSONAS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestPersonas {
    private final PersonasServicios ps;

    @Inject
    public RestPersonas(PersonasServicios ps) {
        this.ps = ps;
    }

    //Get todas las personas.
    @GET
    public Response getAllPersonas() {
        Response response;
        Either<ApiError, List<Persona>> resultado = ps.getAllPersonas();
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

    //Coger a una persona por ID.
    @GET
    @Path(Constantes.IDLLAVES)
    public Response getPersona(@PathParam(Constantes.ID) String id) {
        Response response;
        Either<ApiError, Persona> resultado = ps.getOnePersona(id);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }


    @POST
    public Response addPersona(Persona p) {
        Response response;
        Either<ApiError, Persona> resultado = ps.addPersona(p);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.CREATED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    @DELETE
    public Response borrarPersona(@QueryParam(Constantes.ID) String id) {
        Response response;
        Either<ApiError, List<Persona>> resultado = ps.deletePersona(id);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    @PUT
    public Response updatePersona(Persona p) {
        Response response;
        Either<ApiError, Persona> resultado = ps.updatePersona(p);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.CREATED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    //GET de las consultas.
    @GET
    @Path(Constantes.FILTRAR)
    public Response getConsultas(@QueryParam(Constantes.LUGAR_PROCEDENCIA) String lugarProcedencia, @QueryParam(Constantes.ANO_NACIMIENTO) int anoNacimiento,
                                 @QueryParam(Constantes.N_HIJOS) int nHijos, @QueryParam(Constantes.ESTADO_CIVIL) String estadoCivil) {
        Response response;
        Either<ApiError, List<Persona>> resultado = ps.getPorFiltros(lugarProcedencia, anoNacimiento, nHijos, estadoCivil);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }
}
