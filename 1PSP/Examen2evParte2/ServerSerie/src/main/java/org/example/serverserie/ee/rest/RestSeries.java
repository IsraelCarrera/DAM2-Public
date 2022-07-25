package org.example.serverserie.ee.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.example.common.modelo.ApiError;
import org.example.common.modelo.Capitulo;
import org.example.common.modelo.Serie;
import org.example.serverserie.ee.utils.Constantes;
import org.example.serverserie.servicios.ServiciosSeries;

import java.util.List;

@RolesAllowed(Constantes.USER)
@Log4j2
@Path("/series")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestSeries {
    private final ServiciosSeries ss;

    @Inject
    public RestSeries(ServiciosSeries ss) {
        this.ss = ss;
    }

    @POST
    public Response addSerie(Serie serie){
        Response response;
        Either<ApiError, Serie> resultado = ss.addSerie(serie);
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
    public Response borrarSerie(@QueryParam("idUser")int idUser, @QueryParam("idSerie") int idSerie){
        Response response;
        Either<ApiError, Boolean> resultado = ss.borrarSerie(idUser, idSerie);
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
    @Path("/capitulo")
    public Response addCapitulo(Capitulo capitulo){
        Response response;
        Either<ApiError, Capitulo> resultado = ss.addCapitulo(capitulo);
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
    @Path("/capitulo")
    public Response borrarCapitulo(@QueryParam("idUser")int idUser, @QueryParam("idSerie") int idSerie, @QueryParam("idCapitulo") int idCapitulo){
        Response response;
        Either<ApiError, Boolean> resultado = ss.borrarCapitulo(idUser, idSerie,idCapitulo);
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
    @Path("/updateCap")
    public Response updateCapitulo( @QueryParam("idUser")int idUser, @QueryParam("idSerie") int idSerie, @QueryParam("idCapitulo") int idCapitulo, @QueryParam("quePaso") boolean quepaso){
        Response response;
        Either<ApiError, Boolean> resultado = ss.updateCapitulo(idUser, idSerie, idCapitulo,quepaso);
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

    @GET
    public Response getAllSeries( @QueryParam("idUser")int idUser){
        Response response;
        Either<ApiError, List<Serie>> resultado = ss.getAllSeries(idUser);
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

    @GET
    @Path("/getOneSerie")
    public Response getOneSerie(@QueryParam("idUser")int idUser, @QueryParam("idSerie") int idSerie){
        Response response;
        Either<ApiError, Serie> resultado = ss.getOneSerie(idUser,idSerie);
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
