package org.example.encriptar.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.example.encriptar.dao.DBConnPool;
import org.example.encriptar.utils.Constantes;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

@Log4j2
public class MainFXWithDI {
    FXMLLoader fxmlLoader;
    DBConnPool dbConnPool;

    @Inject
    public MainFXWithDI(FXMLLoader fxmlLoader, DBConnPool dbConnPool) {
        this.fxmlLoader = fxmlLoader;
        this.dbConnPool = dbConnPool;
    }

    public void start(@Observes Stage stage) {
        try {
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream(Constantes.FXML_PRINCIPAL_FXML));
            stage.setScene(new Scene(fxmlParent));
            stage.setTitle(Constantes.ENCRIPTACIÓN);
            stage.show();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        stage.setOnCloseRequest(windowEvent -> {
            dbConnPool.closePool();
        });
    }
}
