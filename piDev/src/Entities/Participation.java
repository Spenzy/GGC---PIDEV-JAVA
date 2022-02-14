/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Azer Lahmer
 */
public class Participation {

    private int idParticipation;
    private int idClient;
    private int idEvent;
    private int nbrEtoile;

    public Participation() {

    }

    public Participation(int idParticipation, int idClient, int idEvent, int nbrEtoile) {
        this.idParticipation = idParticipation;
        this.idClient = idClient;
        this.idEvent = idEvent;
        this.nbrEtoile = nbrEtoile;
    }

    public int getIdParticipation() {
        return idParticipation;
    }

    public void setIdParticipation(int idParticipation) {
        this.idParticipation = idParticipation;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public void setNbrEtoile(int nbrEtoile) {
        this.nbrEtoile = nbrEtoile;
    }

    public int getNbrEtoile() {
        return nbrEtoile;
    }

    @Override
    public String toString() {
        return "Participation{" + "idParticipation=" + idParticipation + ", idClient=" + idClient + ", idEvent=" + idEvent + ", nbrEtoile=" + nbrEtoile + '}';
    }

    

}
