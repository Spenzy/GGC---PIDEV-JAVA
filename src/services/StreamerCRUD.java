/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Streamer;
import entities.Personne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.MyConnection;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeDebug.getClass;
import static sun.security.x509.OIDMap.getClass;

/**
 *
 * @author msi
 */
public class StreamerCRUD extends PersonneCRUD{
        Connection cnxx;
    
    public StreamerCRUD(){
            cnxx = MyConnection.getInstance().getCnx();

    }
    
    
    
    public void ajouterStreamer(Streamer s) {
        int idP = ajouterPersonne(s);
        String req = "INSERT INTO streamer (idStreamer,informations,lienStreaming) VALUES (?,?,?)";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS); //returns generated DB keys
            pst.setInt(1, idP);
            pst.setString(2, s.getInformations());
            pst.setString(3,s.getLienStreaming());
            pst.executeUpdate();
            System.out.println("Streamer ajoute");
            ResultSet rs = pst.getGeneratedKeys();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
     public void modifierStreamer(Streamer s) {

        // int modif = modifierPersonne(c);  
        String req = "update streamer set informations=?,lienStreaming=? WHERE idStreamer=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setString(1, s.getInformations());
            pst.setString(2, s.getLienStreaming());
            pst.setInt(3, s.getIdStreamer());
           
            pst.executeUpdate();
            System.out.println("Streamer modifier avec succes");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
     public Streamer afficherStramerid(int idc){
        Streamer s = new Streamer();
        try {
            String req = "SELECT * FROM streamer WHERE idStreamer = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1,idc);
            ResultSet rs = pst.executeQuery();
            rs.next();
            s.setIdStreamer(rs.getInt(1));
            s.setInformations(rs.getString(2));
            s.setLienStreaming(rs.getString(3));
            
          
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return s;
    }
     public void supprimerStreamer(int idStreamer) {

        String req = "delete from streamer where idStreamer=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idStreamer);
            pst.executeUpdate();
            System.out.println("streamer suprimée");
            supprimerPersonne(idStreamer);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
         public List<Streamer> afficherSt() {

        List<Streamer> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM streamer,personne";

            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                Streamer s = new Streamer();
                s.setIdStreamer(rs.getInt(1));
                s.setInformations(rs.getString(2));
                s.setLienStreaming(rs.getString(3));

                myList.add(s);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //return null;
        }
        return myList;
    }
         
         public ObservableList<Streamer> getStreamerList(){
           
             ObservableList<Streamer> StreamerList = FXCollections.observableArrayList();
             
             try{
             String query = "SELECT * from streamer,personne where id_personne = idStreamer";
             Statement st;
             ResultSet rs;
             
             //try{
                 st=cnxx.createStatement();
                 rs=st.executeQuery(query);
                 Streamer S;
                 Personne p;
                 
                 while(rs.next()){
                     
                     Streamer s = new Streamer() ;
                     s.setIdStreamer(rs.getInt(1));
                     s.setNom(rs.getString("nom"));
                     s.setPrenom(rs.getString("prenom"));
                     s.setDateNaissance(rs.getDate("dateNaissance"));
                     s.setEmail(rs.getString("email"));
                     s.setPassword(rs.getString("password"));
                     s.setTelephone(rs.getInt("telephone"));
                     s.setInformations(rs.getString("informations"));
                     s.setLienStreaming(rs.getString("lienStreaming"));
                     
                     StreamerList.add(s);
                     
//(rs.getInt(1, p.getId_personne());//,rs.getString("nom"),rs.getString("prenom"),rs.getDate("dateNaissance"),rs.getString("email"));
                     
                 }
                 
                 
             }catch(Exception ex){
                 
                 ex.printStackTrace();
             }
             
             return StreamerList;
             
             
         };
         
         
        
          
    
}
