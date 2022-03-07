/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Evenement;
import entities.Participation;
import utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author Azer Lahmer
 */
public class ParticipationCrud {

    Connection cnxx;

    public ParticipationCrud() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public void ajouterParticipation(Participation p) {
        if (verifNbrParticipant(p.getIdEvent())) {
            String req = "INSERT INTO participation ( idClient,idEvent,nbrEtoile) VALUES (?,?,?)";
            PreparedStatement pst;
            try {
                pst = cnxx.prepareStatement(req);
                // pst.setInt(1, p.getIdParticipation());
                pst.setInt(1, p.getIdClient());
                pst.setInt(2, p.getIdEvent());
                pst.setInt(3, p.getNbrEtoile());
                pst.executeUpdate();
                pst = cnxx.prepareStatement(req);

                System.out.println("Participation Ajoutée avec succées");

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());

            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Participation");
            alert2.setHeaderText("le nombre des participations sont occupées");
            alert2.show();

            System.out.println("le nombre des participations sont occupées");
        }

    }

    public void supprimerParticipation(int idParticipation) {

        String req = "delete from Participation where idParticipation=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idParticipation);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        System.out.println("Participation Supprimé");
    }

    public List<Participation> afficherParticipation() {

        List<Participation> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM participation";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {

                Participation p = new Participation();
                p.setIdParticipation(rs.getInt(1));
                p.setIdClient(rs.getInt(2));
                p.setIdEvent(rs.getInt(3));
                p.setNbrEtoile(rs.getInt(4));
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return myList;
    }

    public List<String> getParticipEmails(int idEvent) {
        List<String> myList = new ArrayList();
        String req = "SELECT email FROM personne inner JOIN client on(client.idClient=personne.id_personne) inner join participation ON (client.idClient = participation.idClient) WHERE participation.idEvent = ?";
        try {

            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idEvent);
            ResultSet rs;
            rs = pst.executeQuery();
            while (rs.next()) {
                myList.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return myList;
    }

    public int getNbrParticipant(int idEvent) {
        String req = "SELECT nbrParticipant FROM evenement WHERE reference = ? ";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idEvent);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return -1; //-1 une erreur dans la methode
    }

    public boolean verifNbrParticipant(int idEvent) { //
        String req = "SELECT * FROM participation WHERE idEvent = ? ";
        int i = 0;
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idEvent);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                i++;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return i < getNbrParticipant(idEvent);
    }

    // vérifier si le client a participer deja ou bien nn 
    public boolean verifParticipation(int idClient, int idEvent) {
        String req = "SELECT * FROM participation WHERE idClient = ? AND idEvent = ? ";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idClient);
            pst.setInt(2, idEvent);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

   
}
