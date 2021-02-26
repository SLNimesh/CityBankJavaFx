package com.citybank.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class SampleController {

    @FXML
    private Button loginButton;

    @FXML
    private Label loginLabel;

    @FXML
    private Circle midCircle;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        System.out.println("Login button clicked");
        loginLabel.setText("Logged");
        midCircle.setFill(Paint.valueOf("#191926"));
    }
}
