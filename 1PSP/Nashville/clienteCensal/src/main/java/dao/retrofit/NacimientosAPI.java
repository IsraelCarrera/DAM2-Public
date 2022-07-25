package dao.retrofit;

import dao.data.PersonaData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Query;
import utils.Constantes;

public interface NacimientosAPI {

    @POST(Constantes.NACIMIENTO)
    Call<PersonaData> registrarNacimiento(@Query(Constantes.ID_PADRE) String idPadre, @Query(Constantes.ID_MADRE) String idMadre, @Body PersonaData p);

    @DELETE(Constantes.NACIMIENTO)
    Call<Void> deletearPromiscuos(@Query(Constantes.ID_P_1) String idP1, @Query(Constantes.ID_P_2) String idP2);
}
