package controllers;

import dao.modelos.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import servicios.SeriesTvServicios;
import utils.Constantes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SeriesMasInformacionPantalla implements Initializable {
    @FXML
    private ListView<SerieSacadaDeBuscar> lvSerie;
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

    @FXML
    private void buscarSerie() {
        Alert alerta;
        SeriesTvServicios ss = new SeriesTvServicios();
        String serie = tfBuscarSerie.getText();
        if (!serie.isEmpty()) {
            List<SerieSacadaDeBuscar> listaSeries = ss.buscarSeries(serie);
            if (listaSeries != null) {
                limpiarAll();
                lvSerie.getItems().addAll(listaSeries);
            } else {
                alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText(Constantes.NO_EXISTE_SERIE);
                alerta.showAndWait();
            }
        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_ESCRITO_NADA);
            alerta.showAndWait();
        }
    }

    @FXML
    private void verTemporada() {
        Alert alerta;
        SeriesTvServicios ss = new SeriesTvServicios();
        if (lvSerie.getSelectionModel().getSelectedItem() != null) {
            int idSerie = lvSerie.getSelectionModel().getSelectedItem().getId();
            idSerieLocalizador = idSerie;
            SerieTv serie = ss.seriePorId(idSerie);
            lvTemporadas.getItems().clear();
            lvTemporadas.getItems().addAll(serie.getSeasons());

        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_SELECCIONADO);
            alerta.showAndWait();
        }
    }

    @FXML
    private void verCapitulos() {
        Alert alerta;
        SeriesTvServicios ss = new SeriesTvServicios();
        if (lvTemporadas.getSelectionModel().getSelectedItem() != null) {
            int numeroTemporada = lvTemporadas.getSelectionModel().getSelectedItem().getSeasonNumber();
            numTemporadaLocalizador = numeroTemporada;
            TemporadaTV temporada = ss.getTemporada(idSerieLocalizador, numeroTemporada);
            if (temporada != null) {
                lvCapitulos.getItems().clear();
                lvCast.getItems().clear();
                lbSipnosis.setText("");
                lvCapitulos.getItems().addAll(temporada.getEpisodes());
            } else {
                alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText(Constantes.NO_TEMPORADA_EXISTENTE);
                alerta.showAndWait();
            }
        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_TEMPORADA_SELECCIONADA);
            alerta.showAndWait();
        }
    }

    @FXML
    private void verCast() {
        Alert alerta;
        SeriesTvServicios ss = new SeriesTvServicios();
        if (lvCapitulos.getSelectionModel().getSelectedItem() != null) {
            int numeroCapitulo = lvCapitulos.getSelectionModel().getSelectedItem().getEpisodeNumber();
            CreditosEpisodio episodio = ss.getEpisodio(idSerieLocalizador, numTemporadaLocalizador, numeroCapitulo);
            if (episodio != null) {
                lvCast.getItems().clear();
                lvCast.getItems().addAll(episodio.getCast());
                lvCast.getItems().addAll(episodio.getGuestStars());
            } else {
                alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText(Constantes.ERROR_LECTURA_EPISODIO);
                alerta.showAndWait();
            }
        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_EPISODIO_SELECCIONADO);
            alerta.showAndWait();
        }
    }

    @FXML
    private void ponerSipnosis() {
        Alert alerta;
        if (lvCapitulos.getSelectionModel().getSelectedItem() != null) {
            if (lvCapitulos.getSelectionModel().getSelectedItem().getOverview() != null) {
                lbSipnosis.setText(lvCapitulos.getSelectionModel().getSelectedItem().getOverview());
            } else {
                alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText(Constantes.EPISODIO_SIN_INFORMACION);
                alerta.showAndWait();
            }
        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_EPISODIO_SELECCIONADO);
            alerta.showAndWait();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }
}
