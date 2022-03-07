/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Forum;

import GUI.DashboardController;
import entities.Publication;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.PersonneCRUD;
import services.PublicationCRUD;

/**
 * FXML Controller class
 *
 * @author Spenz
 */
public class ForumHomeGUIController implements Initializable {

    int idClient;
    PublicationCRUD pc = new PublicationCRUD();
    PersonneCRUD pcrud = new PersonneCRUD();

    @FXML
    private Button btnAjoutPub;
    @FXML
    private VBox vboxPub;
    @FXML
    private VBox vboxPubArch;
    @FXML
    private Button btnStat;

    public ForumHomeGUIController() {
    }

    public ForumHomeGUIController(int idClient) {
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

        initPublications();
        
        btnStat.setOnAction(a->{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("StatistiqueForum.fxml"));
                Stage stg = new Stage();
                stg.setScene(new Scene(root));
                stg.show();
            } catch (IOException ex) {
                Logger.getLogger(ForumHomeGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnAjoutPub.setOnAction((ActionEvent a) -> {
            PublierGUIController ppc = new PublierGUIController(idClient);
            DashboardController.refreshParent(ppc.refreshPublier(btnAjoutPub));
            
        });
    }

    public void initPublications() {
        ArrayList publications = pc.afficherPublication();
        publications.stream()
                .forEach(p -> {
                    try {
                        FXMLLoader cLoader = new FXMLLoader(getClass().getResource("PublicationForumGUI.fxml"));
                        PublicationForumGUIController controller = new PublicationForumGUIController((Publication) p, idClient);
                        cLoader.setController(controller);
                        Parent cNode = cLoader.load();
                        if (((Publication) p).isArchive()) {
                            vboxPubArch.getChildren().add(cNode);
                        } else {
                            vboxPub.getChildren().add(cNode);
                        }

                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                });
    }

    public Parent refreshForum() {//temp jusqau vrai main
        try {
            //init FXML loader et controller
            FXMLLoader forumMain = new FXMLLoader(getClass().getResource("ForumHomeGUI.fxml"));
            ForumHomeGUIController fhc = new ForumHomeGUIController(idClient);
            forumMain.setController(fhc);
            Parent root = forumMain.load();
            return root;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

}
