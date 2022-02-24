/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Commentaire;
import entities.Publication;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.CommentaireCRUD;
import services.PublicationCRUD;
import services.VoteCRUD;

/**
 * FXML Controller class
 *
 * @author Spenz
 */
public class AfficherPublicationGUIController implements Initializable {

    @FXML
    private TextField tfTitre;
    @FXML
    private TextArea taDesc;
    @FXML
    private Label lblNbrVote;
    @FXML
    private ToggleButton tBtnUP;
    @FXML
    private ToggleButton tBtnDown;
    @FXML
    private VBox vboxComm;

    int idClient;//un id pour tester les fonctionalités de l'application
    int idPublication;// id pub de test

    PublicationCRUD pc = new PublicationCRUD();
    CommentaireCRUD cc = new CommentaireCRUD();
    VoteCRUD vc = new VoteCRUD();

    public AfficherPublicationGUIController() {
    }

    public AfficherPublicationGUIController(int idClient, int idPublication) {
        this.idClient = idClient;
        this.idPublication = idPublication;
    }
    
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Publication p = pc.afficherPublication(idPublication);
        //init des champs d'affichage et vote
        tfTitre.setText(p.getTitre());
        tfTitre.setEditable(false);//disable TF
        taDesc.setText(p.getDesc());
        taDesc.setEditable(false);//disable TA

        initCommentaires();

        lblNbrVote.setText(vc.calculNbrVote(idPublication) + "");

        if (vc.verifVote(idClient, idPublication)) {
            if (vc.afficherVote(idClient, idPublication).getType().equals("UP")) {
                tBtnUP.setSelected(true);
            } else {
                tBtnDown.setSelected(true);
            }
        } else {
            tBtnUP.setSelected(false);
            tBtnDown.setSelected(false);
        }

        //Action des boutons
        tBtnUP.setOnAction((ActionEvent a) -> {
            updateVote("UP");
        });

        tBtnDown.setOnAction((ActionEvent a) -> {
            updateVote("DOWN");
        });
    }

    public void updateVote(String type) {
        vc.voter(idClient, idPublication, type);
        lblNbrVote.setText(vc.calculNbrVote(idPublication) + "");
        if (vc.verifVote(idClient, idPublication)) {
            if (vc.afficherVote(idClient, idPublication).getType().equals("UP")) {
                tBtnUP.setSelected(true);
                tBtnDown.setSelected(false);
            } else {
                tBtnUP.setSelected(false);
                tBtnDown.setSelected(true);
            }
        } else {
            tBtnUP.setSelected(false);
            tBtnDown.setSelected(false);
        }
    }

    public void initCommentaires() {
        List commentaires = cc.afficherCommentaires(idPublication);
        ArrayList<Parent> panes = new ArrayList<>();
        int i = 0;
        commentaires.stream()
                .forEach((Object c) -> {
                    try {
                        FXMLLoader cLoader = new FXMLLoader(getClass().getResource("AfficherCommentaireGUI.fxml"));
                        AfficherCommentaireGUIController controller = new AfficherCommentaireGUIController((Commentaire) c);
                        cLoader.setController(controller);
                        Parent cNode = cLoader.load();
                        vboxComm.getChildren().add(cNode);
                    } catch (IOException ex) {
                        Logger.getLogger(AfficherPublicationGUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
        vboxComm.getChildren().addAll(panes);
        commenter();
    }

    public void commenter(){
        try {
            FXMLLoader cLoader = new FXMLLoader(getClass().getResource("AjoutCommentaireGUI.fxml"));
//            FXMLLoader parentLoader = (FXMLLoader) vboxComm.getScene().getUserData();
//            System.out.println(parentLoader);
//            AfficherPublicationGUIController parentController = parentLoader.<AfficherPublicationGUIController>getController();
//            System.out.println("hay");
            AjoutCommentaireGUIController controller = new AjoutCommentaireGUIController(idClient, idPublication);
            cLoader.setController(controller);
            Parent cNode = cLoader.load();
            HBox hb = new HBox(cNode);
            vboxComm.getChildren().add(hb);
        } catch (IOException ex) {
            Logger.getLogger(AfficherPublicationGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
