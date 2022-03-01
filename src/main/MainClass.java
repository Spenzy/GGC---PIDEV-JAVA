package main;

import entities.Commentaire;
import entities.Publication;
import entities.Vote;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import services.CommentaireCRUD;
import services.PublicationCRUD;
import services.VoteCRUD;

import org.apache.log4j.BasicConfigurator;
import utils.MailAPI;
import utils.PdfAPI;

public class MainClass {
    public static void main(String[] args) {
        
        BasicConfigurator.configure();

        //*************Publication
        //Publication p = new Publication(30,"QUESTION?","JUST RANDOM FOLLOW STUFF",false);
        PublicationCRUD pc = new PublicationCRUD();

        //pc.ajouterPublication(p);
        //System.out.println(pc.afficherPublication());
        //p.setDesc("CHANGED ONCE AGAIN YAY");
        //pc.modifierPublication(p);
        //System.out.println(pc.afficherPublication(1));
        //pc.supprimerPublication(1);

        //*************Commentaire
        CommentaireCRUD cc = new CommentaireCRUD();
//        Commentaire c1 = new Commentaire(1,30,"Banana");

        //cc.ajouterCommentaire(c1);
        //System.out.println(cc.afficherCommentaires());
        //Commentaire c12 = new Commentaire(4,1,1,"Patata");
        //cc.modifierCommentaire(c12);
        //System.out.println(cc.afficherCommentaire(5));
        //System.out.println(cc.afficherCommentaires());
        //cc.supprimerCommentaire(1);
        //System.out.println(cc.afficherCommentaires());

        //************Vote
        VoteCRUD vc = new VoteCRUD();
//        Vote v = new Vote(1,1,"UP");

        //vc.ajouterVote(v);
        //Vote ve = new Vote(1,1,"DOWN");
        //vc.modifierVote(ve);
        //System.out.println(vc.afficherVote());
        //vc.supprimerVote(1,1);

        //************testing
        //pc.autoArchive(p);
        
        //***************mails4
//        try {
//            MailAPI.sendMail("dridi.zied@esprit.tn", "Test Api", "Test body");
//        } catch (MessagingException ex) {
//            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //***************Pdf & mailfile
        Publication p = pc.afficherPublication(1);
        List commentaires = cc.afficherCommentaires(1);
        PdfAPI.createAndSendForumPost("dridi.zied@esprit.tn", p, commentaires);
    }
    
}
