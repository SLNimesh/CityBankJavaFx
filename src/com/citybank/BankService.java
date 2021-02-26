package com.citybank;

import com.citybank.model.*;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankService {

    private static final String commonPath = "src/com/citybank/data/";
    private static final String accountsFile = commonPath.concat("accounts.txt");
    private static final String accountsHoldersFile = commonPath.concat("accountHolders.txt");
    private static final String transactionsFile = commonPath.concat("transactions.txt");
    private static final String credentialsFile = commonPath.concat("credentials.txt");
    private static final String usersFile = commonPath.concat("users.txt");

    private static UserContext currentUserContext;
    private static Set<Account> allAccounts;
    private static Set<Credentials> securityTokens;
    private static Set<AccountHolder> accountHolders;
    private static Set<UserContext> users;

    private static boolean somethingWentWrong = false;

    private static Logger LOGGER = Logger.getLogger(BankService.class.getName());

    public static UserContext authenticate(String userName, String password) throws RuntimeException{
        Credentials enteredCred = securityTokens.stream().
                filter(credentials -> credentials.getUserName().equals(userName) && credentials.getPassword().equals(password)).findFirst().
                orElseThrow(() -> new ServiceException("Entered credentials are incorrect"));

        UserContext current = users.stream().filter(userContext -> userContext.getBankAssignedID().equals(enteredCred.getBankAssignedId())).findFirst().
                orElseThrow(() -> new ServiceException("Failed to load user."));

        setCurrentUserContext(current);
        return currentUserContext;
    }

    public static void addNewUser(UserContext userContext){
        LOGGER.log(Level.INFO, "User added with id: " + userContext.getBankAssignedID());
        users.add(userContext);
    }

    public static UserContext getCurrentUserContext() {
        return currentUserContext;
    }

    public static void setCurrentUserContext(UserContext userContext) {
        LOGGER.log(Level.INFO, "Current user set to : " + userContext.getBankAssignedID());
        currentUserContext = userContext;
    }

    public static void loadExistingData() {
        users = genericFileReader(usersFile, UserContext.class);
        allAccounts = genericFileReader(accountsFile, Account.class);
        accountHolders = genericFileReader(accountsHoldersFile, AccountHolder.class);
        securityTokens = genericFileReader(credentialsFile, Credentials.class);
    }

    public void persistData() {
        genericFileWriter(usersFile, users);
        genericFileWriter(accountsHoldersFile, accountHolders);
        genericFileWriter(accountsFile, allAccounts);
        genericFileWriter(credentialsFile, securityTokens);
    }

    public static <T> Set<T> genericFileReader(String fileName, Class<T> classType) {
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
