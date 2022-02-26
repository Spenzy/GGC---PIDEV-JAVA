package services;

import entities.Commentaire;
import utils.MyConnection;

import java.sql.*;
import static java.sql.JDBCType.NULL;
import java.util.ArrayList;
import java.util.List;


public class CommentaireCRUD {
    Connection cnxx;

    public CommentaireCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public boolean verifCommentaire(Commentaire c){ //on teste si le champ titre est vide oubien nulle on retourne faux;
        return !c.getDescription().equals("") && !c.getDescription().equals(NULL);
    };

    public int ajouterCommentaire(Commentaire c) {
        int id = 0;
        if (verifCommentaire(c)) {
            String req = "INSERT INTO commentaire (idPublication,description,idClient,date) VALUES (?,?,?,?)";
            try {
                PreparedStatement pst = cnxx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, c.getId_publication());
                pst.setString(2, c.getDescription());
                pst.setInt(3, c.getIdClient());
                pst.setDate(4, c.getDatePost());
                pst.executeUpdate(); //execution update query
                //execution d'autoarchivage
                //pc.autoArchive(pc.afficherPublication(c.getIdClient()), LocalDateTime.now());
                System.out.println("Commentaire ajouté avec succés");
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Champ description invalide");
        }
        return id;
    }

    public void modifierCommentaire(Commentaire c) {
        String req = "UPDATE commentaire SET idPublication=?,idClient=? ,description=? WHERE idCommentaire = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, c.getId_publication());
            pst.setInt(2, c.getIdClient());
            pst.setString(3, c.getDescription());
            pst.setInt(4, c.getId_commentaire());
            pst.executeUpdate();
            System.out.println("Commentaire modifié avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void supprimerCommentaire(int idCommentaire) {
        String req = "delete from commentaire where idCommentaire = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idCommentaire);
            pst.executeUpdate();
            System.out.println("Commentaire supprimé avec succés");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Commentaire afficherCommentaire(int idCommentaire){
        Commentaire c = new Commentaire();
        try {
            String req = "SELECT * FROM commentaire WHERE idCommentaire = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1,idCommentaire);
            ResultSet rs = pst.executeQuery();
            rs.next();
            c.setId_commentaire(rs.getInt(1));
            c.setId_publication(rs.getInt(2));
            c.setDescription(rs.getString(3));
            c.setIdClient(rs.getInt(4));
            c.setDatePost(rs.getDate(5));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return c;
    }

    public List<Commentaire> afficherCommentaires(int idPub) {
        ArrayList listeCommentaires = new ArrayList();
        try {
            String req = "SELECT * FROM commentaire WHERE idPublication = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1,idPub);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Commentaire c = new Commentaire();
                c.setId_commentaire(rs.getInt(1));
                c.setId_publication(rs.getInt(2));
                c.setDescription(rs.getString(3));
                c.setIdClient(rs.getInt(4));
                c.setDatePost(rs.getDate(5));
                listeCommentaires.add(c);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return listeCommentaires;
    }
    
    public int calculNbrCommentaire(int idP) {
        String req = "SELECT count(*) FROM commentaire WHERE idPublication = ?";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idP);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return -1; //en cas d'erreur
    }

}
