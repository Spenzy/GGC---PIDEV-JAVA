package services;

import entities.Publication;
import entities.Publication;
import utils.MyConnection;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationCRUD {
    Connection cnxx;

    public PublicationCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public long ajouterPublication (Publication p) {
        long id = 0;
        String req = "INSERT INTO Publication (idPublication,object,description,nbrVote,archive,idClient ) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, p.getId_publication());
            pst.setString(2, p.getTitre());
            pst.setString(3, p.getDesc());
            pst.setInt(4, p.getNbrVote());
            pst.setBoolean(5, p.isArchive());
            pst.setInt(6, p.getId_client());
            pst.executeUpdate();
            System.out.println("Publication ajoutée avec succés");
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return id;
    }

    public void modifierPublication(Publication p) {
        String req = "UPDATE publication SET object=? ,description=?, description=?,nbrVote=? WHERE idPublication=? "; //id_client est fixe //archive aura son propre methode
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setString(2, p.getTitre());
            pst.setString(3, p.getDesc());
            pst.setInt(4,p.getNbrVote());
            pst.executeUpdate();
            System.out.println("Publication modifiée avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void supprimerPublication(int id_Publication) {
        String req = "delete from publication where idPublication = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, id_Publication);
            pst.executeUpdate();
            System.out.println("Publication supprimée avec succés");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Publication afficherPublication(int idP){
        Publication p = new Publication();
        try {
            String req = "SELECT * FROM commentaire WHERE idCommentaire = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1,idP);
            ResultSet rs = pst.executeQuery();
            rs.next();
            p.setId_publication(rs.getInt(1));
            p.setTitre(rs.getString(2));
            p.setDesc(rs.getString(3));
            p.setNbrVote(rs.getInt(4));
            p.setNbrVote(rs.getInt(5));
            p.setId_client(rs.getInt(6));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return p;
    }

    public List<Publication> afficherPublication() {
        List listePublications = new ArrayList();
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM publication";
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Publication p = new Publication();
                p.setId_publication(rs.getInt(1));
                p.setTitre(rs.getString(2));
                p.setDesc(rs.getString(3));
                p.setNbrVote(rs.getInt(4));
                p.setNbrVote(rs.getInt(5));
                p.setId_client(rs.getInt(6));
                listePublications.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return listePublications;
    }

}
