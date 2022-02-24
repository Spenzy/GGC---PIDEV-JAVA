/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.AfficherPublicationGUIController;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Spenz
 */
public class ForumHomeGUIController implements Initializable {

    int idClient;

    @FXML
    private TableView<ObservableList<String>> tblPublication;
    @FXML
    private Button btnTest;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTableView();
        int idPublication = 2;

        btnTest.setOnAction((ActionEvent a) -> {
            try {
                //init loader root
                FXMLLoader testLoad = new FXMLLoader(getClass().getResource("AfficherPublicationGUI.fxml"));

                //init Controller
                AfficherPublicationGUIController controller = new AfficherPublicationGUIController(1,idPublication);
                testLoad.setController(controller);
               
                Parent root = testLoad.load();

                //scene switch
                Scene affPubScene = new Scene(root);
                affPubScene.setUserData(testLoad);
                ((Stage) btnTest.getScene().getWindow()).setScene(affPubScene);

            } catch (IOException ex) {
                Logger.getLogger(ForumHomeGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    public void initTableView() {
        // add columns
        List<String> colonnes = Arrays.asList(new String[]{"NbrVotes", "Titre", "Description", "Auteur", "Modifier", "Archiver"});
        for (int i = 0; i < colonnes.size(); i++) {
            final int finalId = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    colonnes.get(i)
            );
            column.setCellValueFactory(param
                    -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalId))
            );
            tblPublication.getColumns().add(column);
        }
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

}
