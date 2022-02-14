/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Evenement;
import Entities.Participation;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            String req = "INSERT INTO participation ( idParticipation ,idClient,idEvent,nbrEtoile) VALUES (?,?,?,?)";
            PreparedStatement pst;
            try {
                pst = cnxx.prepareStatement(req);
                pst.setInt(1, p.getIdParticipation());
                pst.setInt(2, p.getIdClient());
                pst.setInt(3, p.getIdEvent());
                pst.setInt(4, p.getNbrEtoile());
                pst.executeUpdate();pst = cnxx.prepareStatement(req);
                
                System.out.println("Participation Ajoutée avec succées");

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        } else {
            System.out.println("Nbr de Participants depacées");
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
        String req = "SELECT count(*) FROM participation WHERE idEvent = ? ";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idEvent);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) < getNbrParticipant(idEvent);  
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    // vérifier si le client a participer deja ou bien nn 
    public boolean verifParticipation(int idClient) {
        String req = "SELECT * FROM participation WHERE idClient = ?";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idClient);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public void participer(int idParticipation, int idClient, int idEvent, int nbrEtoile) {
        if (verifParticipation(idClient)) {
            supprimerParticipation(idParticipation);
            System.out.println("le client a particpé deja ");
        } else {
            Participation p = new Participation(idParticipation, idClient, idEvent, nbrEtoile);
            ajouterParticipation(p);
            System.out.println("waw");
        }
    }

}
