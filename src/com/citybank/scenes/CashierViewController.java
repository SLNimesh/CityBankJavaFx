package com.citybank.scenes;

import com.citybank.BankService;
import com.citybank.model.*;
import com.citybank.model.enums.AccountType;
import com.citybank.model.enums.Branch;
import com.citybank.model.enums.TransactionType;
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

//        initiateAllDepositsTable();
//        initiateAllUsersTable();
//        initiateAllWithdrawalsTable();
//        initiateAllAccountsTable();
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
        bankId.clear(); branch.setValue(null); accType.setValue(null); initialDeposit.clear();
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

    @FXML private TextField dDesc;

    @FXML private TextField depositAmount;

    @FXML void confirmDeposit(ActionEvent event) {
        BankService.getAllAccounts().stream()
                .filter(account -> account.getAccountNumber().equals(dAccNo.getText()) && account.getAccountType().equals(DAccType.getValue()))
                .findAny().orElseThrow(() -> new ServiceException("Account Number / Account Type invalid"));
        Double amount = Double.parseDouble(depositAmount.getText());
        Account account = BankService.findAccount(dAccNo.getText());
        account.setCurrentBalance(account.getCurrentBalance() + amount);
        Transaction newTransaction
                = new Transaction(dAccNo.getText(), dDesc.getText(), amount, account.getCurrentBalance(), TransactionType.DEPOSIT);
        BankService.makeTransaction(newTransaction);
        dAccNo.clear(); DAccType.setValue(null); dDesc.clear(); depositAmount.clear();
    }

    // all deposits

    @FXML private TableView<Transaction> allDepositsTable;

    @FXML private TableColumn<Transaction, String> dTranID;

    @FXML private TableColumn<Transaction, String> dStamp;

    @FXML private TableColumn<Transaction, String> dAccNoColumn;

    @FXML private TableColumn<Transaction, Double> dAmount;

    @FXML private TableColumn<Transaction, Double> dAvailBal;

    @FXML
    void initiateAllDepositsTable() {
        dTranID.setCellValueFactory(cell -> cell.getValue().getIdTableView());
        dStamp.setCellValueFactory(cell -> cell.getValue().getTransactionDateTableView());
        dAccNoColumn.setCellValueFactory(cell -> cell.getValue().getAccountNoTableView());
        dAmount.setCellValueFactory(cell -> cell.getValue().getAmountTableView().asObject());
        dAvailBal.setCellValueFactory(cell -> cell.getValue().getAccountBalanceTableView().asObject());
        allDepositsTable.setItems(BankService.getAllDeposits());
    }

    // all withdrawals

    @FXML
    private TableView<Transaction> allWithdrawalsTable;

    @FXML
    private TableColumn<Transaction, String> wTranID1;

    @FXML
    private TableColumn<Transaction, String> wStampCol;

    @FXML
    private TableColumn<Transaction, String> wAccNoColumn;

    @FXML
    private TableColumn<Transaction, Double> wAmount;

    @FXML
    private TableColumn<Transaction, Double> wAvailBal;

    @FXML
    void initiateAllWithdrawalsTable() {
        wTranID1.setCellValueFactory(cell -> cell.getValue().getIdTableView());
        wStampCol.setCellValueFactory(cell -> cell.getValue().getTransactionDateTableView());
        wAccNoColumn.setCellValueFactory(cell -> cell.getValue().getAccountNoTableView());
        wAmount.setCellValueFactory(cell -> cell.getValue().getAmountTableView().asObject());
        wAvailBal.setCellValueFactory(cell -> cell.getValue().getAccountBalanceTableView().asObject());
        allDepositsTable.setItems(BankService.getAllWithdrawals());
    }



    // WITHDRAW

    @FXML private TextField wAccNo;

    @FXML private ChoiceBox<AccountType> wAccType = new ChoiceBox<>();

    @FXML private TextField wOtp;

    @FXML private TextField withdrawAmount;

    @FXML
    void confirmWithdraw(ActionEvent event) {
        Double amount = Double.parseDouble(withdrawAmount.getText());
        Account account = BankService.findAccount(wAccNo.getText());
        account.setCurrentBalance(account.getCurrentBalance() - amount);
        Transaction newTransaction
                = new Transaction(wAccNo.getText(), "Private matter.", amount, account.getCurrentBalance(), TransactionType.WITHDRAWAL);
        BankService.makeTransaction(newTransaction);
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

    //Accounts
        //User
            //All users
    @FXML private TableView<AccountHolder> allUsersTable;

    @FXML private TableColumn<AccountHolder, String> uBankIdColumn;

    @FXML private TableColumn<AccountHolder, String> uNameColumn;

    @FXML private TableColumn<AccountHolder, String> uNICColumn;

    @FXML private TableColumn<AccountHolder, String> uContactNoColumn;

    @FXML
    void initiateAllUsersTable() {
        uBankIdColumn.setCellValueFactory(cell -> cell.getValue().getBankAssignedIdTableView());
        uNameColumn.setCellValueFactory(cell -> cell.getValue().getNameTableView());
        uNICColumn.setCellValueFactory(cell -> cell.getValue().getNICTableView());
        uContactNoColumn.setCellValueFactory(cell -> cell.getValue().getContactNumberTableView());
        allUsersTable.setItems(BankService.getAllAccountHolders());
    }

        //Accounts
            //All accounts

    @FXML private TableView<Account> allAccountsTable;

    @FXML private TableColumn<Account, String> accountNoCol;

    @FXML private TableColumn<Account, String> accHolIdCol;

    @FXML private TableColumn<Account, String> accHolNameCol;

    @FXML private TableColumn<Account, String> accBranchCol;

    @FXML private TableColumn<Account, String> accTypeCol;

    @FXML private TableColumn<Account, Double> accBalCol;

    @FXML
    void initiateAllAccountsTable() {
        accountNoCol.setCellValueFactory(cell -> cell.getValue().getAccountNumberTable());
        accHolIdCol.setCellValueFactory(cell -> cell.getValue().getAccountHolderTable());
        accHolNameCol.setCellValueFactory(cell -> cell.getValue().getAccHolderName());
        accBranchCol.setCellValueFactory(cell -> cell.getValue().getAccountBranchTable());
        accTypeCol.setCellValueFactory(cell -> cell.getValue().getAccountTypeTable());
        accBalCol.setCellValueFactory(cell -> cell.getValue().getCurrentBalanceTable().asObject());
        allAccountsTable.setItems(BankService.getAllAccounts());
    }


}
