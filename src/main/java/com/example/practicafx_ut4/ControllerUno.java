package com.example.practicafx_ut4;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerUno {

    @FXML
    public ToggleGroup botonesAlergia;

    @FXML
    public RadioButton radioButtonSi;

    @FXML
    public RadioButton radioButtonNo;

    @FXML
    public Button botonFirma;

    @FXML
    public ImageView imgFirma;

    @FXML
    public ImageView imgFoto;

    @FXML
    public Button botonFoto;

    @FXML
    public Label datosHijo;
    @FXML
    public Label nombreHijo;
    @FXML
    public Label fotoHijo;
    @FXML
    public Label fechaHijo;
    @FXML
    public Label direccionHijo;
    @FXML
    public Label alergia;
    @FXML
    public Label nombreAlergia;

    @FXML
    public MenuButton menuIdioma;

    ResourceBundle bundle = ResourceBundle.getBundle("EtiquetasBundle", new Locale("es","ES"));

    @FXML
    public void initialize() {
        botonFirma.setOnAction(e -> cargarFoto(imgFirma));
        botonFoto.setOnAction(e -> cargarFoto(imgFoto));

        MenuItem es = new MenuItem("Español");
        MenuItem en  = new MenuItem("Inglés");

        es.setOnAction(e -> menuIdioma.setText("Español"));
        en.setOnAction(e -> menuIdioma.setText("Inglés"));

        menuIdioma.getItems().addAll(es, en);
        es.setOnAction(e -> cambiarIdioma(new Locale("es", "ES"), "Español"));
        en.setOnAction(e -> cambiarIdioma(new Locale("en", "EN"), "Inglés"));
        actualizarIdioma();

    }

    public void actualizarIdioma(){
        datosHijo.setText(bundle.getString("label.datosHijo"));
        nombreHijo.setText(bundle.getString("label.nombreHijo"));
        fotoHijo.setText(bundle.getString("label.fotoHijo"));
        fechaHijo.setText(bundle.getString("label.fechaHijo"));
        direccionHijo.setText(bundle.getString("label.direccionHijo"));
        alergia.setText(bundle.getString("label.alergia"));
        nombreAlergia.setText(bundle.getString("label.nombreAlergia"));
        radioButtonSi.setText(bundle.getString("radioButton.radioButtonSi"));
        radioButtonNo.setText(bundle.getString("radioButton.radioButtonNo"));
    }

    public void cambiarIdioma(Locale locale, String idiomaMenuText) {
        bundle = ResourceBundle.getBundle("EtiquetasBundle", locale);
        menuIdioma.setText(idiomaMenuText);
        actualizarIdioma();
    }

    private void cargarFoto(ImageView img) {
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
}