package controllers;

import dao.modelos.Pelicula;
import dao.modelos.PeliculaSacadaDeBuscar;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import servicios.PeliculasServicios;
import utils.Constantes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PelisColeccionesPantalla implements Initializable {
    @FXML
    private ListView<PeliculaSacadaDeBuscar> lvPeliBuscador;
    @FXML
    private TextField tfIdPelicula;
    @FXML
    private ListView<Pelicula> lvPelicula;
    @FXML
    private Button btnVerColecciones;
    @FXML
    private ListView<Pelicula> lvColecciones;

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
    private void btnCargarPeliSelec() {
        PeliculasServicios ps = new PeliculasServicios();
        Alert alerta;
        if (lvPeliBuscador.getSelectionModel().getSelectedItem() != null) {
            int id = lvPeliBuscador.getSelectionModel().getSelectedItem().getId();
            Either<String, Pelicula> resultado = ps.peliculaPorId(id);
            if(resultado.isRight()) {
                limpiar();
                lvPelicula.getItems().add(resultado.get());
                if (resultado.get().getBelongsToCollection() != null) {
                    btnVerColecciones.setVisible(true);
                    lvColecciones.setVisible(true);
                } else {
                    btnVerColecciones.setVisible(false);
                    lvColecciones.setVisible(false);
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
    private void mostrarColeciones() {
        PeliculasServicios ps = new PeliculasServicios();
        Either<String,List<Pelicula>> resultado = ps.coleccionPelis(lvPelicula.getItems().get(0).getBelongsToCollection().getId());
        if(resultado.isRight()) {
            lvColecciones.getItems().clear();
            lvColecciones.getItems().addAll(resultado.get());
        }

    }

    private void limpiar() {
        lvColecciones.getItems().clear();
        lvPelicula.getItems().clear();
        btnVerColecciones.setVisible(false);
        lvColecciones.setVisible(false);
    }

    @FXML
    private void btnLimpiar() {
        limpiar();
        tfIdPelicula.clear();
        lvPeliBuscador.getItems().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnVerColecciones.setVisible(false);
        lvColecciones.setVisible(false);
    }
}
