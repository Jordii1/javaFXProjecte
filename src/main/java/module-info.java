module jordi.sanchez.javafxprojecte {
    requires javafx.controls;
    requires javafx.fxml;


    opens jordi.sanchez.javafxprojecte to javafx.fxml;
    exports jordi.sanchez.javafxprojecte;
}