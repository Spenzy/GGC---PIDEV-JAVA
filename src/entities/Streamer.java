/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;

/**
 *
 * @author msi
 */
public class Streamer extends Personne{
    private int idStreamer;
    private String informations;
    private String lienStreaming;

    public Streamer() {
    }

    public Streamer(int id_personne, String nom, String prenom, Date dateNaissance, String email, int telephone, String password,String informations, String lienStreaming ) {
        super(id_personne, nom, prenom, dateNaissance, email, telephone, password);
        this.informations = informations;
        this.lienStreaming = lienStreaming;
    }

    public Streamer(int aInt, String string, String string0) {
        this.idStreamer=aInt;
        this.informations=string;
        this.lienStreaming=string0;
    }

    public int getIdStreamer() {
        return idStreamer;
    }

    public String getInformations() {
        return informations;
    }

    public String getLienStreaming() {
        return lienStreaming;
    }

    public void setIdStreamer(int idStreamer) {
        this.idStreamer = idStreamer;
    }

    public void setInformations(String informations) {
        this.informations = informations;
    }

    public void setLienStreaming(String lienStreaming) {
        this.lienStreaming = lienStreaming;
    }

    @Override
    public String toString() {
        return "Streamer{" + "idStreamer=" + idStreamer + ", informations=" + informations + ", lienStreaming=" + lienStreaming + '}';
    }
    
    
    
}
