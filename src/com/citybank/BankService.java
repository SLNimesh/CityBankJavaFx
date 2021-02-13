package com.citybank;

import com.citybank.model.Account;
import com.citybank.model.AccountHolder;
import com.citybank.model.AccountHolderDTO;
import com.citybank.model.Credentials;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankService {

    private static final String accountsFile = "data/sample.txt";

    private AccountHolderDTO currentUserContext;
    private Set<Account> allAccounts = new HashSet<>();
    private Set<Credentials> securityTokens = new HashSet<>();
    private Set<AccountHolder> accountHolders = new HashSet<>();

    private static boolean somethingWentWrong = false;

    private static Logger LOGGER = Logger.getLogger(BankService.class.getName());

    public void readAccountsFile() {

    }

    public <T> Set<T> genericFileReader(String fileName, T classType) {
        Set<T> objectSet = new HashSet<>();
        try {

            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(new File(fileName)));
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            while (true){
                try {
                    Object object = objectInputStream.readObject();
                    objectSet.add((T) object);
                }catch (IOException e){
                    LOGGER.log(Level.INFO, "Reached end of file:" + fileName);
                    break;
                }
            }
            try {
                inputStream.close();
                objectInputStream.close();
            } catch (IOException e){
                LOGGER.log(Level.WARNING, "Failed to close streams.");
            }

        } catch (FileNotFoundException e) {
            somethingWentWrong = true;
            String msg = "File : " + fileName + " could not be found.";
            LOGGER.log(Level.SEVERE, msg);

        } catch (IOException e) {
            somethingWentWrong = true;
            LOGGER.log(Level.SEVERE, "IO Exception occurred.");

        } catch (ClassNotFoundException e) {
            somethingWentWrong = true;
            LOGGER.log(Level.SEVERE, "No such class found.");
        }

        return objectSet;
    }

    public static boolean isSomethingWentWrong() {
        return somethingWentWrong;
    }
}
