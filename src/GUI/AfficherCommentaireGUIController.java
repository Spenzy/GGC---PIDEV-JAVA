/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Commentaire;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import services.CommentaireCRUD;

/**
 * FXML Controller class
 *
 * @author Spenz
 */
public class AfficherCommentaireGUIController implements Initializable {

    String username = "Zied Dridi"; //sera un parametre passé en init
    Commentaire c;
    CommentaireCRUD cc = new CommentaireCRUD();

    @FXML
    private Label lblClient;
    @FXML
    private TextArea taCommentaire;
    
    public AfficherCommentaireGUIController() {
    }

    public AfficherCommentaireGUIController(Commentaire c) {
        this.c = c;
    }

    
    
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblClient.setText(username);
        taCommentaire.setText(c.getDescription());
        taCommentaire.setEditable(false);

    }

}
