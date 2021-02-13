package com.citybank;

import com.citybank.model.Account;
import com.citybank.model.AccountHolder;
import com.citybank.model.AccountHolderDTO;
import com.citybank.model.Credentials;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankService {

    private static final String commonPath = "src/com/citybank/data/";
    private static final String accountsFile = commonPath.concat("accounts.txt");
    private static final String accountsHoldersFile = commonPath.concat("accountHolders.txt");
    private static final String transactions = commonPath.concat("transactions.txt");
    private static final String credentials = commonPath.concat("credentials.txt");

    private AccountHolderDTO currentUserContext;
    private Set<Account> allAccounts;
    private Set<Credentials> securityTokens;
    private Set<AccountHolder> accountHolders;

    private static boolean somethingWentWrong = false;

    private static Logger LOGGER = Logger.getLogger(BankService.class.getName());

    public void loadExistingData() {
        allAccounts = genericFileReader(accountsFile, Account.class);
        accountHolders = genericFileReader(accountsHoldersFile, AccountHolder.class);
        securityTokens = genericFileReader(credentials, Credentials.class);
    }

    public void persistData() {
//        HashMap<String, Set<?>> applicationDataSetMap = new HashMap<>();

        genericFileWriter(accountsHoldersFile, accountHolders);
        genericFileWriter(accountsFile, allAccounts);
        genericFileWriter(credentials, securityTokens);
    }

    public <T> Set<T> genericFileReader(String fileName, Class<T> classType) {
        Set<T> objectSet = new HashSet<>();
        try {

            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(new File(fileName)));
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            while (true) {
                try {
                    Object object = objectInputStream.readObject();
                    objectSet.add((T) object);
                } catch (IOException e) {
                    LOGGER.log(Level.INFO, "Reached end of file:" + fileName);
                    break;
                }
            }
            try {
                inputStream.close();
                objectInputStream.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Failed to close streams.");
            }

        } catch (FileNotFoundException e) {
            somethingWentWrong = true;
            String msg = "File : " + fileName + " could not be found.";
            LOGGER.log(Level.SEVERE, msg);

        } catch (IOException e) {
            somethingWentWrong = true;
            LOGGER.log(Level.SEVERE, "IO Exception occurred while reading.");

        } catch (ClassNotFoundException e) {
            somethingWentWrong = true;
            LOGGER.log(Level.SEVERE, "No such class found.");
        }

        return objectSet;
    }

    public void genericFileWriter(String fileName, Set<?> genericSet) {
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            genericSet.forEach(t -> {
                try {
                    objectOutputStream.writeObject(t);
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "IO Exception occurred while writing object.");
                }
            });
            try {
                outputStream.close();
                objectOutputStream.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Failed to close streams.");
            }
        } catch (FileNotFoundException e) {
            somethingWentWrong = true;
            LOGGER.log(Level.SEVERE, "File : " + fileName + " could not be found.");

        } catch (IOException e) {
            somethingWentWrong = true;
            LOGGER.log(Level.SEVERE, "IO Exception occurred while writing.");
        }
    }

    public static boolean isSomethingWentWrong() {
        return somethingWentWrong;
    }
}
