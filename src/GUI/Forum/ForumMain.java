/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Forum;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 
 * @author Spenz
 */
public class ForumMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            int idClient = 1;
            Parent root = FXMLLoader.load(getClass().getResource("ForumHomeGUI.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
