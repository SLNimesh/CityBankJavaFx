package com.citybank;

import com.citybank.model.Account;
import com.citybank.model.ServiceException;
import com.citybank.model.Transaction;
import com.citybank.model.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class TransactionService {

    private static Logger LOGGER = Logger.getLogger(TransactionService.class.getName());

    public static Transaction makeTransaction(String accNo, String desc, Double amount, TransactionType type) throws ServiceException {
        if (amount <= 0) {
            throw new ServiceException("Amount must always be greater than 0.");
        }
        Transaction newTransaction = new Transaction();
        Account account = BankService.findAccount(accNo);
        if (type.equals(TransactionType.WITHDRAWAL)) {
            if (amount <= account.getAvailBalance()) {
                newTransaction.setAccountBalance(account.getCurrentBalance() - amount);
            } else {
                throw new ServiceException("Not enough account balance. ACC.BAL = " + account.getAvailBalance() + " lkr");
            }
        } else if (type.equals(TransactionType.DEPOSIT)) {
            newTransaction.setAccountBalance(account.getCurrentBalance() + amount);
        } else {
            throw new ServiceException("Transaction type must be specified");
        }
        newTransaction.setAmount(amount);
        newTransaction.setDescription(desc);
        newTransaction.setAccountNo(accNo);
        newTransaction.setTransactionDate(LocalDateTime.now());
        newTransaction.setType(type);
        account.setCurrentBalance(newTransaction.getAccountBalance());
        return newTransaction;
    }
}
