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
import servicios.PeliculasServicios;
import utils.Constantes;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BuscarPeliculaPantalla implements Initializable {
    //FXML
    @FXML
    private ListView<Pelicula> lvPeliBuscador;
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
    private ListView<Genero> lvGeneros;
    @FXML
    private ListView<Compania> lvProducido;
    @FXML
    private TextField tfIdPelicula;

    private PrincipalPantalla principalPantalla;

    public void setPrincipalPantalla(PrincipalPantalla principalPantalla) {
        this.principalPantalla = principalPantalla;
    }

    //A inyectar.
    private final PeliculasServicios ps;

    @Inject
    public BuscarPeliculaPantalla(PeliculasServicios ps) {
        this.ps = ps;
    }

    @FXML
    private void buscarPelicula() {
        String name = tfIdPelicula.getText();
        if (!name.isEmpty()) {
            var tarea = new Task<Either<String, List<Pelicula>>>() {
                @Override
                protected Either<String, List<Pelicula>> call() {
                    return ps.buscarPelicula(name);
                }
            };
            //Me obliga a meter el .get en un trycatch.
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::mostrarListaPeliculas)
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
    private void btnCargarPeli() {
        if (lvPeliBuscador.getSelectionModel().getSelectedItem() != null) {
            int id = lvPeliBuscador.getSelectionModel().getSelectedItem().getId();
            var tarea = new Task<Either<String, Pelicula>>() {
                @Override
                protected Either<String, Pelicula> call() {
                    return ps.peliculaPorId(id);
                }
            };
            //Ya que se hacen a la vez las mismas llamadas, no sé realmente como integrarlas.
            Either<String, CreditosPelicula> creditos = ps.castPeli(id);
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(peli -> mostrarPeli(peli, creditos.get()))
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

    private void mostrarListaPeliculas(List<Pelicula> peliculas) {
        lvPeliBuscador.getItems().clear();
        lvPeliBuscador.getItems().addAll(peliculas);
    }

    private void mostrarPeli(Pelicula resultado, CreditosPelicula cast) {
        limpiar();
        lbNombrePeli.setText(resultado.getTitle());
        lbSipnosis.setText(resultado.getOverview());
        lbNombreOriginal.setText(resultado.getOriginalTitle());
        lbLenguajeOriginal.setText(resultado.getOriginalLanguage());
        lbFechaLanzamiento.setText(String.valueOf(resultado.getReleaseDate()));
        hlHomepage.setText(resultado.getHomepage());
        lvGeneros.getItems().addAll(resultado.getGenres());
        lvProducido.getItems().addAll(resultado.getProductionCompanies());
        Image im = new Image(Constantes.URL_FOTOS + resultado.getPosterPath());
        ivPoster.setImage(im);
        if (!cast.getCast().isEmpty()) {
            lvCast.getItems().addAll(cast.getCast());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }

}
