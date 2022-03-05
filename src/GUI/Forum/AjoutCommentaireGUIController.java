/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Forum;

import GUI.DashboardController;
import entities.Commentaire;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import services.CommentaireCRUD;
import services.PersonneCRUD;

/**
 * FXML Controller class
 *
 * @author Spenz
 */
public class AjoutCommentaireGUIController implements Initializable {

    @FXML
    private Label lblClient;
    @FXML
    private TextArea taCommentaire;
    @FXML
    private Button btnCommenter;
    @FXML
    private Label errDesc;

    int idClient;
    int idPublication;
    CommentaireCRUD cc = new CommentaireCRUD();
    PersonneCRUD pcrud = new PersonneCRUD();
            
    public AjoutCommentaireGUIController() {
    }

    public AjoutCommentaireGUIController(int idClient, int idPublication) {
        this.idClient = idClient;
        this.idPublication = idPublication;
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblClient.setText(pcrud.getUsername(idClient));
        //Lors du click sur le bouton publier
        btnCommenter.setOnAction((ActionEvent a) -> {
            errDesc.setText("");
            //test si l'un des champs est vide
            if (taCommentaire.getText().isEmpty()) {
                errDesc.setText("Erreur, Champ vide!");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Commenter?");
                alert.setHeaderText("Passer votre commentaire?");
                alert.setContentText("Acceptez pour passer votre commentaire");

                Optional<ButtonType> option = alert.showAndWait();
                //confirmation d'ajout
                if (option.get() == ButtonType.OK) {
                    Commentaire c = new Commentaire(idPublication, idClient, taCommentaire.getText());
                    cc.ajouterCommentaire(c);
                    //message de test
                    System.out.println("Commentaire ajouté avec succée!");

                    //refresh
                    AfficherPublicationGUIController apc = new AfficherPublicationGUIController(idClient,idPublication);
                    DashboardController.refreshParent(apc.refreshPublication());
                } else {
                    System.out.println("Erreur de confirmation!");
                }
            }
        });
    }
    
}
