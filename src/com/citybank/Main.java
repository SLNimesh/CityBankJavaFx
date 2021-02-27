package com.citybank;

import com.citybank.model.Account;
import com.citybank.model.AccountHolder;
import com.citybank.model.Address;
import com.citybank.model.enums.AccountType;
import com.citybank.model.enums.Branch;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

/* TODO: https://www.jfx-ensemble.com/?page=sample/jfoenix/Button */

public class Main extends Application {

    /* TODO <final> */
    public static Stage window;
    private static BankService bankService = new BankService();
    public static String loginForm = "scenes/LoginForm.fxml";

    /* TODO </final> */

    @Override
    public void init() throws Exception {
        System.out.println("Application starting");
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource(loginForm));
        window.setTitle("Commercial Bank");
        window.setScene(new Scene(root, 600, 400));
        window.show();
    }


    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Colombo"));
        launch(args);

    }

    @Override
    public void stop() throws Exception {
        System.out.println("Application closed");
        super.stop();
    }

    public static void test(){
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
}
