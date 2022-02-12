package services;

import entities.Vote;
import utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoteCRUD {
    Connection cnxx;

    public VoteCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public void ajouterVote(Vote v) {
        String req = "INSERT INTO vote (idClient,idPublication,type) VALUES (?,?,?)";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, v.getId_client());
            pst.setInt(2, v.getId_publication());
            pst.setString(3, v.getType());
            pst.executeUpdate();
            System.out.println("Vote ajouté avec succés");
            ResultSet rs = pst.getGeneratedKeys();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public void modifierVote(Vote v) {
        String req = "UPDATE vote SET type = ? WHERE idClient = ? AND idPublication = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setString(1, v.getType());
            pst.setInt(2, v.getId_client());
            pst.setInt(3, v.getId_publication());

            pst.executeUpdate();
            System.out.println("Vote modifié avec succés");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void supprimerVote(int idClient, int idPublication) {
        String req = "delete from vote where idClient = ? AND idPublication = ?";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idClient);
            pst.setInt(2, idPublication);
            pst.executeUpdate();
            System.out.println("Vote supprimé avec succés");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Vote afficherVote(int idClient, int idPublication){
        Vote v = new Vote();
        try {
            String req = "SELECT * FROM vote WHERE idClient = ? AND idPublication = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1,idClient);
            pst.setInt(2,idPublication);
            ResultSet rs = pst.executeQuery();
            rs.next();
            v.setId_client(rs.getInt(2));
            v.setId_publication(rs.getInt(1));
            v.setType(rs.getString(3));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return v;
    }

    public List<Vote> afficherVote() {
        ArrayList listeVotes = new ArrayList();
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM vote";
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Vote v = new Vote();
                v.setId_client(rs.getInt(2));
                v.setId_publication(rs.getInt(1));
                v.setType(rs.getString(3));
                listeVotes.add(v);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return listeVotes;
    }

}