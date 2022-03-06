/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Forum;

import GUI.DashboardController;
import entities.Commentaire;
import entities.Publication;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.CommentaireCRUD;
import services.PersonneCRUD;
import services.PublicationCRUD;
import services.VoteCRUD;
import utils.PdfAPI;

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
    private Label lblDate;
    @FXML
    private Label lblUser;
    @FXML
    private VBox vboxComm;
    @FXML
    private Button btnForum;
    @FXML
    private Button btnSendPub;
    @FXML
    private Pane panePub;

    int idClient;
    int idPublication;

    PublicationCRUD pc = new PublicationCRUD();
    CommentaireCRUD cc = new CommentaireCRUD();
    PersonneCRUD pcrud = new PersonneCRUD();
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
        lblDate.setText(p.getDatePub().toString());
        lblUser.setText(pcrud.getUsername(p.getId_client()));
        //init commentaires
        List commentaires = cc.afficherCommentaires(idPublication);
        initCommentaires(commentaires);

        lblNbrVote.setText(vc.calculNbrVote(idPublication) + "");

        if (p.isArchive()) {
            panePub.setDisable(true);
            vboxComm.setDisable(true);
        }

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
        
        btnSendPub.setOnAction((ActionEvent a)->{
            PdfAPI.createAndSendForumPost(pcrud.afficherPersonne(idClient).getEmail(), p, commentaires);
        });

        btnForum.setOnAction((ActionEvent a) -> {
            ForumHomeGUIController fhc = new ForumHomeGUIController(idClient);
            DashboardController.refreshParent(fhc.refreshForum());
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

    public void initCommentaires(List commentaires) {
        commentaires.stream()
                .forEach((Object c) -> {
                    try {
                        System.out.println(c);
                        FXMLLoader cLoader = new FXMLLoader(getClass().getResource("AfficherCommentaireGUI.fxml"));
                        AfficherCommentaireGUIController controller = new AfficherCommentaireGUIController((Commentaire) c, idClient);
                        cLoader.setController(controller);
                        Parent cNode = cLoader.load();
                        vboxComm.getChildren().add(cNode);
                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                });
        commenter();
    }

    public void commenter() {
        try {
            FXMLLoader cLoader = new FXMLLoader(getClass().getResource("AjoutCommentaireGUI.fxml"));
            AjoutCommentaireGUIController controller = new AjoutCommentaireGUIController(idClient, idPublication);
            cLoader.setController(controller);
            Parent cNode = cLoader.load();
            HBox hb = new HBox(cNode);
            vboxComm.getChildren().add(hb);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public Parent refreshPublication() {
        try {
            //init loader root
            FXMLLoader testLoad = new FXMLLoader(getClass().getResource("AfficherPublicationGUI.fxml"));

            //init Controller
            AfficherPublicationGUIController controller = new AfficherPublicationGUIController(idClient, idPublication);
            testLoad.setController(controller);

            Parent root = testLoad.load();

            return root;

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
        return null;
    }

}
