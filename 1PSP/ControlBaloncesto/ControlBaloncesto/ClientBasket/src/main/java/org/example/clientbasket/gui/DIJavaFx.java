package org.example.clientbasket.gui;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.util.AnnotationLiteral;

public class DIJavaFx extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setResizable(true);
        container.getBeanManager().fireEvent(stage, new AnnotationLiteral<StartupScene>() {
        });

    }
}
