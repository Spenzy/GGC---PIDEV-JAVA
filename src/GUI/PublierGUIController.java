/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Publication;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.PublicationCRUD;

/**
 * FXML Controller class
 *
 * @author Spenz
 */
public class PublierGUIController implements Initializable {

    @FXML
    private AnchorPane ajoutPub;
    @FXML
    private Button btnPublier;
    @FXML
    private TextField tfTitre;
    @FXML
    private TextArea taDesc;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Label errTitre;
    @FXML
    private Label errDesc;

    int idClient = 1;//un id pour tester les fonctionalités de l'application
        
    PublicationCRUD pc = new PublicationCRUD();
    @FXML
    private Label errWindow;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Lors du click sur le bouton publier
        btnAnnuler.setOnAction((ActionEvent a) -> {
            Stage stage = (Stage) btnAnnuler.getScene().getWindow();
            stage.close();
        });
        
        //Lors du click sur le bouton publier
        btnPublier.setOnAction((ActionEvent a) ->{
            resetErrLabels();
            //test si l'un des champs est vide
            if (tfTitre.getText().isEmpty()) {
                errTitre.setText("Erreur, Champ vide!");
            }else if (pc.verifQuotaPub(idClient)) {
                errTitre.setText("Vous avez attein la limite de publications courantes!");
            }else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Publier?");
                alert.setHeaderText("Etes vous pret a publier?");
                alert.setContentText("Acceptez pour publier votre publication");

                Optional<ButtonType> option = alert.showAndWait();
                //tous les conséquences de l'alerte de confirmation
                if (option.get() == null) {
                   this.errWindow.setText("Pas de selection!");
                } else if (option.get() == ButtonType.CANCEL) {
                   this.errWindow.setText("Vous avez annulé la selection!");
                } else if (option.get() == ButtonType.OK) {
                    Publication p = new Publication(idClient ,tfTitre.getText(), taDesc.getText(), false);
                    pc.ajouterPublication(p);
                    //message de test
                    this.errWindow.setText("Poste ajouté avec succée!");
                    //on ferme l'interface d'ajout après un cas succéssive
                    Stage stage = (Stage) btnAnnuler.getScene().getWindow();
                    stage.close();
                } else {
                   this.errWindow.setText("-");
                }
            }
            
        });
    }    

    public void resetErrLabels(){
        errTitre.setText("");
        errDesc.setText("");
    }
    
}
