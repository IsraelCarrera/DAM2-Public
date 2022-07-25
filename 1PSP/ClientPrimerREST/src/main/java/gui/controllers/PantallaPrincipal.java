package gui.controllers;

import gui.controllers.empresa.AddEmpresa;
import gui.controllers.empresa.DeleteEmpresa;
import gui.controllers.empresa.UpdateEmpresa;
import gui.controllers.empresa.VerEmpresa;
import gui.controllers.trabajador.AddTrabajador;
import gui.controllers.trabajador.DeleteTrabajador;
import gui.controllers.trabajador.UpdateTrabajador;
import gui.controllers.trabajador.VerTrabajador;
import gui.controllers.verAll.PantallaVerTodo;
import gui.utils.Constantes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class PantallaPrincipal implements Initializable {
    @FXML
    private BorderPane principal;



    public BorderPane getPrincipal() {
        return principal;
    }

    //Pantallas
    private AnchorPane paneInicio;
    private AnchorPane paneVerTodo;
    private AnchorPane paneEmpresaAdd;
    private AnchorPane paneEmpresaDelete;
    private AnchorPane paneEmpresaUpdate;
    private AnchorPane paneEmpresaVer;
    private AnchorPane paneTrabajadorAdd;
    private AnchorPane paneTrabajadorDelete;
    private AnchorPane paneTrabajadorUpdate;
    private AnchorPane paneTrabajadorVer;

    //Loaders

    private final FXMLLoader fxmlLoaderInicio;
    private final FXMLLoader fxmlLoaderVerTodo;
    private final FXMLLoader fxmlLoaderEmpresaAdd;
    private final FXMLLoader fxmlLoaderEmpresaDelete;
    private final FXMLLoader fxmlLoaderEmpresaUpdate;
    private final FXMLLoader fxmlLoaderEmpresaVer;
    private final FXMLLoader fxmlLoaderTrabajadorAdd;
    private final FXMLLoader fxmlLoaderTrabajadorDelete;
    private final FXMLLoader fxmlLoaderTrabajadorUpdate;
    private final FXMLLoader fxmlLoaderTrabajadorVer;

    //Constructor con inyección

    @Inject
    public PantallaPrincipal(FXMLLoader fxmlLoaderInicio, FXMLLoader fxmlLoaderVerTodo, FXMLLoader fxmlLoaderEmpresaAdd,
                             FXMLLoader fxmlLoaderEmpresaDelete, FXMLLoader fxmlLoaderEmpresaUpdate, FXMLLoader fxmlLoaderEmpresaVer,
                             FXMLLoader fxmlLoaderTrabajadorAdd, FXMLLoader fxmlLoaderTrabajadorDelete, FXMLLoader fxmlLoaderTrabajadorUpdate,
                             FXMLLoader fxmlLoaderTrabajadorVer) {
        this.fxmlLoaderInicio = fxmlLoaderInicio;
        this.fxmlLoaderVerTodo = fxmlLoaderVerTodo;
        this.fxmlLoaderEmpresaAdd = fxmlLoaderEmpresaAdd;
        this.fxmlLoaderEmpresaDelete = fxmlLoaderEmpresaDelete;
        this.fxmlLoaderEmpresaUpdate = fxmlLoaderEmpresaUpdate;
        this.fxmlLoaderEmpresaVer = fxmlLoaderEmpresaVer;
        this.fxmlLoaderTrabajadorAdd = fxmlLoaderTrabajadorAdd;
        this.fxmlLoaderTrabajadorDelete = fxmlLoaderTrabajadorDelete;
        this.fxmlLoaderTrabajadorUpdate = fxmlLoaderTrabajadorUpdate;
        this.fxmlLoaderTrabajadorVer = fxmlLoaderTrabajadorVer;
    }

    private PantallaVerTodo verTodo;
    private AddEmpresa addEmpresa;
    private DeleteEmpresa deleteEmpresa;
    private UpdateEmpresa updateEmpresa;
    private VerEmpresa verEmpresa;
    private AddTrabajador addTrabajador;
    private DeleteTrabajador deleteTrabajador;
    private UpdateTrabajador updateTrabajador;
    private VerTrabajador verTrabajador;

    //Carga de pantallas.
    private void cargarInicio(){
        try{
            if(paneInicio==null){
                paneInicio = fxmlLoaderInicio.load(getClass().getResourceAsStream("/fxml/pantallaInicio.fxml"));
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }
    private void cargarPantallaVerTodo(){
        try{
            if(paneVerTodo==null){
                paneVerTodo = fxmlLoaderVerTodo.load(getClass().getResourceAsStream("/fxml/verAll/pantallaVerTodo.fxml"));
                verTodo = fxmlLoaderVerTodo.getController();
                verTodo.setPrincipalPantalla(this);
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }
    private void cargarEmpresaAdd(){
        try{
            if(paneEmpresaAdd==null){
                paneEmpresaAdd = fxmlLoaderEmpresaAdd.load(getClass().getResourceAsStream("/fxml/empresa/addEmpresa.fxml"));
                addEmpresa = fxmlLoaderEmpresaAdd.getController();
                addEmpresa.setPrincipalPantalla(this);
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }
    private void cargarEmpresaDelete(){
        try{
            if(paneEmpresaDelete==null){
                paneEmpresaDelete = fxmlLoaderEmpresaDelete.load(getClass().getResourceAsStream("/fxml/empresa/deleteEmpresa.fxml"));
                deleteEmpresa = fxmlLoaderEmpresaDelete.getController();
                deleteEmpresa.setPrincipalPantalla(this);
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }
    private void cargarEmpresaUpdate(){
        try{
            if(paneEmpresaUpdate==null){
                paneEmpresaUpdate = fxmlLoaderEmpresaUpdate.load(getClass().getResourceAsStream("/fxml/empresa/updateEmpresa.fxml"));
                updateEmpresa = fxmlLoaderEmpresaUpdate.getController();
                updateEmpresa.setPrincipalPantalla(this);
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }
    private void cargarEmpresaVer(){
        try{
            if(paneEmpresaVer==null){
                paneEmpresaVer = fxmlLoaderEmpresaVer.load(getClass().getResourceAsStream("/fxml/empresa/verEmpresa.fxml"));
                verEmpresa=fxmlLoaderEmpresaVer.getController();
                verEmpresa.setPrincipalPantalla(this);
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }
    private void cargarTrabajadorAdd(){
        try{
            if(paneTrabajadorAdd==null){
                paneTrabajadorAdd = fxmlLoaderTrabajadorAdd.load(getClass().getResourceAsStream("/fxml/trabajador/addTrabajador.fxml"));
                addTrabajador = fxmlLoaderTrabajadorAdd.getController();
                addTrabajador.setPrincipalPantalla(this);
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }
    private void cargarTrabajadorDelete(){
        try{
            if(paneTrabajadorDelete==null){
                paneTrabajadorDelete = fxmlLoaderTrabajadorDelete.load(getClass().getResourceAsStream("/fxml/trabajador/deleteTrabajador.fxml"));
                deleteTrabajador = fxmlLoaderTrabajadorDelete.getController();
                deleteTrabajador.setPrincipalPantalla(this);
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }
    private void cargarTrabajadorUpdate(){
        try{
            if(paneTrabajadorUpdate==null){
                paneTrabajadorUpdate = fxmlLoaderTrabajadorUpdate.load(getClass().getResourceAsStream("/fxml/trabajador/updateTrabajador.fxml"));
                updateTrabajador = fxmlLoaderTrabajadorUpdate.getController();
                updateTrabajador.setPrincipalPantalla(this);
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }
    private void cargarTrabajadorVer(){
        try{
            if(paneTrabajadorVer==null){
                paneTrabajadorVer = fxmlLoaderTrabajadorVer.load(getClass().getResourceAsStream("/fxml/trabajador/verTrabajador.fxml"));
                verTrabajador = fxmlLoaderTrabajadorVer.getController();
                verTrabajador.setPrincipalPantalla(this);
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }

    //Ahora los pases
    private void pasarInicio(){
        if(paneInicio != null){
            principal.setCenter(paneInicio);
        }else{
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        }
    }
    private void pasarVerTodo(){
        if(paneVerTodo != null){
            principal.setCenter(paneVerTodo);
        }else{
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        }
    }
    private void pasarAddEmpresa(){
        if(paneEmpresaAdd != null){
            principal.setCenter(paneEmpresaAdd);
        }else{
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        }
    }
    private void pasarDeleteEmpresa(){
        if(paneEmpresaDelete != null){
            principal.setCenter(paneEmpresaDelete);
        }else{
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        }
    }
    private void pasarUpdateEmpresa(){
        if(paneEmpresaUpdate != null){
            principal.setCenter(paneEmpresaUpdate);
        }else{
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        }
    }
    private void pasarVerEmpresa(){
        if(paneEmpresaVer != null){
            principal.setCenter(paneEmpresaVer);
        }else{
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        }
    }
    private void pasarAddTrabajador(){
        if(paneTrabajadorAdd != null){
            principal.setCenter(paneTrabajadorAdd);
        }else{
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        }
    }
    private void pasarDeleteTrabajador(){
        if(paneTrabajadorDelete != null){
            principal.setCenter(paneTrabajadorDelete);
        }else{
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        }
    }
    private void pasarUpdateTrabajador(){
        if(paneTrabajadorUpdate != null){
            principal.setCenter(paneTrabajadorUpdate);
        }else{
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        }
    }
    private void pasarVerTrabajador(){
        if(paneTrabajadorVer != null){
            principal.setCenter(paneTrabajadorVer);
        }else{
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        }
    }

    //Clicks para ir a las pantallas.
    @FXML
    private void irInicio() {
        pasarInicio();
    }

    @FXML
    private void irVerTodo() {
        verTodo.preloadEmpresas();
        pasarVerTodo();
    }

    @FXML
    private void irVerEmpresa() {
        pasarVerEmpresa();
    }

    @FXML
    private void irAddEmpresa() {
        pasarAddEmpresa();
    }

    @FXML
    private void irUpdateEmpresa() {
        pasarUpdateEmpresa();
    }

    @FXML
    private void irDeleteEmpresa() {
        deleteEmpresa.loadEmpresas();
        pasarDeleteEmpresa();
    }

    @FXML
    private void irVerTrabajador() {
        pasarVerTrabajador();
    }

    @FXML
    private void irAddTrabajador() {
        addTrabajador.loadEmpresas();
        pasarAddTrabajador();
    }

    @FXML
    private void irUpdateTrabajador() {
        pasarUpdateTrabajador();
    }

    @FXML
    private void irDeleteTrabajador() {
        deleteTrabajador.loadTrabajadores();
        pasarDeleteTrabajador();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarInicio();
        cargarPantallaVerTodo();
        cargarEmpresaAdd();
        cargarEmpresaDelete();
        cargarEmpresaUpdate();
        cargarEmpresaVer();
        cargarTrabajadorAdd();
        cargarTrabajadorDelete();
        cargarTrabajadorUpdate();
        cargarTrabajadorVer();

        pasarInicio();
    }

    public void mandarAlert(Alert.AlertType tipoAlerta, String aviso) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setContentText(aviso);
        alerta.showAndWait();
    }
}
