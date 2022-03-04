/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint1.pidev.principal;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;

import sprint1.pidev.entities.Commande;
import sprint1.pidev.entities.LigneCommande;
import sprint1.pidev.entities.Livraison;
import sprint1.pidev.services.CommandeCRUD;
import sprint1.pidev.services.LigneCommandeCRUD;
import sprint1.pidev.services.LivraisonCRUD;

/**
 *
 * @author Marwa
 */
public class Sprint1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //test crud commande
        java.sql.Date date = null;
        try {
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");//préparation du formart de la date
            date = new java.sql.Date(sdformat.parse("2020-01-25").getTime());//création de la date de la livraison
        } catch (ParseException exp) {
            exp.getMessage();
        }
        Commande c1 = new Commande(111, "carthage byrsa");
        LigneCommande lc1 = new LigneCommande(1, 1010, 2, 0);
        LigneCommande lc2 = new LigneCommande(9, 1010, 2, 100);

        CommandeCRUD c = new CommandeCRUD();
        LigneCommandeCRUD LC = new LigneCommandeCRUD();
System.out.println(c.affecterProduit());
        
        /*
        //ajout commande
        //ajout lignes commande
        //calcul prix commande
        if (c.VerifClient(c1.getIdClient())) {
            c.ajouterCommande(c1);
            lc1.setIdCommande(c1.getIdCommande());
            lc2.setIdCommande(c1.getIdCommande());
            LC.ajouterLigneCommande(lc1);
            LC.ajouterLigneCommande(lc2);
            c.calculPrixCommande(c1.getIdCommande());

        } else {
            System.out.println("Erreur ajout commande, Ce client n'existe pas");
        }
*/
        
        //affichage des commandes suivies de leurs lignes
     /*   System.out.println(c.afficher());

        
        
        
        //modification commande
        //modification lignes commandes
        c1.setIdCommande(9);
        c1.setLivree(true);
        c1.setAdresse("gammarth");*/

        /*if (c.VerifCommande(c1.getIdCommande())) {
            if (c.VerifClient(c1.getIdClient())) {
                    c.modifierCommande(c1);
                    System.out.println(c.afficher());            
            } else {
                System.out.println("Erreur modification commande, Ce client n'existe pas");
            }
        } else {
            System.out.println("Erreur modification commande, Cette commande n'existe pas");
        }*/
        
        
      /*  //test métier calcul prix ligne commande (jointure 2 tables)
        lc1.setIdLigne(13);
        lc1.setIdCommande(9);
        lc1.setIdProduit(9512);
        lc1.setQuantite(5);
        //LC.calculPrixLigne(lc1);*/
        
        /*
        //modification d'une ligne 
        lc2.setIdLigne(12);
        lc2.setQuantite(10);
        if (LC.VerifProduit(lc2.getIdProduit())) {
                    LC.modifierLigneCommande(lc2);
                    System.out.println(c.afficher());            
        } else {
            System.out.println("Erreur modification ligne de commande, Ce produit n'existe pas");
        }
        */
        
        
        
        //suppression commande (vérification existence)  -> fi westha isir appel lel suppression de toutes les lignes d'une commande
 /*       if (c.VerifCommande(8)) {
            c.supprimerCommande(8);
            System.out.println(c.afficher());
        } else {
            System.out.println("Erreur suppression commande, Cette commande n'existe pas");
        }*/
 
 
 
 
 

        //test CRUD livraison
        // LivraisonCRUD l = new LivraisonCRUD();

      /*  java.sql.Date date=null;
        try{
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");//préparation du formart de la date
            date = new java.sql.Date(sdformat.parse("2020-01-25").getTime());//création de la date de la livraison
        }catch(ParseException exp ){
            exp.getMessage();
        }
        

        Livraison l1 = new Livraison(465,111 , date);*/
 /*  if (l.VerifCommande(l1.getIdCommande())) {
            System.out.println("Erreur d'ajout , la livraison de cette commande existe déja");
        } else if (l.VerifLivreur(l1.getIdLivreur())) {
            l.ajouterLivraison(l1);
            System.out.println(l.afficher());

        } else {
            System.out.println("Erreur d'ajout livraison, ce livreur n'existe pas");

        }
        
        l1.setIdLivreur(6253);
        if (l.VerifCommande(l1.getIdCommande())) {
            if (l.VerifLivreur(l1.getIdLivreur())) {
                l.modifierLivraison(l1);
                System.out.println(l.afficher());
                System.out.println("Livraison modifiée");
            } else {
                System.out.println("ce livreur n'existe pas");
            }
        } else {
            System.out.println("Erreur de modification, cette livraison n'existe pas (idCommande incorrecte)");
        }
         


  if (l.VerifCommande(1223)) {
            l.supprimerLivraison(1223);
            System.out.println("Livraison supprimée");
        } else {
            System.out.println("Erreur de suppression, cette livraison n'existe pas (idCommande incorrecte)");
        }
         */
        //System.out.println(l.afficher());
        //l.RemiseLivraison();System.out.println(l.afficher());//remise livraison ssi elle n'est pas livrée et en retard (jointure)
        //c.commandeLivree(c1);System.out.println(l.afficher());
        //System.out.println(l.recupererMailClient(456)); //test récuperer email client pour le mail d'excuse*/*/
    }

}
