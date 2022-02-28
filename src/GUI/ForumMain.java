/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Spenz
 */
public class ForumMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        int idClient = 5;
        ForumHomeGUIController fhc = new ForumHomeGUIController();

        primaryStage.setScene(fhc.refreshForum(idClient));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
