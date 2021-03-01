package com.citybank.scenes;

import com.citybank.BankService;
import com.citybank.model.UserContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class CashierViewController implements Initializable {

    private UserContext currentUser;

    @FXML private Label headerGreeting;

    @FXML private Label headerUsername;

    @FXML private Circle profilePic;

    @FXML private ImageView profileImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserContext user = BankService.getCurrentUserContext();
        headerUsername.setText(user.getFirstName().concat(" ").concat(user.getLastName()));
        headerGreeting.setText("Welcome " + user.getRole());
        if (user.getRole().equals("MANAGER")) {
            //  Open ui for manager
            //  Create a new cashier account
            //  Create a new user account
            // Same stuff as an cashier
        } else {
            // Open ui for cashier

        }
    }

    //  CREATE NEW ACCOUNT
    @FXML private TextField bankId;

    @FXML private ChoiceBox<?> branch;

    @FXML private ChoiceBox<?> accType;

    @FXML private TextField initialDeposit;

    //REGISTER
    @FXML private TextField rFullName;

    @FXML private TextField rNic;

    @FXML private TextField rAddress;

    @FXML private TextField rContact;

    @FXML private DatePicker rDOB;

    @FXML
    void openRegisterPrompt(ActionEvent event) {

    }

    // DEPOSIT

    @FXML private TextField dAccNo;

    @FXML private ChoiceBox<?> DAccType;

    @FXML private Label dDesc;

    @FXML private TextField depositAmount;

    @FXML void confirmDeposit(ActionEvent event) {

    }

    // WITHDRAW

    @FXML private TextField wAccNo;

    @FXML private ChoiceBox<?> wAccType;

    @FXML private TextField wOtp;

    @FXML private TextField withdrawAmount;

    @FXML
    void confirmWithdraw(ActionEvent event) {

    }

    @FXML
    void sendOtp(ActionEvent event) {

    }

}
