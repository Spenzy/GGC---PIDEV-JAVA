/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Streamer;

import entities.Plan;
import entities.Streamer;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import services.PlanCRUD;
import services.StreamerCRUD;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class StreamerPlanController implements Initializable {

    public PlanCRUD plc = new PlanCRUD();
    
    public int idP = 1;

    private TextField tfBPidS;
    @FXML
    private DatePicker tfBPDate;
    @FXML
    private TextField tfBPHeure;
    @FXML
    private TextField tfBPDuree;
    @FXML
    private TextField tfBPDesc;
    @FXML
    private TextField tfBPIdE;
    @FXML
    private TableView<Plan> tvBP;
    @FXML
    private TableColumn<Plan, Integer> tvBPIdS;
    @FXML
    private TableColumn<Plan, Date> tvBPDate;
    @FXML
    private TableColumn<Plan, Time> tvBPHeure;
    @FXML
    private TableColumn<Plan, Float> tvBPDuree;
    @FXML
    private TableColumn<Plan, String> tvBPDesc;
    @FXML
    private TableColumn<Plan, Integer> tvBPIdE;
    @FXML
    private Button AjouterPlan;
    @FXML
    private Button ModifierPlan;
    @FXML
    private Button SupprimerPlan;
    @FXML
    private Button recupererP;
    @FXML
    private ComboBox<Integer> cbIdS;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showPlans();
        cbIdS.setItems(plc.affecterStreamer());
        cbIdS.getSelectionModel().selectFirst();
    }

    @FXML
    private void AjouterPlan(ActionEvent event) {

        Integer IdS = cbIdS.getValue();
        Date date = Date.valueOf(tfBPDate.getValue());
        Time heure = Time.valueOf(tfBPHeure.getText());
        Float duree = Float.parseFloat(tfBPDuree.getText());
        String Desc = tfBPDesc.getText();
        Integer IdE = Integer.parseInt(tfBPIdE.getText());

        Plan p = new Plan(idP, IdS, date, heure, duree, Desc, IdE);

        PlanCRUD PC = new PlanCRUD();
        PC.ajouterPlan(p);
        showPlans();

    }

    @FXML
    private void ModifierPlan(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider la modification de ce Plan ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation 
        if (option.get() == ButtonType.OK) {
            //Modifier Livraison
            //    if(!tfBInfoStreamer.getText().isEmpty() || !tfBLienStreamer.getText().isEmpty()||!tfBIDS1.getText().isEmpty()){

            Integer IdS = cbIdS.getValue();
            Date date = Date.valueOf(tfBPDate.getValue());
            Time heure = Time.valueOf(tfBPHeure.getText());
            Float duree = Float.parseFloat(tfBPDuree.getText());
            String Desc = tfBPDesc.getText();
            Integer IdE = Integer.parseInt(tfBPIdE.getText());

            Plan p = new Plan(idP, IdS, date, heure, duree, Desc, IdE);
            System.out.println(p);
            plc.modifierPlan(p);
            showPlans();
            //  }
        }

    }

    @FXML
    private void SupprimerPlan(ActionEvent event) {

        Plan l1 = tvBP.getSelectionModel().getSelectedItem();
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
            alert.setHeaderText("Voulez vous vraiment supprimer ce Plan ?");
            //alert.setContentText("");

            Optional<ButtonType> option = alert.showAndWait();
            //confirmation 
            if (option.get() == ButtonType.OK) {
                System.out.println(l1.getIdPlan());
                plc.supprimerPlan(l1.getIdPlan());

                //refresh table views and ComboBox and buttons and labels
                showPlans();

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Confirmation !");
                alert1.setHeaderText(null);
                alert1.setContentText("Le plan a été supprimée avec sucées");
                alert1.show();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Erreur!");
                alert2.setHeaderText(null);
                alert2.setContentText("Le plan n'a pas été supprimée");
                alert2.show();
            }
        }

    }


    public void showPlans() {

        PlanCRUD ssc = new PlanCRUD();

        ObservableList<Plan> list = ssc.getPlanList();

        tvBPIdS.setCellValueFactory(new PropertyValueFactory<Plan, Integer>("idStreamer"));
        tvBPDate.setCellValueFactory(new PropertyValueFactory<Plan, Date>("date"));
        tvBPHeure.setCellValueFactory(new PropertyValueFactory<Plan, Time>("heure"));
        tvBPDuree.setCellValueFactory(new PropertyValueFactory<Plan, Float>("duree"));
        tvBPDesc.setCellValueFactory(new PropertyValueFactory<Plan, String>("description"));
        tvBPIdE.setCellValueFactory(new PropertyValueFactory<Plan, Integer>("idEvenement"));

        tvBP.setItems(list);

    }

    @FXML
    private void recupererOnCP(ActionEvent event) {

        Plan p1 = tvBP.getSelectionModel().getSelectedItem();
        if (p1 == null) {
            //veuillez selectionner une liiiiiiiiiiiiiiiigne
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Erreur !");
            alert1.setHeaderText(null);
            alert1.setContentText("veuillez selectionner une ligne du tableau puis appuyez sur le bouton recuperer");
            alert1.show();

        } else {
            idP = p1.getIdPlan();
            cbIdS.setValue(p1.getIdStreamer());
            tfBPDate.setValue(p1.getDate().toLocalDate());
            tfBPHeure.setText(p1.getHeure() + "");
            tfBPDuree.setText(p1.getDuree() + "");
            tfBPDesc.setText(p1.getDescription());
            tfBPIdE.setText(p1.getIdEvennement() + "");
            
        }
    }

}
