package com.example.practicafx_ut4;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PantallaDeCarga {

    @FXML
    private ProgressBar barraDeProgreso;

    public void initialize() {
        // Iniciamos la barra de progreso
        barraDeProgreso.setProgress(0);

        // contador para ir rellenando la barra
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(50), event -> {
                    double progress = barraDeProgreso.getProgress() + 0.01;
                    barraDeProgreso.setProgress(progress);
                })
        );

        timeline.setCycleCount(100); // tiempo en llenar la barra
        timeline.setOnFinished(event -> {
            // Usar Platform.runLater para mostrar el mensaje después de la animación
            Platform.runLater(this::mensajeCompletado);
        });

        timeline.play(); // Iniciar la animación
    }

    // Mensaje que se muestre al terminar el proceso
    private void mensajeCompletado() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inscripción completada");
        alert.setHeaderText(null);
        alert.setContentText("La inscripción se ha completado con éxito.");
        alert.showAndWait();
        Stage ventanaActual = (Stage)barraDeProgreso.getScene().getWindow();
        ventanaActual.close();
    }
}
