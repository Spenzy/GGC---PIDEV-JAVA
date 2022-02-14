/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Entities.Evenement;
import Entities.Participation;
import Services.EvenementCrud;
import Services.ParticipationCrud;
import Utils.MyConnection;
import java.sql.Date;

/**
 *
 * @author Azer Lahmer
 */
public class PiDev {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        MyConnection mc = MyConnection.getInstance();
        EvenementCrud ec = new EvenementCrud();
        ParticipationCrud pc = new ParticipationCrud();
        
        Date date = new Date(1998, 12, 1);
        Evenement e = new Evenement(1, date, "02:06", "03:06", "sousse", "Azer particpant", 10);
        Evenement e1 = new Evenement(2, date, "02:06", "03:06", "sousse", "Azer particpant", 12);
        Evenement e2 = new Evenement(3, date, "02:06", "03:06", "soussa", "Azer particpant", 11);
        Participation p = new Participation(1,1,1,3);
        Participation p1 = new Participation(2,2,2,4);
          Participation p2 = new Participation(3,3,2,4);
           Participation p3 = new Participation(4,3,1,2);
           Participation p4 = new Participation(5,3,1,2);
           
        //**************************CRUD EVENEMENT**************************
        //**Ajout d'un evenement** 
        //ec.ajouterEvenement(e);
        //System.out.println(ec.ajouterEvenement(e1));
        //System.out.println(ec.VerifEvenement(2));
        
        //**Modifier evenement**
        //e.setLocalisation("Tunis");
        //ec.modifierEvenement(e);
        
        //**Supprimer Evenement** 
        //ec.supprimerEvenement(4);
        
        //**Afficher Evenement**
        //System.out.println(ec.afficher());
        
        //*********************Crud Participation****************
        
        //**Ajouter participation**
        //pc.ajouterParticipation(p4);
        pc.participer(1,1,1,2);
        
        //**Supprimer Participation**
        //pc.supprimerParticipation(1);
        
        //**Afficher Participation**
        //System.out.println(pc.afficherParticipation());
    }

}
