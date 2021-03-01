package com.citybank.scenes;

import com.citybank.BankService;
import com.citybank.Main;
import com.citybank.model.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorText;


    @FXML
    void authenticate(ActionEvent event) throws IOException {
        System.out.println("Authenticate user " + username.getText() + " " + password.getText());
        errorText.setText("");
        try {
            BankService.authenticate(username.getText(), password.getText());
            Parent root = FXMLLoader.load(getClass().getResource("CashierView.fxml"));
            Main.window.setScene(new Scene(root, 1280, 720));

        } catch (ServiceException e) {
            errorText.setText("" + e.getMessage());
        }
    }
}
