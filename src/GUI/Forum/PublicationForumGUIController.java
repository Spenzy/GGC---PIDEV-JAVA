/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Forum;

import GUI.DashboardController;
import entities.Publication;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CommentaireCRUD;
import services.PersonneCRUD;
import services.PublicationCRUD;
import services.VoteCRUD;

/**
 * FXML Controller class
 *
 * @author Spenz
 */
public class PublicationForumGUIController implements Initializable {

    int idClient;
    Publication p;
    PublicationCRUD pc = new PublicationCRUD();
    CommentaireCRUD cc = new CommentaireCRUD();
    PersonneCRUD pcrud = new PersonneCRUD();
    VoteCRUD vc = new VoteCRUD();

    @FXML
    private Label lblNbrVotes;
    @FXML
    private TextField tfTitre;
    @FXML
    private Label lblNbrCommentaire;
    @FXML
    private Button btnAfficher;
    @FXML
    private Button btnArchiver;
    @FXML
    private Button btnModifier;
    @FXML
    private Label lblDate;

    public PublicationForumGUIController() {
    }

    public PublicationForumGUIController(Publication p, int idClient) {
        this.idClient = idClient;
        this.p = p;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initPublication();

        btnAfficher.setOnAction((ActionEvent a) -> {
            AfficherPublicationGUIController apc = new AfficherPublicationGUIController(idClient, p.getId_publication());
            DashboardController.refreshParent(apc.refreshPublication());
        });

        btnArchiver.setOnAction((ActionEvent a) -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Archiver?");
            alert.setHeaderText("Archiver cette publication?");
            alert.setContentText("Votre publication sera archivé lors de l'acceptation");

            Optional<ButtonType> option = alert.showAndWait();
            //confirmation d'archivage
            if (option.get() == ButtonType.OK) {
                pc.archiver(p);
                ForumHomeGUIController fhc = new ForumHomeGUIController(idClient);
                DashboardController.refreshParent(fhc.refreshForum());
            } else {
                System.out.println("Erreur de confirmation!");
            }

        });

        btnModifier.setOnAction((ActionEvent a) -> {
            ModifierPublicationGUIController mpc = new ModifierPublicationGUIController(idClient, p.getId_publication());
            DashboardController.refreshParent(mpc.refreshPublier());
        });
    }

    public void initPublication() {
        btnModifier.setVisible(false);
        btnArchiver.setVisible(false);
        if (isOwner(idClient) && !p.isArchive()) {
            btnModifier.setVisible(true);
        }
        if (pcrud.isAdmin(idClient)) {
            if (p.isArchive()) {
                btnArchiver.setText("Désarchiver");
            }
            btnArchiver.setVisible(true);
        }
        lblNbrVotes.setText(vc.calculNbrVote(p.getId_publication()) + "");
        tfTitre.setText(p.getTitre());
        lblDate.setText(p.getDatePub().toString());
        lblNbrCommentaire.setText(cc.calculNbrCommentaire(p.getId_publication()) + "");
    }

    public boolean isOwner(int idClient) {
        return p.getId_client() == idClient;
    }

}
