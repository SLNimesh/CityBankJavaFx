package com.citybank.scenes;

import com.citybank.BankService;
import com.citybank.model.*;
import com.citybank.model.enums.AccountType;
import com.citybank.model.enums.Branch;
import com.citybank.model.enums.TransactionType;
import com.citybank.model.enums.UserRole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        openMsgPrompt("New account with ACC NO. : " + newAccount.getAccountNumber()
                +  " created for " + newAccount.getAccHolderName().getValue() + "(" + newAccount.getAccountHolder() + ").");
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
        stage.getIcons().add(new Image(getClass().getResourceAsStream("msgLogo.png")));
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

    @FXML private TextArea depositDetail;

    @FXML void confirmDeposit(ActionEvent event) {
        Double amount = Double.parseDouble(depositAmount.getText());
        Account account = BankService.findAccount(dAccNo.getText());
        account.setCurrentBalance(account.getCurrentBalance() + amount);
        Transaction newTransaction
                = new Transaction(dAccNo.getText(), dDesc.getText(), amount, account.getCurrentBalance(), TransactionType.DEPOSIT);
        BankService.makeTransaction(newTransaction);
        openMsgPrompt(newTransaction.toString());
        dAccNo.clear(); DAccType.setValue(null); dDesc.clear(); depositAmount.clear(); depositDetail.clear();
    }

    @FXML
    void loadAccountAccountDeposit(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            Account account = BankService.findAccount(dAccNo.getText());
            DAccType.setValue(account.getAccountType());
            AccountHolder selected = BankService.findAccountHolder(account.getAccountHolder());
            String details = "\nNAME : " + selected.getName()
                    + "\nNIC : " + selected.getNIC()
                    + "\nDOB : " + selected.getDateOfBirth().toString()
                    + "\nADDRESS : " + selected.getAddress()
                    + "\nACCOUNTS : " + selected.getAccounts();
            depositDetail.setText(details);
        }
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
        updateAllDepositsTable();
    }

    @FXML
    void updateAllDepositsTable() {
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
        updateAllWithdrawalsTable();
    }

    @FXML
    void updateAllWithdrawalsTable() {
        allWithdrawalsTable.setItems(BankService.getAllWithdrawals());
    }

    // WITHDRAW

    @FXML private TextField wAccNo;

    @FXML private ChoiceBox<AccountType> wAccType = new ChoiceBox<>();

    @FXML private TextField wBalance;

    @FXML private TextField withdrawAmount;

    @FXML private TextArea wAccDetail;

//    private String temporaryOTP;

    @FXML
    void loadAccountAccountWithdraw(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
           Account account = BankService.findAccount(wAccNo.getText());
           wAccType.setValue(account.getAccountType());
           wBalance.setText(account.getAvailBalance().toString());
           AccountHolder selected = BankService.findAccountHolder(account.getAccountHolder());
           String details = "\nNAME : " + selected.getName()
                   + "\nNIC : " + selected.getNIC()
                   + "\nDOB : " + selected.getDateOfBirth().toString()
                   + "\nADDRESS : " + selected.getAddress()
                   + "\nACCOUNTS : " + selected.getAccounts();
           wAccDetail.setText(details);
        }
    }

    @FXML
    void confirmWithdraw(ActionEvent event) {
//        if(wOtp.getText().equals(temporaryOTP)) {
//
//        }else {
//            System.out.println("Invalid OTP");
//        }
        Double amount = Double.parseDouble(withdrawAmount.getText());
        Account account = BankService.findAccount(wAccNo.getText());
        account.setCurrentBalance(account.getCurrentBalance() - amount);
        Transaction newTransaction
                = new Transaction(wAccNo.getText(), "Withdrawal", amount, account.getCurrentBalance(), TransactionType.WITHDRAWAL);
        BankService.makeTransaction(newTransaction);
        openMsgPrompt(newTransaction.toString());
        wAccNo.clear(); wAccType.setValue(null); withdrawAmount.clear(); wBalance.clear();
    }

//    @FXML
//    void sendOtp(ActionEvent event) {
//        temporaryOTP = String
//                .format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16))
//                .substring(0,16);
//        openMsgPrompt("Your OTP is " + temporaryOTP);
//    }

    //Search
    @FXML
    private TextField tAccountNumber;

    @FXML
    private DatePicker tFrom;

    @FXML
    private DatePicker tTo;

    @FXML
    private TableView<Transaction> tTable;

    @FXML
    private TableColumn<Transaction, String> tDateCol;

    @FXML
    private TableColumn<Transaction, String> tAccNoCol;

    @FXML
    private TableColumn<Transaction, String> tTypeCol;

    @FXML
    private TableColumn<Transaction, Double> tAmountCol;

    @FXML
    private TableColumn<Transaction, Double> tAccBalCol;

    @FXML
    void initiateTransactionSearchTable() {
        tDateCol.setCellValueFactory(cell -> cell.getValue().getTransactionDateTableView());
        tAccNoCol.setCellValueFactory(cell -> cell.getValue().getAccountNoTableView());
        tTypeCol.setCellValueFactory(cell -> cell.getValue().getTransactionTypeTableView());
        tAmountCol.setCellValueFactory(cell -> cell.getValue().getAmountTableView().asObject());
        tAccBalCol.setCellValueFactory(cell -> cell.getValue().getAccountBalanceTableView().asObject());
    }

    @FXML
    void transactionSearch() {
        Set<Transaction> showData;
        if(tAccountNumber.getText().length() != 0){
            showData = BankService.getAllTransactions().stream()
                    .filter(transaction -> transaction.getAccountNo().equals(tAccountNumber.getText())).collect(Collectors.toSet());
        }else{
            showData = BankService.getAllTransactions();
        }
        if(tTo.getValue() != null) {
            showData = showData.stream()
                    .filter(transaction -> transaction.getTransactionDate().toLocalDate().isBefore(tTo.getValue())).collect(Collectors.toSet());
        }
        if(tFrom.getValue() != null) {
            showData = showData.stream()
                    .filter(transaction -> transaction.getTransactionDate().toLocalDate().isAfter(tFrom.getValue())).collect(Collectors.toSet());
        }
        ObservableList<Transaction> showDataList = FXCollections.observableArrayList(showData);
        System.out.println("Search results count : " + showDataList.size());
        tTable.setItems(showDataList);
    }

    @FXML
    void clearSearch() {
        tTable.setItems(null); tFrom.setValue(null); tTo.setValue(null); tAccountNumber.clear();
    }

    // Management
        //Customer
            //Edit
    @FXML private TextField cusEditID;

    @FXML private TextField cusEditFullName;

    @FXML private TextField cusEditNIC;

    @FXML private TextField cusEditAdd;

    @FXML private TextField cusEditContact;

    @FXML private DatePicker cusEditDOB;

    @FXML
    void loadAccountHolderEdit(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            AccountHolder selected = BankService.findAccountHolder(cusEditID.getText());
            cusEditFullName.setText(selected.getName());
            cusEditNIC.setText(selected.getNIC());
            cusEditAdd.setText(selected.getAddress());
            cusEditContact.setText(selected.getContactNumber());
            cusEditDOB.setValue(selected.getDateOfBirth());
        }
    }

    @FXML
    void clearSelectedAccountHolder() {
        cusEditFullName.clear(); cusEditNIC.clear(); cusEditAdd.clear();
        cusEditContact.clear(); cusEditDOB.setValue(null);
    }

    @FXML
    void updateAccountHolderInfo() {
        AccountHolder selected = BankService.findAccountHolder(cusEditID.getText());
        selected.setName(cusEditFullName.getText());
        selected.setNIC(cusEditNIC.getText());
        selected.setDateOfBirth(cusEditDOB.getValue());
        selected.setContactNumber(cusEditContact.getText());
        selected.setAddress(cusEditAdd.getText());
        clearSelectedAccountHolder();
    }


            //Delete
    @FXML private TextField dAccountHolderID;

    @FXML private TextArea accHolderDetails;

    @FXML
    void loadAccountHolder(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) {
            AccountHolder selected = BankService.findAccountHolder(dAccountHolderID.getText());
            String details = "\nNAME : " + selected.getName()
                    + "\nNIC : " + selected.getNIC()
                    + "\nDOB : " + selected.getDateOfBirth().toString()
                    + "\nADDRESS : " + selected.getAddress()
                    + "\nACCOUNTS : " + selected.getAccounts();
            accHolderDetails.setText(details);
        }
    }

    @FXML
    void clearAccountHolder() {
        accHolderDetails.setText("[Details]"); dAccountHolderID.clear();
    }

    @FXML
    void deleteAccountHolder(ActionEvent event) {
        BankService.deleteAccountHolder(dAccountHolderID.getText());
        clearAccountHolder();
    }

        //Cashier
    @FXML
    private TableView<UserContext> AllCashiersTable;

    @FXML
    private TableColumn<UserContext, String> cIDCol;

    @FXML
    private TableColumn<UserContext, String> cNameCol;

    @FXML
    private TableColumn<UserContext, String> cNICCol;

    @FXML
    private TableColumn<UserContext, String> CContactCol;

    @FXML
    private TableColumn<UserContext, String> cUserNameCol;

    @FXML
    void initializeAllCashiersTable() {
        cIDCol.setCellValueFactory(cell -> cell.getValue().getBankAssignedIDTable());
        cNameCol.setCellValueFactory(cell -> cell.getValue().getFullNameTable());
        cNICCol.setCellValueFactory(cell -> cell.getValue().getNICTable());
        CContactCol.setCellValueFactory(cell -> cell.getValue().getContactNumberTable());
        cUserNameCol.setCellValueFactory(cell -> cell.getValue().getUserName());
        updateCashiersTableData();
        loadCashierIDs();
    }

    @FXML
    void updateCashiersTableData() {
        AllCashiersTable.setItems(BankService.getAllCashiers());
    }

            //Create new
    @FXML private TextField cFirstName;

    @FXML private TextField cLastName;

    @FXML private TextField cnic;

    @FXML private TextField cContact;

    @FXML private TextField cUser;

    @FXML private TextField cPW;

    @FXML
    void createCashier(ActionEvent event) {
        UserContext newUserContext = new UserContext(cFirstName.getText(), cLastName.getText(), cnic.getText(), cContact.getText(), UserRole.CASHIER);
        Credentials credentials = new Credentials(cUser.getText(), cPW.getText(), newUserContext.getBankAssignedID());
        String msg  = BankService.addNewCashier(newUserContext, credentials);
        openMsgPrompt(msg);
        cFirstName.clear();  cLastName.clear();  cnic.clear();  cContact.clear();  cUser.clear();  cPW.clear();
    }

            //Manage cashier
    @FXML private ComboBox<String> cashierId;

    @FXML private Label cEditFullName;

    @FXML private TextField cEditUsername;

    @FXML private TextField cEditPwOne;

    @FXML private TextField cEditPwTwo;

    @FXML
    void loadCashierIDs() {
        cashierId.getItems().setAll(BankService.getCashierIDs());
    }

    @FXML
    void loadCashierDetails(ActionEvent event) {
        UserContext selectedCashier = BankService.findUserContext(cashierId.getValue());
        cEditFullName.setText(selectedCashier.getFullNameTable().getValue());
        cEditUsername.setText(selectedCashier.getUserName().getValue());
    }

    @FXML
    void deleteCashier(ActionEvent event) {
        UserContext deleted = BankService.deleteCashierAccount(cashierId.getValue());
        cashierId.setValue(null); cEditFullName.setText("[Select cashier id]"); cEditUsername.clear(); cEditPwOne.clear(); cEditPwTwo.clear();
        openMsgPrompt("CASHIER DELETED \n ID : " + deleted.getBankAssignedID() + "\n NAME : " + deleted.getFullNameTable().getValue());
    }

    @FXML
    void openChangeCredentials(ActionEvent event) {
        if(cEditPwOne.getText().equals(cEditPwTwo.getText())) {
            Credentials changedCredentials = BankService.findCredentials(cashierId.getValue());
            changedCredentials.setUserName(cEditUsername.getText());
            changedCredentials.setPassword(cEditPwOne.getText());
            System.out.println("Credentials changed for : " + changedCredentials.getUserName());
        }else {
            openMsgPrompt("Make sure you enter the same password in both the places");
        }
        cEditPwOne.clear(); cEditPwTwo.clear();
    }

            //Settings

    @FXML private TextField rootCurrent;

    @FXML private TextField rootNew;

    @FXML private TextField rootNewTwo;

    @FXML
    void updateManagerCredentials() {
        Credentials cr = BankService.findCredentials(BankService.getCurrentUserContext().getBankAssignedID());
        if(cr.getPassword().equals(rootCurrent.getText())) {
            if(rootNew.equals(rootNewTwo)){
                cr.setPassword(rootNew.getText());
                rootCurrent.clear();
            }else{
                openMsgPrompt("Make sure you enter the same password in both the places");
            }
            rootNew.clear(); rootNewTwo.clear();
        }else {
            openMsgPrompt("Entered current password seems to be invalid.");
        }
    }

    //Accounts
        //Customer
            //Search
    @FXML
    private TextField searchCustomerID;

    @FXML
    private TextArea searchCustomerDetail;

    @FXML
    void searchAccountHolder(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) {
            AccountHolder selected = BankService.findAccountHolder(searchCustomerID.getText());
            String details = "\nNAME : " + selected.getName()
                    + "\nNIC : " + selected.getNIC()
                    + "\nDOB : " + selected.getDateOfBirth().toString()
                    + "\nADDRESS : " + selected.getAddress()
                    + "\nACCOUNTS : " + selected.getAccounts();
            searchCustomerDetail.setText(details);
        }
    }

    @FXML
    void clearAccountHolderResult() {
        searchCustomerDetail.clear(); searchCustomerDetail.clear();
    }

            //All users
    @FXML private TableView<AccountHolder> allUsersTable;

    @FXML private TableColumn<AccountHolder, String> uBankIdColumn;

    @FXML private TableColumn<AccountHolder, String> uNameColumn;

    @FXML private TableColumn<AccountHolder, String> uNICColumn;

    @FXML private TableColumn<AccountHolder, String> uContactNoColumn;

    @FXML private TableColumn<AccountHolder, String> uAddressColumn;

    @FXML
    void initiateAllUsersTable() {
        uBankIdColumn.setCellValueFactory(cell -> cell.getValue().getBankAssignedIdTableView());
        uNameColumn.setCellValueFactory(cell -> cell.getValue().getNameTableView());
        uNICColumn.setCellValueFactory(cell -> cell.getValue().getNICTableView());
        uContactNoColumn.setCellValueFactory(cell -> cell.getValue().getContactNumberTableView());
        uAddressColumn.setCellValueFactory(cell -> cell.getValue().getAddressTableView());
        updateAllUsersTable();
    }

    @FXML
    void updateAllUsersTable() {
        allUsersTable.setItems(BankService.getAllAccountHolders());
    }

    //Accounts

        //Search
    @FXML private TextField searchAccountNo;

    @FXML private TextArea searchAccDetail;

    @FXML private ListView<String> searchAccTransactions;


    @FXML
    void clearAccountResult() {
        searchAccDetail.clear(); searchAccountNo.clear(); searchAccTransactions.setItems(null);
    }

    @FXML
    void searchAccount(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) {
           Account searchedAccount = BankService.findAccount(searchAccountNo.getText());
           String detail = "ACCOUNT NO : " + searchedAccount.getAccountNumber()
                   + "\nACCOUNT HOLDER : " + searchedAccount.getAccHolderName().getValue()
                   + "\nBRANCH : " + searchedAccount.getAccountBranch()
                   + "\nACCOUNT TYPE : " + searchedAccount.getAccountType().name()
                   + "\nCURRENT BALANCE : " + searchedAccount.getCurrentBalance()
                   + "\nAVAILABLE BALANCE : " + searchedAccount.getAvailBalance();
           searchAccDetail.setText(detail);
           searchAccTransactions.getItems().addAll(BankService.findTransactionsForAccount(searchedAccount.getAccountNumber()));
        }
    }

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
        updateAllAccountsTable();
    }

    @FXML
    void updateAllAccountsTable() {
        allAccountsTable.setItems(BankService.getAllAccounts());
    }

    void openMsgPrompt(String msg) {
        try {
            MessagePromptController.setMsg(msg);
            Parent root = FXMLLoader.load(getClass().getResource("MessagePrompt.fxml"));
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("msgLogo.png")));
            stage.setTitle("Notification Center");
            stage.setScene(new Scene(root, 400, 200));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
