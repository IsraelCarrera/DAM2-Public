package controllers;

import dao.modelos.*;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import servicios.PeliculasServicios;
import utils.Constantes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BuscarPeliculaPantalla implements Initializable {
    @FXML
    private ListView<PeliculaSacadaDeBuscar> lvPeliBuscador;
    @FXML
    private Label lbFechaLanzamiento;
    @FXML
    private Hyperlink hlHomepage;
    @FXML
    private ImageView ivPoster;
    @FXML
    private ListView<Persona> lvCast;
    @FXML
    private Label lbNombrePeli;
    @FXML
    private Label lbNombreOriginal;
    @FXML
    private Label lbLenguajeOriginal;
    @FXML
    private Label lbSipnosis;
    @FXML
    private ListView<GenresItem> lvGeneros;
    @FXML
    private ListView<Compania> lvProducido;
    @FXML
    private TextField tfIdPelicula;

    @FXML
    private void buscarPelicula() {
        Alert alerta;
        String name = tfIdPelicula.getText();
        if (!name.isEmpty()) {
            PeliculasServicios ps = new PeliculasServicios();
            Either <String,List<PeliculaSacadaDeBuscar>> peliculas = ps.buscarPelicula(name);
            if (peliculas.isRight()) {
                if (!peliculas.get().isEmpty()) {
                    lvPeliBuscador.getItems().clear();
                    lvPeliBuscador.getItems().addAll(peliculas.get());
                }else{
                    alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setContentText(Constantes.NO_EXISTE_PELI);
                    alerta.showAndWait();
                }
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(peliculas.getLeft());
                alerta.showAndWait();
            }
        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_ESCRITO_NADA);
            alerta.showAndWait();
        }
    }

    @FXML
    private void btnCargarPeli() {
        PeliculasServicios ps = new PeliculasServicios();
        Alert alerta;
        if (lvPeliBuscador.getSelectionModel().getSelectedItem() != null) {
            int id = lvPeliBuscador.getSelectionModel().getSelectedItem().getId();
            Either<String, Pelicula> resultado = ps.peliculaPorId(id);
            if(resultado.isRight()) {
                Either <String,CastPelicula> cast = ps.castPeli(id);
                limpiar();
                lbNombrePeli.setText(resultado.get().getTitle());
                lbSipnosis.setText(resultado.get().getOverview());
                lbNombreOriginal.setText(resultado.get().getOriginalTitle());
                lbLenguajeOriginal.setText(resultado.get().getOriginalLanguage());
                lbFechaLanzamiento.setText(String.valueOf(resultado.get().getReleaseDate()));
                hlHomepage.setText(resultado.get().getHomepage());
                lvGeneros.getItems().addAll(resultado.get().getGenres());
                lvProducido.getItems().addAll(resultado.get().getProductionCompanies());
                Image im = new Image(Constantes.URL_FOTOS + resultado.get().getPosterPath());
                ivPoster.setImage(im);
                if (cast.isRight()) {
                    lvCast.getItems().addAll(cast.get().getCast());
                }
            }else{
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(resultado.getLeft());
                alerta.showAndWait();
            }
        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_SELECCIONADO);
            alerta.showAndWait();
        }
    }

    @FXML
    private void btnLimpiar() {
        lvPeliBuscador.getItems().clear();
        tfIdPelicula.clear();
        limpiar();
    }

    private void limpiar() {
        lbNombrePeli.setText("");
        lbSipnosis.setText("");
        lbNombreOriginal.setText("");
        lbLenguajeOriginal.setText("");
        lbFechaLanzamiento.setText("");
        hlHomepage.setText("");
        lvGeneros.getItems().clear();
        lvProducido.getItems().clear();
        lvCast.getItems().clear();
        ivPoster.setImage(null);
    }

    @FXML
    private void irPagWeb() {
        //No sé como abrir navegador externo.
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }

}
