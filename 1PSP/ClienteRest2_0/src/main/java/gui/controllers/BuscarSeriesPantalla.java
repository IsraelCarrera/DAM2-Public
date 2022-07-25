package gui.controllers;

import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelos.*;
import servicios.SeriesTvServicios;
import utils.Constantes;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BuscarSeriesPantalla implements Initializable {

    //FXML
    @FXML
    private ListView<Serie> lvSeriesBuscadas;
    @FXML
    private ListView<Genero> lvGenero;
    @FXML
    private ListView<Persona> lvCreadoPor;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbTituloOriginal;
    @FXML
    private Label lbLenguajeOriginal;
    @FXML
    private Label lbNumTemporadas;
    @FXML
    private Label lbNumCapitulos;
    @FXML
    private ListView<Compania> lvCompania;
    @FXML
    private Label lbStatus;
    @FXML
    private Hyperlink hlLink;
    @FXML
    private Label lbSipnosis;
    @FXML
    private ImageView ivPoster;
    @FXML
    private ListView<Networks> lvEmitidoPor;
    @FXML
    private TextField tfNombreSerie;

    private PrincipalPantalla principalPantalla;

    public void setPrincipalPantalla(PrincipalPantalla principalPantalla) {
        this.principalPantalla = principalPantalla;
    }

    //Inyectar
    @Inject
    private SeriesTvServicios ss;


    @FXML
    private void buscarSerie() {
        String nombreSerie = tfNombreSerie.getText();
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
    private void mostrarSerie() {
        if (lvSeriesBuscadas.getSelectionModel().getSelectedItem() != null) {
            int id = lvSeriesBuscadas.getSelectionModel().getSelectedItem().getId();
            var tarea = new Task<Either<String, Serie>>() {
                @Override
                protected Either<String, Serie> call() {
                    return ss.seriePorId(id);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::imprimirSerie)
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
    private void btnLimpiar() {
        tfNombreSerie.clear();
        lvSeriesBuscadas.getItems().clear();
        limpiar();
    }

    private void mostrarListaSeries(List<Serie> series) {
        btnLimpiar();
        lvSeriesBuscadas.getItems().addAll(series);
    }

    private void imprimirSerie(Serie serie) {
        lbTitulo.setText(serie.getName());
        lbTituloOriginal.setText(serie.getOriginalName());
        lbLenguajeOriginal.setText(serie.getOriginalLanguage());
        lbNumCapitulos.setText(String.valueOf(serie.getNumberOfEpisodes()));
        lbNumTemporadas.setText(String.valueOf(serie.getNumberOfSeasons()));
        lbStatus.setText(serie.getStatus());
        lbSipnosis.setText(serie.getOverview());
        hlLink.setText(serie.getHomepage());
        lvCreadoPor.getItems().addAll(serie.getCreatedBy());
        lvGenero.getItems().addAll(serie.getGenres());
        lvCompania.getItems().addAll(serie.getProductionCompanies());
        lvEmitidoPor.getItems().addAll(serie.getNetworks());
        Image im = new Image(Constantes.URL_FOTOS + serie.getPosterPath());
        ivPoster.setImage(im);
    }

    private void limpiar() {
        lbTitulo.setText("");
        lbTituloOriginal.setText("");
        lbLenguajeOriginal.setText("");
        lbNumCapitulos.setText("");
        lbNumTemporadas.setText("");
        lbStatus.setText("");
        lbSipnosis.setText("");
        hlLink.setText("");
        lvCreadoPor.getItems().clear();
        lvGenero.getItems().clear();
        lvCompania.getItems().clear();
        lvEmitidoPor.getItems().clear();
        ivPoster.setImage(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }
}
