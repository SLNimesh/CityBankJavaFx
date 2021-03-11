package com.citybank;

import com.citybank.model.*;
import com.citybank.model.enums.TransactionType;
import com.citybank.model.enums.UserRole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BankService {

    private static final String commonPath = "src/com/citybank/data/";
    private static final String accountsFile = commonPath.concat("accounts.txt");
    private static final String accountsHoldersFile = commonPath.concat("accountHolders.txt");
    private static final String transactionsFile = commonPath.concat("transactions.txt");
    private static final String credentialsFile = commonPath.concat("credentials.txt");
    private static final String usersFile = commonPath.concat("users.txt");

    private static UserContext currentUserContext;
    private static Set<Account> allAccounts = new HashSet<>();
    private static Set<Credentials> securityTokens = new HashSet<>();
    private static Set<AccountHolder> accountHolders = new HashSet<>();
    private static Set<UserContext> users = new HashSet<>();
    private static Set<Transaction> transactions = new HashSet<>();

    private static boolean somethingWentWrong = false;

    private static Logger LOGGER = Logger.getLogger(BankService.class.getName());

    public static UserContext authenticate(String userName, String password) throws ServiceException {
        Credentials enteredCred = securityTokens.stream().
                filter(credentials -> credentials.getUserName().equals(userName) && credentials.decodePassword().equals(password)).findFirst().
                orElseThrow(() -> new ServiceException("Entered credentials are incorrect"));

        UserContext current = findUserContext(enteredCred.getBankAssignedId());

        LOGGER.log(Level.INFO, "User " + userName + " authenticated and authorized with role : " + current.getRole());
        setCurrentUserContext(current);
        return currentUserContext;
    }

    public static void makeTransaction(Transaction transaction) {
        transactions.add(transaction);
        System.out.println("Transaction added on id : " + transaction.getId());
        LOGGER.log(Level.INFO, transaction.toString());
    }

    public static Credentials findCredentials(String bankAssignedID) throws ServiceException {
        return securityTokens.stream().filter(credentials -> credentials.getBankAssignedId().equals(bankAssignedID))
                .findAny().orElseThrow(() -> new ServiceException("No credentials found for given ID"));
    }

    public static Account findAccount(String accNo) throws ServiceException {
        return allAccounts.stream().filter(account -> account.getAccountNumber().equals(accNo)).findAny()
                .orElseThrow(() -> new ServiceException("No account exists under ACC.NO : " + accNo));
    }

    public static AccountHolder findAccountHolder(String bankAssignedId) throws ServiceException {
        return accountHolders.stream().filter(accountHolder -> accountHolder.getBankAssignedId().equals(bankAssignedId)).findAny()
                .orElseThrow(() -> new ServiceException("No user exists under BANK ID : " + bankAssignedId));
    }

    public static UserContext findUserContext(String bankAssignedID) throws ServiceException {
        return users.stream().filter(userContext -> userContext.getBankAssignedID().equals(bankAssignedID)).findFirst().
                orElseThrow(() -> new ServiceException("Failed to load user."));
    }

    public static List<String> findTransactionsForAccount(String accountNo) {
        return transactions.stream().
                filter(transaction -> transaction.getAccountNo().equals(accountNo)).sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .map(Transaction::toString).collect(Collectors.toList());
    }

    public static Set<Account> findAccountsByAccHolder(String accHolderName) {
        return new HashSet<>();
    }

    public static String addNewCashier(UserContext userContext, Credentials userCredentials) {
        String msg = "Cashier added with,\n ID: "
                + userContext.getBankAssignedID()
                + "\n NAME : " + userContext.getFirstName() + " " + userContext.getLastName()
                + "\n ROLE : " + userContext.getRole();
        LOGGER.log(Level.INFO, msg);
        users.add(userContext);
        securityTokens.add(userCredentials);
        LOGGER.log(Level.INFO, "Credentials added for username :" + userCredentials.getUserName());
        return msg;
    }

    public static void addNewUser(AccountHolder accountHolder) {
        LOGGER.log(Level.INFO, "User added with id: " + accountHolder.getBankAssignedId());
        accountHolders.add(accountHolder);
    }

    public static void createNewAccount(Account account) throws ServiceException {
        LOGGER.log(Level.INFO, "New Account added with account number: " + account.getAccountNumber());
        allAccounts.add(account);
        AccountHolder accHolder = accountHolders.stream()
                .filter(accountHolder -> accountHolder.getBankAssignedId().equals(account.getAccountHolder())).findFirst()
                .orElseThrow(() -> new ServiceException("Account holder's bank assigned id seems to invalid"));
        accHolder.getAccounts().add(account.getAccountNumber());
    }

    public static UserContext deleteCashierAccount(String bankAssignedId) {
        UserContext deletedAccount = findUserContext(bankAssignedId);
        Credentials deletedCredentials = findCredentials(bankAssignedId);
        users.remove(deletedAccount);
        LOGGER.log(Level.INFO, "Deleted account " + bankAssignedId);
        securityTokens.remove(deletedCredentials);
        LOGGER.log(Level.INFO, "Credentials removed under username : " + deletedCredentials.getUserName());
        return deletedAccount;
    }

    public static void deleteAccountHolder(String bankAssignedId) {
        AccountHolder deletedAccountHolder = findAccountHolder(bankAssignedId);
        accountHolders.remove(deletedAccountHolder);
        deletedAccountHolder.getAccounts().forEach(account -> {
            allAccounts.remove(findAccount(account));
        });
        LOGGER.log(Level.INFO, "Account holder deactivated : " + bankAssignedId);
    }

    public static UserContext getCurrentUserContext() {
        return currentUserContext;
    }

    public static ObservableList<AccountHolder> getAllAccountHolders() {
        return FXCollections.observableArrayList(accountHolders);
    }

    public static ObservableList<Account> getAllAccounts() {
        return FXCollections.observableArrayList(allAccounts);
    }

    public static ObservableList<UserContext> getAllCashiers() {
        return FXCollections
                .observableArrayList(
                        users.stream().filter(user -> user.getRole().equals(UserRole.CASHIER.name())).collect(Collectors.toList()));
    }

    public static ObservableList<Transaction> getAllDeposits() {
        return FXCollections.
                observableArrayList(
                        transactions.stream().filter(transaction -> transaction.getType().equals(TransactionType.DEPOSIT))
                                .sorted(Comparator.comparing(Transaction::getTransactionDate)).collect(Collectors.toList()));
    }

    public static ObservableList<Transaction> getAllWithdrawals() {
        return FXCollections.
                observableArrayList(
                        transactions.stream().filter(transaction -> transaction.getType().equals(TransactionType.WITHDRAWAL))
                                .sorted(Comparator.comparing(Transaction::getTransactionDate)).collect(Collectors.toList()));
    }

    public static List<String> getCashierIDs() {
        return users.stream()
                .filter(userContext -> userContext.getRole().equals(UserRole.CASHIER.name()))
                .map(UserContext::getBankAssignedID).collect(Collectors.toList());
    }

    public static Set<Transaction> getAllTransactions() {
        return transactions;
    }

    public static void setCurrentUserContext(UserContext userContext) {
        LOGGER.log(Level.INFO, "Current user set to : " + userContext.getBankAssignedID());
        currentUserContext = userContext;
    }

    public static void loadExistingData() {
        users.addAll(genericFileReader(usersFile, UserContext.class));
        allAccounts.addAll(genericFileReader(accountsFile, Account.class));
        accountHolders.addAll(genericFileReader(accountsHoldersFile, AccountHolder.class));
        securityTokens.addAll(genericFileReader(credentialsFile, Credentials.class));
        transactions.addAll(genericFileReader(transactionsFile, Transaction.class));
    }

    public static void persistData() {
        genericFileWriter(usersFile, users);
        genericFileWriter(accountsHoldersFile, accountHolders);
        genericFileWriter(accountsFile, allAccounts);
        genericFileWriter(credentialsFile, securityTokens);
        genericFileWriter(transactionsFile, transactions);
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

    public static void genericFileWriter(String fileName, Set<?> genericSet) {
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
        LOGGER.log(Level.FINE, "Updated file : " + fileName);
    }

    public static boolean isSomethingWentWrong() {
        return somethingWentWrong;
    }

}
