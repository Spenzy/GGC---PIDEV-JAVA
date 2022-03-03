/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.control.TextField;
import animatefx.animation.Shake;
/**
 *
 * @author Dell
 */
public class DataValidation {
    public static boolean emailFormat(TextField inputTextField) {
        boolean isEmail = true;

        if (!inputTextField.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com")) {
            isEmail = false;
        inputTextField.setStyle("-fx-border-color: red");
        new Shake(inputTextField).play();

        }
          else
        {
            inputTextField.setStyle("-fx-border-color: transparent");

        }
        return isEmail;

    }
    public static boolean dataLength(TextField inputTextField, String requiredLength) {
        boolean isDataLength = true;
        String validationString = null;

        if (!inputTextField.getText().matches("\\b\\w" + "{" + requiredLength + "}")) {
            isDataLength = false;
                    inputTextField.setStyle("-fx-border-color: red");
                            new Shake(inputTextField).play();


        }
        else
        {
                        inputTextField.setStyle("-fx-border-color: transparent");

        }
        return isDataLength;

    }
    public static boolean textAlphabet(TextField inputTextField) {
        boolean isAlphabet = true;
        String validationString = null;

        if (!inputTextField.getText().matches("[a-z A-Z]+")) {
            isAlphabet = false;
                     inputTextField.setStyle("-fx-border-color: red");
                            new Shake(inputTextField).play();
        }
  else
        {
                        inputTextField.setStyle("-fx-border-color: transparent");

        }
        return isAlphabet;

    }
     public static boolean textNumeric(TextField inputTextField) {
        boolean isNumeric = true;

        if (!inputTextField.getText().matches("[0-9]+")) {
            isNumeric = false;
                    inputTextField.setStyle("-fx-border-color: red");
        new Shake(inputTextField).play();

        }
        else
        {
            inputTextField.setStyle("-fx-border-color: transparent");

        }
        
        return isNumeric;

    }
     public static boolean isNotEmpty(TextField inputTextField) {
        boolean isNull = true;


        //point out difference between null and isEmpty() *FIND OUT WHEN TO USE NULL
        if (inputTextField.getText().isEmpty()) {
            isNull = false;
              inputTextField.setStyle("-fx-border-color: red");
        new Shake(inputTextField).play();
        }
          else
        {
            inputTextField.setStyle("-fx-border-color: transparent");

        }
 

        return isNull;

    }
    
}
