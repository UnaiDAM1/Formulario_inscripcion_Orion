module com.example.practicafx_ut4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.practicafx_ut4 to javafx.fxml;
    exports com.example.practicafx_ut4;
}