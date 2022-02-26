/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Publication;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CommentaireCRUD;
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initPublication();

        btnAfficher.setOnAction((ActionEvent a) -> {
            AfficherPublicationGUIController apc = new AfficherPublicationGUIController(idClient, p.getId_publication());
            apc.refreshPublication(btnAfficher);
        });
        
        btnArchiver.setOnAction((ActionEvent a) -> {
            pc.archiver(p);
            ForumHomeGUIController fhc = new ForumHomeGUIController(idClient);
            ((Stage) btnArchiver.getScene().getWindow()).setScene(fhc.refreshForum(idClient));
        });
        
        btnModifier.setOnAction(a -> {
            ModifierPublicationGUIController mpc = new ModifierPublicationGUIController(idClient, p.getId_publication());
            ((Stage) btnModifier.getScene().getWindow()).setScene(mpc.refreshPublier(btnModifier));
        });
    }

    public void initPublication() {
        System.out.println(p);
        lblNbrVotes.setText(vc.calculNbrVote(p.getId_publication()) + "");
        tfTitre.setText(p.getTitre());
        lblDate.setText(p.getDatePub().toString());
        lblNbrCommentaire.setText(cc.calculNbrCommentaire(p.getId_publication())+"");
    }

}
