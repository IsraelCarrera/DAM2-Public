package gui.controllers;

import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import modelos.*;
import servicios.SeriesTvServicios;
import utils.Constantes;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SeriesMasInformacionPantalla implements Initializable {
    //FXML
    @FXML
    private ListView<Serie> lvSerie;
    @FXML
    private ListView<TemporadaTV> lvTemporadas;
    @FXML
    private ListView<Episodio> lvCapitulos;
    @FXML
    private ListView<Persona> lvCast;
    @FXML
    private TextField tfBuscarSerie;
    @FXML
    private Label lbSipnosis;

    //Voy a utilizar una variable para poder ir seleccionado dicha serie. Para que si quiere ver los capitulos y ha seleccionado otra
    //serie, se vean los de la temporada (con los que dio al botón buscar capitulos).
    private int idSerieLocalizador;
    //Lo mismo para la temporada y que no haya problemas al ver el cast.
    private int numTemporadaLocalizador;

    private PrincipalPantalla principalPantalla;

    public void setPrincipalPantalla(PrincipalPantalla principalPantalla) {
        this.principalPantalla = principalPantalla;
    }

    //Inyect
    @Inject
    private SeriesTvServicios ss;

    @FXML
    private void buscarSerie() {
        String nombreSerie = tfBuscarSerie.getText();
        if (!nombreSerie.isEmpty()) {
            var tarea = new Task<Either<String, List<Serie>>>() {
                @Override
                protected Either<String, List<Serie>> call() {
                    return ss.buscarSeries(nombreSerie);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::mostrarListaSeries)
                                .peekLeft(s -> principalPantalla.mandarAlert(Alert.AlertType.WARNING, s)))
                        .onFailure(t -> principalPantalla.mandarAlert(Alert.AlertType.ERROR, t.getMessage()));
                this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                principalPantalla.mandarAlert(Alert.AlertType.ERROR, workerStateEvent.getSource().getException().getMessage());
                this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.principalPantalla.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principalPantalla.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_ESCRITO_NADA);
        }
    }

    @FXML
    private void verTemporada() {
        if (lvSerie.getSelectionModel().getSelectedItem() != null) {
            int idSerie = lvSerie.getSelectionModel().getSelectedItem().getId();
            idSerieLocalizador = idSerie;
            var tarea = new Task<Either<String, Serie>>() {
                @Override
                protected Either<String, Serie> call() {
                    return ss.seriePorId(idSerie);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::addTemporada)
                                .peekLeft(s -> principalPantalla.mandarAlert(Alert.AlertType.WARNING, s)))
                        .onFailure(t -> principalPantalla.mandarAlert(Alert.AlertType.ERROR, t.getMessage()));
                this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                principalPantalla.mandarAlert(Alert.AlertType.ERROR, workerStateEvent.getSource().getException().getMessage());
                this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.principalPantalla.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principalPantalla.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_SELECCIONADO);
        }
    }

    @FXML
    private void verCapitulos() {
        if (lvTemporadas.getSelectionModel().getSelectedItem() != null) {
            int numeroTemporada = lvTemporadas.getSelectionModel().getSelectedItem().getSeasonNumber();
            numTemporadaLocalizador = numeroTemporada;
            var tarea = new Task<Either<String, TemporadaTV>>() {
                @Override
                protected Either<String, TemporadaTV> call() {
                    return ss.getTemporada(idSerieLocalizador, numeroTemporada);
                }
            };

            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::addCapitulos)
                                .peekLeft(s -> principalPantalla.mandarAlert(Alert.AlertType.WARNING, s)))
                        .onFailure(t -> principalPantalla.mandarAlert(Alert.AlertType.ERROR, t.getMessage()));
                this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                principalPantalla.mandarAlert(Alert.AlertType.ERROR, workerStateEvent.getSource().getException().getMessage());
                this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.principalPantalla.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principalPantalla.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_TEMPORADA_SELECCIONADA);
        }
    }

    @FXML
    private void verCast() {
        if (lvCapitulos.getSelectionModel().getSelectedItem() != null) {
            int numeroCapitulo = lvCapitulos.getSelectionModel().getSelectedItem().getEpisodeNumber();
            var tarea = new Task<Either<String, CreditosEpisodio>>() {
                @Override
                protected Either<String, CreditosEpisodio> call() {
                    return ss.getEpisodio(idSerieLocalizador, numTemporadaLocalizador, numeroCapitulo);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::addCast)
                                .peekLeft(s -> principalPantalla.mandarAlert(Alert.AlertType.WARNING, s)))
                        .onFailure(t -> principalPantalla.mandarAlert(Alert.AlertType.ERROR, t.getMessage()));
                this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                principalPantalla.mandarAlert(Alert.AlertType.ERROR, workerStateEvent.getSource().getException().getMessage());
                this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.principalPantalla.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principalPantalla.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_EPISODIO_SELECCIONADO);
        }
    }

    @FXML
    private void ponerSipnosis() {
        if (lvCapitulos.getSelectionModel().getSelectedItem() != null) {
            if (lvCapitulos.getSelectionModel().getSelectedItem().getOverview() != null) {
                lbSipnosis.setText(lvCapitulos.getSelectionModel().getSelectedItem().getOverview());
            } else {
                principalPantalla.mandarAlert(Alert.AlertType.WARNING, Constantes.EPISODIO_SIN_INFORMACION);
            }
        } else {
            principalPantalla.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_EPISODIO_SELECCIONADO);
        }

    }

    @FXML
    private void limpiarAll() {
        lvSerie.getItems().clear();
        lvTemporadas.getItems().clear();
        tfBuscarSerie.clear();
        limpiarCapitulos();
    }

    @FXML
    private void limpiarCapitulos() {
        lvCapitulos.getItems().clear();
        lbSipnosis.setText("");
        limpiarCast();
    }

    @FXML
    private void limpiarCast() {
        lvCast.getItems().clear();
    }

    private void mostrarListaSeries(List<Serie> series) {
        limpiarAll();
        lvSerie.getItems().addAll(series);
    }

    private void addTemporada(Serie s) {
        lvTemporadas.getItems().clear();
        lvTemporadas.getItems().addAll(s.getSeasons());
    }

    private void addCapitulos(TemporadaTV temporada) {
        lvCapitulos.getItems().clear();
        lvCast.getItems().clear();
        lbSipnosis.setText("");
        lvCapitulos.getItems().addAll(temporada.getEpisodes());
    }

    private void addCast(CreditosEpisodio ce) {
        lvCast.getItems().clear();
        lvCast.getItems().addAll(ce.getCast());
        lvCast.getItems().addAll(ce.getGuestStars());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }
}
