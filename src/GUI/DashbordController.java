/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import GUI.Forum.ForumHomeGUIController;

/**
 * FXML Controller class
 *
 * @author Spenz
 */
public class DashbordController implements Initializable {
    
    int idClient;
    
    @FXML
    private Button btnHome;
    @FXML
    private Button btnForum;
    @FXML
    private Button btnShop;
    @FXML
    private Button btnStreamer;
    @FXML
    private Button btnEvennement;
    @FXML
    private Button btnSetting;
    @FXML
    private AnchorPane homeClient;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnForum.setOnAction(a -> {
            try {
                FXMLLoader forumPage = new FXMLLoader(getClass().getResource("Forum/ForumHomeGUI.fxml"));
                ForumHomeGUIController fhc = new ForumHomeGUIController(idClient);
                forumPage.setController(fhc);
                Parent root = forumPage.load();
                homeClient.getChildren().clear();
                homeClient.getChildren().add(root);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }    
    
}
