package com.citybank;

import com.citybank.model.Account;
import com.citybank.model.AccountHolder;
import com.citybank.model.Address;
import com.citybank.model.enums.AccountType;
import com.citybank.model.enums.Branch;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/* TODO: https://www.jfx-ensemble.com/?page=sample/jfoenix/Button */

public class Main extends Application {

    private Integer count = 0;

    /* TODO <legit> */

    private static BankService bankService = new BankService();

    /* TODO </legit> */

    @Override
    public void start(Stage primaryStage) throws Exception {

        Button btn1 = new Button("Click me");
        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                count++;
                System.out.println("Clicked " + count + " times.");
            }
        });

        Text sampleText = new Text("Text");
        sampleText.setFont(new Font(45));
        sampleText.setX(100);
        sampleText.setY(250);
        sampleText.setFill(Color.WHITE);


        Group root = new Group();
        root.getChildren().addAll(btn1, sampleText);
        Scene scene = new Scene(root, 600, 400);
        scene.setFill(Color.DARKGRAY);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        //launch(args);

        System.out.println();

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
