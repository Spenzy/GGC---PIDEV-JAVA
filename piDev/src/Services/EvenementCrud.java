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
        String req = "INSERT INTO Evenement (reference,dateDebut,heureDebut,heureFin,localisation,description,nbrParticipant) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, e.getReference());
            pst.setDate(2, e.getDateDebut());
            pst.setString(3, e.getHeureDebut());
            pst.setString(4, e.getHeureFin());
            pst.setString(5, e.getLocalisation());
            pst.setString(6, e.getDescription());
            pst.setInt(7, e.getNbrParticipant());
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
        String req = "UPDATE Evenement set dateDebut=?,heureDebut=?,heureFin=? ,localisation=?,description=?,nbrParticipant=? WHERE reference=?";

        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setDate(1, e.getDateDebut());
            pst.setString(2, e.getHeureDebut());
            pst.setString(3, e.getHeureFin());
            pst.setString(4, e.getLocalisation());
            pst.setString(5, e.getDescription());
            pst.setInt(6, e.getNbrParticipant());
            pst.setInt(7, e.getReference());
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

    }

    public List<Evenement> afficher() {

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
                e.setHeureDebut(rs.getString(3));
                e.setHeureFin(rs.getString(4));
                e.setLocalisation(rs.getString(5));
                e.setDescription(rs.getString(6));
                e.setNbrParticipant(rs.getInt(7));
                myList.add(e);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return myList;
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
