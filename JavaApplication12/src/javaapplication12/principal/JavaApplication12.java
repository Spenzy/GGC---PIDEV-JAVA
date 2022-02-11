/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12.principal;

import javaapplication12.entities.Avis;
import javaapplication12.entities.Produit;
import javaapplication12.services.AvisCRUD;
import javaapplication12.services.ProduitCRUD;

/**
 *
 * @author Mr
 */
public class JavaApplication12 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//MyConnection mc =  MyConnection.getInstance();

        ProduitCRUD p = new ProduitCRUD();

        Produit p1 = new Produit(9512, "A32", "Telephone", "ecran...camera....", (float) 700.5);
        Produit p2 = new Produit(5632, "bleudragon", "test", "testestest", (float) 1000.5);
        Produit p3 = new Produit(999, "paul", "Clavier", "testestest", (float) 300.5);
        //p.ajouterProduit(p1);
        //p.ajouterProduit(p2);
        //p.ajouterProduit(p3);
        System.out.println(p.afficher());

        p1.setCategorie("Clavier");
        p1.setNom("RedDragonKumara");
        p1.setFiche_technique("RGBQ lights");
        p1.setPrix((float) 290);

        //p.modifierProduit(p1);
        System.out.println(p.afficher());

        //p.supprimerProduit(5632);
        System.out.println(p.afficher());

        //*************
        AvisCRUD a = new AvisCRUD();

        Avis a1 = new Avis(6, 9512, "ce clavier est excellent", "excellent");

        //a.ajouterAvis(a1);
        System.out.println(a.afficher());

        a1.setDescription("ce clavier n'est pas excellent ");
        a1.setType("mediocre");

        //a.modifierAvis(a1);
        System.out.println(a.afficher());

        //a.supprimerAvis(6);
        System.out.println(a.afficher());

        //a.supprimerAvisParProduit(9512);
        System.out.println(a.afficher());

        p.remiseCategorie("fvghb", 50);
        p.remiseCategorie("Clavier", 50);
    }

}
