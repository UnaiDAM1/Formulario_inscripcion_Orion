package com.example.practicafx_ut4;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerDos {
    @FXML
    public ImageView imgFirma;

    @FXML
    public Button botonFirma;

    @FXML
    public TextField txtNombre;

    @FXML
    public TextField dni;

    @FXML
    public DatePicker nacimiento;

    @FXML
    public TextField direcc;

    @FXML
    public TextField codPos;

    @FXML
    public Label datosTutor;
    @FXML
    public Label nombreTutor;
    @FXML
    public Label fechaTutor;
    @FXML
    public Label direccionTutor;

    @FXML
    public MenuButton menuIdioma;

    @FXML
    public Button botonEnviar;

    ResourceBundle bundle = ResourceBundle.getBundle("EtiquetasBundle", new Locale("es", "ES"));

    @FXML
    public void initialize() {

        botonEnviar.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        botonEnviar.fire(); // Con .fire() se simula un clic
                    }
                });
            }
        });


        botonFirma.setOnAction(e -> cargarFoto(imgFirma));

        MenuItem es = new MenuItem("Español");
        MenuItem en = new MenuItem("Inglés");

        es.setOnAction(e -> menuIdioma.setText("Español"));
        en.setOnAction(e -> menuIdioma.setText("Inglés"));

        menuIdioma.getItems().addAll(es, en);
        es.setOnAction(e -> cambiarIdioma(new Locale("es", "ES"), "Español"));
        en.setOnAction(e -> cambiarIdioma(new Locale("en", "EN"), "Inglés"));
        actualizarIdioma();
    }

    public void actualizarIdioma() {
        datosTutor.setText(bundle.getString("label.datosTutor"));
        nombreTutor.setText(bundle.getString("label.nombre"));
        fechaTutor.setText(bundle.getString("label.fecha"));
        direccionTutor.setText(bundle.getString("label.direccion"));
        botonEnviar.setText(bundle.getString("button.botonEnviar"));
        botonFirma.setText(bundle.getString("button.botonFirma"));
    }

    public void cambiarIdioma(Locale locale, String idiomaMenuText) {
        bundle = ResourceBundle.getBundle("EtiquetasBundle", locale);
        menuIdioma.setText(idiomaMenuText);
        actualizarIdioma();
    }

    public void cargarFoto(ImageView img) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione una imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Tipos de archivo permitidos", "*.jpg", "*.png", "*.gif")
        );

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            img.setImage(image);
        }
    }
    @FXML
    public void hypervinculoInstagram(){
        try {
            URI uri = new URI("https://www.instagram.com/alh_orion/");
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
