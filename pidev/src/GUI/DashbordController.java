/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class DashbordController implements Initializable {
 private Stage stage;
 private Scene scene;
 private Parent root;
    @FXML
    private Button btnclient;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
    
    
    
    
    
    
    
    
    
   /* 
    private void btnclient(ActionEvent event) throws IOException {
  
        
        
        
        
         if (event.getSource() == btnclient) {
        Stage primaryStage = new Stage();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("ListeClient.fxml"));

            Scene scene = new Scene(root);
            primaryStage.setTitle("Ajouter Evenement");
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        }
    
}
*/
    @FXML
    private void clientRoot(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ListeClient.fxml"));
  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
    }
     @FXML
    private void ModerateurRoot(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ListeModerateur.fxml"));
  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
    }
     
}
