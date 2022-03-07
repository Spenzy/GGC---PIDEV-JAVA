/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author msi
 */
public class Plan {
    int idPlan,idStreamer,idEvennement;
    Date date;
    Time heure;
    float duree;
    String Description;

    public Plan() {
    }

    public Plan(int idPlan, int idStreamer, Date date, Time heure, float duree, String Description, int idEvennement) {
        this.idPlan = idPlan;
        this.idStreamer = idStreamer;
        this.idEvennement = idEvennement;
        this.date = date;
        this.heure = heure;
        this.duree = duree;
        this.Description = Description;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public int getIdStreamer() {
        return idStreamer;
    }

    public void setIdStreamer(int idStreamer) {
        this.idStreamer = idStreamer;
    }

    public int getIdEvennement() {
        return idEvennement;
    }

    public void setIdEvennement(int idEvennement) {
        this.idEvennement = idEvennement;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public float getDuree() {
        return duree;
    }

    public void setDuree(float duree) {
        this.duree = duree;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public String toString() {
        return "Plan{" + "idPlan=" + idPlan + ", idStreamer=" + idStreamer + ", idEvennement=" + idEvennement + ", date=" + date + ", heure=" + heure + ", duree=" + duree + ", Description=" + Description + '}';
    }
    
}
