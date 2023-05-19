module jordi.sanchez.javafxprojecte {
    requires javafx.controls;
    requires javafx.fxml;
    requires Fitxers;


    opens jordi.sanchez.javafxprojecte to javafx.fxml;
    exports jordi.sanchez.javafxprojecte;
}