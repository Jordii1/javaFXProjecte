package jordi.sanchez.javafxprojecte;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pkgFitxers.Fitxers;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HelloController {
    //<editor-fold desc="Components FXML">

    //<editor-fold desc="Tab1">
    @FXML
    private Button BTGuarda;
    @FXML
    private TextField TFNom;
    @FXML
    private TextField TFPreu;
    @FXML
    private TextField TFDescripcio;
    @FXML
    private Label LBNom;
    @FXML
    private Label LBPreu;
    @FXML
    private Label LBData;
    @FXML
    private Label LBDescripcio;
    @FXML
    private DatePicker TFDataCaducitat;

    @FXML
    private TextField TFNomC;
    @FXML
    private TextField TFPreuC;
    @FXML
    private TextField TFDescripcioC;
    @FXML
    private Button BTCerca;
    @FXML
    private Label LBError;
    @FXML
    private Button BTTreure;
    @FXML
    private Label LBDataT;
    @FXML
    private Label LBDescripcioT;
    @FXML
    private Label LBPreuT;
    @FXML
    private Label LBNomT;
    @FXML
    private DatePicker TFDataCaducitatC;



    //</editor-fold>

    static Nevera p = new Nevera();
    static Fitxers f = new Fitxers();
    static List<Nevera> contingutFitxer;           // variable estàtica amb el contingut del fitxer
    static String dir = ".data";                     // no utilitzat el directori



    //    private void actualitzaPantalla() throws IOException, InterruptedException {
//        contingutFitxer = p.retornaLlistaPersones();
//        String text = "";
//
//        for (Nevera freez : contingutFitxer) {
//            text = text +
//                    freez.getNom() + " " + freez.getPreu() + "\n" +
//                    freez.getDescripcio() + " anys\n"
//                    + freez.getDataCaducitat() + " €" + "\n\n";
//        }
//
//        TAPantalla.setText(text);
//        BTGuarda.setText("Guarda");
//    }
    public void guardar() throws IOException, InterruptedException {

        LBError.setText("");

        // Ens assegurem que tots els camps estàn plens
        if (
                TFNom.getText().length() >= 1 &&
                        TFPreu.getText().length() >= 1 &&
                        TFDescripcio.getText().length() >= 1 &&
                        TFDataCaducitat.getValue().lengthOfMonth() >= 1) {

            // Agafem els camps dels TextFields
            String nom = TFNom.getText();
            int preu = Integer.parseInt(TFPreu.getText());
            String descripcio = TFDescripcio.getText();
            String dataCaducitat = String.valueOf(TFDataCaducitat.getValue().lengthOfMonth());

            // Construïm una persona amb aquests camps
            Nevera freez = new Nevera(nom, preu, descripcio, dataCaducitat);

            // Agafem el text del botó (per comprovar si guardem o modifiquem)
            String textBoto = BTGuarda.getText();

            // si volem modificar
            if (!textBoto.equals("Guarda")) {
                BTGuarda.setText("Guarda");
                freez.modificarPersona();
            } else {                    // si volem guardar
                freez.guardar();
                LBNom.setText(freez.getNom());
                LBPreu.setText(String.valueOf(freez.getPreu()));
                LBDescripcio.setText(freez.getDescripcio());
                LBData.setText(freez.getDataCaducitat());
            }
            netejaCamps();              // reiniciem els TextFields
            //actualitzaPantalla();       // recarreguem el fitxer al TextArea
        } else {
            // En cas que no hagim posat algun dels camps als textFields. Missatge de Warning avisant
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("ERROR");
            al.setContentText("Has d'omplir tots els camps");
            al.show();
        }
    }

    private void netejaCamps() {

        TFNom.setText("");
        TFPreu.setText("");
        TFDescripcio.setText("");
        TFDataCaducitat.getValue();

        TFNom.setDisable(false);

    }

//    public void initialize() throws IOException, InterruptedException {
//
//        // comprovem que existeix el fitxer i si és així
//        // llegim el fitxer
//        // formatem les dades de les persones de manera més amigable
//        if (f.existeix(p.getFitxer())) {
//            actualitzaPantalla();
//        }
//    }


    public void cerca() throws IOException, InterruptedException, ClassNotFoundException {
        LBError.setText("");
        String nom = TFNomC.getText();

        List<Nevera> llista = p.cercarPerNom(nom);

        if (llista.isEmpty()) {
            LBError.setText("Aquesta persona no existeix");
            netejaCamps();
        } else {            // agafem sols la primera persona de la llista (per fer-ho més fàcil)
            Nevera freez = llista.get(0);

            // canviem el text al botó
            BTGuarda.setText("Modifica");

            // afegim els camps TextField amb les dades de la persona
            LBNomT.setText(freez.getNom());
            LBPreuT.setText(String.valueOf(freez.getPreu()));
            LBDescripcioT.setText(freez.getDescripcio());
            LBDataT.setText(freez.getDataCaducitat());
            TFNomC.setText(freez.getNom());
            TFPreuC.setText(String.valueOf(freez.getPreu()));
            TFDescripcioC.setText(freez.getDescripcio());
            TFDataCaducitatC.getValue();

            TFNom.setDisable(true);
        }
    }

    public void eliminar() throws IOException, InterruptedException, ClassNotFoundException {
        LBError.setText("");
        String nom = TFNomC.getText();

        List<Nevera> llista = p.cercarPerNom(nom);

        if (llista.isEmpty()) {
            LBError.setText("Aquesta persona no existeix");
            netejaCamps();
        } else {
            p.eliminaPersona(nom);
            netejaCamps();
            LBError.setText("Aquesta persona ha sigut eliminada");
        }
    }

    public void start(Stage primaryStage) {
        DatePicker datePicker = new DatePicker();
        datePicker.getStylesheets().add(getClass().getResource("label.css").toExternalForm());

        VBox root = new VBox(datePicker);
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }



//    public void initialize() {
//
//        // Càrrega del fitxer CSS
//        String cssFile = Objects.requireNonNull(getClass().getResource("label.css")).toExternalForm();
//        Scene scene = TFDataCaducitat.getScene();
//        scene.getStylesheets().add(cssFile);
//
//    }

//    public void eliminaPersona() throws IOException, InterruptedException {
//        LBError.setText("");
//        String nom = TFNom.getText();
//
//        List<Nevera> llista = p.cercarPerNom(nom);
//
//        if (llista.isEmpty()) {
//            LBError.setText("Aquesta persona no existeix");
//            netejaCamps();
//        } else {
//            p.eliminaPersona(nom);
//            netejaCamps();
//            actualitzaPantalla();
//        }
//    }
}