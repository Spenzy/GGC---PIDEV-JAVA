/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint1.pidev.principal;

import java.sql.Date;
import sprint1.pidev.entities.Commande;
import sprint1.pidev.entities.Livraison;
import sprint1.pidev.services.CommandeCRUD;
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
        Commande c1 = new Commande(6253, 9512, 2, "2 rue des narcisses, carthage byrsa", 100);
        CommandeCRUD c = new CommandeCRUD();

        if (c.VerifClient(c1.getIdClient())) {
            if (c.VerifProduit(c1.getIdProduit())) {
                c.ajouterCommande(c1);
            } else {
                System.out.println("Erreur ajout commande, Ce produit n'existe pas");
            }
        } else {
            System.out.println("Erreur ajout commande, Ce client n'existe pas");
        }
        System.out.println(c.afficher());

        
        c1.setIdCommande(1);
        c1.setLivree(true);
        c1.setAdresse("gammarth");
        c1.setIdClient(6253);
        c1.setIdProduit(9512);
        if (c.VerifCommande(c1.getIdCommande())) {
            if (c.VerifClient(c1.getIdClient())) {
                if (c.VerifProduit(c1.getIdProduit())) {
                    c.modifierCommande(c1);
                } else {
                    System.out.println("Erreur modification commande, Ce produit n'existe pas");
                }
            } else {
                System.out.println("Erreur modification commande, Ce client n'existe pas");
            }
        } else {
            System.out.println("Erreur modification commande, Cette commande n'existe pas");
        }
        
        if(c.VerifCommande(21)){
            c.supprimerCommande(21);
        }else {
            System.out.println("Erreur suppression commande, Cette commande n'existe pas");
        }
        
        
        
        
        //test CRUD livraison
        /*LivraisonCRUD l = new LivraisonCRUD();
        Date date = new Date(2000, 2, 2);
        Livraison l1 = new Livraison(1, 111, date);

        if (l.VerifCommande(l1.getIdCommande())) {
            System.out.println("Erreur d'ajout , la livraison de cette commande existe déja");
        } else if (l.VerifLivreur(l1.getIdLivreur())) {
            l.ajouterLivraison(l1);
        } else {
            System.out.println("Erreur d'ajout livraison, ce livreur n'existe pas");

        }

        l1.setIdLivreur(6253);
        if (l.VerifCommande(l1.getIdCommande())) {
            if (l.VerifLivreur(l1.getIdLivreur())) {
                l.modifierLivraison(l1);
                System.out.println("Livraison modifiée");
            } else {
                System.out.println("ce livreur n'existe pas");
            }
        } else {
            System.out.println("Erreur de modification, cette livraison n'existe pas (idCommande incorrecte)");
        }

        if (l.VerifCommande(1223)) {
            l.supprimerLivraison(1);
            System.out.println("Livraison supprimée");
        } else {
            System.out.println("Erreur de suppression, cette livraison n'existe pas (idCommande incorrecte)");
        }*/
    }

}
