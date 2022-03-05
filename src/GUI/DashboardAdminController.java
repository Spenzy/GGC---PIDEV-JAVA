/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.DashboardController.parentClient;
import static GUI.DashboardController.refreshParent;
import GUI.Forum.ForumHomeGUIController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class DashboardAdminController implements Initializable {

    @FXML
    private Button btnGestProduit;
    @FXML
    private Button btnGestCommande;
    @FXML
    private Button btnGestClient;
    @FXML
    private Button btnGestModerateur;
    @FXML
    private AnchorPane homeAdmin;
    
    public static AnchorPane parentAdmin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        parentAdmin = homeAdmin;
        btnGestProduit.setOnAction(a -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Shop/GestionProduit.fxml"));
                
                refreshParent(root);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });
        
        btnGestCommande.setOnAction(a -> {
            try {
                FXMLLoader forumPage = new FXMLLoader(getClass().getResource("Commande/AffecterLivraison.fxml"));
                Parent root = forumPage.load();
                
                refreshParent(root);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });
        
        btnGestClient.setOnAction(a -> {
            try {
                FXMLLoader forumPage = new FXMLLoader(getClass().getResource("Forum/ForumHomeGUI.fxml"));
                Parent root = forumPage.load();
                
                refreshParent(root);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });
                
        btnGestModerateur.setOnAction(a -> {
            try {
                FXMLLoader forumPage = new FXMLLoader(getClass().getResource("Forum/ForumHomeGUI.fxml"));
                Parent root = forumPage.load();
                
                refreshParent(root);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    public static void refreshParent(Parent root) {
        parentAdmin.getChildren().clear();
        parentAdmin.getChildren().add(root);
        Stage stg = ((Stage) parentAdmin.getScene().getWindow());
        stg.sizeToScene();
    }

}
