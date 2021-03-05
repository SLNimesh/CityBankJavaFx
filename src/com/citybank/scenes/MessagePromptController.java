package com.citybank.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MessagePromptController implements Initializable {

    private static String msg;

    @FXML
    private TextArea msgText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        msgText.setText(msg);
        msgText.setEditable(false);
    }

    public static void setMsg(String msgTxt){
        msg = msgTxt;
    }

    @FXML
    void okButton(ActionEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
