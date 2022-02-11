/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javaapplication12.entities.Avis;
import javaapplication12.entities.Produit;
import javaapplication12.utils.MyConnection;

/**
 *
 * @author Mr
 */
public class AvisCRUD {
    Connection cnxx;

    public AvisCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public void ajouterAvis(Avis a) {

        String req = "INSERT INTO avis (referenceProduit,description,type) VALUES (?,?,?)";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, a.getReferenceProduit());
            pst.setString(2, a.getDescription());
            pst.setString(3, a.getType());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
        public void supprimerAvis(int idAvis) {

        String req = "delete from avis where idAvis = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idAvis);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
        public void supprimerAvisParProduit(int referenceProduit) {

        String req = "delete from avis where referenceProduit=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, referenceProduit);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
        public void modifierAvis(Avis a) {

        String req = "update avis set referenceProduit=? ,description=?, type=? where idAvis = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(4, a.getIdAvis());
            pst.setInt(1, a.getReferenceProduit());
            pst.setString(2, a.getDescription());
            pst.setString(3, a.getType());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public List<Avis> afficher() {

        List<Avis> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM avis";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {

                Avis a = new Avis();
                a.setIdAvis(rs.getInt(1));
                a.setReferenceProduit(rs.getInt(2));
                a.setDescription(rs.getString(3));
                a.setType(rs.getString(4));
                myList.add(a);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return myList;
    }
}
