package com.citybank;

import com.citybank.model.Account;
import com.citybank.model.AccountHolder;
import com.citybank.model.AccountHolderDTO;
import com.citybank.model.Credentials;

import java.util.HashSet;
import java.util.Set;

public class BankService {

    private AccountHolderDTO currentUserContext;
    private Set<Account> allAccounts = new HashSet<>();
    private Set<Credentials> securityTokens = new HashSet<>();
    private Set<AccountHolder> accountHolders = new HashSet<>();

    

}
