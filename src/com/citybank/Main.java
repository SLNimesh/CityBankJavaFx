package com.citybank;

import com.citybank.model.*;
import com.citybank.model.enums.AccountType;
import com.citybank.model.enums.Branch;
import com.citybank.model.enums.UserRole;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

/* TODO: https://bitbucket.org/agix-material-fx/materialfx-material-design-for-javafx/src/master/material-fx-v0_3.css */

public class Main extends Application {

    /* TODO <final> */
    public static Stage window;
    private static BankService bankService = new BankService();
    public static String loginForm = "scenes/LoginForm.fxml";

    /* TODO </final> */

    @Override
    public void init() throws Exception {
        System.out.println("Application starting");
        BankService.loadExistingData();
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource(loginForm));
        window.setTitle("City Bank");
        window.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
        window.setScene(new Scene(root, 600, 400));
        window.show();
    }


    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Colombo"));
        //numGenTest();
        //addDummyData();
        launch(args);

    }

    @Override
    public void stop() throws Exception {
        System.out.println("Application closed");
        BankService.persistData();
        super.stop();
    }

    public static void addDummyData() {
        UserContext rootUser = new UserContext("Super", "Admin", "2000121480", "0987456123", UserRole.MANAGER);
        Credentials rootCredentials = new Credentials("root", "toor", rootUser.getBankAssignedID());
        BankService.addNewCashier(rootUser, rootCredentials);
    }

    public static void test() {
        AccountHolder accountHolder = dummyAccountHolder();
        AccountHolder accountHolder2 = dummyAccountHolder();

        Set<AccountHolder> holders = new HashSet<>();
        holders.add(accountHolder);
        holders.add(accountHolder2);

        bankService.genericFileWriter("src/com/citybank/data/sample.txt", holders);

        Set<AccountHolder> accountHolders = bankService.genericFileReader("src/com/citybank/data/sample.txt", AccountHolder.class);
        accountHolders.forEach(accountHolder1 -> System.out.println(accountHolder1.getBankAssignedId()));
    }

    public static AccountHolder dummyAccountHolder() {
        AccountHolder accountHolder = new AccountHolder("200124566V", "Van De Bona", LocalDate.now());
        Address dummyAddress = new Address("No. 69", "Araliya Uyana", "Mattegoda", 10240);
        accountHolder.setAddress(dummyAddress.toString());
        Account dummyAccount = new Account(accountHolder.getBankAssignedId(), Branch.COLOMBO_03, AccountType.SAVINGS_ACCOUNT, 500D);
        accountHolder.addAccount(dummyAccount.getAccountNumber());
        return accountHolder;
    }

    public static void numGenTest() {
        Set<String> testSet = new HashSet<>();
        for (int i = 0; i <= 100; i++){
            String str = String
                    .format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16))
                    .substring(0,6);
            testSet.add(str);
        }
        System.out.println(testSet.size());
    }
}
