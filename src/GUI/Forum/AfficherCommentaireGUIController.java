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
public class AfficherCommentaireGUIController implements Initializable {

    int idClient;
    Commentaire c;
    CommentaireCRUD cc = new CommentaireCRUD();

    @FXML
    private Label lblClient;
    @FXML
    private TextArea taCommentaire;
    @FXML
    private Button btnSupp;
    @FXML
    private Button btnMod;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Button btnApp;
    @FXML
    private Label errDesc;
    @FXML
    private Label lblDate;

    public AfficherCommentaireGUIController() {
    }

    public AfficherCommentaireGUIController(Commentaire c, int idClient) {
        this.c = c;
        this.idClient = idClient;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initAffichage();

        btnSupp.setOnAction((ActionEvent a) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Supprimer?");
            alert.setHeaderText("Supprimer votre commentaire?");
            alert.setContentText("Votre commentaire sera supprimée lors de l'acceptation");

            Optional<ButtonType> option = alert.showAndWait();
            //confirmation d'ajout
            if (option.get() == ButtonType.OK) {
                cc.supprimerCommentaire(c.getId_commentaire());
                //message de test
                System.out.println("Commentaire supprimée avec succée!");

                //refresh
                AfficherPublicationGUIController apc = new AfficherPublicationGUIController(c.getIdClient(), c.getId_publication());
                DashboardController.refreshParent(apc.refreshPublication());
            } else {
                System.out.println("Erreur de confirmation!");
            }
        });

        btnMod.setOnAction(a -> {
            initModification();
        });

        btnApp.setOnAction((ActionEvent a) -> {
            errDesc.setText("");
            //test si l'un des champs est vide
            if (taCommentaire.getText().isEmpty()) {
                errDesc.setText("Erreur, Champ vide!");
            } else {
                errDesc.setText("");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Modifier?");
                alert.setHeaderText("Modifier votre commentaire?");
                alert.setContentText("Acceptez pour passer votre modification");

                Optional<ButtonType> option = alert.showAndWait();
                //confirmation d'ajout
                if (option.get() == ButtonType.OK) {
                    c.setDescription(taCommentaire.getText());
                    cc.modifierCommentaire(c);
                    //message de test
                    System.out.println("Commentaire modifiée avec succée!");
                    //refresh
                    AfficherPublicationGUIController apc = new AfficherPublicationGUIController(c.getIdClient(), c.getId_publication());
                    DashboardController.refreshParent(apc.refreshPublication());
                } else {
                    System.out.println("Erreur de confirmation!");
                }
            }
        });

        btnAnnuler.setOnAction((ActionEvent a) -> {
            initAffichage();
        });
    }

    public void initAffichage() {
        btnApp.setVisible(false);
        btnAnnuler.setVisible(false);
        btnSupp.setVisible(false);
        btnMod.setVisible(false);
        if (verifOwner()) {
            btnSupp.setVisible(true);
            btnMod.setVisible(true);
        }
        PersonneCRUD pc = new PersonneCRUD();
        lblDate.setText(c.getDatePost().toString());
        lblClient.setText(pc.getUsername(c.getIdClient()));
        taCommentaire.setText(c.getDescription());
        taCommentaire.setEditable(false);
    }

    public void initModification() {
        btnAnnuler.setVisible(true);
        btnSupp.setVisible(false);
        btnMod.setVisible(false);
        btnApp.setVisible(true);
        taCommentaire.setEditable(true);
    }

    public boolean verifOwner() {
        return idClient == c.getIdClient();
    }

}
