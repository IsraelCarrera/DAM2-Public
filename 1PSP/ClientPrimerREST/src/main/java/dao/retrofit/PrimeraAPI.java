package dao.retrofit;

import dao.data.Empresa;
import dao.data.Trabajador;
import retrofit2.Call;
import retrofit2.http.*;
import utils.Constantes;

import java.util.List;

public interface PrimeraAPI {

    //Empresa
    @GET(Constantes.EMPRESAS)
    Call<List<Empresa>> getAllEmpresas();

    @GET(Constantes.EMPRESAS_ID)
    Call<Empresa> getEmpresaId(@Path(Constantes.ID) int id);

    @POST(Constantes.EMPRESAS)
    Call<Empresa> postEmpresa(@Body Empresa e);

    @DELETE(Constantes.EMPRESAS)
    Call<Boolean> deleteEmpresa(@Query(Constantes.ID) int id);

    @PUT(Constantes.EMPRESAS)
    Call<Empresa> putEmpresa(@Body Empresa e);

    //Trabajador
    @GET(Constantes.TRABAJADORES)
    Call<List<Trabajador>> getAllTrabajadores();

    @GET(Constantes.TRABAJADORES_ID)
    Call<Trabajador> getTrabajadorId(@Path(Constantes.ID) int id);

    @GET(Constantes.TRABAJADORES_EMPRESA)
    Call<List<Trabajador>> getTrabajadorPorIdEmpresa(@Query(Constantes.ID_EMPRESA) int idEmpresa);

    @POST(Constantes.TRABAJADORES)
    Call<Trabajador> postTrabajador(@Body Trabajador t);

    @DELETE(Constantes.TRABAJADORES)
    Call<Boolean> deleteTrabajador(@Query(Constantes.ID) int id);

    @PUT(Constantes.TRABAJADORES)
    Call<Trabajador> putTrabajador(@Body Trabajador t);
}
