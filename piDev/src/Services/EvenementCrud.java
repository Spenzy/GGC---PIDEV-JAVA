/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Entities.Evenement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Azer Lahmer
 */
public class EvenementCrud   {

    Connection cnxx;

    public EvenementCrud() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public int ajouterEvenement(Evenement e) {
        int idE = 0;
        String req = "INSERT INTO Evenement (reference,dateDebut, dateFin,localisation,description,nbrParticipant,image) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, e.getReference());
            pst.setDate(2, e.getDateDebut());
            pst.setDate(3, e.getDateFin());
            //pst.setString(3, e.getHeureDebut());
            //pst.setString(4, e.getHeureFin());
            pst.setString(4, e.getLocalisation());
            pst.setString(5, e.getDescription());
            pst.setInt(6, e.getNbrParticipant());
            pst.setString(7, e.getImage());
            pst.executeUpdate();
            System.out.println("Evenement Ajouté avec succés");
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                idE = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return idE;
        
    }
    

    public void modifierEvenement(Evenement e) {
        String req = "UPDATE Evenement set dateDebut=?,dateFin=? ,localisation=?,description=?,nbrParticipant=? WHERE reference=?";

        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setDate(1, e.getDateDebut());
            pst.setDate(2, e.getDateFin());
            //pst.setString(2, e.getHeureDebut());
            //pst.setString(3, e.getHeureFin());
            pst.setString(3, e.getLocalisation());
            pst.setString(4, e.getDescription());
            pst.setInt(5, e.getNbrParticipant());
            pst.setInt(6, e.getReference());
            pst.executeUpdate();
            System.out.println("Evenement Modifié ");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void supprimerEvenement(int reference) {

        String req = "delete from Evenement where reference=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, reference);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        System.out.println("Evenement supprimé");
    }

    public List<Evenement> afficherEvenements() {

        List<Evenement> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM Evenement";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {

                Evenement e = new Evenement();
                e.setReference(rs.getInt(1));
                e.setDateDebut(rs.getDate(2));
                e.setDateFin(rs.getDate(3));
               // e.setHeureDebut(rs.getString(3));
               // e.setHeureFin(rs.getString(4));
                e.setLocalisation(rs.getString(4));
                e.setDescription(rs.getString(5));
                e.setNbrParticipant(rs.getInt(6));
                e.setImage(rs.getString(7));
                myList.add(e);
                
       
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return myList;
    }
    public Evenement afficherEvenement(int idE){
        Evenement e = new Evenement();
        try {
            String req = "SELECT * FROM evenement WHERE reference = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1,idE);
            ResultSet rs = pst.executeQuery();
            rs.next();
            e.setReference(rs.getInt(1));
            e.setDateDebut(rs.getDate(2));
            e.setDateFin(rs.getDate(3));
            e.setLocalisation(rs.getString(4));
            e.setDescription(rs.getString(5));
            e.setNbrParticipant(rs.getInt(6));
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return e;
    }
    
    
    
        public boolean VerifEvenement(int reference) {
        boolean testEvenement = false;
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT reference FROM evenement";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getInt(1) == reference) {
                    testEvenement = true;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return testEvenement;
    }
        
      
        
        

}
