package controllers;

import dao.modelos.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import servicios.SeriesTvServicios;
import utils.Constantes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BuscarSeriesPantalla implements Initializable {

    @FXML
    private ListView<SerieSacadaDeBuscar> lvSeriesBuscadas;
    @FXML
    private ListView<GenresItem> lvGenero;
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

    @FXML
    private void buscarSerie() {
        Alert alerta;
        SeriesTvServicios ss = new SeriesTvServicios();
        String nombreSerie = tfNombreSerie.getText();
        if (!nombreSerie.isEmpty()) {
            List<SerieSacadaDeBuscar> series = ss.buscarSeries(nombreSerie);
            if (series != null) {
                btnLimpiar();
                lvSeriesBuscadas.getItems().addAll(series);
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
    private void mostrarSerie() {
        Alert alerta;
        SeriesTvServicios ss = new SeriesTvServicios();
        if (lvSeriesBuscadas.getSelectionModel().getSelectedItem() != null) {
            int id = lvSeriesBuscadas.getSelectionModel().getSelectedItem().getId();
            SerieTv serie = ss.seriePorId(id);
            limpiar();
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
        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_SELECCIONADO);
            alerta.showAndWait();
        }
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

    @FXML
    private void btnLimpiar() {
        tfNombreSerie.clear();
        lvSeriesBuscadas.getItems().clear();
        limpiar();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }
}
