package com.citybank.scenes;

import com.citybank.BankService;
import com.citybank.model.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginFormController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorText;


    @FXML
    void authenticate(ActionEvent event) {
        System.out.println("Authenticate user " + username.getText() + " " +password.getText());
        errorText.setText("");
        try {
            BankService.authenticate(username.getText(), password.getText());
        }catch (ServiceException e){
            errorText.setText("" + e.getMessage());
        }
    }

}
