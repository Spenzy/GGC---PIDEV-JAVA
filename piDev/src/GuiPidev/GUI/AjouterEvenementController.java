/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiPidev.GUI;

import Entities.Evenement;
import Services.EvenementCrud;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;




/**
 * FXML Controller class
 *
 * @author Azer Lahmer
 */
public class AjouterEvenementController implements Initializable {

    @FXML
    private TextField tfReference;
    private TextField tfHeureDebut;
    @FXML
    private TextField tfLocalisation;
    @FXML
    private TextField tfNbrParticipants;
    private TextField tfHeureFin;
    @FXML
    private TextField tfDescription;
    @FXML
    private Button btnAjouter;
    @FXML
    private DatePicker tfDateDebut;
    @FXML
    private DatePicker tfDateFin;

    /**
     * Initializes the controller class.
     */
   

    
    EvenementCrud ec = new EvenementCrud();
    String[] words = {"manzah1"
    ,"manzah2","manzah3","manzah4","manzah5"
    ,"manzah6","manzah7","manzah8","manzah9"
    ,"nasr","tunis","monastir","mahdia"
    ,"sousse","sfax","mednine" ," selyenna"
    ,"beja","benzart","gammarth","marssa"
    ,"tabarka","aindrahem","jandouba"
    };
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        //String[] possibleWords = {"hai","hello"};
        TextFields.bindAutoCompletion(tfLocalisation,words);
        
    }    

    @FXML
    private void ajouterEvenement(ActionEvent event) {
       
         if (tfReference.getText().isEmpty() || tfLocalisation.getText().isEmpty()|| tfDescription.getText().isEmpty()|| tfNbrParticipants.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText(" Champ vide!");
            alert.show();
          
        }else {
        //Récuperer les données saisies dans le formulaire 
        int ref = Integer.parseInt(tfReference.getText());
        LocalDate dateDebut = tfDateDebut.getValue();
        LocalDate dateFin = tfDateFin.getValue();
        String local = tfLocalisation.getText();
        String desc = tfDescription.getText();
        int nbrPart = Integer.parseInt(tfNbrParticipants.getText());

       Evenement e = new Evenement( ref,java.sql.Date.valueOf(dateDebut),  java.sql.Date.valueOf(dateFin),  local,  desc,  nbrPart);
        EvenementCrud ec = new EvenementCrud();
        ec.ajouterEvenement(e);
       
        Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succesful");
            alert.setHeaderText(null);
            alert.setContentText(" Evenement ajoutée avec succéez!");
            alert.show();
         }
        ((Stage) btnAjouter.getScene().getWindow()).close();
       
    }

}
