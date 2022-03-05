/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Client;
import entities.Moderateur;
import entities.Personne;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.Authentification;
import services.ClientCRUD;
import services.ModerateurCRUD;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class ListeModerateurController implements Initializable {
 private Stage stage;
 private Scene scene;
 private Parent root;
 Authentification aa = new Authentification();


    @FXML
    private TextField tfidmoderateur;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private DatePicker tfdatenaissance;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tftelephone;
    @FXML
    private TextField tfpassword;
    @FXML
    private Button btnajouterm;
    @FXML
    private Button btndeletem;
    @FXML
    private Button btnupdatem;
    Connection cnxx;
    @FXML
    private TableView<Personne> tvmoderateur;
    @FXML
    private TableColumn<Moderateur, Integer> colidmoderateur;
    @FXML
    private TableColumn<Moderateur, String> colnom;
    @FXML
    private TableColumn<Moderateur, String> colprenom;
    @FXML
    private TableColumn<Moderateur, Date> coldatenaissance;
    @FXML
    private TableColumn<Moderateur, String> colemail;
    @FXML
    private TableColumn<Moderateur, Integer> coltelephone;
    @FXML
    private TableColumn<Moderateur, String> colpassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showModerateur();
        // TODO
    }

    public void rafraichir() {
        tfdatenaissance.setValue(null);
        tfemail.setText("");
        tfidmoderateur.setText("");
        tfnom.setText("");
        tfprenom.setText("");
        tftelephone.setText("");
        tfpassword.setText("");
    }
@FXML
    private void clientRoot(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ListeClient.fxml"));
  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
    }
     @FXML
    
    
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnupdatem) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Demande");
            alert.setHeaderText("Voulez vous modifier ce modérateur?");
            Optional<ButtonType> option = alert.showAndWait();
            //confirmation 
            if (option.get() == ButtonType.OK) {
                UpdateRecord1();
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Validation");
                alert2.setHeaderText("Modification avec succés");

            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Erreur!");
                alert2.setHeaderText(null);
                alert2.setContentText("Pas de modification");
                alert2.show();
            }
        } else if (event.getSource() == btndeletem) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Validation");
            alert.setHeaderText("Voulez vous supprimer ce modérateur?");
            //alert.setContentText("");

            Optional<ButtonType> option = alert.showAndWait();
            //confirmation 
            if (option.get() == ButtonType.OK) {
                deletemoderateur();
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Validation");
                alert2.setHeaderText("Suppression avec succés");
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Erreur!");
                alert2.setHeaderText(null);
                alert2.setContentText("Suppression abandonnée");
                alert2.show();
            }
        } else if (event.getSource() == btnajouterm) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Validation");
            alert.setHeaderText("Voulez vous ajouter ce modérateur ?");
            //alert.setContentText("");

            Optional<ButtonType> option = alert.showAndWait();
            //confirmation 
            if (option.get() == ButtonType.OK) {
                addModerateur1();
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Validation");
                alert2.setHeaderText("Ajout avec succés");
                // Moderateur m =new Moderateur(id_personne,tfnom.getText(),tfprenom.getText(),tfdatenaissance.getText(),tfemail.getText(),tftelephone.getText(),tfpassword.getText());
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Erreur!");
                alert2.setHeaderText(null);
                alert2.setContentText("Ajout abandonné ");
                alert2.show();
            }
        }
        rafraichir();

    }

    public ListeModerateurController() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public ObservableList<Personne> getModerateurlist() {
        ObservableList<Personne> Moderateurlist = FXCollections.observableArrayList();

        String req = "SELECT * FROM personne,moderateur WHERE id_personne = id_moderateur ";
        Statement st;
        ResultSet rs;
        try {
            st = cnxx.createStatement();
            rs = st.executeQuery(req);
            Personne moderateur;
            while (rs.next()) {
                moderateur = new Personne(rs.getInt("id_personne"), rs.getString("nom"), rs.getString("prenom"), rs.getDate("dateNaissance"), rs.getString("email"), rs.getInt("telephone"), rs.getString("password"));
                Moderateurlist.add(moderateur);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Moderateurlist;
    }

    @FXML
    public void showModerateur() {
        //ObservableListe<moderateur> list = getModerateurlist();
        ObservableList<Personne> listm = getModerateurlist();
        colidmoderateur.setCellValueFactory(new PropertyValueFactory<Moderateur, Integer>("id_personne"));
        colnom.setCellValueFactory(new PropertyValueFactory<Moderateur, String>("nom"));
        colprenom.setCellValueFactory(new PropertyValueFactory<Moderateur, String>("prenom"));
        coldatenaissance.setCellValueFactory(new PropertyValueFactory<Moderateur, Date>("dateNaissance"));
        colemail.setCellValueFactory(new PropertyValueFactory<Moderateur, String>("email"));
        coltelephone.setCellValueFactory(new PropertyValueFactory<Moderateur, Integer>("telephone"));
        colpassword.setCellValueFactory(new PropertyValueFactory<Moderateur, String>("password"));
        tvmoderateur.setItems(listm);

    }

    @FXML
    private void handlemousseclick1(MouseEvent event) {

        Personne client = tvmoderateur.getSelectionModel().getSelectedItem();
        tfidmoderateur.setText("" + client.getId_personne());
        tfnom.setText(client.getNom());
        tfprenom.setText(client.getPrenom());

        tfdatenaissance.setValue(client.getDateNaissance().toLocalDate());
        tfemail.setText(client.getEmail());
        tftelephone.setText("" + client.getTelephone());
        tfpassword.setText("" + client.getPassword());

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

    private void UpdateRecord1() {

        String req = " UPDATE Personne SET nom ='" + tfnom.getText() + "', prenom ='" + tfprenom.getText() + "', dateNaissance ='" + java.sql.Date.valueOf(tfdatenaissance.getValue()) + "' , email = '" + tfemail.getText() + "', telephone= '" + tftelephone.getText() + "', password ='" + aa.hashagePWD(tfpassword.getText()) + "' WHERE id_personne = '" + tfidmoderateur.getText() + "'";
        executeQuery(req);

        showModerateur();

    }

    private void deletemoderateur() {
        String req = "delete from moderateur where id_moderateur=" + tfidmoderateur.getText() + "";
        executeQuery(req);
        showModerateur();
    }

    private void addModerateur1() {
        DataValidation validator = new DataValidation();

        String id_moderateur = tfidmoderateur.getText();
        String nom = tfnom.getText();
        String prenom = tfprenom.getText();
        String myDate = String.valueOf(tfdatenaissance.getValue());
        String email = tfemail.getText();
        int telephone = Integer.parseInt(tftelephone.getText());
        String password = tfpassword.getText();
        if (validator.isNotEmpty(tfemail) && validator.isNotEmpty(tfpassword) && validator.isNotEmpty(tfnom)
                && validator.isNotEmpty(tfprenom) && validator.isNotEmpty(tftelephone) && validator.isNotEmpty(tfdatenaissance.getEditor())) {

            if (validator.emailFormat(tfemail) && validator.textNumeric(tftelephone) && validator.dataLength(tftelephone, "8")
                    && validator.textAlphabet(tfnom) && validator.textAlphabet(tfprenom)) {

                Authentification aa = new Authentification();
                ModerateurCRUD mc = new ModerateurCRUD();
                Moderateur m = new Moderateur(nom, prenom, java.sql.Date.valueOf(myDate), email, telephone, aa.hashagePWD(password));

                mc.ajouterModerateur(m);
                showModerateur();
            }

        }
    }
}
