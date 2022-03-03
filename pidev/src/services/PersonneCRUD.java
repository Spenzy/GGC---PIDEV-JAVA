/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import Bcrypt.BCrypt;
import GUI.homePage;
import entities.Client;
import entities.Personne;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import utils.MyConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Dell
 */
public class PersonneCRUD {

    Connection cnxx;
    Authentification aa = new Authentification();

    public PersonneCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public int ajouterPersonne(Personne P) {
        int id = 0;
        String req = "INSERT INTO Personne (nom,prenom,dateNaissance,email,telephone,password) VALUES (?,?,?,?,?,?)";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS); //returns generated DB keys
            pst.setString(1, P.getNom());
            pst.setString(2, P.getPrenom());
            pst.setDate(3, P.getDateNaissance());
            pst.setString(4, P.getEmail());
            pst.setInt(5, P.getTelephone());
            pst.setString(6, P.getPassword());
            pst.executeUpdate();
            System.out.println("personne ajoute");
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }

    public void modifierPersonne(Personne p) {
        //  int id = 0;
        String req = "update Personne set nom=?,prenom=?,dateNaissance=?,email=?,telephone=?,password=? WHERE id_personne=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);

            pst.setString(1, p.getNom());
            pst.setString(2, p.getPrenom());
            pst.setDate(3, p.getDateNaissance());
            pst.setString(4, p.getEmail());
            pst.setInt(5, p.getTelephone());
            pst.setString(6, p.getPassword());
            pst.setInt(7, p.getId_personne());
            pst.executeUpdate();
            System.out.println("personne modifier avec succes");
            /*
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
             */
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        // return id;
    }

    public void supprimerPersonne(int id_personne) {

        String req = "delete from personne where id_personne=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, id_personne);
            pst.executeUpdate();
            System.out.println("Personne suprimée");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    /*public Commentaire afficherCommentaire(int idC){
        Commentaire c = new Commentaire();
        try {
            String req = "SELECT * FROM commentaire WHERE idCommentaire = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1,idC);
            ResultSet rs = pst.executeQuery();
            rs.next();
            c.setId_commentaire(rs.getInt(1));
            c.setId_publication(rs.getInt(2));
            c.setDescription(rs.getString(3));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return c;
    }*/
    public List<Personne> afficher() {

        List<Personne> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM Personne";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {

                Personne p = new Personne();
                p.setId_personne(rs.getInt(1));
                p.setNom(rs.getString(2));
                p.setPrenom(rs.getString(3));
                p.setDateNaissance(rs.getDate(4));
                p.setEmail(rs.getString(5));
                p.setTelephone(rs.getInt(6));
                p.setPassword(rs.getString(7));
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return myList;
    }

    /*
    public List<Personne> afficherParid(int id_personne) {

        List<Personne> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM Personne where id_personne="+ id_personne;
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {

                Personne p = new Personne();
                p.setId_personne(rs.getInt(1));
                p.setNom(rs.getString(2));
                p.setPrenom(rs.getString(3));
                p.setDateNaissance(rs.getDate(4));
                p.setEmail(rs.getString(5));
                p.setTelephone(rs.getInt(6));
                p.setId_personne(id_personne);
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return myList;
    }
     */
    public boolean getUserBy(String email, String pwdId) {
        String requete = "SELECT id_personne, password FROM personne"
                + " WHERE ( email = ? )";
        try {
            PreparedStatement ps = cnxx.prepareStatement(requete);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String pwdBD = rs.getString(2);
                if (aa.hashagePWD(pwdId).equals(pwdBD)) {
                    int idUser = rs.getInt(1);
                    homePage.loggedInID = idUser;
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
