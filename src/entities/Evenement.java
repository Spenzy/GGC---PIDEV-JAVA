/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import java.time.LocalDate;
import javafx.scene.control.Button;

/**
 *
 * @author Azer Lahmer
 */
public class Evenement {

    private int reference;
    private Date dateDebut;
    private Date dateFin;
    //private String heureDebut;
    //private String heureFin;
    private String localisation;
    private String description;
    private int nbrParticipant;
    //private String image ;
    private Button participer;

    public Evenement() {
    }

    public Evenement(int reference) {
        this.reference = reference;
    }

    public Evenement(int reference, Date dateDebut, Date dateFin, String localisation, String description, int nbrParticipant) {
        this.reference = reference;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.localisation = localisation;
        this.description = description;
        this.nbrParticipant = nbrParticipant;
        //this.image = image;
    }



    public int getReference() {
        return reference;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    /*public String getHeureDebut() {
        return heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }*/

    public String getLocalisation() {
        return localisation;
    }

    public String getDescription() {
        return description;
    }

    public int getNbrParticipant() {
        return nbrParticipant;
    }

//    public String getImage() {
//        return image;
//    }

//    public void setImage(String image) {
//        this.image = image;
//    }
    

    public void setReference(int reference) {
        this.reference = reference;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateFin() {
        return dateFin;
    }

   /* public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }*/

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
        return "Evenement{" + "reference=" + reference + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin  + ", localisation=" + localisation + ", description=" + description + ", nbrParticipant=" + nbrParticipant + '}';
    }

}
