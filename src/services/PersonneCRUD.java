/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Personne;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import utils.MyConnection;
import java.sql.SQLException;

/**
 *
 * @author Dell
 */
public class PersonneCRUD {

    Connection cnxx;

    public PersonneCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
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
            String req = "SELECT * FROM Personne WHERE id_personne = "+id_personne;
            ResultSet rs;
            rs = st.executeQuery(req);
            if(rs.next()) {
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