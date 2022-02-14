/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Client;
import entities.Moderateur;
import entities.Personne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.MyConnection;

/**
 *
 * @author Dell
 */
public class ModerateurCRUD extends PersonneCRUD {

    Connection cnxx;

    public ModerateurCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public void ajouterModerateur(Moderateur m) {
        int idm = ajouterPersonne(m);
        String req = "INSERT INTO moderateur (id_moderateur) VALUES (?)";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS); //returns generated DB keys
            pst.setInt(1, idm);

            pst.executeUpdate();
            System.out.println("moderateur ajoute");
            ResultSet rs = pst.getGeneratedKeys();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
      public void supprimerModerateur(int id_moderateur) {

        String req = "delete from moderateur where id_moderateur=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, id_moderateur);
            pst.executeUpdate();
            System.out.println("client suprimée");
            supprimerPersonne(id_moderateur);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
    
    

    public Moderateur afficherModerateur(int idm) {
        Moderateur m = new Moderateur();
        try {
            String req = "SELECT * FROM moderateur WHERE id_moderateur = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idm);
            ResultSet rs = pst.executeQuery();
            rs.next();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return m;
    }

    public List<Personne> moderateurafficher(int id_moderateur) {

        List<Personne> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM Personne ";
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
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return myList;
    }
    

}
