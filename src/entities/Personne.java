/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import pidev.Pidev;

/**
 *
 * @author Dell
 */
public class Personne {

    private int id_personne;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String email;
    private int telephone;
    private String password;

    public Personne() {

    }

    public Personne(int id_personne, String nom, String prenom) {
        this.id_personne = id_personne;
        this.nom = nom;
        this.prenom = prenom;
    }
    

    public Personne(int id_personne, String nom, String prenom, Date dateNaissance, String email, int telephone, String password) {
        this.id_personne = id_personne;
        this.nom = nom;
        this.prenom = prenom;
        
            this.dateNaissance = dateNaissance;
       

        this.email = email;
        this.telephone = telephone;
        this.password = password;
    }

    public Personne(String nom, String prenom, Date dateNaissance, String email, int telephone, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
    }

    public int getId_personne() {
        return id_personne;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public int getTelephone() {
        return telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setId_personne(int id_personne) {
        this.id_personne = id_personne;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Personne{" + "id_personne=" + id_personne + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance + ", email=" + email + ", telephone=" + telephone + ", password=" + password + '}';
    }

}
