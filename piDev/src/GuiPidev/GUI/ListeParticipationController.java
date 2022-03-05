/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiPidev.GUI;

import Entities.Evenement;
import Entities.Participation;
import Entities.Personne;
import Services.ParticipationCrud;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private TableColumn<Personne,String> nomCol;
    private TableColumn<Personne,String> prenomCol;
    private TableColumn<Personne,Integer> telephoneCol;
    
    
    Connection cnxx;
    @FXML
    private TableView<Participation> tvp;
    @FXML
    private TableColumn<Participation, Integer> id;
    @FXML
    private TableColumn<Participation, Integer> idc;
    @FXML
    private TableColumn<Participation, Integer> ide;
    @FXML
    private TableColumn<Participation, Integer> nbrec;
    @FXML
    private TableColumn<Personne, String> nomc;
    @FXML
    private TableColumn<Personne, String> prenomc;
    @FXML
    private TableColumn<Personne, Integer> telephonec;
 
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
      
      nomCol= new TableColumn<>("nom");//nom de l'afichage
      nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
      
      prenomCol= new TableColumn<>("nom");//nom de l'afichage
      prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
      
       telephoneCol= new TableColumn<>("nom");//nom de l'afichage
      telephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
      
      
     
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
    
    public ObservableList<Personne> getModerateurlist() {
        ObservableList<Personne> Clientlist = FXCollections.observableArrayList();

        String req = "SELECT * FROM personne,client WHERE id_personne = idClient ";
        Statement st;
        ResultSet rs;
        try {
            st = cnxx.createStatement();
            rs = st.executeQuery(req);
            Personne Client;
            while (rs.next()) {
                Client = new Personne(rs.getInt("id_personne"), rs.getString("nom"), rs.getString("prenom"), rs.getDate("dateNaissance"), rs.getString("email"), rs.getInt("telephone"), rs.getString("password"));
                Clientlist.add(Client);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Clientlist;
    }
    
   
    public void showPersonne() {
        //ObservableListe<moderateur> list = getModerateurlist();
        ObservableList<Personne> list = getModerateurlist();
        id.setCellValueFactory(new PropertyValueFactory<Participation, Integer>("idParticipation"));
        idc.setCellValueFactory(new PropertyValueFactory<Participation, Integer>("idClient"));
        ide.setCellValueFactory(new PropertyValueFactory<Participation, Integer>("idEvent"));
        nbrec.setCellValueFactory(new PropertyValueFactory<Participation, Integer>("nbrEtoile"));
        
        
        nomc.setCellValueFactory(new PropertyValueFactory<Personne, String>("nom"));
        prenomc.setCellValueFactory(new PropertyValueFactory<Personne, String>("prenom"));       
        telephonec.setCellValueFactory(new PropertyValueFactory<Personne, Integer>("telephone"));
       // tvp.setItems(list);

    }


    
    
    
    
}
