/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Statement;
import entities.Client;
import entities.Personne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.MyConnection;

/**
 *
 * @author Dell
 */
public class ClientCRUD extends PersonneCRUD {

    Connection cnxx;

    public ClientCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public void ajouterClient(Client c) {
        int idP = ajouterPersonne(c);
        String req = "INSERT INTO client (idClient,nbrAvertissement,ban,etat,dateDebutBlock,dateFinBlock) VALUES (?,?,?,?,?,?)";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS); //returns generated DB keys
            pst.setInt(1, idP);
            pst.setInt(2, c.getNbrAvertissement());
            pst.setInt(3, c.getBan());
            pst.setInt(4, c.getEtat());
            pst.setDate(5, c.getDateDebutBlock());
            pst.setDate(6, c.getDateFinBlock());
            pst.executeUpdate();
            System.out.println("Client ajoute");
            ResultSet rs = pst.getGeneratedKeys();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void modifierClient(Client c) {

        // int modif = modifierPersonne(c);  
        String req = "update client set nbrAvertissement=?,ban=?,etat=?,dateDebutBlock=?,dateFinBlock=? WHERE idClient=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, c.getNbrAvertissement());
            pst.setInt(2, c.getBan());
            pst.setInt(3, c.getEtat());
            pst.setDate(4, c.getDateDebutBlock());
            pst.setDate(5, c.getDateFinBlock());
            pst.setInt(6, c.getIdClient());
            pst.executeUpdate();
            System.out.println("client modifier avec succes");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void supprimerClient(int idClient) {

        String req = "delete from client where idClient=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idClient);
            pst.executeUpdate();
            System.out.println("client suprimée");
            supprimerPersonne(idClient);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public Client afficherClientid(int idc) {
        Client c = new Client();
        try {
            String req = "SELECT * FROM client WHERE idClient = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idc);
            ResultSet rs = pst.executeQuery();
            rs.next();
            c.setIdClient(rs.getInt(1));
            c.setNbrAvertissement(rs.getInt(2));
            c.setBan(rs.getInt(3));
            c.setEtat(rs.getInt(4));
            c.setDateDebutBlock(rs.getDate(5));
            c.setDateFinBlock(rs.getDate(6));

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return c;
    }

    public List<Client> afficherCl() {

        List<Client> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM client,personne";

            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                Client c = new Client();
                c.setIdClient(rs.getInt(1));
                c.setNbrAvertissement(rs.getInt(2));
                c.setBan(rs.getInt(3));
                c.setEtat(rs.getInt(4));
                c.setDateDebutBlock(rs.getDate(5));
                c.setDateFinBlock(rs.getDate(6));
                c.setNom(rs.getString(7));

                myList.add(c);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //return null;
        }
        return myList;
    }

}
