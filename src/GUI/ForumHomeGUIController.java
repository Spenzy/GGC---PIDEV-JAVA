/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

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

        btnAjoutPub.setOnAction((ActionEvent a) -> {
            PublierGUIController ppc = new PublierGUIController(idClient);
            Scene newScene = ppc.refreshPublier(btnAjoutPub);
            ((Stage) btnAjoutPub.getScene().getWindow()).setScene(newScene);
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
                        Logger.getLogger(AfficherPublicationGUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
        System.out.println(vboxPubArch.getChildren());
        System.out.println(vboxPub.getChildren());
    }

    public Scene refreshForum(int idClient) {//temp jusqau vrai main
        Scene scene = new Scene(new Parent() {
        });
        try {
            //init FXML loader et controller
            FXMLLoader forumMain = new FXMLLoader(getClass().getResource("ForumHomeGUI.fxml"));
            ForumHomeGUIController fhc = new ForumHomeGUIController(idClient);
            forumMain.setController(fhc);
            Parent root = forumMain.load();
            //init du scene 
            scene = new Scene(root);
        } catch (IOException ex) {
            Logger.getLogger(ForumMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scene;
    }

}
