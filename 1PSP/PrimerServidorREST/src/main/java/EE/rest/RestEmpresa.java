package EE.rest;

import EE.errores.ApiError;
import EE.utils.Constantes;
import dao.modelo.Empresa;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import servicios.EmpresaServicios;

import java.time.LocalDate;
import java.util.List;

@Path(Constantes.EMPRESAS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestEmpresa {

    private final EmpresaServicios es;

    @Inject
    public RestEmpresa(EmpresaServicios es) {
        this.es = es;
    }

    //Coger una empresa por ID.
    @GET
    @Path(Constantes.ID_LLAVES)
    public Response getEmpresa(@PathParam(Constantes.ID) int id) {
        Response response;
        Either<ApiError, Empresa> resultado = es.getEmpresa(id);
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

    //Coger todas
    @GET
    public Response getAllEmpresas() {
        Response response;
        Either<ApiError, List<Empresa>> resultado = es.getAllEmpresas();
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

    //Insertar
    @POST
    public Response addEmpresa(Empresa e) {
        Response response;
        if (es.addEmpresa(e) != null) {
            response = Response.status(Response.Status.OK)
                    .entity(e).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(Constantes.EMPRESA_NOADD, LocalDate.now())).build();
        }
        return response;
    }

    //Delete
    @DELETE
    public Response borrarEmpresa(@QueryParam(Constantes.ID) int id) {
        Response response;
        if (es.borrarEmpresa(id)) {
            response = Response.status(Response.Status.OK)
                    .entity(true).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(Constantes.EMPRESA_NOBORRADA, LocalDate.now())).build();
        }
        return response;
    }

    //Update
    @PUT
    public Response updateEmpresa(Empresa e) {
        Response response;
        Either<ApiError, Empresa> resultado = es.updateEmpresa(e);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK).entity(resultado.get()).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).entity(resultado.getLeft()).build();
        }
        return response;
    }
}
