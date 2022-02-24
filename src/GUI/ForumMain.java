/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Spenz
 */
public class ForumMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            int idClient = 1;
            
            //init FXML loader et controller
            FXMLLoader forumMain = new FXMLLoader(getClass().getResource("ForumHomeGUI.fxml"));
            Parent root = forumMain.load();
            ForumHomeGUIController fhController = forumMain.<ForumHomeGUIController>getController();
            fhController.setIdClient(idClient);

            //init du scene 
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ForumMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
