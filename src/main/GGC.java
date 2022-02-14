/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entities.Plan;
import entities.Streamer;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.PlanCRUD;
import services.StreamerCRUD;

/**
 *
 * @author msi
 */
public class GGC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Date date = null;
        
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = new Date(sdformat.parse("2020-01-25").getTime());
        } catch (ParseException ex) {
            Logger.getLogger(GGC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        StreamerCRUD sc = new StreamerCRUD();
        Streamer s1 = new Streamer(30,"ahmed","mezni",date,"ahmed.mezni@esprit.tn",54405584,"ahmed","In ancient Arab mythology","https://www.nimo.tv/omarmallek");
        Streamer s2 = new Streamer(48,"oussema","beloumi",date,"bnl.ff@esprit.tn",54405584,"ahmed","best of ff in serveur mena","https://www.nimo.tv/bnl");
        
        //////////////////////////ajouter Streamer///////////////////////      
        //sc.ajouterStreamer(s1);
        //sc.ajouterStreamer(s2);
        ////////////////////Modifier Streamer///////////////////
        Streamer s = new Streamer();
        //s = sc.afficherStramerid(45);
        //s.setInformations("1ere place 2019");
        //sc.modifierStreamer(s);
        ////////////////////////////////Suprimer Streamers///////////////////////////////
        //sc.supprimerStreamer(35);
        ///////////////////Afficher Streamers////////////////////////////////////
        //System.out.println(sc.afficherSt());
        
        ///////////////////////////////////////
        
        PlanCRUD pc = new PlanCRUD();
        Plan p1 = new Plan(1,54,date,date,5,"Bech naamlou jaw", 1);
        Plan p2 = new Plan(2,54,date,date,5,"Bech naamlou jaw", 1);
        //////////////////////////ajouter Streamer///////////////////////      
        //pc.ajouterPlan(p1);
        //pc.ajouterPlan(p2);
        ////////////////////Modifier Streamer///////////////////
        Plan p = new Plan();
        //p = pc.afficherPlan(45);
        //p.setDescription("Battalt nalaabou league khir");
        //p.modifierPlan(s);
        ////////////////////////////////Suprimer Streamers///////////////////////////////
        //pc.supprimerPlan(1);
        ///////////////////Afficher Streamers////////////////////////////////////
        System.out.println(pc.afficherPlan());
         


    }
}

     
