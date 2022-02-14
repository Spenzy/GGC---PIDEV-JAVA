/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;

/**
 *
 * @author Azer Lahmer
 */
public class Evenement {

    private int reference;
    private Date dateDebut;
    private String heureDebut;
    private String heureFin;
    private String localisation;
    private String description;
    private int nbrParticipant;

    public Evenement() {
    }

    public Evenement(int reference) {
        this.reference = reference;
    }

    public Evenement(int reference, Date dateDebut, String heureDebut, String heureFin, String localisation, String description, int nbrParticipant) {
        this.reference = reference;
        this.dateDebut = dateDebut;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.localisation = localisation;
        this.description = description;
        this.nbrParticipant = nbrParticipant;
    }

    public int getReference() {
        return reference;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getDescription() {
        return description;
    }

    public int getNbrParticipant() {
        return nbrParticipant;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNbrParticipant(int nbrParticipant) {
        this.nbrParticipant = nbrParticipant;
    }

    @Override
    public String toString() {
        return "Evenement{" + "reference=" + reference + ", dateDebut=" + dateDebut + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + ", localisation=" + localisation + ", description=" + description + ", nbrParticipant=" + nbrParticipant + '}';
    }

}
