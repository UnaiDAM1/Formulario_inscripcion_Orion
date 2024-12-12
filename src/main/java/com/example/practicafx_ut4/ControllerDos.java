package com.example.practicafx_ut4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    // Inicialización del bundle en el que pondremos como lengua por defecto el español
    ResourceBundle bundle = ResourceBundle.getBundle("EtiquetasBundle", new Locale("es", "ES"));

    @FXML
    public void initialize() {

        // Con este metodo hacemos que por defecto al darle a la tecla intro se pulse el boton Enviar
        botonEnviar.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        botonEnviar.fire(); // Con .fire() se simula un clic
                    }
                });
            }
        });


        // Con este metodo al darle al boton para añadir una foto se cargue la imagen elegida en el imageView
        botonFirma.setOnAction(e -> cargarFoto(imgFirma));

        // Le damos los dos idiomas que hay dentro del menuButton
        MenuItem es = new MenuItem("Español");
        MenuItem en = new MenuItem("Inglés");

        // Hacemos que al elegir un idioma se traduzca la página al idioma elegido
        es.setOnAction(e -> menuIdioma.setText("Español"));
        en.setOnAction(e -> menuIdioma.setText("Inglés"));

        menuIdioma.getItems().addAll(es, en);
        es.setOnAction(e -> cambiarIdioma(new Locale("es", "ES"), "Español"));
        en.setOnAction(e -> cambiarIdioma(new Locale("en", "EN"), "Inglés"));
        actualizarIdioma();
    }

    // Metodo donde cambia los elementos de idioma en finción del bundle
    public void actualizarIdioma() {
        datosTutor.setText(bundle.getString("label.datosTutor"));
        nombreTutor.setText(bundle.getString("label.nombre"));
        fechaTutor.setText(bundle.getString("label.fecha"));
        direccionTutor.setText(bundle.getString("label.direccion"));
        botonEnviar.setText(bundle.getString("button.botonEnviar"));
        botonFirma.setText(bundle.getString("button.botonFirma"));
    }

    // Metodo para cambiar el texto del manuButton
    public void cambiarIdioma(Locale locale, String idiomaMenuText) {
        bundle = ResourceBundle.getBundle("EtiquetasBundle", locale);
        menuIdioma.setText(idiomaMenuText);
        actualizarIdioma();
    }

    // Metodo que muestra un fileChooser
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

    // Metodo que manda al usuario a una dirección
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

    // Metodo que valida si el formulario esta relleno y los datos estan metidos correctamente
    public void enviar() {

        if (txtNombre.getText().isEmpty() || dni.getText().isEmpty() || nacimiento.getValue() == null ||
                direcc.getText().isEmpty() || codPos.getText().isEmpty() || imgFirma.getImage() == null) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Campos incompletos");
            alerta.setHeaderText("Por favor, rellena todos los campos.");
            alerta.setContentText("Todos los campos son obligatorios para continuar.");
            alerta.show();
            return;
        }

        if (!validarDNI(dni.getText())){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("DNI incorrecto");
            alerta.setHeaderText("Porfavor introduzca un DNI o NIE valido.");
            alerta.setContentText("El DNI debe ser correcto para continuar.");
            alerta.show();
            return;
        }
        if (!validarNombre(txtNombre.getText())) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Nombre incorrecto");
            alerta.setHeaderText("Porfavor introduzca un Nombre valido");
            alerta.setContentText("El nombre debe empezar en mayúsculas.");
            alerta.show();
            return;
        }
        { try {
            FXMLLoader cargar = new FXMLLoader(getClass().getResource("/com/example/practicafx_ut4/PantallaDeCarga.fxml"));
            Parent root = cargar.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 500, 500));
            stage.showAndWait();

            Stage ventanaActual = (Stage) botonEnviar.getScene().getWindow();
            ventanaActual.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo cargar la nueva interfaz.");
            alerta.setContentText("Por favor, verifica los archivos del programa.");
            alerta.showAndWait();
        }
        }
    }

    // Metodo para validar el DNI introducido
    public boolean validarDNI(String dni) {
        String[] letras = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
        if (dni.length() != 9){
            return false;
        } else{
            if (dni.substring(0, 1).equals("X")){
                dni = "0" + dni.substring(1);
            } else if (dni.substring(0, 1).equals("Y")) {
                dni = "1" + dni.substring(1);
            } else if (dni.substring(0, 1).equals("Z")) {
                dni = "2" + dni.substring(1);
            }
            int numero = Integer.parseInt(dni.substring(0, 8));
            if (letras[numero%23].equals(dni.substring(8))){
                return true;
            } else {
                return false;
            }
        }
    }

    // Metodo para validar el nombre introducido
    public boolean validarNombre(String nombre){
        if (nombre != null && nombre.length() > 0 && Character.isUpperCase(nombre.charAt(0))) {
            return true;
        } else {
            return false;
        }
    }
}
