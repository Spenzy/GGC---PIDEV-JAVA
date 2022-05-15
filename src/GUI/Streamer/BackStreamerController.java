/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Streamer;

import GUI.DataValidation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import entities.Streamer;
import java.sql.Date;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import services.StreamerCRUD;
import entities.Streamer;
import entities.Personne;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import services.PersonneCRUD;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class BackStreamerController implements Initializable {

    public PersonneCRUD PC = new PersonneCRUD();
    public StreamerCRUD SC = new StreamerCRUD();

    
    @FXML
    private TableView<Streamer> tvBS;
    @FXML
    public TableColumn<Streamer, Integer> tvIdS;
    @FXML
    private TableColumn<Streamer, String> tvBNomS;
    @FXML
    private TableColumn<Streamer, String> tvBPrenS;
    @FXML
    private TableColumn<Streamer, Date> tvBDateS;
    @FXML
    private TableColumn<Streamer, String> tvBEmailS;
    @FXML
    private TableColumn<Streamer, Integer> tvBNumS;
    @FXML
    private TableColumn<Streamer, String> tvBInfoS;
    @FXML
    private TableColumn<Streamer, String> tvBLS;
    
    @FXML
    private Button btnBAS;
    @FXML
    private Button btnBMS;
    @FXML
    private Button btnBDS;
    @FXML
    private Button btnBRS;
    @FXML
    private TextField tfBLienStreamer;
    @FXML
    private TextField tfBInfoStreamer;
    @FXML
    private TextField tfBIDS1;
    @FXML
    private Button btnRecuperer;
    @FXML
    private ComboBox<Integer> cbIdP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showStreamers();
        cbIdP.setItems(SC.affecterPersonne());
        cbIdP.getSelectionModel().selectFirst();
    }

    @FXML
    private void AjouterStreamer(ActionEvent event) {
        

        /*String nom = tfBNomS.getText();
        String prenom = tfBPrenS.getText();
        Date DateNais = Date.valueOf(tfBDateS.getValue());
        String email = tfBEmailS.getText();
        Integer NumStreamer = Integer.parseInt(tfBNumS.getText());
        String PW = "";*/
        Integer ids = cbIdP.getValue();
        
        String InfoS = tfBInfoStreamer.getText();
        String LienS = tfBLienStreamer.getText();

        if (DataValidation.isNotEmpty(tfBInfoStreamer) && DataValidation.isNotEmpty(tfBLienStreamer)) {

            
                Streamer s = new Streamer(ids, InfoS, LienS);

                SC.ajouterStreamer(s);

                showStreamers();
            
            
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Erreur! Champs vides!");
            alert.showAndWait();
        }

    }

    @FXML
    private void ModifierStreamer(ActionEvent event) {
        PersonneCRUD pc = new PersonneCRUD();

        
        
        if (DataValidation.isNotEmpty(tfBInfoStreamer) && DataValidation.isNotEmpty(tfBLienStreamer)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Validation");
                alert.setHeaderText("Voulez vous valider la modification de ce Streamer ?");
                //alert.setContentText("");

                Optional<ButtonType> option = alert.showAndWait();
                //confirmation 
                if (option.get() == ButtonType.OK) {
                    //Modifier Livraison
                    //    if(!tfBInfoStreamer.getText().isEmpty() || !tfBLienStreamer.getText().isEmpty()||!tfBIDS1.getText().isEmpty()){

                    Streamer s = new Streamer(Integer.parseInt(tfBIDS1.getText()), tfBInfoStreamer.getText(), tfBLienStreamer.getText());
                    SC.modifierStreamer(s);
                    showStreamers();
                    //  }
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Erreur! Champs invalides!");
                alert.showAndWait();
            }
        
        

    }

    @FXML
    private void BackHome(ActionEvent event) {

    }

    @FXML
    private void SupprimerStreamer(ActionEvent event) {

        Streamer l1 = tvBS.getSelectionModel().getSelectedItem();
        if (l1 == null) {
            //veuillez selectionner une liiiiiiiiiiiiiiiigne
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Erreur !");
            alert1.setHeaderText(null);
            alert1.setContentText("veuillez selectionner une ligne du tableau puis appuyez sur le bouton supprimer");
            alert1.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Validation");
            alert.setHeaderText("Voulez vous vraiment supprimer ce Streamer ?");
            //alert.setContentText("");

            Optional<ButtonType> option = alert.showAndWait();
            //confirmation 
            if (option.get() == ButtonType.OK) {
                System.out.println(l1.getIdStreamer());
                SC.supprimerStreamer(l1.getIdStreamer());

                //refresh table views and ComboBox and buttons and labels
                showStreamers();

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Confirmation !");
                alert1.setHeaderText(null);
                alert1.setContentText("Le streamer a été supprimée avec sucées");
                alert1.show();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Erreur!");
                alert2.setHeaderText(null);
                alert2.setContentText("Le streamer n'a pas été supprimée");
                alert2.show();
            }
        }
    }

    public void showStreamers() {

        StreamerCRUD ssc = new StreamerCRUD();

        ObservableList<Streamer> list = ssc.getStreamerList();
        tvIdS.setCellValueFactory(new PropertyValueFactory<Streamer, Integer>("idStreamer"));
        tvBNomS.setCellValueFactory(new PropertyValueFactory<Streamer, String>("nom"));
        tvBPrenS.setCellValueFactory(new PropertyValueFactory<Streamer, String>("prenom"));
        tvBDateS.setCellValueFactory(new PropertyValueFactory<Streamer, Date>("dateNaissance"));
        tvBEmailS.setCellValueFactory(new PropertyValueFactory<Streamer, String>("email"));
//        tvBPwS.setCellValueFactory(new PropertyValueFactory<Streamer, String>("password"));
        tvBNumS.setCellValueFactory(new PropertyValueFactory<Streamer, Integer>("telephone"));
        tvBInfoS.setCellValueFactory(new PropertyValueFactory<Streamer, String>("informations"));
        tvBLS.setCellValueFactory(new PropertyValueFactory<Streamer, String>("lienStreaming"));

        tvBS.setItems(list);
    }

    @FXML
    private void RecupererOnclick(ActionEvent event) {
        Streamer l1 = tvBS.getSelectionModel().getSelectedItem();
        if (l1 == null) {
            //veuillez selectionner une liiiiiiiiiiiiiiiigne
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Erreur !");
            alert1.setHeaderText(null);
            alert1.setContentText("veuillez selectionner une ligne du tableau puis appuyez sur le bouton recuperer");
            alert1.show();

        } else {
            cbIdP.setValue(l1.getIdStreamer());
            tfBInfoStreamer.setText(l1.getInformations());
            tfBLienStreamer.setText(l1.getLienStreaming());
        }
    }

}
