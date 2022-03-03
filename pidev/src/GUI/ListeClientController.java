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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.ClientCRUD;
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
    private DatePicker tfdatedebut;
    @FXML
    private DatePicker tfdatefin;
    @FXML
    private TableView<Client> tvBooks;
    @FXML
    private TableColumn<Client, Integer> colid;
    @FXML
    private TableColumn<Client, Integer> colnbr;
    @FXML
    private TableColumn<Client, Integer> colban;
    @FXML
    private TableColumn<Client, Integer> coletat;
    @FXML
    private TableColumn<Client, Date> coldatedebut;
    @FXML
    private TableColumn<Client, Date> coldatefin;
    @FXML
    private Button btnupdate;
    Connection cnxx;
    @FXML
    private Button btnavertit;

    /**
     * Initializes the controller class.
     */
    public ListeClientController() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnupdate) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Demande");
            alert.setHeaderText("Voulez vous modifier ce client?");
            Optional<ButtonType> option = alert.showAndWait();
            //confirmation 
            if (option.get() == ButtonType.OK) {
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Validation");
                alert2.setHeaderText("Modification avec succés");
                UpdateRecord();

            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Erreur!");
                alert2.setHeaderText(null);
                alert2.setContentText("Pas de modification");
                alert2.show();
            }
        } else if (event.getSource() == btnavertit) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Demande");
            alert.setHeaderText("Voulez vous avertir ce client?");
            Optional<ButtonType> option = alert.showAndWait();
            //confirmation 
            if (option.get() == ButtonType.OK) {
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Validation");
                alert2.setHeaderText("Avertssement donné avec succés");
                UpdateRecordavertir();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Erreur!");
                alert2.setHeaderText(null);
                alert2.setContentText("Avertissement abandonné");
                alert2.show();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showClient();
        // TODO
    }

    public ObservableList<Client> getClientlist() {
        ObservableList<Client> Clientlist = FXCollections.observableArrayList();

        String req = "SELECT * FROM client";
        Statement st;
        ResultSet rs;
        try {
            st = cnxx.createStatement();
            rs = st.executeQuery(req);
            Client client;
            while (rs.next()) {
                client = new Client(rs.getInt("idClient"), rs.getInt("nbrAvertissement"), rs.getInt("ban"), rs.getInt("etat"), rs.getDate("dateDebutBlock"), rs.getDate("dateFinBlock"));
                Clientlist.add(client);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Clientlist;
    }

    @FXML
    public void showClient() {
        ObservableList<Client> list = getClientlist();
        colid.setCellValueFactory(new PropertyValueFactory<Client, Integer>("idClient"));
        colnbr.setCellValueFactory(new PropertyValueFactory<Client, Integer>("nbrAvertissement"));
        colban.setCellValueFactory(new PropertyValueFactory<Client, Integer>("ban"));
        coletat.setCellValueFactory(new PropertyValueFactory<Client, Integer>("etat"));
        coldatedebut.setCellValueFactory(new PropertyValueFactory<Client, Date>("dateDebutBlock"));
        coldatefin.setCellValueFactory(new PropertyValueFactory<Client, Date>("dateFinBlock"));
        tvBooks.setItems(list);

    }

    private void UpdateRecordavertir() {

        String req = " UPDATE client SET nbrAvertissement=nbrAvertissement+1  WHERE idClient =" + tfid.getText();
        executeQuery(req);

        showClient();

    }

    private void UpdateRecord() {

        String req = " UPDATE client SET nbrAvertissement ='" + tfnbr.getText() + "', ban ='" + tfban.getText() + "', etat ='" + tfetat.getText() + "' , dateDebutBlock = '" + java.sql.Date.valueOf(tfdatedebut.getValue()) + "', dateFinBlock = '" + java.sql.Date.valueOf(tfdatefin.getValue()) + "' WHERE idClient = '" + tfid.getText() + "'";
        executeQuery(req);

        showClient();

    }

    private void executeQuery(String req) {
        cnxx = MyConnection.getInstance().getCnx();
        Statement st;
        try {
            st = cnxx.createStatement();
            st.executeUpdate(req);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void handlemousseclick(MouseEvent event) {

        Client client = tvBooks.getSelectionModel().getSelectedItem();
        tfid.setText("" + client.getIdClient());
        tfnbr.setText("" + client.getNbrAvertissement());
        tfban.setText("" + client.getBan());
        tfetat.setText("" + client.getEtat());
        //tfdatenaissance.setValue(client.getDateNaissance().toLocalDate());

        tfdatedebut.setValue(client.getDateDebutBlock().toLocalDate());
        tfdatefin.setValue(client.getDateFinBlock().toLocalDate());

    }

}
