/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.DashboardAdminController.parentAdmin;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class DashboardModerateurController implements Initializable {

    @FXML
    private Button btnGestEvennement;
    @FXML
    private Button btnGestStreamer;
    @FXML
    private AnchorPane homeModerateur;

    public static AnchorPane parentModerateur;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        parentModerateur = homeModerateur;

        try {
            Parent root = FXMLLoader.load(getClass().getResource("Evennement/TableView.fxml"));
            homeModerateur.getChildren().add(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        btnGestEvennement.setOnAction(a -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Evennement/TableView.fxml"));

                refreshParent(root);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });

        btnGestStreamer.setOnAction(a -> {

        });
    }

    public static void refreshParent(Parent root) {
        parentModerateur.getChildren().clear();
        parentModerateur.getChildren().add(root);
        Stage stg = ((Stage) parentModerateur.getScene().getWindow());
        stg.sizeToScene();
    }
}
