package com.edwin.emsp.domain.service;

import com.edwin.emsp.domain.model.entity.Account;
import com.edwin.emsp.domain.repository.facade.AccountRepository;
import com.edwin.emsp.domain.repository.po.AccountPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountFactory {
    @Autowired
    AccountRepository accountRepository;

    public AccountPO createAccountPO(Account account) {
        AccountPO accountPO = new AccountPO();
        accountPO.setId(account.getId());
        accountPO.setStatus(account.getStatus());
        accountPO.setEmail(account.getEmail());
        accountPO.setCreateTime(account.getCreateTime());
        return accountPO;
    }

    public Account createAccount(AccountPO accountPO) {
        Account account = new Account();
        account.setId(accountPO.getId());
        account.setStatus(accountPO.getStatus());
        account.setEmail(accountPO.getEmail());
        account.setCreateTime(accountPO.getCreateTime());
        account.setUpdateTime(accountPO.getUpdateTime());
        return account;
    }

    public Account getAccount(AccountPO accountPO) {
         accountPO = accountRepository.findByEmail(accountPO.getEmail());
         return createAccount(accountPO);
    }
}
