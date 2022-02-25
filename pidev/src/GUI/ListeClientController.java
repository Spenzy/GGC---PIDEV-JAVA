/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Client;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class ListeClientController implements Initializable {

    @FXML
    private TextField tfid;
    @FXML
    private TextField tfnbr;
    @FXML
    private TextField tfban;
    @FXML
    private TextField tfetat;
    @FXML
    private TextField tfdatedebut;
    @FXML
    private TextField tfdatefin;
    @FXML
    private TableView<Client> tvBooks;
    @FXML
    private TableColumn<Client,Integer> colid;
    @FXML
    private TableColumn<Client,Integer> colnbr;
    @FXML
    private TableColumn<Client,Integer> colban;
    @FXML
    private TableColumn<Client,Integer> coletat;
    @FXML
    private TableColumn<Client,Date> coldatedebut;
    @FXML
    private TableColumn<Client,Date> coldatefin;
    @FXML
    private Button btninsert;
    @FXML
    private Button btnupdate;
    Connection cnxx;
    /**
     * Initializes the controller class.
     */
    
    
    public ListeClientController() {
        cnxx = MyConnection.getInstance().getCnx();
    } 
@FXML
private void handleButtonAction(ActionEvent event){
    if (event.getSource() == btnupdate){
UpdateRecord();
}
    
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showClient();
        // TODO
    }  
    
    public ObservableList<Client>getClientlist(){
	ObservableList<Client> Clientlist = FXCollections.observableArrayList();
	
	String req = "SELECT * FROM client";
	Statement st ;
	ResultSet rs ;
  try{
st = cnxx.createStatement();
rs=st.executeQuery(req);
Client client;
while(rs.next()){
client = new Client(rs.getInt("idClient"),rs.getInt("nbrAvertissement"),rs.getInt("ban"),rs.getInt("etat"),rs.getDate("dateDebutBlock"),rs.getDate("dateFinBlock"));
Clientlist.add(client);
}


}catch(Exception ex){
ex.printStackTrace();
}

    
   return Clientlist;
}
    public void showClient(){
    ObservableList<Client> list = getClientlist();
    colid.setCellValueFactory(new PropertyValueFactory<Client,Integer>("idClient"));
    colnbr.setCellValueFactory(new PropertyValueFactory<Client,Integer>("nbrAvertissement"));
    colban.setCellValueFactory(new PropertyValueFactory<Client,Integer>("ban"));
    coletat.setCellValueFactory(new PropertyValueFactory<Client,Integer>("etat"));
    coldatedebut.setCellValueFactory(new PropertyValueFactory<Client,Date>("dateDebutBlock"));
    coldatefin.setCellValueFactory(new PropertyValueFactory<Client,Date>("dateFinBlock"));
    tvBooks.setItems(list);
    
    
}
    private void UpdateRecord(){
String req = " UPDATE client SET nbrAvertissement ='"+ tfnbr.getText() + "', ban ='" + tfban.getText() + "', etat ='" + tfetat.getText() + "' , dateDebutBlock = '" + tfdatedebut.getText() + "', dateFinBlock = '" + tfdatefin.getText() + "' WHERE idClient = '" + tfid.getText()  +"'";
executeQuery(req);
showClient();


}
    private void executeQuery(String req){
cnxx = MyConnection.getInstance().getCnx();
Statement st;
try{
st= cnxx.createStatement();
st.executeUpdate(req);

}catch(Exception ex){
ex.printStackTrace();
}

}
}
