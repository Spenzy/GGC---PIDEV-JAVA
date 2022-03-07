/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Personne;
import entities.Plan;
import entities.Streamer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyConnection;

/**
 *
 * @author msi
 */
public class PlanCRUD {

    Connection cnxx;

    public PlanCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public void ajouterPlan(Plan p) {
        String req = "INSERT INTO plan (idStreamer,date,heure,duree,description,idEvenement) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, p.getIdStreamer());
            pst.setDate(2, p.getDate());
            pst.setTime(3, p.getHeure());
            pst.setFloat(4, p.getDuree());
            pst.setString(5, p.getDescription());
            pst.setInt(6, p.getIdEvennement());
            pst.executeUpdate();
            System.out.println("Plan ajouté avec succés !");
        } catch (SQLException e) {
            System.err.println("Exception cause : " + e.getMessage());
        }
    }

    public void modifierPlan(Plan p) {
        String req = "UPDATE plan SET idStreamer = ?,date = ?,heure = ?,duree = ?,description = ?,idEvenement = ? WHERE idPlan = ?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, p.getIdStreamer());
            pst.setDate(2, p.getDate());
            pst.setTime(3, p.getHeure());
            pst.setFloat(4, p.getDuree());
            pst.setString(5, p.getDescription());
            pst.setInt(6, p.getIdEvennement());
            pst.setInt(7, p.getIdPlan());
            pst.executeUpdate();
            System.out.println("Plan modifié avec succés !!");
        } catch (SQLException e) {
            System.err.println("Exception cause : " + e.getMessage());
        }
    }

    public void supprimerPlan(int idPlan) {
        String req = "delete from plan where idPlan = ?";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idPlan);
            pst.executeUpdate();
            System.out.println("Plan supprimé avec succés");
        } catch (SQLException e) {
            System.err.println("Exception cause : " + e.getMessage());
        }
    }

    public Plan afficherPlan(int idPlan) {
        Plan p = new Plan();
        try {
            String req = "SELECT * FROM plan WHERE idPlan = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idPlan);
            ResultSet rs = pst.executeQuery();
            rs.next();
            p.setIdPlan(rs.getInt(1));
            p.setIdStreamer(rs.getInt(2));
            p.setDate(rs.getDate(3));
            p.setHeure(rs.getTime(4));
            p.setDuree(rs.getFloat(5));
            p.setDescription(rs.getString(6));
            p.setIdEvennement(rs.getInt(7));

        } catch (SQLException e) {
            System.err.println("Exception cause : " + e.getMessage());
        }
        return p;
    }

    public List<Plan> afficherPlan() {
        ArrayList listePlans = new ArrayList();

        try {

            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM plan ";
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Plan p = new Plan();
                p.setIdPlan(rs.getInt(1));
                p.setIdStreamer(rs.getInt(2));
                p.setDate(rs.getDate(3));
                p.setHeure(rs.getTime(4));
                p.setDuree(rs.getFloat(5));
                p.setDescription(rs.getString(6));
                p.setIdEvennement(rs.getInt(7));
                listePlans.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Exception cause : " + e.getMessage());
        }
        return listePlans;
    }

    public ObservableList<Plan> getPlanList() {

        ObservableList<Plan> PlanList = FXCollections.observableArrayList();

        try {
            String query = "SELECT * from Plan";
            Statement st;
            ResultSet rs;

            //try{
            st = cnxx.createStatement();
            rs = st.executeQuery(query);

            Plan p;

            while (rs.next()) {
                /*
                     int idPlan, int idStreamer, Date date, Date heure, float duree, String Description, int idEvennement
                 */

                Plan s = new Plan();
                s.setIdPlan(rs.getInt("idPlan"));
                s.setIdStreamer(rs.getInt("idStreamer"));
                s.setDate(rs.getDate("date"));
                s.setHeure(rs.getTime("heure"));
                s.setDuree(rs.getFloat("duree"));
                //s.setPassword(rs.getString("password"));
                s.setIdEvennement(rs.getInt("idEvenement"));
                s.setDescription(rs.getString("description"));
                //s.setLienStreaming(rs.getString("lienStreaming"));

                PlanList.add(s);

//(rs.getInt(1, p.getId_personne());//,rs.getString("nom"),rs.getString("prenom"),rs.getDate("dateNaissance"),rs.getString("email"));
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return PlanList;

    }
;

}
