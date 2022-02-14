/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;

/**
 *
 * @author Dell
 */
public class Moderateur extends Personne {

    private int id_moderateur;

    public Moderateur() {
    }

    public Moderateur(int id_moderateur, String nom, String prenom, Date dateNaissance, String email, int telephone, String password) {
        super(nom, prenom, dateNaissance, email, telephone, password);
        this.id_moderateur = id_moderateur;
    }
    

/*
    public Moderateur(int id_personne, String nom, String prenom, Date dateNaissance, String email, int telephone, String password) {
        super(id_personne, nom, prenom, dateNaissance, email, telephone, password);
        
        
    }
*/
/*
       public Moderateur(int id_moderateur, String nom, String prenom, Date dateNaissance, String email, int telephone, String password) {
        this.id_moderateur=id_moderateur;
        Super(nom, prenom, dateNaissance, email, telephone, password);
        
        
    }
*/


    public int getId_moderateur() {
        return id_moderateur;
    }

    public void setId_moderateur(int id_moderateur) {
        this.id_moderateur = id_moderateur;
    }

    @Override
    public String toString() {
        return "Moderateur{" + "id_moderateur=" + id_moderateur + '}';
    }

}
