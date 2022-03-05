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

/**
 *
 * @author Dell
 */
public class Client extends Personne {

    private int idClient;
    private int nbrAvertissement;
    private int ban;
    private int etat;
    private Date dateDebutBlock;
    private Date dateFinBlock;

    public Client() {
    }

    public Client(Integer idClient, Integer nbrAvertissement, Integer ban, Date dateDebutBlock, Date dateFinBlock) {
        this.idClient = idClient;
        this.nbrAvertissement = nbrAvertissement;
        this.ban = ban;
        this.dateDebutBlock = dateDebutBlock;
        this.dateFinBlock = dateFinBlock;
    }

    public Client(String nom, String prenom, Date dateNaissance, String email, int telephone, String password) {
        super(nom, prenom, dateNaissance, email, telephone, password);
    }

    public Client(int id_personne, String nom, String prenom, Date dateNaissance, String email, int telephone, String password, int nbrAvertissement, int ban, Date dateDebutBlock, Date dateFinBlock) {
        super(id_personne, nom, prenom, dateNaissance, email, telephone, password);
        this.nbrAvertissement = nbrAvertissement;
        this.ban = ban;
        this.dateDebutBlock = dateDebutBlock;
        this.dateFinBlock = dateFinBlock;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getNbrAvertissement() {
        return nbrAvertissement;
    }

    public int getBan() {
        return ban;
    }

    public int getEtat() {
        return etat;
    }

    public Date getDateDebutBlock() {
        return dateDebutBlock;
    }

    public Date getDateFinBlock() {
        return dateFinBlock;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setNbrAvertissement(int nbrAvertissement) {
        this.nbrAvertissement = nbrAvertissement;
    }

    public void setBan(int ban) {
        this.ban = ban;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setDateDebutBlock(Date dateDebutBlock) {
        this.dateDebutBlock = dateDebutBlock;
    }

    public void setDateFinBlock(Date dateFinBlock) {
        this.dateFinBlock = dateFinBlock;
    }

    @Override
    public String toString() {
        return "Client{" + "idClient=" + idClient + ", nbrAvertissement=" + nbrAvertissement + ", ban=" + ban + ", dateDebutBlock=" + dateDebutBlock + ", dateFinBlock=" + dateFinBlock + '}';
    }

}
