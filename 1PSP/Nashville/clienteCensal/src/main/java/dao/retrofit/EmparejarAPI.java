package dao.retrofit;

import dao.data.PersonaData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import utils.Constantes;

import java.util.List;

public interface EmparejarAPI {

    @GET(Constantes.EMPAREJAR)
    Call<List<PersonaData>> getAllPersonas(@Query(Constantes.SEXO) String sexo);

    @PUT(Constantes.EMPAREJAR)
    Call<Boolean> hacerEmparejamiento(@Query(Constantes.ID_HOMBRE) String idHombre, @Query(Constantes.ID_MUJER) String idMujer);
}
