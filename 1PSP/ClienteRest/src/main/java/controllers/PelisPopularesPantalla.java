package controllers;

import dao.modelos.Pelicula;
import dao.modelos.PeliculasPopulares;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import servicios.PeliculasServicios;
import utils.Constantes;

import java.net.URL;
import java.util.ResourceBundle;

public class PelisPopularesPantalla implements Initializable {

    @FXML
    private ComboBox<Integer> cbSelectorPag;
    @FXML
    private ListView<Pelicula> lvPeliculas;

    @FXML
    private void btCargar() {
        PeliculasServicios ps = new PeliculasServicios();
        int paginaSelec = cbSelectorPag.getSelectionModel().getSelectedItem();
        Either<String, PeliculasPopulares> resultado = ps.peliculasPopulares(paginaSelec);
        if(resultado.isRight()) {
            lvPeliculas.getItems().clear();
            lvPeliculas.getItems().addAll(resultado.get().getResults());
        }else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(resultado.getLeft());
            alerta.showAndWait();
        }
    }

    @FXML
    private void btnLimpiar() {
        lvPeliculas.getItems().clear();
        cbSelectorPag.getSelectionModel().clearSelection();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 1; i <= 10; i++) {
            cbSelectorPag.getItems().add(i);
        }
    }

}
