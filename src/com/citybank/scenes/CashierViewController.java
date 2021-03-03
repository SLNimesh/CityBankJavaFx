package com.citybank.scenes;

import com.citybank.BankService;
import com.citybank.model.Account;
import com.citybank.model.AccountHolder;
import com.citybank.model.Credentials;
import com.citybank.model.UserContext;
import com.citybank.model.enums.AccountType;
import com.citybank.model.enums.Branch;
import com.citybank.model.enums.UserRole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CashierViewController implements Initializable {

    private UserContext currentUser;

    @FXML private Tab managementTab = new Tab();

    @FXML private Label headerGreeting;

    @FXML private Label headerUsername;

    @FXML private Circle profilePic;

    @FXML private ImageView profileImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserContext user = BankService.getCurrentUserContext();
        headerUsername.setText(user.getFirstName().concat(" ").concat(user.getLastName()));
        headerGreeting.setText("Welcome " + user.getRole().toLowerCase());
        if (!user.getRole().equals("MANAGER")) {
            managementTab.setDisable(true);
        }
        branch.getItems().setAll(Branch.values());
        accType.getItems().setAll(AccountType.values());
        DAccType.getItems().setAll(AccountType.values());
        wAccType.getItems().setAll(AccountType.values());
    }



    //  CREATE NEW ACCOUNT
    @FXML private TextField bankId;

    @FXML private ChoiceBox<Branch> branch = new ChoiceBox<>();

    @FXML private ChoiceBox<AccountType> accType = new ChoiceBox<>();

    @FXML private TextField initialDeposit;

    @FXML
    void openAccount(ActionEvent event) {
        Account newAccount =
                new Account(bankId.getText(), branch.getSelectionModel().getSelectedItem(),
                        accType.getSelectionModel().getSelectedItem(), Double.parseDouble(initialDeposit.getText()));
        BankService.createNewAccount(newAccount);
    }

    //REGISTER
    @FXML private TextField rFullName;

    @FXML private TextField rNic;

    @FXML private TextField rAddress;

    @FXML private TextField rContact;

    @FXML private DatePicker rDOB;

    @FXML
    void openRegisterPrompt(ActionEvent event) throws IOException {
        AccountHolder newAccountHolder = new AccountHolder(rNic.getText(), rFullName.getText(), rDOB.getValue());
        newAccountHolder.setAddress(rAddress.getText());
        newAccountHolder.setContactNumber(rContact.getText());
        BankService.addNewUser(newAccountHolder);
        RegisteredController.setUserContext(newAccountHolder);
        Parent root = FXMLLoader.load(getClass().getResource("Registered.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Registration");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
        clearFormData();
    }

    void clearFormData() {
        rFullName.clear(); rNic.clear(); rAddress.clear(); rContact.clear(); rDOB.setValue(null);
    }

    // DEPOSIT

    @FXML private TextField dAccNo;

    @FXML private ChoiceBox<AccountType> DAccType = new ChoiceBox<>();

    @FXML private Label dDesc;

    @FXML private TextField depositAmount;

    @FXML void confirmDeposit(ActionEvent event) {

    }

    // WITHDRAW

    @FXML private TextField wAccNo;

    @FXML private ChoiceBox<AccountType> wAccType = new ChoiceBox<>();

    @FXML private TextField wOtp;

    @FXML private TextField withdrawAmount;

    @FXML
    void confirmWithdraw(ActionEvent event) {

    }

    @FXML
    void sendOtp(ActionEvent event) {

    }

    // Management
        //Cashier
            //Create new
    @FXML private TextField cFirstName;

    @FXML private TextField cLastName;

    @FXML private TextField cnic;

    @FXML private TextField cContact;

    @FXML private TextField cUser;

    @FXML private TextField cPW;

    @FXML
    void createCashier(ActionEvent event) {
        UserContext newUserContext = new UserContext(cFirstName.getText(), cLastName.getText(), cnic.getText(), cContact.getText(), UserRole.MANAGER);
        Credentials credentials = new Credentials(newUserContext.getBankAssignedID(), cUser.getText(), cPW.getText());
        BankService.addNewCashier(newUserContext, credentials);
    }

            //Manage cashier
    @FXML private ComboBox<String> cashierId;

    @FXML
    void deleteCashier(ActionEvent event) {

    }

    @FXML
    void openChangeCredentials(ActionEvent event) {

    }

}
