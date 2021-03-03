package com.citybank.scenes;

import com.citybank.model.AccountHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisteredController implements Initializable {

    private static AccountHolder userContext;

    @FXML
    private Label name = new Label();

    @FXML
    private Label nic = new Label();

    @FXML
    private TextField bAssId = new TextField();

    public static void setUserContext(AccountHolder user) {
       userContext = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(userContext.getName());
        nic.setText(userContext.getNIC());
        bAssId.setText(userContext.getBankAssignedId());
        bAssId.setEditable(false);
    }

    @FXML
    void closeWindow(ActionEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
