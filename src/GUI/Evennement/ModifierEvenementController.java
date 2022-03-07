/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Evennement;

import entities.Evenement;
import services.EvenementCrud;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Azer Lahmer
 */
public class ModifierEvenementController implements Initializable {

    @FXML
    private TextField tfReference;

    @FXML
    private DatePicker tfDateDebut;
    @FXML
    private DatePicker tfDateFin;

    /**
     * Initializes the controller class.
     */
    String[] words = {"manzah1", "manzah2", "manzah3", "manzah4", "manzah5", "manzah6", "manzah7", "manzah8", "manzah9", "nasr", "tunis", "monastir", "mahdia", "sousse", "sfax", "mednine", " selyenna", "beja", "benzart", "gammarth", "marssa", "tabarka", "aindrahem", "jandouba"
    };

    int reference = 1;
    EvenementCrud ec = new EvenementCrud();
    @FXML
    private TextField tfLocalisation;
    @FXML
    private TextField tfNbrParticipants;
    @FXML
    private TextField tfDescription;
    @FXML
    private Button btnModifier;

    Evenement e;
    @FXML
    private TextField tfTitre;

//    public void setEvenement(Evenement e) {
//        this.e = e;
//    } 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TextFields.bindAutoCompletion(tfLocalisation, words);
        e = ec.afficherEvenement(reference);
        tfReference.setText(e.getReference() + "");
        tfReference.setEditable(false);
        tfReference.setDisable(true);

        tfDateDebut.setValue(e.getDateDebut().toLocalDate());
        tfDateFin.setValue(e.getDateFin().toLocalDate());

        tfLocalisation.setText(e.getLocalisation());
        tfDescription.setText(e.getDescription());
        tfNbrParticipants.setText(e.getNbrParticipant() + "");
        tfTitre.setText(e.getTitre());

    }

    @FXML
    private void modifierEvenement(ActionEvent event) {
        if (tfReference.getText().isEmpty() || tfLocalisation.getText().isEmpty() || tfDescription.getText().isEmpty() || tfNbrParticipants.getText().isEmpty() || tfTitre.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText(" Champ vide!");
            alert.show();

        } else if (Integer.parseInt(tfNbrParticipants.getText()) < 1) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText(" nombre de participants doit etre superieur à 1");
            alert.show();

        } else if (tfDateDebut.getValue().isBefore(LocalDate.now()) || tfDateFin.getValue().isBefore(tfDateDebut.getValue())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Date deb et fin erronée");
            alert.show();
        } else {
            e.setTitre(tfTitre.getText());
            e.setDateDebut(java.sql.Date.valueOf(tfDateDebut.getValue()));
            e.setDateFin(java.sql.Date.valueOf(tfDateFin.getValue()));
            e.setLocalisation(tfLocalisation.getText());
            e.setDescription(tfDescription.getText());
            e.setNbrParticipant(Integer.parseInt(tfNbrParticipants.getText()));

            ec.modifierEvenement(e);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succesful");
            alert.setHeaderText(null);
            alert.setContentText(" Evenement modifié avec succéez!");
            alert.show();
            ((Stage) btnModifier.getScene().getWindow()).close();
        }
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

}
