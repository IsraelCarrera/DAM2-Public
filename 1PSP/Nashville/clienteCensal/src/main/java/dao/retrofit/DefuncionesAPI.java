package dao.retrofit;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Query;
import utils.Constantes;

public interface DefuncionesAPI {

    @DELETE(Constantes.DEFUNCION)
    Call<Integer> matarAPersona(@Query(Constantes.ID_PERSONA) String idHombre);
}
