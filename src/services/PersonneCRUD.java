/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import GUI.homePage;
import entities.Personne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import utils.MyConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class PersonneCRUD {

    Connection cnxx;
    Authentification aa = new Authentification();
    private Statement st;

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

    public boolean ResetPass1(String Email, String Token) {
        String qry = "UPDATE Personne SET  password='" + Token + "' WHERE email='" + Email + "' ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(qry);
            pst.setString(1, Token);

            if (pst.executeUpdate() > 0) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonneCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public boolean ResetPass(String Email, String Token) {
        String qry = "UPDATE personne p SET p.password= ? WHERE email= ? ";
        try {
            PreparedStatement pst = cnxx.prepareStatement(qry);
            pst.setString(1, aa.hashagePWD(Token));
            pst.setString(2, Email);
            int res = pst.executeUpdate();
            if (res > 0) {
                return true;
            } else {
                System.out.println("Erreur");

            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonneCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

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

    public boolean getUserBy(String email, String pwdId) {
        String requete = "SELECT id_personne, password FROM personne,client"
                + " WHERE ( email = ? ) AND (id_personne = idClient)";
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

    public boolean getModerateurBy(String email, String pwdId) {

        String requete = "SELECT id_personne, password FROM personne,moderateur"
                + " WHERE ( email = ? ) AND (id_personne = id_moderateur)";
        try {
            PreparedStatement ps = cnxx.prepareStatement(requete);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            System.out.println("1111");
            if (rs.next()) {
                String pwdBD = rs.getString(2);
                if (aa.hashagePWD(pwdId).equals(pwdBD)) {
                    System.out.println("2222");
                    int idUser = rs.getInt(1);
                    homePage.loggedInID = idUser;
                    System.out.println("33333");
                    return true;

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("444444");
        return false;

    }

    public String getUsername(int id_personne) {
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT nom,prenom FROM Personne where id_personne=" + id_personne;
            ResultSet rs;
            rs = st.executeQuery(req);
            if (rs.next()) {
                return rs.getString(1) + " " + rs.getString(2);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return "";
    }

    public boolean isAdmin(int id_personne) {
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT id_admin FROM admin where id_admin=" + id_personne;
            ResultSet rs;
            rs = st.executeQuery(req);
            return rs.next();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return false;
    }

    public Personne afficherPersonne(int id_personne) {
        Personne p = new Personne();
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM Personne WHERE id_personne = " + id_personne;
            ResultSet rs;
            rs = st.executeQuery(req);
            if (rs.next()) {
                p.setId_personne(rs.getInt(1));
                p.setNom(rs.getString(2));
                p.setPrenom(rs.getString(3));
                p.setDateNaissance(rs.getDate(4));
                p.setEmail(rs.getString(5));
                p.setTelephone(rs.getInt(6));
                p.setPassword(rs.getString(7));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return p;
    }
    
   
    

}
