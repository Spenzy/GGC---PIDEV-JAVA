/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiPidev.GUI;

import Entities.Evenement;
import Entities.Participation;
import Services.ParticipationCrud;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Azer Lahmer
 */
public class ListeParticipationController implements Initializable {
    
 @FXML
    private TableView<Participation> participationTable;
    private TableColumn<Participation,String > idParticipationCol;
    private TableColumn<Participation,String> idClientCol;
    private TableColumn<Participation,String> idEventCol;
    private TableColumn<Participation,String> nbrEtoileCol;
    
 
    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCols();
        loadParticipation();
        // TODO
    }  
     private void initCols() {
      idParticipationCol = new TableColumn<>("idParticipationCol");//nom de l'afichage
      idParticipationCol.setCellValueFactory(new PropertyValueFactory<>("idParticipation"));
      
      idClientCol = new TableColumn<>("id_Client");//nom de l'afichage
      idClientCol.setCellValueFactory(new PropertyValueFactory<>("idClient"));
      
      idEventCol = new TableColumn<>("idEvent");//nom de l'afichage
      idEventCol.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
      
      nbrEtoileCol = new TableColumn<>("nbrEtoile");//nom de l'afichage
      nbrEtoileCol.setCellValueFactory(new PropertyValueFactory<>("nbrEtoile"));
      
      
     
      participationTable.getColumns().addAll(idParticipationCol, idClientCol, idEventCol, nbrEtoileCol);
    }
   
    public void loadParticipation() {
      ParticipationCrud pc = new ParticipationCrud();
      List<Participation> participations = pc.afficherParticipation();
      participationTable.getItems().clear();
      //afficher par objet dans une boucle   
      for (Participation e : participations) {
       participationTable.getItems().add(e);   
      }
    
}
}
