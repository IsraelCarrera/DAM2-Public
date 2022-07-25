package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

@Log4j2
public class MainFXWithDI {

    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream("/fxml/principalPantalla.fxml"));
            stage.setScene(new Scene(fxmlParent));
            stage.setTitle("Pelis y series API");
            stage.show();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
