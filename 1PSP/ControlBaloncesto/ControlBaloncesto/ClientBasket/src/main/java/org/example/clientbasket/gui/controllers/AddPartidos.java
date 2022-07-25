package org.example.clientbasket.gui.controllers;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.clientbasket.gui.utils.Constantes;
import org.example.clientbasket.servicios.ServiciosEquipo;
import org.example.clientbasket.servicios.ServiciosJornada;
import org.example.clientbasket.servicios.ServiciosPartido;
import org.example.common.modelo.Equipo;
import org.example.common.modelo.Jornada;
import org.example.common.modelo.Partido;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddPartidos implements Initializable {
    private final ServiciosEquipo se;
    private final ServiciosJornada sj;
    private final ServiciosPartido sp;
    @FXML
    private ListView<Partido> lvPartidos;
    @FXML
    private ListView<Equipo> lvEquipoLocal;
    @FXML
    private ListView<Equipo> lvEquipoVisitante;
    @FXML
    private ListView<Jornada> lvJornada;
    @FXML
    private TextField tfPuntosLocal;
    @FXML
    private TextField tfPuntosVisitante;
    //Pantalla principal
    private Principal principal;

    @Inject
    public AddPartidos(ServiciosEquipo se, ServiciosJornada sj, ServiciosPartido sp) {
        this.se = se;
        this.sj = sj;
        this.sp = sp;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @FXML
    private void addPartido() {
        //Lo primero es comprobar que está too seleccionado y escrito. Posteriormente comprobaremos que los dos equipos no son los mismos, y por último, agregamos.
        try {
            int puntosEquipoVisitante = Integer.parseInt(tfPuntosVisitante.getText());
            int puntosEquipoLocal = Integer.parseInt(tfPuntosLocal.getText());
            if (lvEquipoVisitante.getSelectionModel().getSelectedItem() != null || lvEquipoLocal.getSelectionModel().getSelectedItem() != null ||
                    lvJornada.getSelectionModel().getSelectedItem() != null) {
                if (!lvEquipoVisitante.getSelectionModel().getSelectedItem().getNombreEquipo().equals(lvEquipoLocal.getSelectionModel().getSelectedItem().getNombreEquipo())) {
                    //Aquí ya too estaría bien seleccionado, procedemos a agregar.
                    Partido partido = Partido.builder()
                            .idJornada(lvJornada.getSelectionModel().getSelectedItem().getIdJornada())
                            .nombreEquipoLocal(lvEquipoLocal.getSelectionModel().getSelectedItem().getNombreEquipo())
                            .nombreEquipoVisitante(lvEquipoVisitante.getSelectionModel().getSelectedItem().getNombreEquipo())
                            .puntosEquipoLocal(puntosEquipoLocal)
                            .puntosEquipoVisitante(puntosEquipoVisitante)
                            .build();
                    Single<Either<String, Partido>> singlePartido = Single.fromCallable(() -> sp.addPartido(partido))
                            .subscribeOn(Schedulers.io())
                            .observeOn(JavaFxScheduler.platform())
                            .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));
                    singlePartido.subscribe(either -> either
                                    .peek(p -> {
                                        principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.SE_HA_PROCEDIDO_A_GUARDAR_EL_PARTIDO_CORRECTAMENTE);
                                        lvPartidos.getItems().add(p);
                                    })
                                    .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                            throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
                    principal.getPrincipal().setCursor(Cursor.WAIT);
                } else {
                    principal.mandarAlert(Alert.AlertType.ERROR, Constantes.UN_EQUIPO_NO_PUEDE_JUGAR_UN_PARTIDO_CONTRA_SI_MISMO_SELECCIONA_OTRO_EQUIPO_CONTRINCANTE);
                }
            } else {
                principal.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_HA_SELECCIONADO_LOS_EQUIPOS_Y_O_LA_JORNADA_SELECCIONALOS);
            }
        } catch (Exception e) {
            principal.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_HA_ESCRITO_NUMEROS_EN_EL_CAMPO_DE_LOS_PUNTOS_ESCRIBA_NUMEROS);
        }
    }

    public void cargarAddPartidos() {
        //Partidos
        Single<Either<String, List<Partido>>> singlePartido = Single.fromCallable(sp::getAllPartidos)
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

        singlePartido.subscribe(either -> either
                        .peek(partidos -> {
                            lvPartidos.getItems().clear();
                            lvPartidos.getItems().addAll(partidos);
                        })
                        .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
        principal.getPrincipal().setCursor(Cursor.WAIT);
        //Jornadas
        Single<Either<String, List<Jornada>>> singleJornadas = Single.fromCallable(sj::getAllJornadas)
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

        singleJornadas.subscribe(either -> either
                        .peek(jornadas -> {
                            lvJornada.getItems().clear();
                            lvJornada.getItems().addAll(jornadas);
                        })
                        .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
        principal.getPrincipal().setCursor(Cursor.WAIT);

        //Partidos
        Single<Either<String, List<Equipo>>> singleEquipos = Single.fromCallable(se::getAllEquipos)
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

        singleEquipos.subscribe(either -> either
                        .peek(equipos -> {
                            lvEquipoLocal.getItems().clear();
                            lvEquipoLocal.getItems().addAll(equipos);
                            lvEquipoVisitante.getItems().clear();
                            lvEquipoVisitante.getItems().addAll(equipos);
                        })
                        .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));

        principal.getPrincipal().setCursor(Cursor.WAIT);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
